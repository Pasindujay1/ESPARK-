package com.example.loginnregui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import com.example.loginnregui.databinding.ActivityMainBinding
import com.example.loginnregui.databinding.ActivityRegistrationUiBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


import com.google.firebase.database.*


class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding


    // Initialize the TextViews for firstname, lastname, email, logout, and update button

    private lateinit var tvFirstName: TextView
    private lateinit var tvLastName: TextView
    private lateinit var tvEmail: TextView
    private lateinit var tvLogOut:TextView
    private lateinit var tvupdate:TextView


    // Initialize FirebaseAuth and FirebaseDatabase instances, and DatabaseReference variable

    private lateinit var firebaseAuth: FirebaseAuth
    var databaseReference: DatabaseReference? = null
    var database : FirebaseDatabase? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize the binding instance

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize TextView instances with the corresponding ids from the layout


        tvFirstName = findViewById(R.id.firstnameText)
        tvLastName = findViewById(R.id.lastnameText)
        tvEmail = findViewById(R.id.emailText)

        // Initialize tvLogOut   and  tvupdate
        tvLogOut = findViewById(R.id.logoutButton)
        tvupdate = findViewById(R.id.update2btn)



        // Initialize FirebaseAuth and FirebaseDatabase instances

        firebaseAuth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()

        // Get the profile information from the FirebaseDatabase instance

        databaseReference = database?.reference!!.child("profile")

        // Call the function to load the user profile information

        loadProfile()

        // Set a click listener to the delete button

        binding.deletebtn.setOnClickListener{
            // Get the current user instance

            val user = firebaseAuth.currentUser
            // Delete the user account and associated data from FirebaseAuth and FirebaseDatabase

            user?.delete()?.addOnCompleteListener{
                if(it.isSuccessful){
                    //account already deleted
                    Toast.makeText(this,"Account Successfully deleted",Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this@ProfileActivity, LoginUI::class.java))
                    finish()

                }else{
                    //catch error          during deletion
                    Log.e("error", it.exception.toString())
                }
            }
        }
    }



    // Function to load the user profile information

    private fun loadProfile(){
            val user = firebaseAuth.currentUser
            val userreference = databaseReference?.child(user?.uid!!)

            tvEmail.text = "Email  -- > "+user?.email

            userreference?.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    tvFirstName.text = "Firstname -   " +snapshot.child("firstnameInput").value.toString()
                    tvLastName.text = "Lastname -   " +snapshot.child("lastnameInput").value.toString()
                    tvEmail.text ="Email -  " + snapshot.child("reg_email").value.toString() //20.43min

                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })

            tvupdate.setOnClickListener {
                startActivity(Intent(this@ProfileActivity, UpdataData::class.java))

            }

            tvLogOut.setOnClickListener {
                firebaseAuth.signOut()
                startActivity(Intent(this@ProfileActivity, LoginUI::class.java))
                finish()
            }


        }
    }