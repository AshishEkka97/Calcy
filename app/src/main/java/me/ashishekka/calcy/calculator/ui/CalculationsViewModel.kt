package me.ashishekka.calcy.calculator.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import me.ashishekka.calcy.calculator.data.Calculation
import me.ashishekka.calcy.calculator.data.source.CalculationsRepository
import me.ashishekka.calcy.utils.CalculationUtil.calculateExpression
import me.ashishekka.calcy.utils.CalculationUtil.getExpressionList
import me.ashishekka.calcy.utils.CalculationUtil.isOperator
import java.lang.Exception
import java.util.*

class CalculationsViewModel(
    private val calculationsRepository: CalculationsRepository
) : ViewModel() {

    companion object {
        private const val TAG = "CalculationsViewModel"
    }

    private val mutableExpression = MutableLiveData("")
    val expression: LiveData<String> get() = mutableExpression

    private val mutableResult = MutableLiveData("")
    val result: LiveData<String> get() = mutableResult

    private val mutableHistory: MutableLiveData<List<Calculation>> = MutableLiveData(emptyList())
    val history: LiveData<List<Calculation>> get() = mutableHistory

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

                value.isOperator() -> {
                    it.takeIf { it.isNotEmpty() && !(it.last().isOperator()) }?.let { expr ->
                        mutableExpression.postValue(expr.plus(value))
                    }
                }

                value == '=' -> {
                    it.takeIf { it.isNotEmpty() && !(it.last().isOperator()) }
                        ?.calculateAndSaveExpression()
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

    fun onClear() {
        mutableExpression.postValue("")
        mutableResult.postValue("")
    }

    private fun String.calculateAndSaveExpression() {
        val expressionList = this.getExpressionList()
        val result: Int
        try {
            result = expressionList.calculateExpression(operatorsByPrecedence)
            mutableResult.postValue(result.toString())
            saveToHistory(expressionList, result)
        } catch (ex: Exception) {
            mutableResult.postValue(ex.message)
        }
    }

    private fun saveToHistory(expression: List<String>, result: Int) {
        val calculation = Calculation(UUID.randomUUID().toString(), expression, result)
        calculationsRepository.addToHistory(calculation)
    }

    fun getCalculationHistory() {
        mutableHistory.postValue(calculationsRepository.getHistory())
    }

    fun getCalculation(id: String) {
        val calculation = calculationsRepository.getCalculation(id)
        mutableExpression.postValue(calculation.expression.joinToString(""))
        mutableResult.postValue(calculation.result.toString())
    }
}