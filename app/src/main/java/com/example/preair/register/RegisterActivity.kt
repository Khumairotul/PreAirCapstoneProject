package com.example.preair.register

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.preair.R
import com.example.preair.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    private lateinit var registerBinding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        registerBinding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(registerBinding.root)
    }

    private fun showLoading(isLoading: Boolean) {
        registerBinding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}