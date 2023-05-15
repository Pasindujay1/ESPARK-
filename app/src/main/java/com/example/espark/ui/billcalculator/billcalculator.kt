package com.example.espark.ui.billcalculator

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.espark.R

class billcalculator : Fragment() {

    private lateinit var viewModel: BillcalculatorViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_billcalculator, container, false)
        viewModel = ViewModelProvider(this).get(BillcalculatorViewModel::class.java)

        val calculateButton = rootView.findViewById<Button>(R.id.calBtn)
        calculateButton.setOnClickListener {

            //get the user entered values
            val editText1 = rootView.findViewById<EditText>(R.id.calEdtText1)
            val editText2 = rootView.findViewById<EditText>(R.id.calEdtText2)
            val spinnerBillType = rootView.findViewById<Spinner>(R.id.cusType)

            val lastReading = editText1.text.toString().toInt()
            val currentReading = editText2.text.toString().toInt()
            val selectedOption = spinnerBillType.selectedItem.toString()

            //calling functions
            val totAmount = viewModel.calculateSum(selectedOption,lastReading,currentReading)
            val powerUnits = viewModel.powerUnits(lastReading,currentReading)

            //display the output based on the calculations
            if(totAmount > 0.00) {
                val alertDialog = AlertDialog.Builder(requireContext())
                    .setTitle("Electricity Bill Calculation")
                    .setMessage("Consumed Units: $powerUnits \n" +
                            "Total Amount for the Usage: $totAmount")
                    .setPositiveButton("OK", null)
                    .create()

                alertDialog.show()
            } else {
                val alertDialog = AlertDialog.Builder(requireContext())
                    .setTitle("Input Error!!!")
                    .setMessage("Please enter the correct customer type and meter readings")
                    .setPositiveButton("OK", null)
                    .create()
                alertDialog.show()
            }
        }

        return rootView
    }
}