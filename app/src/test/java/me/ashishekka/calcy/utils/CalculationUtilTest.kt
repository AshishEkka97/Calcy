package me.ashishekka.calcy.utils

import com.google.common.truth.Truth.assertThat
import com.google.common.truth.Truth.assertWithMessage
import me.ashishekka.calcy.utils.CalculationUtil.calculateExpression
import me.ashishekka.calcy.utils.CalculationUtil.getExpressionList
import me.ashishekka.calcy.utils.CalculationUtil.isOperator
import org.junit.Test

class CalculationUtilTest {

    private val calculations = listOf(
        "50+20/10" to 7,
        "50/20+5" to 2,
        "25-2*10" to 5,
        "10/2-20" to -15,
        "10-2-3" to 5,
        "10/2/5" to 1,
        "10/2/4+1" to 1
    )

    @Test
    fun getExpressionList() {
        val result = "56+92-23/2".getExpressionList()
        assertThat(result).isEqualTo(listOf("56", "+", "92", "-", "23", "/", "2"))
    }

    @Test
    fun calculateExpression() {
        val precedence = arrayOf('*', '+', '/', '-')
        calculations.forEach {
            val (expr, result) = it
            assertWithMessage("Assert that $expr = $result")
                .that(expr.getExpressionList().calculateExpression(precedence))
                .isEqualTo(result)
        }
    }

    @Test
    fun isOperator() {
        val operators = arrayOf('*', '+', '/', '-')
        val nonOperators = arrayOf('1', '5')
        operators.forEach { assertThat(it.isOperator()).isTrue() }
        nonOperators.forEach { assertThat(it.isOperator()).isFalse() }
    }

    @Test
    fun testIsOperator() {
        val operators = arrayOf("*", "+", "/", "-")
        val nonOperators = arrayOf("1202", "500", "-90")
        operators.forEach { assertThat(it.isOperator()).isTrue() }
        nonOperators.forEach { assertThat(it.isOperator()).isFalse() }
    }
}