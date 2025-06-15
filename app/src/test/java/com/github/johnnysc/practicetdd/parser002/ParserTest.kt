package com.github.johnnysc.practicetdd.parser002

import junit.framework.TestCase.assertEquals
import org.junit.Test

/**
 * @author Asatryan on 26.12.2022
 */
class ParserTest {

    @Test(expected = IllegalStateException::class)
    fun `test empty delimiter`() {
        Parser.Base(delimiter = "")
    }

    @Test
    fun `test empty`() {
        val parser = Parser.Base(delimiter = "\n")
        val actual = parser.parse(raw = "")
        val expected = emptyList<Any>()
        assertEquals(expected, actual)
    }

    @Test
    fun `test incorrect`() {
        val parser = Parser.Base(delimiter = "\n")
        val actual = parser.parse(raw = "\n")
        val expected = emptyList<Any>()
        assertEquals(expected, actual)
    }

    @Test
    fun `test byte`() {
        val parser = Parser.Base(delimiter = "\n")
        val actual = parser.parse(raw = "127")
        val byte: Byte = 127
        val expected = listOf<Any>(byte)
        assertEquals(expected, actual)
    }

    @Test
    fun `test short`() {
        val parser = Parser.Base(delimiter = "\n")
        val actual = parser.parse(raw = "128")
        val short: Short = 128
        val expected = listOf<Any>(short)
        assertEquals(expected, actual)
    }

    @Test
    fun `test int`() {
        val parser = Parser.Base(delimiter = "\n")
        val actual = parser.parse(raw = "32768")
        val int = 32768
        val expected = listOf<Any>(int)
        assertEquals(expected, actual)
    }

    @Test
    fun `test long`() {
        val parser = Parser.Base(delimiter = "\n")
        val actual = parser.parse(raw = "2147483648")
        val long = 2147483648
        val expected = listOf<Any>(long)
        assertEquals(expected, actual)
    }

    @Test
    fun `test float`() {
        val parser = Parser.Base(delimiter = "\n")
        val actual = parser.parse(raw = "9.223372036854775808")
        val float = 9.223372036854775808f
        val expected = listOf<Any>(float)
        assertEquals(expected, actual)
    }

    @Test
    fun `test double`() {
        val parser = Parser.Base(delimiter = "\n")
        val actual = parser.parse(raw = "540282346638528860000000000000000000000")
        val double = 540282346638528860000000000000000000000.0
        val expected = listOf<Any>(double)
        assertEquals(expected, actual)
    }

    @Test
    fun `test char`() {
        val parser = Parser.Base(delimiter = "\n")
        val actual = parser.parse(raw = "x")
        val char = 'x'
        val expected = listOf<Any>(char)
        assertEquals(expected, actual)
    }

    @Test
    fun `test boolean`() {
        val parser = Parser.Base(delimiter = "\n")
        val actual = parser.parse(raw = "true")
        val boolean = true
        val expected = listOf<Any>(boolean)
        assertEquals(expected, actual)
    }

    @Test
    fun `test string`() {
        val parser = Parser.Base(delimiter = "\n")
        val actual = parser.parse(raw = "here is some text")
        val string = "here is some text"
        val expected = listOf<Any>(string)
        assertEquals(expected, actual)
    }

    @Test
    fun `test null`() {
        val parser = Parser.Base(delimiter = "\n")
        val actual = parser.parse(raw = "null")
        val string = "null"
        val expected = listOf<Any>(string)
        assertEquals(expected, actual)
    }

    @Test
    fun `test complex`() {
        val parser = Parser.Base(delimiter = "\n")
        val actual =
            parser.parse(raw = "false\nd\n-121\n-32123\n-2123123123\n32123123123\n132123123123.1\nblah blah blah")
        val first = false
        val second = 'd'
        val third: Byte = -121
        val forth: Short = -32123
        val fifth: Int = -2123123123
        val sixth = 32123123123
        val seventh = 132123123123.1f
//        val eighth: Double = 51234923432123123123.55
        val ninth = "blah blah blah"
        val expected =
            listOf<Any>(first, second, third, forth, fifth, sixth, seventh, ninth)
        assertEquals(expected, actual)
    }

    @Test
    fun `test different delimiter`() {
        val parser = Parser.Base(delimiter = ";")
        val actual =
            parser.parse(raw = "false;d;-121;-32123;-2123123123;32123123123;132123123123.1f;blah blah blah")
        val first = false
        val second = 'd'
        val third: Byte = -121
        val forth: Short = -32123
        val fifth: Int = -2123123123
        val sixth = 32123123123
        val seventh = 132123123123.1f
//        val eighth: Double = 51234923432123123123.55
        val ninth = "blah blah blah"
        val expected =
            listOf<Any>(first, second, third, forth, fifth, sixth, seventh, ninth)
        assertEquals(expected, actual)
    }
}
