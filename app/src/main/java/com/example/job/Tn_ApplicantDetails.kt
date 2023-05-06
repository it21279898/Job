package com.example.job

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.database.FirebaseDatabase

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

        btnUpdate.setOnClickListener {
            openUpdateDialog(
                intent.getStringExtra("applicantId").toString(),
                intent.getStringExtra("name").toString()
            )
        }
    }

    private fun initView(){
        tvApplicantId = findViewById(R.id.tvID)
        tvName = findViewById(R.id.tvName)
        tvEmail = findViewById(R.id.tvEmail)
        tvNumber = findViewById(R.id.tvPhoneNo)
        tvJobName = findViewById(R.id.tvJob)
        tvPosition = findViewById(R.id.tvPosition)
        tvStatus  = findViewById(R.id.tvStatus)

        btnUpdate = findViewById(R.id.btnUpdate)
        btnDelete = findViewById(R.id.btnDelete)

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
    private fun openUpdateDialog(
        applicantId : String,
        ApplicantName : String
    ){
        val mDialog = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val mDialogView = inflater.inflate(R.layout.tn_update_applicant_details, null)

        mDialog.setView(mDialogView)

        val appName = mDialogView.findViewById<EditText>(R.id.etName)
        val appEmail = mDialogView.findViewById<EditText>(R.id.etEmail)
        val appNumber = mDialogView.findViewById<EditText>(R.id.etNumber)
        val appJob = mDialogView.findViewById<EditText>(R.id.etJobName)
        val appPosition = mDialogView.findViewById<EditText>(R.id.etPosition)
        val appStatus = mDialogView.findViewById<EditText>(R.id.etStatus)

        val updateBtn = mDialogView.findViewById<Button>(R.id.btnUpdateData)

        appName.setText(intent.getStringExtra("name").toString())
        appEmail.setText(intent.getStringExtra("email").toString())
        appNumber.setText(intent.getStringExtra("phoneNo").toString())
        appJob.setText(intent.getStringExtra("jobName").toString())
        appPosition.setText(intent.getStringExtra("position").toString())
        appStatus.setText(intent.getStringExtra("status").toString())

        mDialog.setTitle("Updating $appName Record")

        val alertDialog = mDialog.create()
        alertDialog.show()

        btnUpdate.setOnClickListener {
            updateApplicantData(
                applicantId,
                appName.text.toString(),
                appEmail.text.toString(),
                appNumber.text.toString(),
                appJob.text.toString(),
                appPosition.text.toString(),
                appStatus.text.toString()

                )
            Toast.makeText(applicationContext,"Applicant Data Updated", Toast.LENGTH_LONG).show()

            //setting updated data to our textView
            tvName.text = appName.text.toString()
            tvEmail.text = appEmail.text.toString()
            tvNumber.text = appEmail.text.toString()
            tvJobName.text = appNumber.text.toString()
            tvPosition.text = appPosition.text.toString()
            tvStatus.text = appStatus.text.toString()

            alertDialog.dismiss()
        }


    }

    private fun updateApplicantData(
        id:String,
        name:String,
        email:String,
        phone:String,
        job:String,
        position:String,
        status:String
    ){
        val dbRef = FirebaseDatabase.getInstance().getReference("Applicant").child(id)
        val applicantInfo = Tn_ApplicantModel(id,name,email,phone,job,position,status)

        dbRef.setValue(applicantInfo)
    }

}