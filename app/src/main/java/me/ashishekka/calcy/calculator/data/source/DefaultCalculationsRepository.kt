package me.ashishekka.calcy.calculator.data.source

import me.ashishekka.calcy.calculator.data.Calculation

class DefaultCalculationsRepository(private val localDataSource: CalculationDataSource) :
    CalculationsRepository {

    override fun addToHistory(calculation: Calculation) {
        localDataSource.addCalculation(calculation)
    }

    override fun getHistory(): List<Calculation> = localDataSource.getCalculations()

    override fun getCalculation(id: String): Calculation = localDataSource.getCalculation(id)

    override fun editCalculation(id: String, calculation: Calculation) {
        val oldCalculation = getCalculation(id)
        if (oldCalculation != calculation) {
            localDataSource.setCalculation(id, calculation)
        }
    }
}