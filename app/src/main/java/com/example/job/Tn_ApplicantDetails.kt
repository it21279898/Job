package com.example.job

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class Tn_ApplicantDetails : AppCompatActivity() {

    private lateinit var tvApplicantId: TextView
    private lateinit var tvName: TextView
    private lateinit var tvEmail: TextView
    private lateinit var tvNumber: TextView
    private lateinit var tvJobName: TextView
    private lateinit var tvPosition: TextView
    private lateinit var tvStatus: TextView
    private lateinit var btnUpdate : Button
    private lateinit var btnDelete : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tn_applicant_details)

        initView()

        setValuesToViews()
    }

    private fun initView(){

    }

    private  fun setValuesToViews(){
        tvApplicantId.text = intent.getStringExtra("applicantId")
        tvName.text = intent.getStringExtra("name")
        tvEmail.text = intent.getStringExtra("email")
        tvNumber.text = intent.getStringExtra("phoneNo")
        tvJobName.text = intent.getStringExtra("jobName")
        tvPosition.text = intent.getStringExtra("position")
        tvStatus.text = intent.getStringExtra("status")






    }
}