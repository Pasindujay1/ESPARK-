package com.example.espark.ui.billHistory

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.example.espark.AddBillActivity
import com.example.espark.PastBills
import com.example.espark.R
import com.example.espark.databinding.FragmentBillHistoryBinding

class BillHistory : Fragment() {

    private var _binding: FragmentBillHistoryBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val billHistoryViewModel =
            ViewModelProvider(this).get(BillHistoryViewModel::class.java)

        _binding = FragmentBillHistoryBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //open AddBillActivity when clicked on the add bill button
        val buttonAddBill: Button = view.findViewById(R.id.btnAddBill)
        buttonAddBill.setOnClickListener {
            val intent = Intent(requireContext(), AddBillActivity::class.java)
            startActivity(intent)
        }

        //open PastBills activity when clicked on the past bills button
        val buttonViewBills: Button = view.findViewById(R.id.btnPastBills)
        buttonViewBills.setOnClickListener {
            val intent = Intent(requireContext(), PastBills::class.java)
            startActivity(intent)
        }

    }


}