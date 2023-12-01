package com.example.book_recommendations_app_group1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.book_recommendations_app_group1.databinding.ActivityMain2Binding
import com.google.firebase.auth.FirebaseAuth

class MainActivity2 : AppCompatActivity() {

    private lateinit var binding: ActivityMain2Binding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.button.setOnClickListener {
            val email = binding.editTextTextEmailAddress.text.toString()
            val pass = binding.editTextTextPassword.text.toString()
            val confpass = binding.editTextTextPassword2.text.toString()

            if(email.isNotEmpty() && pass.isNotEmpty() && confpass.isNotEmpty()) {
                if (pass.equals(confpass)) {

                    firebaseAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener{
                        if(it.isSuccessful){
                            Toast.makeText(this, "User Successfully Registered!", Toast.LENGTH_LONG).show()
                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                        }else{
                            Toast.makeText(this, "An Error Occurred!" + it.exception.toString(), Toast.LENGTH_LONG).show()
                        }
                    }

                } else {
                    Toast.makeText(this, "Passwords Do not Match!", Toast.LENGTH_LONG).show()
                }
            }else{
                Toast.makeText(this, "Ensure All Fields are Filled In!", Toast.LENGTH_LONG).show()
            }
        }


        binding.textView3.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }
}