package com.example.espark.ui.powerconsumptioncalc

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.espark.R
import com.example.espark.fragments.adapters.VPAdapter
import com.example.espark.fragments.AddItemFragment
import com.example.espark.fragments.PlansFragment
import androidx.viewpager.widget.ViewPager

import com.google.android.material.tabs.TabLayout

class PowerConsumptionCalc : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_power_consumption_calc, container, false)

        val adapter = VPAdapter(childFragmentManager)
        adapter.addFragment(AddItemFragment(),"Add Item")
        adapter.addFragment(PlansFragment(),"Plans")
        val viewPager = view.findViewById<ViewPager>(R.id.viewPager)
        val tabLayout = view.findViewById<TabLayout>(R.id.tab_Layout)

        viewPager.adapter  = adapter
        tabLayout.setupWithViewPager(viewPager)
        tabLayout.getTabAt(0)!!.setIcon(R.drawable.ic_baseline_add_24)
        tabLayout.getTabAt(1)!!.setIcon(R.drawable.ic_baseline_next_plan_24)
        return view
    }


}


