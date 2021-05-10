package me.ashishekka.calcy

import androidx.annotation.VisibleForTesting
import me.ashishekka.calcy.calculator.data.source.LRUCacheDataSource
import me.ashishekka.calcy.calculator.data.source.CalculationsRepository
import me.ashishekka.calcy.calculator.data.source.DefaultCalculationsRepository

object ServiceLocator {

    private val lock = Any()

    @Volatile
    var calculationsRepository: CalculationsRepository? = null
        @VisibleForTesting set

    fun provideCalculationsRepository(): CalculationsRepository {
        synchronized(this) {
            return calculationsRepository ?: calculationsRepository
            ?: createCalculationsRepository()
        }
    }

    private fun createCalculationsRepository(): CalculationsRepository {
        val repo = DefaultCalculationsRepository(createLocalDataSource())
        calculationsRepository = repo
        return repo
    }

    private fun createLocalDataSource() = LRUCacheDataSource(10)
}