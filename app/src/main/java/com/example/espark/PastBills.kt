package com.example.espark

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import android.widget.Toast
import com.example.espark.models.BillAdapter
import com.example.espark.models.BillData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

// Define a class for the PastBills activity that extends the AppCompatActivity class
class PastBills : AppCompatActivity() {

    // Declare variables
    private lateinit var listView: ListView
    private lateinit var database: FirebaseDatabase

    // Define what happens when the activity is created
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_past_bills)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        listView = findViewById(R.id.list_view_bills)
        database = FirebaseDatabase.getInstance()

        // Get a reference to the "Bills" node in the Firebase database
        val billsRef = database.reference.child("Bills")
        // Add a listener to the "Bills" node in the Firebase database
        billsRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val bills = mutableListOf<BillData>()
                for (billSnapshot in snapshot.children) {
                    val billId = billSnapshot.child("billId").value.toString()
                    val units = billSnapshot.child("consumedUnits").value.toString()
                    val year = billSnapshot.child("billYear").value.toString()
                    val month = billSnapshot.child("billMonth").value.toString()
                    val startDate = billSnapshot.child("startDate").value.toString()
                    val endDate = billSnapshot.child("endDate").value.toString()
                    val amount = billSnapshot.child("totAmount").value.toString()
                    val bill = BillData(billId,year, month, startDate,endDate,units, amount)
                    bills.add(bill)
                }
                val adapter = BillAdapter(this@PastBills, bills)
                listView.adapter = adapter
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@PastBills, "Failed to read data", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}

