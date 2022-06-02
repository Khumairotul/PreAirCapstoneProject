package com.example.preair.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.preair.R
import com.example.preair.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var loginBinding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        loginBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(loginBinding.root)
    }

    private fun showLoading(isLoading: Boolean) {
        loginBinding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}