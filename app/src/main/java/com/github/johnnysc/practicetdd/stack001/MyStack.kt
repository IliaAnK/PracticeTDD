package com.github.johnnysc.practicetdd.stack001

interface MyStack<T : Any> {
    companion object {
        const val EMPTY_QUEUE_SIZE = 0

        const val HAVE_NOT_BE_NEGATIVE = "maxCount have not be negative"
        const val MUST_BE_GREATER_THAN_ZERO = "maxCount must be greater than 0"
        const val STACK_IS_EMPTY = "Stack is empty"
        const val STACK_OVERFLOW_EXCEPTION = "Stack overflow exception, maximum is "
    }

    val maxCount: Int
    val stack: ArrayDeque<T>

    fun pop(): T
    fun push(item: T)

    class FIFO<T : Any> : MyStack<T> {
        constructor(maxCount: Int) {
            when {
                maxCount < EMPTY_QUEUE_SIZE -> throw IllegalArgumentException(HAVE_NOT_BE_NEGATIVE)
                maxCount == EMPTY_QUEUE_SIZE -> throw IllegalArgumentException(MUST_BE_GREATER_THAN_ZERO)
                else -> {
                    this.maxCount = maxCount
                    this.stack = ArrayDeque()
                }
            }
        }

        override val maxCount: Int
        override val stack: ArrayDeque<T>

        override fun pop(): T {
            if (stack.isEmpty()) {
                throw NoSuchElementException(STACK_IS_EMPTY)
            }

            return stack.removeFirst()
        }

        override fun push(item: T) {
            if (stack.size >= maxCount) {
                throw IllegalStateException(STACK_OVERFLOW_EXCEPTION + maxCount)
            }

            stack.addLast(item)
        }
    }

    class LIFO<T : Any> : MyStack<T> {
        constructor(maxCount: Int) {
            when {
                maxCount < EMPTY_QUEUE_SIZE -> throw IllegalArgumentException(HAVE_NOT_BE_NEGATIVE)
                maxCount == EMPTY_QUEUE_SIZE -> throw IllegalArgumentException(MUST_BE_GREATER_THAN_ZERO)
                else -> {
                    this.maxCount = maxCount
                    this.stack = ArrayDeque()
                }
            }
        }

        override val maxCount: Int
        override val stack: ArrayDeque<T>

        override fun pop(): T {
            if (stack.isEmpty()) {
                throw NoSuchElementException(STACK_IS_EMPTY)
            }

            return stack.removeLast()
        }

        override fun push(item: T) {
            if (stack.size >= maxCount) {
                throw IllegalStateException(STACK_OVERFLOW_EXCEPTION + maxCount)
            }

            stack.addLast(item)
        }
    }
}
