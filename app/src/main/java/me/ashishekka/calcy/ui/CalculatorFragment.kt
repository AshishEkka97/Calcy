package me.ashishekka.calcy.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import me.ashishekka.calcy.CalculationsViewModel
import me.ashishekka.calcy.R
import me.ashishekka.calcy.ViewModelFactory
import me.ashishekka.calcy.databinding.FragmentCalculatorBinding
import me.ashishekka.calcy.utils.getViewModelFactory

class CalculatorFragment : Fragment() {

    companion object {
        private const val TAG = "CalculatorFragment"
    }

    private var _binding: FragmentCalculatorBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val viewModel by activityViewModels<CalculationsViewModel> { getViewModelFactory() }

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
        binding.keyPad.apply {
            setOnKeyEntered { viewModel.onKeyEntered(it) }
            setOnClear { viewModel.onClear() }
        }
        binding.buttonHistory.setOnClickListener {
            findNavController().navigate(R.id.action_fragment_calculator_dest_to_fragment_history_dest)
        }
        observeData()
    }

    private fun observeData() {
        viewModel.expression.observe(viewLifecycleOwner, {
            binding.textExpression.text = it.toString()
        })
        viewModel.result.observe(viewLifecycleOwner, {
            binding.textResult.text = it.toString()
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}