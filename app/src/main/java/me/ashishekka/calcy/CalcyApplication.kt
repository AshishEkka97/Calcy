package me.ashishekka.calcy

import android.app.Application
import me.ashishekka.calcy.data.source.CalculationsRepository

class CalcyApplication : Application() {
    val calculationsRepository: CalculationsRepository
    get() = ServiceLocator.provideCalculationsRepository()
}