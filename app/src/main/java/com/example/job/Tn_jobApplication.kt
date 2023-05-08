package com.example.job

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Tn_jobApplication : AppCompatActivity() {

    private lateinit var etName: EditText
    private lateinit var etEmail: EditText
    private lateinit var etPhoneNo: EditText
    private lateinit var etJobName: EditText
    private lateinit var etPosition: EditText
    private lateinit var etStatus: EditText
    private lateinit var btnSubmit: Button

    private lateinit var dbRef : DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tn_job_application)

        etName = findViewById(R.id.et_name)
        etEmail = findViewById(R.id.et_email)
        etPhoneNo = findViewById(R.id.et_phoneNo)
        etJobName = findViewById(R.id.et_jobName)
        etPosition = findViewById(R.id.et_position)
        etStatus = findViewById(R.id.et_status)
        btnSubmit = findViewById(R.id.btn_submit)

        dbRef = FirebaseDatabase.getInstance().getReference("Applicant")

        btnSubmit.setOnClickListener {
            saveApplicantData()
        }
    }
    private fun saveApplicantData(){
        //getting values
        val name = etName.text.toString()
        val email = etEmail.text.toString()
        val phone = etPhoneNo.text.toString()
        val job = etJobName.text.toString()
        val position = etPosition.text.toString()
        val status= etStatus.text.toString()

        //if the variables are empty show error msg
        if(name.isEmpty()){
            etName.error = "Please enter the name"
        }
        if(email.isEmpty()){
            etEmail.error = "Please enter the email"
        }
        if(phone.isEmpty()){
            etPhoneNo.error = "Please enter the phone number"
        }
        if(job.isEmpty()){
            etJobName.error = "Please enter the job"
        }
        if(position.isEmpty()){
            etPosition.error = "Please enter the position"
        }
        if(status.isEmpty()){
            etStatus.error = "Please enter the status"
        }
        //identify data from a id
        val applicantId = dbRef.push().key!!

        val applicant = Tn_ApplicantModel(applicantId,name,email,phone,job,position,status)

        dbRef.child(applicantId).setValue(applicant)
            .addOnCompleteListener() {
                Toast.makeText(this,"Data inserted successfully", Toast.LENGTH_LONG).show()

                etName.text.clear()
                etEmail.text.clear()
                etPhoneNo.text.clear()
                etJobName.text.clear()
                etPosition.text.clear()
                etStatus.text.clear()

            }.addOnFailureListener() { err ->
                Toast.makeText(this,"Error ${err.message}", Toast.LENGTH_LONG).show()
            }
    }
}