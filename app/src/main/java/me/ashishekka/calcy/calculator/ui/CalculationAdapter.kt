package me.ashishekka.calcy.calculator.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import me.ashishekka.calcy.calculator.data.Calculation
import me.ashishekka.calcy.databinding.ItemCalculationBinding

class CalculationAdapter(
    private var calculations: List<Calculation>,
    private val onCalculationClick: ((String) -> Unit)? = null
) :
    RecyclerView.Adapter<CalculationAdapter.CalculationViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalculationViewHolder {
        val binding =
            ItemCalculationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CalculationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CalculationViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount() = calculations.size

    fun setCalculations(calculations: List<Calculation>) {
        this.calculations = calculations
        notifyDataSetChanged()
    }

    inner class CalculationViewHolder(private val binding: ItemCalculationBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            binding.textExpression.text = calculations[position].expression.joinToString("")
            binding.textResult.text = calculations[position].result.toString()
            binding.root.setOnClickListener {
                onCalculationClick?.invoke(calculations[position].id)
            }
        }
    }
}