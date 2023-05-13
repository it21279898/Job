package com.example.job

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class Tn_jobApplicationHome : AppCompatActivity() {

    //    Tharanee
    private lateinit var btnApply: Button
    private lateinit var btnView: Button
    private lateinit var btnCv:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tn_job_application_home)
        btnApply = findViewById(R.id.btn_Apply)
        btnView = findViewById(R.id.btn_View)
        btnCv = findViewById(R.id.btn_apply)

        btnApply.setOnClickListener {
            val intent = Intent(this,Tn_jobApplication::class.java)
            startActivity(intent)
        }
        btnView.setOnClickListener {
            val intent = Intent(this, Tn_fetchData::class.java)
            startActivity(intent)
        }

        btnCv.setOnClickListener{
            val intent = Intent(this, CvApplicationHome::class.java)
            startActivity(intent)
        }

    }
}