package com.example.book_recommendations_app_group1

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.book_recommendations_app_group1.databinding.ActivityAdministratorModuleBinding
import com.example.book_recommendations_app_group1.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth

var managebooks = 0

class administrator_module : AppCompatActivity() {

    private lateinit var binding: ActivityAdministratorModuleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdministratorModuleBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var auth:Int = intent.getIntExtra("logInt", 0)
        val sharedPreference = getSharedPreferences("My_Log", Context.MODE_PRIVATE)

        if(auth == 1) {

            binding.logout.setOnClickListener {
                val editor = sharedPreference.edit()
                editor.remove("My_Log")
                editor.apply()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }

            binding.add.setOnClickListener {
                val intent = Intent(this, add_book::class.java)
                intent.putExtra("managebooks", 3)
                startActivity(intent)
                finish()
            }

            binding.view.setOnClickListener {
                val intent = Intent(this, view_books::class.java)
                startActivity(intent)
                finish()
            }

        }else{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

    }
}