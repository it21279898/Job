package com.example.job

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.job.databinding.ActivityLoginBinding






class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener{
            startActivity(Intent(this, HomeActivity::class.java))
        }
        binding.tvHaventAccount.setOnClickListener{
            startActivity(Intent(this, RegisterActivity ::class.java))
        }
    }
    private fun showTextMinimalAlert(isNotValid:Boolean,text:String) {
        if (text == "Username")
            binding.etEmail.error = if (isNotValid) "$text tidak boleh kosong!" else null
        else if (text == "Password")
            binding.etPassword.error = if (isNotValid) "$text tidak boleh kosong!" else null
    }

}