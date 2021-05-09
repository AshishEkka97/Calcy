package me.ashishekka.calcy.utils

import androidx.fragment.app.Fragment
import me.ashishekka.calcy.CalcyApplication
import me.ashishekka.calcy.ViewModelFactory

fun Fragment.getViewModelFactory(): ViewModelFactory {
    val repository = (requireContext().applicationContext as CalcyApplication).calculationsRepository
    return ViewModelFactory(repository, this)
}