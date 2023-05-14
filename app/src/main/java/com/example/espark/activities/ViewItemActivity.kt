package com.example.espark.activities

import android.content.Intent
import android.content.pm.LabeledIntent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.espark.Active
import com.example.espark.R
import com.example.espark.models.WarManagerModel
import com.example.espark.ui.warrantyManager.warrantyManager
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.database.FirebaseDatabase

class ViewItemActivity : AppCompatActivity() {

    private lateinit var wmProName: TextView
    private lateinit var wmBrandName: TextView
    private lateinit var wmPurchDate: TextInputEditText
    private lateinit var wmWarPeriod: TextInputEditText
    private lateinit var wmPrice: TextInputEditText
    private lateinit var wmLocation: TextInputEditText
    private lateinit var wmPhoneNo: TextInputEditText
    private lateinit var wmEmail: TextInputEditText
    private lateinit var wmBtnUpdate: Button
    private lateinit var wmBtnDelete: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_item)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        initView()
        setValuesToViews()

        wmBtnUpdate.setOnClickListener {
            openUpdateDialog(
                intent.getStringExtra("wmId").toString(),
                intent.getStringExtra("ProName").toString()
            )
        }

        wmBtnDelete.setOnClickListener {
            deleteRecord(
                intent.getStringExtra("wmId").toString()
            )
        }
    }

    private fun deleteRecord(
        id:String
    ){
        val dbRef = FirebaseDatabase.getInstance().getReference("Products_WM").child(id)
        val mTask = dbRef.removeValue()

        mTask.addOnSuccessListener {
            Toast.makeText(this, "Warranty details deleted", Toast.LENGTH_LONG).show()

            setContentView(R.layout.fragment_warranty_manager)
            supportActionBar?.setDisplayHomeAsUpEnabled(false)
            navigateToActiveFragment()
//            val intent = Intent(this, Active::class.java)
//            finish()
//            startActivity(intent)
        }.addOnFailureListener{error->
            Toast.makeText(this, "Deleting Error: ${error.message}", Toast.LENGTH_LONG).show()

        }
    }

    private fun navigateToActiveFragment() {

        val frameLayout = findViewById<FrameLayout>(R.id.frame_layout)
        val fragment = Active()
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(frameLayout.id, fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

    private fun initView(){

        wmProName = findViewById(R.id.tvProName)
        wmBrandName = findViewById(R.id.tvBrand)
        wmPurchDate = findViewById(R.id.tvPurchDate)
        wmWarPeriod = findViewById(R.id.tvWarPeriod)
        wmPrice = findViewById(R.id.tvPrice)
        wmLocation = findViewById(R.id.tvLocation)
        wmPhoneNo = findViewById(R.id.tvPhoneNo)
        wmEmail = findViewById(R.id.tvEmail)

        wmBtnUpdate = findViewById(R.id.btnUpdate)
        wmBtnDelete = findViewById(R.id.btnDelete)

    }

    private fun setValuesToViews(){

        wmProName.text = intent.getStringExtra("ProName")
        wmBrandName.text = intent.getStringExtra("BraName")
        wmPurchDate.setText(intent.getStringExtra("PurchDate"))
        wmWarPeriod.setText(intent.getStringExtra("WarPeriod"))
        wmPrice.setText(intent.getStringExtra("Price"))
        wmLocation.setText(intent.getStringExtra("PurchLocation"))
        wmPhoneNo.setText(intent.getStringExtra("PhoneNo"))
        wmEmail.setText(intent.getStringExtra("Email"))
    }

    private fun openUpdateDialog(
        wmId : String,
        ProName: String
    ){
        val mDialog = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val mDialogView = inflater.inflate(R.layout.update_dialog, null)

        mDialog.setView(mDialogView)

        val etProName = mDialogView.findViewById<TextInputEditText>(R.id.etProName)
        val etBrandName = mDialogView.findViewById<TextInputEditText>(R.id.etBrandName)
        val etPurchDate = mDialogView.findViewById<TextInputEditText>(R.id.etPurchDate)
        val etWarPeriod = mDialogView.findViewById<TextInputEditText>(R.id.etWarPeriod)
        val etPrice = mDialogView.findViewById<TextInputEditText>(R.id.etPrice)
        val etLocation = mDialogView.findViewById<TextInputEditText>(R.id.etLocation)
        val etPhoneNo = mDialogView.findViewById<TextInputEditText>(R.id.etPhoneNo)
        val etEmail = mDialogView.findViewById<TextInputEditText>(R.id.etEmail)
        val btnUpdateData = mDialogView.findViewById<Button>(R.id.btnUpdateData)


        etProName.setText(intent.getStringExtra("ProName").toString())
        etBrandName.setText(intent.getStringExtra("BraName").toString())
        etPurchDate.setText(intent.getStringExtra("PurchDate").toString())
        etWarPeriod.setText(intent.getStringExtra("WarPeriod").toString())
        etPrice.setText(intent.getStringExtra("Price").toString())
        etLocation.setText(intent.getStringExtra("PurchLocation").toString())
        etPhoneNo.setText(intent.getStringExtra("PhoneNo").toString())
        etEmail.setText(intent.getStringExtra("Email").toString())

        mDialog.setTitle("Updating $ProName Record")

        val alertDialog = mDialog.create()
        alertDialog.show()

        btnUpdateData.setOnClickListener {
            updateWarData(
                wmId,
                etProName.text.toString(),
                etBrandName.text.toString(),
                etPurchDate.text.toString(),
                etWarPeriod.text.toString(),
                etPrice.text.toString(),
                etLocation.text.toString(),
                etPhoneNo.text.toString(),
                etEmail.text.toString()
            )

            Toast.makeText(applicationContext, "Product Data Updated", Toast.LENGTH_LONG).show()

            //set the updated data to view Item
            wmProName.text = etProName.text.toString()
            wmBrandName.text = etBrandName.text.toString()
            wmPurchDate.setText(etPurchDate.text.toString())
            wmWarPeriod.setText(etWarPeriod.text.toString())
            wmPrice.setText(etPrice.text.toString())
            wmLocation.setText(etLocation.text.toString())
            wmPhoneNo.setText(etPhoneNo.text.toString())
            wmEmail.setText(etEmail.text.toString())

            alertDialog.dismiss()
        }

    }

    private fun updateWarData(
        id:String,
        pName:String,
        bName:String,
        pDate:String,
        wPeriod:String,
        price:String,
        location:String,
        phone:String,
        email:String
    ){
        val dbRef = FirebaseDatabase.getInstance().getReference("Products_WM").child(id)

        val wmInfo = WarManagerModel(pName, bName, pDate, wPeriod, price, location, phone, email)

        dbRef.setValue(wmInfo)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true

    }


}