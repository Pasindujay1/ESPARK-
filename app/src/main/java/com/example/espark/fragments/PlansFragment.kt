package com.example.espark.fragments


import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.espark.R
import com.example.espark.UpdatePlans
import com.example.espark.fragments.adapters.AddItem
import com.example.espark.fragments.adapters.Plan
import com.example.espark.fragments.adapters.viewPlansAdapter
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class PlansFragment : Fragment(), OnDeletePlanClickListener, OnUpdatePlanClickListener {
    // Declare variables
    lateinit var planList : MutableList<Plan>//delclare variables
//    (
//        Plan ("Plan 1" , "2500" )
//    )

    private lateinit var database : DatabaseReference//create database reference
    lateinit var adapter : viewPlansAdapter
    val addItems = mutableListOf<AddItem>()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        planList = mutableListOf()
        database = FirebaseDatabase.getInstance().getReference("Plans")

        // Read from the database
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                planList.clear()
                for (planSnapshot  in dataSnapshot.children) {

                    val planName = planSnapshot.child("planName").getValue(String::class.java)
                    val amount = planSnapshot.child("amount").getValue(String::class.java)
                    val addItemsSnapshot = planSnapshot.child("addItems")



                    for (addItemSnapshot in addItemsSnapshot.children) {
                        val itemName = addItemSnapshot.child("itemName").getValue(String::class.java)
                        val placeUsing = addItemSnapshot.child("placeUsing").getValue(String::class.java)
                        val watt = addItemSnapshot.child("watt").getValue(String::class.java)
                        val time = addItemSnapshot.child("time").getValue(String::class.java)
                        val minutes = addItemSnapshot.child("minutes").getValue(String::class.java)

                        val addItem = AddItem(itemName, placeUsing, watt?.toDouble(),time,minutes , false)
                        addItems.add(addItem)
                    }
                    val plan = Plan(planName , amount , addItems)

                    if (plan != null) {
                        planList.add(plan)

                    }
                    adapter.notifyDataSetChanged()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w(ContentValues.TAG, "Failed to read value.", error.toException())
            }
        })
        val view :View =  inflater.inflate(R.layout.fragment_plans, container, false)
        val recyclerView: RecyclerView = view.findViewById(R.id.rvPlansLists)
        adapter = viewPlansAdapter(planList, this ,this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager =  LinearLayoutManager(requireContext())

        return view

    }



    // Called when the update button is clicked
    override fun onUpdateButtonClicked(viewHolder: viewPlansAdapter.ViewHolder, position: Int) {
        val plan = planList[position]
        val intent = Intent(context, UpdatePlans::class.java)
        intent.putExtra("planName", plan.planName)
        context?.startActivity(intent)


    }

    override fun onDeleteButtonClicked(name: String) {

        val planRef = database.child(name)
        planRef.removeValue { error, ref ->
            if (error != null) {
                Toast.makeText(context,"Deletion failed" , Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context,"Successfully deleted" , Toast.LENGTH_SHORT).show()
                adapter.notifyDataSetChanged()
            }
        }

    }


}