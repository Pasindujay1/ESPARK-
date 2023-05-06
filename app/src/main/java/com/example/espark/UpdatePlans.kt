package com.example.espark

import android.app.AlertDialog
import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.espark.fragments.OnDeleteClickListener
import com.example.espark.fragments.OnUpdateClickListener
import com.example.espark.fragments.adapters.AddItem
import com.example.espark.fragments.adapters.Plan
import com.example.espark.fragments.adapters.addItemAdapter
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.text.DecimalFormat


class UpdatePlans : AppCompatActivity(), OnDeleteClickListener, OnUpdateClickListener {
    lateinit var plans : Plan
    private lateinit var planName: String
    private lateinit var database : DatabaseReference
    val addItems = mutableListOf<AddItem>()
    lateinit var adapter : addItemAdapter
    var amount :Double= 0.0
    lateinit var edtTxtAmount: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_plans)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        planName = intent.getStringExtra("planName")!!
        val editTextPlanName :EditText = findViewById(R.id.editTextPlanName)
        edtTxtAmount = findViewById(R.id.edtTxtAmount)
        Toast.makeText(this , planName, Toast.LENGTH_SHORT ).show()


        database = FirebaseDatabase.getInstance().getReference("Plans")
        val userRef = database.child(planName)
        userRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                    val planName = dataSnapshot.child("planName").getValue(String::class.java)
                    val amount = dataSnapshot.child("amount").getValue(String::class.java)
                    val addItemsSnapshot = dataSnapshot.child("addItems")


                    for (addItemSnapshot in addItemsSnapshot.children) {
                        val itemName = addItemSnapshot.child("itemName").getValue(String::class.java)
                        val placeUsing = addItemSnapshot.child("placeUsing").getValue(String::class.java)
                        val watt = addItemSnapshot.child("watt").getValue(String::class.java)
                        val time = addItemSnapshot.child("time").getValue(String::class.java)
                        val minutes = addItemSnapshot.child("minutes").getValue(String::class.java)

                        val addItem = AddItem(itemName, placeUsing, watt?.toDouble(),time,minutes,false)
                        addItems.add(addItem)

                    }
                    plans = Plan(planName , amount , addItems)
                    editTextPlanName.setText(planName)
                    edtTxtAmount.setText(plans.amount)


            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w(ContentValues.TAG, "Failed to read value.", error.toException())
            }
        })
        adapter = addItemAdapter(addItems,this,this)
        val recyclerView: RecyclerView = findViewById(R.id.rvAddList)
        val txtProductName: EditText = findViewById(R.id.txtProductName)
        val txtPlaceUsing: EditText = findViewById(R.id.txtPlaceUsing)
        val txtWatt: EditText = findViewById(R.id.txtWatt)
        val spinnerHours : Spinner = findViewById(R.id.select_hours)
        val selectMinutes : Spinner = findViewById(R.id.select_minutes)
        val selectWatt : Spinner = findViewById(R.id.select_watt)



        val btn_save_plan: Button = findViewById(R.id.btn_save_plan)
        val btn_add_item: Button = findViewById(R.id.btn_add_item)

        recyclerView.adapter = adapter
        recyclerView.layoutManager =  LinearLayoutManager(this)
        btn_add_item.setOnClickListener{
//            Toast.makeText(context, editTextPlanName.getText().toString(), Toast.LENGTH_SHORT).show()
            val itemName = txtProductName.text.toString()
            val txtPlaceUsing = txtPlaceUsing.text.toString()
            var txtWatt = txtWatt.text.toString()
            var txtTime = spinnerHours.selectedItem.toString()
            val txtMinutes = selectMinutes.selectedItem.toString()
            val selectWatt = selectWatt.selectedItem.toString()

            var watt :Double

            if(itemName==""){
                Toast.makeText(this,"Enter a item name" ,Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if(txtPlaceUsing==""){
                Toast.makeText(this,"Enter a Using place" ,Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if(txtWatt==""){
                Toast.makeText(this,"Enter a Power Consumption" ,Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if(txtTime=="H" || txtMinutes == "Min"){
                Toast.makeText(this,"Enter a Time" ,Toast.LENGTH_SHORT).show()
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
                Toast.makeText(this,"Enter valid watt number" ,Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            var tempTime = txtTime.toDouble()
            tempTime += txtMinutes.toDouble()/60
            val decimalFormat = DecimalFormat("#.##")
            txtTime = decimalFormat.format(tempTime)
//            txtTime = String.format("%2f" ,tempTime )



            val addItem = AddItem(itemName , txtPlaceUsing ,txtWatt.toDouble() , txtTime , txtMinutes,false)
            addItems.add(addItem)
            adapter.notifyItemInserted(addItems.size-1)
            calcAmount()
        }
        btn_save_plan.setOnClickListener{


            database = FirebaseDatabase.getInstance().getReference("Plans")
            val planName = editTextPlanName.text.toString()
            if(planName==""){
                Toast.makeText(this,"Enter a Plan name" ,Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val plan = Plan(planName,amount.toString(),addItems)
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
            )
            database.child(planName).setValue(planMap).addOnSuccessListener {
                addItems.clear()
                adapter.notifyDataSetChanged()
                Toast.makeText(this,"Successfully Added" ,Toast.LENGTH_SHORT).show()
            }.addOnFailureListener{
                Toast.makeText(this,"Failed" ,Toast.LENGTH_SHORT).show()
            }
        }



    }

    override fun onDeleteButtonClicked(position: Int) {
        addItems.removeAt(position)
        adapter.notifyItemRemoved(position)

        for (i in position until addItems.size) {
            adapter.notifyItemChanged(i)
        }
        calcAmount()
    }

    override fun onUpdateButtonClicked(viewHolder: addItemAdapter.ViewHolder, position: Int) {
        val dialogBuilder = AlertDialog.Builder(this)
        val inflater = this.layoutInflater
        val dialogView = inflater.inflate(R.layout.fragment_update_item, null)

        dialogBuilder.setView(dialogView)
        val editTextItemName: EditText = dialogView.findViewById(R.id.txtProductName)
        val editTextPlaceUsing: EditText = dialogView.findViewById(R.id.txtPlaceUsing)
        val editTextWatt: EditText = dialogView.findViewById(R.id.txtWatt)
        val editTextTime: Spinner = dialogView.findViewById(R.id.select_hours)
        val select_minutes: Spinner = dialogView.findViewById(R.id.select_minutes)
        val selectWatt: Spinner = dialogView.findViewById(R.id.select_Watt)

        val item = addItems[position]
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
            dialog.dismiss()
        }
        dialogBuilder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.dismiss()
        }

        val dialog = dialogBuilder.create()
        dialog.show()
    }
    fun calcAmount(){
        var kwh :Double = 0.0;
        for (i in 0 until addItems.size) {
            kwh += ((addItems[i].watt?.toDouble() ?: 0.0) * (addItems[i].time?.toDouble() ?: 0.0)) / 1000

        }


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
        edtTxtAmount.text = amount.toString()

    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true

    }

}