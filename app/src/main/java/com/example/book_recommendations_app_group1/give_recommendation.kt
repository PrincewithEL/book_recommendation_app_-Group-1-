package com.example.book_recommendations_app_group1

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import android.widget.Toast

private lateinit var spinnerLevel: Spinner
private lateinit var buttonV: Button

class give_recommendation : AppCompatActivity() {
    @SuppressLint("MissingInflatedId", "SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_give_recommendation)
        spinnerLevel = findViewById(R.id.editTextReadingLevel)

        val imageView = findViewById<ImageView>(R.id.adImg)

        imageView.visibility = View.INVISIBLE

        Handler().postDelayed({
            imageView.visibility = View.VISIBLE
        }, 1000)

        imageView.setOnClickListener {
            val adMobUri = Uri.parse("https://admob.google.com")
            val intent = Intent(Intent.ACTION_VIEW, adMobUri)
            startActivity(intent)
        }

        val levels = arrayOf(
            "Kindly Select A Reading Level",
            "Board Books (0-3 years)",
            "Picture Books (3-8 years)",
            "Early Readers (6-9 years)",
            "Middle Grade (8-12 years)",
            "Young Adult (YA) (12-18 years)",
            "Adult Fiction and Non-Fiction (18+ years)",
            "All Ages"
        )

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, levels)

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spinnerLevel.adapter = adapter

        spinnerLevel.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                //val selectedLevel = adapter.getItem(position).toString()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        })

        buttonV = findViewById(R.id.buttonCriteria)

            buttonV.setOnClickListener {

                val genre: String = findViewById<EditText>(R.id.editTextGenre).text.toString()

                if (findViewById<EditText>(R.id.editTextGenre).text.isEmpty()) {
                    Toast.makeText(this, "Ensure all fields are filled!", Toast.LENGTH_SHORT).show()
                }else if(genre.equals("Education, Self-Help") || genre.equals("Education") || genre.equals("Self-Help")) {
                    val intent = Intent(this, view_recommendations::class.java)
                    Toast.makeText(this, "Books successfully found!", Toast.LENGTH_SHORT).show()
                    intent.putExtra("myGenre", (R.id.editTextGenre).toString())
                    intent.putExtra("myRL", (R.id.editTextReadingLevel).toString())
                    startActivity(intent)
                    finish()
                }else {
                    Toast.makeText(this, "Books not found!", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, not_found::class.java)
                    startActivity(intent)
                    finish()
                }
            }

            val buttonBack: Button = findViewById(R.id.buttonSave3)
            buttonBack.setOnClickListener {
                val intent = Intent(this, user_module::class.java)
                intent.putExtra("logInt", 2)
                startActivity(intent)
                finish()
            }

        }
    }

