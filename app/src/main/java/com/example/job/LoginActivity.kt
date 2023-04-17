package com.example.job

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.job.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.jakewharton.rxbinding2.widget.RxTextView

@SuppressLint("CheckResult")
class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Auth

        auth= FirebaseAuth.getInstance()

        //username validation
        val usernameSteam = RxTextView.textChanges(binding.etEmail)
            .skipInitialValue()
            .map { username->
                username.isEmpty()
            }
        usernameSteam.subscribe{
            showTextMinimalAlert(it,"Email/Username")
        }

        //password validation
        val passwordSteam = RxTextView.textChanges(binding.etPassword)
            .skipInitialValue()
            .map { password->
                password.isEmpty()
            }
        passwordSteam.subscribe{
            showTextMinimalAlert(it,"Password")
        }

        //button enable true or false
        val invalidFieldsStream = io.reactivex.Observable.combineLatest(

            usernameSteam,
            passwordSteam,
            {usernameInvalid :Boolean,passwordInvalid :Boolean->
               !usernameInvalid && !passwordInvalid
            })

        invalidFieldsStream.subscribe { isValid->
            if(isValid){
                binding.btnLogin.isEnabled=true
                binding.btnLogin.backgroundTintList= ContextCompat.getColorStateList(this,R.color.primary_color)
            } else {
                binding.btnLogin.isEnabled=false
                binding.btnLogin.backgroundTintList= ContextCompat.getColorStateList(this,android.R.color.darker_gray)

            }
        }



        //click

        binding.btnLogin.setOnClickListener{
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()
            loginUser(email,password)
        }
        binding.tvHaventAccount.setOnClickListener{
            startActivity(Intent(this, RegisterActivity ::class.java))
        }
        binding.tvForgotPw.setOnClickListener {
            startActivity(Intent(this,ResetPasswordActivity::class.java))
        }
    }
    private fun showTextMinimalAlert(isNotValid:Boolean,text:String) {
        if (text == "Email/Username")
            binding.etEmail.error = if (isNotValid) "$text Cannot be empty!" else null
        else if (text == "Password")
            binding.etPassword.error = if (isNotValid) "$text This cannot be empty!" else null
    }

    private fun loginUser(email:String,password:String){
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this){ login->
                if (login.isSuccessful){
                  Intent(this,HomeActivity::class.java).also {
                      it.flags=Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                      startActivity(it)
                      Toast.makeText(this,"Login successful!",Toast.LENGTH_SHORT).show()
                  }
                }else{
                    Toast.makeText(this,login.exception?.message,Toast.LENGTH_SHORT).show()
                }
            }
    }

}