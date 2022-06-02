package com.example.preair.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.preair.R
import com.example.preair.camera.CameraActivity
import com.example.preair.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    private lateinit var homeBinding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        homeBinding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(homeBinding.root)

        cameraAction()
    }

    private fun cameraAction() {
        homeBinding.fabTakePicture.setOnClickListener {
            startActivity(Intent(this@HomeActivity, CameraActivity::class.java))
        }
    }

    private fun showLoading(isLoading: Boolean) {
        homeBinding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

}