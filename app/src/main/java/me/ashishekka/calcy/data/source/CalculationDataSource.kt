package me.ashishekka.calcy.data.source

import me.ashishekka.calcy.data.Calculation

interface CalculationDataSource {
    fun getCalculations(): List<Calculation>
    fun getCalculation(id: String): Calculation
    fun setCalculation(id: String, calculation: Calculation)
    fun addCalculation(calculation: Calculation)
}