package me.ashishekka.calcy.utils

import java.util.ArrayDeque
import kotlin.NumberFormatException

object CalculationUtil {

    private val operatorsByPrecedence = arrayOf('*', '+', '/', '-')

    /**
     * Converts a given expression string into List of string, where each element denotes
     * a proper operand (Digits) or an operator [*, +, /, -].
     */
    fun String.getExpressionList(): List<String> {
        val list = mutableListOf<String>()
        var lastIndex = 0
        this.forEachIndexed { index, c ->
            if (c.isOperator()) {
                list.add(this.substring(lastIndex, index))
                list.add(c.toString())
                lastIndex = index + 1
            }

            if (index == this.length - 1) {
                list.add(this.substring(lastIndex, this.length))
            }
        }

        return list
    }

    /**
     * Calculates the provided expression using the provided operator precedence array.
     * @param operatorPrecedence - An [Char] array indicating operator precedence.
     * @return [Int] denoting the result of expression.
     * @throws [ArithmeticException] in case of an impossible arithmetic expression is provided.
     */
    fun List<String>.calculateExpression(operatorPrecedence: Array<Char>): Int {
        val values = ArrayDeque<Int>()
        val operator = ArrayDeque<Char>()

        for (token in this) {
            if (token.isOperator()) {
                while (operator.isNotEmpty() && operatorPrecedence.hasPrecedence(
                        token[0],
                        operator.peek()
                    )
                ) {
                    values.push(
                        operate(
                            values.pop(),
                            values.pop(),
                            operator.pop()
                        )
                    )
                }
                operator.push(token[0])
            } else {
                try {
                    values.push(token.toInt())
                } catch (ex: NumberFormatException) {
                    throw NumberFormatException("Invalid Input")
                }
            }
        }

        while (!operator.isEmpty()) {
            values.push(
                operate(
                    values.pop(),
                    values.pop(),
                    operator.pop()
                )
            )
        }
        return values.pop()
    }

    /**
     * Checks if [operator1] has higher precedence than [operator2].
     *
     * @param operator1 - the first operator
     * @param operator2 - the second operator
     */
    private fun Array<Char>.hasPrecedence(operator1: Char, operator2: Char) =
        this.indexOf(operator1) >= this.indexOf(operator2)


    /**
     * Operates the provided arithmetic (not unary) operator, on the two operands provided.
     * @param operand1 - the first operand
     * @param operand2 - the second operand
     * @param operator - the operator
     */
    private fun operate(operand1: Int, operand2: Int, operator: Char) = when (operator) {
        '*' -> Math.multiplyExact(operand2, operand1)
        '+' -> Math.addExact(operand2, operand1)
        '-' -> Math.subtractExact(operand2, operand1)
        '/' -> operand2 / operand1
        else -> 0
    }

    /**
     * [Char] extension function to check if the character is an operator
     */
    fun Char.isOperator() = this in operatorsByPrecedence

    /**
     * [String] extension function to check if the string is an operator
     */
    fun String.isOperator(): Boolean {
        return this.length == 1 && this[0] in operatorsByPrecedence
    }
}