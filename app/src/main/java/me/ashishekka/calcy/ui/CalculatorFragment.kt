package me.ashishekka.calcy.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import me.ashishekka.calcy.databinding.FragmentCalculatorBinding

class CalculatorFragment : Fragment() {

    companion object {
        private const val TAG = "CalculatorFragment"
    }

    private var _binding: FragmentCalculatorBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

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
        binding.keyPad.setOnKeyEntered { onKeyEntered(it) }
    }

    private fun onKeyEntered(value: String) {
        Log.d(TAG, "Value entered: $value")
        binding.expressionText.apply {
            text = text.toString() + value
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}