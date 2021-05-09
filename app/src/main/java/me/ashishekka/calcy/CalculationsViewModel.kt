package me.ashishekka.calcy

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.lang.Exception
import java.util.*

class CalculationsViewModel : ViewModel() {

    companion object {
        private const val TAG = "CalculationsViewModel"
    }

    private val mutableExpression = MutableLiveData("")
    val expression: LiveData<String> get() = mutableExpression

    private val mutableResult = MutableLiveData("")
    val result: LiveData<String> get() = mutableResult

    private val operatorsByPrecedence = arrayOf('*', '+', '/', '-')

    /**
     * To be invoked when user presses a key.
     * @param value - The [Char] user has pressed.
     */
    fun onKeyEntered(value: Char) {
        mutableExpression.value?.let {
            when {
                value.isDigit() -> {
                    mutableExpression.postValue(it.plus(value))
                }

                isOperator(value) -> {
                    it.takeIf { it.isNotEmpty() }?.let { expr ->
                        mutableExpression.postValue(expr.plus(value))
                    }
                }

                value == '=' -> {
                    it.takeIf {
                        it.isNotEmpty() && !isOperator(it.last())
                    }?.calculateAndSaveExpression()
                }

                value == 'C' -> {
                    it.takeIf { it.isNotEmpty() }?.let { expr ->
                        mutableExpression.postValue(expr.substring(0, expr.length - 1))
                    }
                }

                else -> Unit
            }
        }
    }

    private fun isOperator(value: Char) = value in operatorsByPrecedence

    private fun String.isOperator(): Boolean {
        return this.length == 1 && this[0] in operatorsByPrecedence
    }

    private fun String.calculateAndSaveExpression() {
        val array = this.getExpressionList()
        val result = try {
            array.calculateExpression(operatorsByPrecedence)
        } catch (ex: Exception) {
            mutableResult.postValue(ex.message)
        }
        mutableResult.postValue(result.toString())
    }

    private fun String.getExpressionList(): List<String> {
        val list = mutableListOf<String>()
        var lastIndex = 0
        this.forEachIndexed { index, c ->
            if (isOperator(c)) {
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
     */
    private fun List<String>.calculateExpression(operatorPrecedence: Array<Char>): Int {
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
                values.push(token.toInt())
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

    private fun operate(operand1: Int, operand2: Int, operator: Char) = when (operator) {
        '*' -> operand2 * operand1
        '+' -> operand2 + operand1
        '-' -> operand2 - operand1
        '/' -> {
            try {
                operand2 / operand1
            } catch (ex: ArithmeticException) {
                throw IllegalArgumentException("Can't divide by zero")
            }
        }
        else -> 0
    }
}