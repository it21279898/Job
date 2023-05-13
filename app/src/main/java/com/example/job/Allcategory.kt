package com.example.job

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.job.databinding.ActivityAllJobsBinding
import com.example.job.databinding.ActivityAllcategoryBinding
import com.example.job.databinding.ActivityNavhomeBinding

class Allcategory : AppCompatActivity() {
    private lateinit var binding: ActivityAllcategoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAllcategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.Del1.setOnClickListener{
            startActivity(Intent(this,AllJobs::class.java))
        }
    }
}