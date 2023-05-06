package com.example.job

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.example.job.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth

//    Tharanee
    private lateinit var btnApply: Button
    private lateinit var btnView: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth= FirebaseAuth.getInstance()

        binding.btnLogin.setOnClickListener{
            startActivity(Intent(this, LoginActivity::class.java))
        }
        binding.btnRegister.setOnClickListener{
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        btnApply = findViewById(R.id.btn_Apply)
        btnView = findViewById(R.id.btn_View)

        btnApply.setOnClickListener {
            val intent = Intent(this,Tn_jobApplication::class.java)
            startActivity(intent)
        }
        btnView.setOnClickListener {
            val intent = Intent(this, Tn_fetchData::class.java)
            startActivity(intent)
        }


    }

    override fun onStart() {
        super.onStart()
        if(auth.currentUser != null){
            Intent(this,HomeActivity::class.java).also {
                it.flags=Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(it)
            }
        }
    }

}