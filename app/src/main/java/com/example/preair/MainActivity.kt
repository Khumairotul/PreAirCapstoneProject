package com.example.preair

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.preair.databinding.ActivityMainBinding
import com.example.preair.home.HomeActivity

class MainActivity : AppCompatActivity() {

    private val TIME_OUT = 3500L
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val handler = Handler(mainLooper)

        handler.postDelayed({
            startActivity(Intent(this@MainActivity, HomeActivity::class.java))
            finish()
        }, TIME_OUT)

        supportActionBar?.hide()
    }
}