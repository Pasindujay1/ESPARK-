package com.example.espark.activities

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import com.example.espark.R
import com.example.espark.databinding.ActivityAddProductBinding
import com.example.espark.models.WarManagerModel
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.io.ByteArrayOutputStream
import java.lang.Exception
import android.util.Base64
import java.io.FileNotFoundException
import java.io.IOException

//Second Commit
class AddProduct : AppCompatActivity() {

    var sImage:String? =""
    private lateinit var binding:ActivityAddProductBinding
    private lateinit var wmProName : TextInputEditText
    private lateinit var wmBraName : TextInputEditText
    private lateinit var wmPurchDate : TextInputEditText
    private lateinit var wmWarPeriod : TextInputEditText
    private lateinit var wmPrice : TextInputEditText
    private lateinit var wmPurchLocation : TextInputEditText
    private lateinit var wmPhoneNo : TextInputEditText
    private lateinit var wmEmail : TextInputEditText
    private lateinit var wmOther : TextInputEditText
    //    private lateinit var wmProImage : Button //upload image
    private lateinit var wmSubmit : Button
//    private lateinit var wmReset : Button

    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddProductBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Initialize the views here
        wmProName = findViewById(R.id.edtProduct)
        wmBraName = findViewById(R.id.edtBrand)
        wmPurchDate = findViewById(R.id.edtPurchasedDate)
        wmWarPeriod = findViewById(R.id.edtWarrantyPeriod)
        wmPrice = findViewById(R.id.edtPrice)
        wmPurchLocation = findViewById(R.id.edtLocation)
        wmPhoneNo = findViewById(R.id.edtPhone)
        wmEmail = findViewById(R.id.edtEmail)
        wmOther = findViewById(R.id.edtOther)
//        wmProImage = view.findViewById(R.id.btnGallery)
        wmSubmit = findViewById(R.id.btnSubmit)
//        wmReset = view.findViewById(R.id.btnReset)

        // Access the Firebase database reference here
        dbRef = FirebaseDatabase.getInstance().getReference("Products_WM")

        wmSubmit.setOnClickListener {
            saveWarrantyData()
        }
    }





    private fun saveWarrantyData(){
        //getting values
        val ProName = wmProName.text.toString()
        val BraName = wmBraName.text.toString()
        val PurchDate = wmPurchDate.text.toString()
        val WarPeriod = wmWarPeriod.text.toString()
        val Price = wmPrice.text.toString()
        val PurchLocation = wmPurchLocation.text.toString()
        val PhoneNo = wmPhoneNo.text.toString()
        val Email = wmEmail.text.toString()
        val Other = wmOther.text.toString()

        if(ProName.isEmpty()){
            wmProName.error = "Please enter Product Name"
        }
        if(BraName.isEmpty()){
            wmBraName.error = "Please enter Brand Name"
        }
        if(PurchDate.isEmpty()){
            wmPurchDate.error = "Please enter Purchased date"
        }
        if(WarPeriod.isEmpty()){
            wmWarPeriod.error = "Please enter Warranty period"
        }
        if(Price.isEmpty()){
            wmPrice.error = "Please enter Product Price"
        }

        val wmId = dbRef.push().key!!

        val Product = WarManagerModel(wmId, ProName, BraName, PurchDate, WarPeriod, Price, PurchLocation, PhoneNo, Email, Other)

        dbRef.child(wmId).setValue(Product)
            .addOnCompleteListener {
                Toast.makeText(this, "Data inserted successfully", Toast.LENGTH_LONG).show()

                wmProName.text?.clear()
                wmBraName.text?.clear()
                wmPurchDate.text?.clear()
                wmWarPeriod.text?.clear()
                wmPrice.text?.clear()
                wmPurchLocation.text?.clear()
                wmPhoneNo.text?.clear()
                wmEmail.text?.clear()
                wmOther.text?.clear()


            }.addOnFailureListener {err->
                Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()

            }


    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true

    }

    fun insert_Image(view: View) {
        val myfileintent = Intent(Intent.ACTION_GET_CONTENT)
        myfileintent.setType("image/*")
        imagePickerLauncher.launch(myfileintent)

    }

//    private val ActivityResultLauncher = registerForActivityResult<Intent, ActivityResult>(
//        ActivityResultContracts.StartActivityForResult()
//
//    ){result:ActivityResult->
//        if(result.resultCode== RESULT_OK){
//            val uri = result.data!!.data
//            try {
//                val inputStream = contentResolver.openInputStream(uri!!)
//                val myBitmap = BitmapFactory.decodeStream(inputStream)
//
//                val stream = ByteArrayOutputStream()
//                myBitmap.compress(Bitmap.CompressFormat.PNG, 100,stream)
//                val bytes = stream.toByteArray()
//                sImage = Base64.encodeToString(bytes, Base64.DEFAULT)
//                binding.imgProduct.setImageBitmap(myBitmap)
//                inputStream!!.close()
//
//            }catch (ex: Exception){
//                Toast.makeText(this, ex.message.toString(), Toast.LENGTH_LONG).show()
//            }
//        }
//
//    }

    private val imagePickerLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val uri = result.data?.data
            try {
                val inputStream = uri?.let { contentResolver.openInputStream(it) }
                val myBitmap = BitmapFactory.decodeStream(inputStream)
                val stream = ByteArrayOutputStream()
                myBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
                val bytes = stream.toByteArray()
                sImage = Base64.encodeToString(bytes, Base64.DEFAULT)
                binding.imgProduct.setImageBitmap(myBitmap)
                inputStream?.close()
            } catch (ex: FileNotFoundException) {
                Toast.makeText(this, "File not found", Toast.LENGTH_LONG).show()
            } catch (ex: IOException) {
                Toast.makeText(this, "Error reading file", Toast.LENGTH_LONG).show()
            } catch (ex: SecurityException) {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_LONG).show()
            }
        }
    }



//    private fun uriToImageFile(uri: Uri): File? {
//        val file = File(requireContext().cacheDir, "tempImage")
//        try {
//            val inputStream = requireContext().contentResolver.openInputStream(uri)
//            inputStream?.let {
//                val outputStream = FileOutputStream(file)
//                val buffer = ByteArray(1024)
//                var bytesRead: Int
//                while (it.read(buffer).also { bytesRead = it } != -1) {
//                    outputStream.write(buffer, 0, bytesRead)
//                }
//                outputStream.close()
//                inputStream.close()
//                return file
//            }
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//        return null
//    }

//    private fun uploadImageToStorage(file: File) {
//        // Get a reference to the Firebase Storage location
////        val storageRef = Firebase.storage.reference
//
//        // Create a reference to the image file to upload
//        val imageRef = dbRef.child("images/${file.name}")
//
//        // Upload the image file to Firebase Storage
//        val uploadTask = imageRef.putFile(file)
//
//        // Register observers to listen for when the upload is done or if it fails
//        uploadTask.addOnSuccessListener {
//            // Image upload successful, do something
//        }.addOnFailureListener {
//            // Image upload failed, do something
//        }
//    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//
//        if (requestCode == REQUEST_IMAGE_GET && resultCode == RESULT_OK) {
//            // Get the selected image file
//            val uri = data?.data
//            val imageFile = uriToImageFile(uri)
//
//            // Upload the image to Firebase Storage
//            uploadImageToStorage(imageFile)
//        }
//    }


}