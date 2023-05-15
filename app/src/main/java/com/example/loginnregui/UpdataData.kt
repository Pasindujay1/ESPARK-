package com.example.loginnregui

import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Email
import android.provider.ContactsContract.Profile
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.loginnregui.databinding.ActivityLoginBinding
import com.example.loginnregui.databinding.ActivityUpdateBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class UpdataData : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateBinding

    private lateinit var database : DatabaseReference
    private lateinit var firebaseAuth: FirebaseAuth
    var databaseReference: DatabaseReference? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        var databaseReference: DatabaseReference? = null
        // databaseReference = database?.reference!!.child("profile")



        binding.updatebtn.setOnClickListener {

            val firstName = binding.updateFirstname.text.toString()
            val lastName = binding.updateLastname.text.toString()

            updateData(firstName,lastName)



        }

    }

    private fun updateData(firstName: String, lastName: String) {

        database = FirebaseDatabase.getInstance().getReference("profile")
        val user = mapOf<String,String>(
            //fieldname first(key) , second one is the value
            "firstnameInput" to firstName,
            "lastnameInput" to lastName,

        )
        val currentUser = firebaseAuth.currentUser

        database.child((currentUser?.uid!!)).updateChildren(user).addOnSuccessListener {

            binding.updateFirstname.text.clear()
            binding.updateLastname.text.clear()

            Toast.makeText(this,"Successfuly Updated", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, Profile::class.java)
            startActivity(intent)


        }.addOnFailureListener{

            Toast.makeText(this,"Failed to Update", Toast.LENGTH_SHORT).show()

        }}
}