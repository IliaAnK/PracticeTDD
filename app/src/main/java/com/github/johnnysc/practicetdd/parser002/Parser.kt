package com.github.johnnysc.practicetdd.parser002

interface Parser {

    fun parse(raw: String): List<Any>

    class Base : Parser {
        constructor(delimiter: String) {
            if (delimiter.isEmpty()) throw IllegalStateException("Delimiter cannot be empty")
            else this.delimiter = delimiter

            firstParser = TokenParser.BooleanParser()
            firstParser
                .setNext(TokenParser.ByteParser())
                .setNext(TokenParser.ShortParser())
                .setNext(TokenParser.IntParser())
                .setNext(TokenParser.LongParser())
                .setNext(TokenParser.FloatParser())
                .setNext(TokenParser.DoubleParser())
                .setNext(TokenParser.CharParser())
                .setNext(TokenParser.StringParser())
        }

        private val delimiter: String
        private val firstParser: TokenParser

        override fun parse(raw: String): List<Any> {
            if (raw.isEmpty()) return emptyList()

            return raw.split(delimiter)
                .filter { it.isNotEmpty() }
                .mapNotNull { token ->
                    firstParser.parseToken(token)
                }
        }
    }
}

interface TokenParser {
    fun setNext(parser: TokenParser): TokenParser
    fun parseToken(token: String): Any?

    abstract class AbstractParser : TokenParser {
        private var nextParser: TokenParser? = null

        override fun setNext(parser: TokenParser): TokenParser {
            this.nextParser = parser
            return parser
        }

        protected fun parseNext(token: String): Any? {
            return nextParser?.parseToken(token)
        }
    }

    class BooleanParser : AbstractParser() {
        override fun parseToken(token: String): Any? {
            return when {
                token.equals("true", ignoreCase = true) -> true
                token.equals("false", ignoreCase = true) -> false
                else -> parseNext(token)
            }
        }
    }

    class ByteParser : AbstractParser() {
        override fun parseToken(token: String): Any? = token.toByteOrNull() ?: parseNext(token)
    }

    class ShortParser : AbstractParser() {
        override fun parseToken(token: String): Any? = token.toShortOrNull() ?: parseNext(token)
    }

    class IntParser : AbstractParser() {
        override fun parseToken(token: String): Any? = token.toIntOrNull() ?: parseNext(token)

    }

    class LongParser : AbstractParser() {
        override fun parseToken(token: String): Any? = token.toLongOrNull() ?: parseNext(token)
    }

    class FloatParser : AbstractParser() {
        override fun parseToken(token: String): Any? {
            if (token.endsWith('f', ignoreCase = true)) {
                return token.toFloatOrNull() ?: parseNext(token)
            }

            if (token.contains('.') && !token.contains('e', ignoreCase = true)) {
                val floatValue = token.toFloatOrNull()
                if (floatValue != null && floatValue.isFinite()) {
                    return floatValue
                }
            }

            return parseNext(token)
        }
    }

    class DoubleParser : AbstractParser() {
        override fun parseToken(token: String): Any? {
            if (token.contains('.') || token.contains('e', ignoreCase = true)) {
                return token.toDoubleOrNull() ?: parseNext(token)
            }

            if (token.length > 19
                && token.toLongOrNull() == null
                && token.toFloatOrNull()?.isInfinite() == true
            ) {
                return token.toDoubleOrNull() ?: parseNext(token)
            }
            return parseNext(token)
        }
    }

    class CharParser : AbstractParser() {
        override fun parseToken(token: String): Any? {
            return if (token.length == 1) {
                token.first()
            } else {
                parseNext(token)
            }
        }
    }

    class StringParser : AbstractParser() {
        override fun parseToken(token: String): Any = token
    }
}
