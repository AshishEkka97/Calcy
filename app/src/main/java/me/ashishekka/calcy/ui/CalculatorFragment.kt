package me.ashishekka.calcy.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import me.ashishekka.calcy.CalculationsViewModel
import me.ashishekka.calcy.ViewModelFactory
import me.ashishekka.calcy.databinding.FragmentCalculatorBinding

class CalculatorFragment : Fragment() {

    companion object {
        private const val TAG = "CalculatorFragment"
    }

    private var _binding: FragmentCalculatorBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val viewModel by viewModels<CalculationsViewModel> { ViewModelFactory(this) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCalculatorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.keyPad.setOnKeyEntered { viewModel.onKeyEntered(it) }
        observeData()
    }

    private fun observeData() {
        viewModel.expression.observe(
            viewLifecycleOwner,
            { binding.expressionText.text = it.toString() }
        )
        viewModel.result.observe(viewLifecycleOwner, {
            binding.resultText.text = it.toString()
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}