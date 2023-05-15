package com.example.loginnregui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.loginnregui.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LoginUI : AppCompatActivity() {

    // Declare binding and firebaseAuth as private lateinit variables
    private lateinit var binding: ActivityLoginBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inflate the layout for the activity using view binding
        binding.newuserredirect.setOnClickListener{
            val intent = Intent(this, RegistrationUI::class.java)
            startActivity(intent)
        }

        firebaseAuth = FirebaseAuth.getInstance()

        binding.Loginbtn.setOnClickListener{
            val email = binding.regEmail.text.toString()
            val pass = binding.regPassword.text.toString()

            if(email.isNotEmpty() && pass.isNotEmpty()){

                firebaseAuth.signInWithEmailAndPassword(email , pass).addOnCompleteListener{
                    if(it.isSuccessful){

                        //to send the user to sign-In/Login activity
                        val intent = Intent(this, ProfileActivity::class.java)
                        startActivity(intent)
                    }else{
                        Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()

                    }
                }

            }else{
                Toast.makeText(this,"Empty fields are not allowed", Toast.LENGTH_SHORT).show()

            }
        }
    }

    override fun onStart() {
        super.onStart()

        if(firebaseAuth.currentUser !=null){
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }
    }
}