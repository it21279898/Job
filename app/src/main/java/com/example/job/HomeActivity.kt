package com.example.job

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.example.job.databinding.ActivityHomeBinding
import com.example.job.databinding.ActivityNavhomeBinding
import com.google.firebase.auth.FirebaseAuth


class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNavhomeBinding
    private lateinit var auth: FirebaseAuth



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNavhomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnTax.setOnClickListener{
            startActivity(Intent(this,TaxActivity::class.java))
        }
//        binding.btnProfile.setOnClickListener{
//            startActivity(Intent(this,profileActivity::class.java))
//        }

        binding.btnEmp.setOnClickListener{
            startActivity(Intent(this,EmpMainActivity::class.java))
        }
        binding.btnCat1.setOnClickListener{
            startActivity(Intent(this,AllJobs::class.java))
        }

//        btnApply = findViewById(R.id.btn_testApply)

//        btnApply.setOnClickListener {
//            val intent = Intent(this,Tn_jobApplicationHome::class.java)
//            startActivity(intent)
//        }

        auth= FirebaseAuth.getInstance()

//        // Redirect to all jobs
//        val redirectToAllJobs = findViewById<Button>(R.id.btnAllJobs);
//        redirectToAllJobs.setOnClickListener {
//            val intent = Intent(this, AllJobs::class.java);
//            startActivity(intent);
//        }

        binding.btnLogout.setOnClickListener{
            auth.signOut()
            Intent(this,LoginActivity::class.java).also {
                it.flags=Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(it)
                Toast.makeText(this,"Logout successful!", Toast.LENGTH_SHORT).show()
            }

        }
    }







}