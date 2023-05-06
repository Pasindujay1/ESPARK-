package com.example.espark.ui.powerconsumptioncalc

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.espark.R
import com.example.espark.databinding.FragmentBillcalculatorBinding
import com.example.espark.databinding.FragmentPowerConsumptionCalcBinding
import com.example.espark.ui.billcalculator.BillcalculatorViewModel

class PowerConsumptionCalc : Fragment() {

    private var _binding: FragmentPowerConsumptionCalcBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val powerConsumptionCalcViewModel =
            ViewModelProvider(this).get(PowerConsumptionCalcViewModel::class.java)

        _binding = FragmentPowerConsumptionCalcBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.powerCalculator
        powerConsumptionCalcViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}