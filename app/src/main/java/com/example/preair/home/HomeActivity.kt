package com.example.preair.home

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
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

        playAnimation()
        cameraAction()
    }

    private fun playAnimation() {
        val textViewEnter = ObjectAnimator.ofFloat(homeBinding.openingTextView, View.TRANSLATION_Y, -5f,18f).setDuration(1000)
        val rvArticleEnter = ObjectAnimator.ofFloat(homeBinding.rvArticle, View.ALPHA, 1f).setDuration(1000)
        val fabCamera = ObjectAnimator.ofFloat(homeBinding.fabTakePicture, View.TRANSLATION_X,10f).setDuration(100)

        AnimatorSet().apply {
            playSequentially(textViewEnter, rvArticleEnter, fabCamera)
            startDelay = 500
        }.start()
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