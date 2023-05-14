package com.example.espark

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import com.example.espark.databinding.ActivityAddBillBinding
import com.example.espark.models.BillData
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import org.w3c.dom.Text

class AddBillActivity : AppCompatActivity() {

    private var _binding : ActivityAddBillBinding? = null
    private val binding get() = _binding!!

    private lateinit var database: FirebaseDatabase
    private lateinit var yearSpinner: Spinner
    private lateinit var monthSpinner: Spinner
    private lateinit var startDateText: EditText
    private lateinit var endDateText: EditText
    private lateinit var unitEditText: EditText
    private lateinit var amountEditText: EditText

    private lateinit var firebaseRef : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_bill)

        // Enable the back button
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        database = FirebaseDatabase.getInstance()

        //Get user input values
        yearSpinner = findViewById<Spinner>(R.id.billSpinner1)
        monthSpinner = findViewById<Spinner>(R.id.billSpinner2)
        startDateText = findViewById(R.id.billStartDate)
        endDateText = findViewById(R.id.billEndDate)
        unitEditText = findViewById(R.id.consumedUnits)
        amountEditText = findViewById(R.id.newBillAmount)

        val saveButton = findViewById<Button>(R.id.billAddBtn)
        saveButton.setOnClickListener {
            //call function to save data
            saveDataToFirebase()
            yearSpinner.setSelection(0)
            monthSpinner.setSelection(0)
            startDateText.setText("")
            endDateText.setText("")
            unitEditText.setText("")
            amountEditText.setText("")
        }

        val cancelButton = findViewById<Button>(R.id.billCancelBtn)
        cancelButton.setOnClickListener {
            yearSpinner.setSelection(0)
            monthSpinner.setSelection(0)
            startDateText.setText("")
            endDateText.setText("")
            unitEditText.setText("")
            amountEditText.setText("")
        }
    }

    private fun saveDataToFirebase() {

        val year = yearSpinner.selectedItem.toString()
        val month = monthSpinner.selectedItem.toString()
        val startDate = startDateText.text.toString()
        val endDate = endDateText.text.toString()
        val consumedUnits = unitEditText.text.toString()
        val totAmount = amountEditText.text.toString()

        val units = consumedUnits.toIntOrNull()
        var hasError = false

        if (startDate.isEmpty()) {
            startDateText.setError("Please enter a start date")
            hasError = true
        }
        if (endDate.isEmpty()) {
            endDateText.setError("Please enter an end date")
            hasError = true
        }
        if (consumedUnits.isEmpty()) {
            unitEditText.setError("Please enter consumed units")
            hasError = true
        }
        if (totAmount.isEmpty()) {
            amountEditText.setError("Please enter the total amount")
            hasError = true
        }
        if (units != null) {
            if (units < 0) {
                amountEditText.setError("Consumed units must be greater than zero")
            }
        }

        if(!hasError) {
            val firebaseRef = database.reference.child("Bills")

            val billId = firebaseRef.push().key!!
            val bill = BillData(billId, year, month, startDate, endDate, consumedUnits, totAmount)
            firebaseRef.child(billId).setValue(bill)
                .addOnSuccessListener {
                    Toast.makeText(this, "Data saved successfully", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Failed to save data", Toast.LENGTH_SHORT).show()
                }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}