package com.example.job

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.job.databinding.ActivityEmpMainBinding



class EmpMainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEmpMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEmpMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.mainupload.setOnClickListener(View.OnClickListener{
            val intent = Intent(this@EmpMainActivity,  UploadEmpActivity::class.java)
            startActivity(intent)
            finish()
        })

        binding.mainsearch.setOnClickListener(View.OnClickListener{
            val intent = Intent(this@EmpMainActivity,ReadDataEmpActivity2::class.java)
            startActivity(intent)
        })
        binding.mainUpdate.setOnClickListener(View.OnClickListener{
            val intent = Intent(this@EmpMainActivity,activity_updateEmp::class.java)
            startActivity(intent)
        })
        binding.mainDelete.setOnClickListener(View.OnClickListener{
            val intent = Intent(this@EmpMainActivity, activity_deleteEmp::class.java)
            startActivity(intent)
        })

        binding.backBtn4.setOnClickListener(View.OnClickListener{
            val intent = Intent(this@EmpMainActivity,MainActivity::class.java)
            startActivity(intent)
        })

    }
}