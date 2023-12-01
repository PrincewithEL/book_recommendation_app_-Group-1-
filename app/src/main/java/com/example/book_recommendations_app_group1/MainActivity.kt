package com.example.book_recommendations_app_group1

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.book_recommendations_app_group1.databinding.ActivityMain2Binding
import com.example.book_recommendations_app_group1.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    var logStatus: String = ""
    var logint = 0
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var binding: ActivityMainBinding
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        sharedPreferences = getPreferences(Context.MODE_PRIVATE)

        binding.button.setOnClickListener {
            val email = binding.editTextTextEmailAddress.text.toString()
            val pass = binding.editTextTextPassword.text.toString()


            fun loginStatus(){
                val editor = sharedPreferences.edit()
                editor.putString("My_Log", "Logged In")
                editor.apply()
                val loadedData = sharedPreferences.getString("My_Log", "")
                logStatus = "Loaded Data: $loadedData"
            }

            if(email.isNotEmpty() && pass.isNotEmpty()) {
                if (email.equals("peter.chemelil@strathmore.edu") && pass.equals("137399") || email.equals("julius.miyumo@strathmore.edu") && pass.equals("137186") || email.equals("amani.chege@strathmore.edu") && pass.equals("137220") || email.equals("asher.njoroge@strathmore.edu") && pass.equals("137187")) {
                    val intent = Intent(this, administrator_module::class.java)
                    intent.putExtra("logInt", 1)
                    startActivity(intent)
                    loginStatus()
                }else{
                    firebaseAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener{
                        if(it.isSuccessful){
                            Toast.makeText(this, "User Successfully Login!", Toast.LENGTH_LONG).show()
                            val intent = Intent(this, user_module::class.java)
                            intent.putExtra("logInt", 2)
                            startActivity(intent)
                            loginStatus()
                        }else{
                            Toast.makeText(this, "An Error Occurred!" + it.exception.toString(), Toast.LENGTH_LONG).show()
                        }
                    }

                }

            }else{
                Toast.makeText(this, "Ensure All Fields are Filled In!", Toast.LENGTH_LONG).show()
            }
        }

        binding.textView3.setOnClickListener {
            val intent = Intent(this, MainActivity2::class.java)
            startActivity(intent)
        }

    }
}