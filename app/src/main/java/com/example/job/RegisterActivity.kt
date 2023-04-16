package com.example.job

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.job.databinding.ActivityRegisterBinding




class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)




        binding.btnRegister.setOnClickListener{
            startActivity(Intent(this, LoginActivity::class.java))
        }
        binding.tvHaveAccount.setOnClickListener{
            startActivity(Intent(this, LoginActivity ::class.java))
        }
    }

    private fun showNameExistAlert(isNotValid:Boolean){
        binding.etFullname.error = if(isNotValid)"Nama tidak boleh kosong!" else null
    }
    private fun showTextMinimalAlert(isNotValid:Boolean,text:String) {
        if (text == "Username")
            binding.etUsername.error = if (isNotValid) "$text harus lebih dari 6 huruf!" else null
        else if (text == "Password")
            binding.etPassword.error = if (isNotValid) "$text harus lebih dari 6 huruf!" else null
    }
    private fun showEmailValidtAlert(isNotValid:Boolean){
        binding.etEmail.error = if(isNotValid)"Email tidak valid!" else null
    }
    private fun showPasswordComfirmAlert(isNotValid:Boolean){
        binding.etComfirmPassword.error = if(isNotValid)"Password tidak sama!" else null
    }
}