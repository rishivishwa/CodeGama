package com.example.codegama

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Common.fullScreen(window)
        setContentView(R.layout.activity_splash)
        Handler().postDelayed({
            // Start the main activity
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)

            startActivity(intent)

            // Close the splash activity
            finish()
        }, 4000)

    }

}