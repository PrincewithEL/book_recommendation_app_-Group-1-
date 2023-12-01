package com.example.book_recommendations_app_group1

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Button
import android.widget.ImageView

class not_found : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_not_found)

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

        val buttonBack: Button = findViewById(R.id.buttonBack)
        buttonBack.setOnClickListener {
            val intent = Intent(this, user_module::class.java)
            intent.putExtra("logInt", 2)
            startActivity(intent)
            finish()
        }

    }
}