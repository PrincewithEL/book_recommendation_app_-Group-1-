package com.example.book_recommendations_app_group1

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.ImageView
import com.example.book_recommendations_app_group1.databinding.ActivityAdministratorModuleBinding
import com.example.book_recommendations_app_group1.databinding.ActivityMainBinding
import com.example.book_recommendations_app_group1.databinding.ActivityUserModuleBinding
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdListener
import com.google.firebase.auth.FirebaseAuth
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.RequestConfiguration


class user_module : AppCompatActivity() {


    private lateinit var binding: ActivityUserModuleBinding
    private lateinit var firebaseAuth: FirebaseAuth
    lateinit var mAdView : AdView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserModuleBinding.inflate(layoutInflater)
        setContentView(binding.root)

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

//        MobileAds.initialize(this, "ca-app-pub-3474120643587438~7659240420")

        MobileAds.initialize(this) {}

        mAdView = findViewById(R.id.adView)
//        val adRequest = AdRequest.Builder().build()
        // Set test device IDs
        val testDeviceIds = listOf("samsung-sm_a135f-R58T91943NJ")
        val configuration = RequestConfiguration.Builder().setTestDeviceIds(testDeviceIds).build()
        MobileAds.setRequestConfiguration(configuration)

        // Create an AdRequest
        val adRequest = AdRequest.Builder().build()

        // Load the ad with the AdRequest
        mAdView.loadAd(adRequest)

        mAdView.adListener = object : AdListener() {
            override fun onAdClicked() {
                // Code to be executed when the user clicks on an ad.
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://example.com")))
            }

            override fun onAdClosed() {
                // Code to be executed when the user is about to return
                // to the app after tapping on an ad.
                // For example, resume the game or show relevant content.
                Log.d("AdClosed", "Ad closed, resuming game or showing content.")
            }

            fun onAdFailedToLoad(adError: AdError) {
                // Code to be executed when an ad request fails.
                // For example, display an error message to the user.
                Log.e("AdLoadError", "Ad failed to load: ${adError.message}")
            }

            override fun onAdImpression() {
                // Code to be executed when an impression is recorded
                // for an ad.
                // For example, track analytics or log impressions.
                Log.d("AdImpression", "Ad impression recorded.")
            }

            override fun onAdLoaded() {
                // Code to be executed when an ad finishes loading.
                // For example, make a view visible or enable a button.
                Log.d("AdLoaded", "Ad loaded successfully.")
            }

            override fun onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
                // For example, pause a game or stop ongoing activities.
                Log.d("AdOpened", "Ad opened, pausing game or stopping activities.")
            }
        }

        firebaseAuth = FirebaseAuth.getInstance()
        var auth:Int = intent.getIntExtra("logInt", 0)
        val sharedPreference = getSharedPreferences("My_Log", Context.MODE_PRIVATE)

        if(auth == 2) {

            binding.logout.setOnClickListener {
                firebaseAuth.signOut()
                val editor = sharedPreference.edit()
                editor.remove("My_Log")
                editor.apply()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }

            binding.recommend.setOnClickListener {
                val intent = Intent(this, give_recommendation::class.java)
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