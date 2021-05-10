package me.ashishekka.calcy.calculator.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import me.ashishekka.calcy.databinding.FragmentHistoryBinding
import me.ashishekka.calcy.utils.getViewModelFactory

class HistoryFragment : Fragment() {
    companion object {
        private const val TAG = "HistoryFragment"
    }

    private var _binding: FragmentHistoryBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val viewModel by activityViewModels<CalculationsViewModel> { getViewModelFactory() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setUpRecyclerView()
        viewModel.getCalculationHistory()
        observeData()
    }

    private fun setUpRecyclerView() {
        binding.historyList.apply {
            adapter = CalculationAdapter(emptyList(), ::onCalculationClick)
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(DividerItemDecoration(context.applicationContext, RecyclerView.VERTICAL))
        }
    }

    private fun observeData() {
        viewModel.history.observe(viewLifecycleOwner, {
            (binding.historyList.adapter as CalculationAdapter).setCalculations(it)
        })
    }

    private fun onCalculationClick(id: String) {
        viewModel.getCalculation(id)
        findNavController().popBackStack()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}