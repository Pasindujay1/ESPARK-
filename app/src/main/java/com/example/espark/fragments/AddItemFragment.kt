package com.example.espark.fragments


import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast

import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.espark.R
import com.example.espark.databinding.FragmentAddItemBinding
import com.example.espark.fragments.adapters.AddItem
import com.example.espark.fragments.adapters.Plan
import com.example.espark.fragments.adapters.addItemAdapter
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.text.DecimalFormat


class AddItemFragment : Fragment(), OnDeleteClickListener, OnUpdateClickListener {

    private lateinit var database : DatabaseReference //Create database reference
    lateinit var txtAmount: TextView//Declare txt amount
    lateinit var editTextPlanName: TextView//Declate txt view PLan name
    var additems = mutableListOf(
        AddItem("Bulb" , "Bedroom" ,10.0, "20" , "30",false)
    )//Add items List
    lateinit var adapter : addItemAdapter//declare adapter
    var planName : String ? = null//declare plana name
    var amount :Double= 0.0// declare amount



//    val txtProductName: EditText =EditText(context)
//    val txtPlaceUsing: EditText =EditText(context)
//    val txtWatt: EditText = =EditText(context)
//    val txtTime: EditText =EditText(context)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        adapter = addItemAdapter(additems,this,this)//create adapter

        val view :View =  inflater.inflate(R.layout.fragment_add_item, container, false)
        val recyclerView: RecyclerView = view.findViewById(R.id.rvAddList)
//        val recyclerView: RecyclerView = binding.rvAddList
        editTextPlanName = view.findViewById(R.id.editTextPlanName)
        val txtProductName: EditText = view.findViewById(R.id.txtProductName)
        val txtPlaceUsing: EditText = view.findViewById(R.id.txtPlaceUsing)
        val txtWatt: EditText = view.findViewById(R.id.txtWatt)
        val spinnerHours : Spinner = view.findViewById(R.id.select_hours)
        val selectMinutes : Spinner = view.findViewById(R.id.select_minutes)
        val selectWatt : Spinner = view.findViewById(R.id.selectWatt)
        val btn_add_item: Button = view.findViewById(R.id.btn_add_item)
        val btn_create_plan: Button = view.findViewById(R.id.btn_create_plan)

        txtAmount = view.findViewById(R.id.txtAmount)
        if(planName != null){
            editTextPlanName.setText(planName)
        }

        var kwh = calcKiloWatt();//call calc amount function

        txtAmount.text = calcAmount(kwh).toString()
        recyclerView.adapter = adapter
        recyclerView.layoutManager =  LinearLayoutManager(requireContext())

        btn_add_item.setOnClickListener{//set listener for add item button

              val itemName = txtProductName.text.toString()
              val txtPlaceUsing = txtPlaceUsing.text.toString()
              var txtWatt = txtWatt.text.toString()
              var txtTime = spinnerHours.selectedItem.toString()
              val txtMinutes = selectMinutes.selectedItem.toString()
              val selectWatt = selectWatt.selectedItem.toString()

              var watt :Double

              //validate inputs
            if(itemName==""){
                Toast.makeText(context,"Enter a item name" ,Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if(txtPlaceUsing==""){
                Toast.makeText(context,"Enter a Using place" ,Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if(txtWatt==""){
                Toast.makeText(context,"Enter a Power Consumption" ,Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if(txtTime=="H" || txtMinutes == "Min"){
                Toast.makeText(context,"Enter a Time" ,Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if(selectWatt =="KW" ){
                var temp  = txtWatt.toDouble()
                temp = temp * 1000;
                txtWatt = temp.toString()

            }
            try {
                watt = txtWatt.toDouble()
            }catch (e:Exception ){
                Toast.makeText(context,"Enter valid watt number" ,Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }



            val addItem = AddItem(itemName , txtPlaceUsing ,watt , txtTime , txtMinutes,false)
            additems.add(addItem)
            adapter.notifyItemInserted(additems.size-1)
            var kwh = calcKiloWatt()


            txtAmount.text = calcAmount(kwh).toString()

        }
        btn_create_plan.setOnClickListener{//set onclick listener for for create plans button


            database = FirebaseDatabase.getInstance().getReference("Plans")//get database reference
            val planName = editTextPlanName.text.toString()
            if(planName==""){//validate plan name
                Toast.makeText(context,"Enter a Plan name" ,Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val plan = Plan(planName,amount.toString(),additems)//create a plan
            val planMap = mapOf(
                "planName" to plan.planName,
                "amount" to plan.amount,
                "addItems" to plan.additems.map {
                    mapOf(
                        "itemName" to it.itemName,
                        "placeUsing" to it.placeUsing,
                        "watt" to it.watt.toString(),
                        "time" to it.time,
                        "minutes" to it.minutes

                    )
                }
            )//create planMap

            database.child(planName).setValue(planMap).addOnSuccessListener {//save in the plan table in the database
                additems.clear()//clear add items list
                adapter.notifyDataSetChanged()//refresh adapter
                Toast.makeText(context,"Successfully Added" ,Toast.LENGTH_SHORT).show()//show success message
            }.addOnFailureListener{
                Toast.makeText(context,"Failed" ,Toast.LENGTH_SHORT).show()//show fail message

            }
        }

        return view
    }

    //Handle item delete button click
    override fun onDeleteButtonClicked(position: Int) {
        additems.removeAt(position)
        adapter.notifyItemRemoved(position)

        for (i in position until additems.size) {
            adapter.notifyItemChanged(i)
        }
        var kwh = calcKiloWatt()
        txtAmount.text = calcAmount(kwh).toString()
    }
    override fun onUpdateButtonClicked(viewHolder: addItemAdapter.ViewHolder, position: Int) {
        val dialogBuilder = AlertDialog.Builder(requireContext())
        val inflater = this.layoutInflater
        val dialogView = inflater.inflate(R.layout.fragment_update_item, null)

        dialogBuilder.setView(dialogView)
        val editTextItemName: EditText = dialogView.findViewById(R.id.txtProductName)
        val editTextPlaceUsing: EditText = dialogView.findViewById(R.id.txtPlaceUsing)
        val editTextWatt: EditText = dialogView.findViewById(R.id.txtWatt)
        val editTextTime: Spinner = dialogView.findViewById(R.id.select_hours)
        val select_minutes: Spinner = dialogView.findViewById(R.id.select_minutes)
        val selectWatt: Spinner = dialogView.findViewById(R.id.select_Watt)

        val item = additems[position]
        editTextItemName.setText(item.itemName)
        editTextPlaceUsing.setText(item.placeUsing)
        editTextWatt.setText(item.watt.toString())

        item.time?.let { editTextTime.setSelection(it.toInt()+1) }
        item.minutes?.let { select_minutes.setSelection(it.toInt()+1) }

        dialogBuilder.setTitle("Update Item")
        dialogBuilder.setPositiveButton("Update") { dialog, _ ->
            item.itemName = editTextItemName.text.toString()
            item.placeUsing = editTextPlaceUsing.text.toString()
            item.watt = editTextWatt.text.toString().toDouble()
            item.time = editTextTime.selectedItem.toString()
            item.minutes = select_minutes.selectedItem.toString()

            adapter.notifyItemChanged(position)
            var kwh = calcKiloWatt();
            txtAmount.text = calcAmount(kwh).toString()
            dialog.dismiss()
        }
        dialogBuilder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.dismiss()
        }

        val dialog = dialogBuilder.create()
        dialog.show()


    }
    //calculate Bill amount
    fun calcAmount(kwh :Double) : Double{

        var amountcal :Double = 0.0
        if(kwh <= 60){
            if(kwh >30 ){
                amountcal = 37.0 * (kwh-30) + 30.0 * 30 + 550
            }else{
                amountcal =  30.0 * kwh + 400
            }
        }else{
            if(kwh >180 ){
                amountcal = 75.0 * (kwh-180) + 50.0 * (180-90) +   42.0 * 90 + 2000.0
            }else if(kwh >90 ){
                amountcal = 50.0 * (kwh-90) +   42.0 * 90 + 1500.0
            }
            else{
                amountcal = 42.0 * kwh + 650.0
            }
        }
        amount = amountcal

        return amount

    }

    //calculate kilo watt
    fun calcKiloWatt():Double{
        var kwh :Double = 0.0;
        for (i in 0 until additems.size) {
            kwh += ((additems[i].watt ?: 0.0) * (additems[i].time?.toDouble() ?: 0.0)*30) / 1000
        }
        return kwh
    }



}