package me.ashishekka.calcy.ui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.children
import me.ashishekka.calcy.databinding.LayoutKeypadBinding

class KeyPadView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : ConstraintLayout(context, attributeSet, defStyleAttr, defStyleRes) {

    private lateinit var binding: LayoutKeypadBinding

    init {
        initViews()
    }

    private var onKeyEntered: ((Char) -> Unit)? = null

    private fun initViews() {
        val layoutInflater = LayoutInflater.from(context)
        binding = LayoutKeypadBinding.inflate(layoutInflater, this)
        binding.apply {
            row1.addClickListenersOnButtons { onKeyEntered?.invoke((it as Button).text[0]) }
            row2.addClickListenersOnButtons { onKeyEntered?.invoke((it as Button).text[0]) }
            row3.addClickListenersOnButtons { onKeyEntered?.invoke((it as Button).text[0]) }
            row4.addClickListenersOnButtons { onKeyEntered?.invoke((it as Button).text[0]) }
        }
    }

    fun setOnKeyEntered(lambda: (Char) -> Unit) {
        onKeyEntered = lambda
    }
}

fun LinearLayout.addClickListenersOnButtons(listener: View.OnClickListener) {
    this.children.filter { it is Button && it.text.length == 1 }.forEach { button ->
        button.setOnClickListener(listener)
    }
}