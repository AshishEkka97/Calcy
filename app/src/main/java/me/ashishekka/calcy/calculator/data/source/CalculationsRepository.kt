package me.ashishekka.calcy.calculator.data.source

import me.ashishekka.calcy.calculator.data.Calculation

interface CalculationsRepository {
    fun addToHistory(calculation: Calculation)
    fun getHistory(): List<Calculation>
    fun getCalculation(id: String): Calculation
    fun editCalculation(id: String, calculation: Calculation)
}