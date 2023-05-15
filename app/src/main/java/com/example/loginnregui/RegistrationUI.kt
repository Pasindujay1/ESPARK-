package com.example.loginnregui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.loginnregui.databinding.ActivityRegistrationUiBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class RegistrationUI : AppCompatActivity() {
    private lateinit var binding: ActivityRegistrationUiBinding
    private lateinit var firebaseAuth: FirebaseAuth
    var databaseReference: DatabaseReference? = null
    var database : FirebaseDatabase? = null





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegistrationUiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        databaseReference = database?.reference!!.child("profile")


        binding.signinalready.setOnClickListener{
            val intent = Intent(this, LoginUI::class.java)
            startActivity(intent)
        }
        binding.Registerbtn.setOnClickListener{

            val fname = binding.firstnameInput.text.toString()
            val lname = binding.lastnameInput.text.toString()
            val email = binding.regEmail.text.toString()
            val pass = binding.regPassword.text.toString()
            val confirmPass = binding.regConfirmPass.text.toString()





            if( fname.isNotEmpty() && lname.isNotEmpty() && email.isNotEmpty() && pass.isNotEmpty() && confirmPass.isNotEmpty()){
                if(pass == confirmPass){

                    firebaseAuth.createUserWithEmailAndPassword(email , pass).addOnCompleteListener{
                        if(it.isSuccessful){

                            //insert user data into db
                            val currentUser = firebaseAuth.currentUser
                            val currentUSerDb = databaseReference?.child((currentUser?.uid!!))


                            currentUSerDb?.child("firstnameInput")?.setValue(fname)
                            currentUSerDb?.child("lastnameInput")?.setValue(lname)
                            currentUSerDb?.child("reg_email")?.setValue(email)



                            //to send the user to sign-In/Login activity
                            val intent = Intent(this, LoginUI::class.java)
                            startActivity(intent)
                        }else{
                            Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()

                        }
                    }
                }else{
                    Toast.makeText(this,"Password is not matching", Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(this,"Empty fields are not allowed", Toast.LENGTH_SHORT).show()

            }

        }
    }
}