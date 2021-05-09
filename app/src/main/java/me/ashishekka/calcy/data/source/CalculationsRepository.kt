package me.ashishekka.calcy.data.source

import me.ashishekka.calcy.data.Calculation

interface CalculationsRepository {
    fun addToHistory(calculation: Calculation)
    fun getHistory(): List<Calculation>
    fun getCalculation(id: String): Calculation
    fun editCalculation(id: String, calculation: Calculation)
}