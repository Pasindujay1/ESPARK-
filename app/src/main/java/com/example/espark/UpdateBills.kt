package com.example.espark

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import com.example.espark.models.BillData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class UpdateBills : AppCompatActivity() {
    private lateinit var database: FirebaseDatabase
    private lateinit var billId: String

    private var _binding: UpdateBills? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_bills)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Get the billId from the intent extras
        billId = intent.getStringExtra("billId")!!

        // Initialize Firebase database
        database = FirebaseDatabase.getInstance()

        // Fetch the bill data from Firebase Realtime Database
        val billsRef = database.reference.child("Bills").child(billId)
        billsRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                // Get the bill data from the snapshot and update the UI
                val year = snapshot.child("billYear").value.toString()
                val month = snapshot.child("billMonth").value.toString()
                val startDate = snapshot.child("startDate").value.toString()
                val endDate = snapshot.child("endDate").value.toString()
                val units = snapshot.child("consumedUnits").value.toString()
                val totAmount = snapshot.child("totAmount").value.toString()

                // Update the UI with the bill data
                val yearSpinner = findViewById<Spinner>(R.id.updateBillSpinner1)
                val yearArray = resources.getStringArray(R.array.bill_year)
                val yearIndex = yearArray.indexOf(year)
                yearSpinner.setSelection(yearIndex)

                val monthSpinner = findViewById<Spinner>(R.id.updateBillSpinner2)
                val monthArray = resources.getStringArray(R.array.bill_month)
                val monthIndex = monthArray.indexOf(month)
                monthSpinner.setSelection(monthIndex)

                findViewById<EditText>(R.id.updateBillStartDate).setText(startDate)
                findViewById<EditText>(R.id.updateBillEndDate).setText(endDate)
                findViewById<EditText>(R.id.updateConsumedUnits).setText(units)
                findViewById<EditText>(R.id.updateBillAmount).setText(totAmount)


                val updateButton = findViewById<Button>(R.id.updateBillAddBtn)
                val cancelButton = findViewById<Button>(R.id.updateBillCancelBtn)

                updateButton.setOnClickListener {
                    updateDataToFirebase()
                }

                cancelButton.setOnClickListener {
                    onBackPressed()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@UpdateBills, "Failed to read data", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun updateDataToFirebase(){
        //get updated values
        val yearSpinner = findViewById<Spinner>(R.id.updateBillSpinner1)
        val monthSpinner = findViewById<Spinner>(R.id.updateBillSpinner2)
        val startDateText = findViewById<EditText>(R.id.updateBillStartDate)
        val endDateText = findViewById<EditText>(R.id.updateBillEndDate)
        val unitEditText = findViewById<EditText>(R.id.updateConsumedUnits)
        val amountEditText = findViewById<EditText>(R.id.updateBillAmount)

        //convert to strings
        val year = yearSpinner.selectedItem.toString()
        val month = monthSpinner.selectedItem.toString()
        val startDate = startDateText.text.toString()
        val endDate = endDateText.text.toString()
        val consumedUnits = unitEditText.text.toString()
        val totAmount = amountEditText.text.toString()

        val firebaseRef = FirebaseDatabase.getInstance().getReference("Bills").child(billId)

        //add values to bill data class
        val bill = BillData(billId, year, month, startDate, endDate, consumedUnits, totAmount)

        //update database
        firebaseRef.setValue(bill)
            .addOnSuccessListener {
                Toast.makeText(this, "Data updated successfully", Toast.LENGTH_SHORT).show()
                finish() // Finish the activity after the data is updated
            }
            .addOnFailureListener {
                Toast.makeText(this, "Failed to update data", Toast.LENGTH_SHORT).show()
            }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}