package com.example.job

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class cvApplication : AppCompatActivity() {

    private lateinit var etName: EditText
    private lateinit var etEmail: EditText
    private lateinit var etNic: EditText
    private lateinit var etPhoneNo: EditText
    private lateinit var etSkill: EditText
    private lateinit var etEducation: EditText
    private lateinit var etWork: EditText
    private lateinit var btnSubmit: Button

    private lateinit var dbRef : DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cv_application)

        etName = findViewById(R.id.et_nameS)
        etEmail = findViewById(R.id.et_emailS)
        etNic = findViewById(R.id.et_nicS)
        etPhoneNo = findViewById(R.id.et_phoneNoS)
        etSkill = findViewById(R.id.et_skill)
        etEducation = findViewById(R.id.et_edu)
        etWork = findViewById(R.id.work2)
        btnSubmit = findViewById(R.id.btn_submitS)

        dbRef = FirebaseDatabase.getInstance().getReference("CV")

        btnSubmit.setOnClickListener {
            saveCvData()
        }
    }

    private fun saveCvData(){

        //getting values
        val name = etName.text.toString()
        val email = etEmail.text.toString()
        val nic = etNic.text.toString()
        val phone = etPhoneNo.text.toString()
        val skills = etSkill.text.toString()
        val education = etEducation.text.toString()
        val work= etWork.text.toString()

        //if the variables are empty show error msg
        if(name.isEmpty()){
            etName.error = "Please enter the name"
        }
        if(email.isEmpty()){
            etEmail.error = "Please enter the email"
        }
        if(nic.isEmpty()){
            etNic.error = "Please enter the nic"
        }
        if(phone.isEmpty()){
            etPhoneNo.error = "Please enter the phone number"
        }
        if(skills.isEmpty()){
            etSkill.error = "Please enter the skills"
        }
        if(education.isEmpty()){
            etEducation.error = "Please enter the education levels"
        }
        if(work.isEmpty()){
            etWork.error = "Please enter the work experience"
        }
        //identify data from a id
        val cvId = dbRef.push().key!!

        val cv = CvModel(cvId,name,email,nic,phone,skills,education,work)

        dbRef.child(cvId).setValue(cv)
            .addOnCompleteListener() {
                Toast.makeText(this,"Data inserted successfully", Toast.LENGTH_LONG).show()

                etName.text.clear()
                etEmail.text.clear()
                etNic.text.clear()
                etPhoneNo.text.clear()
                etSkill.text.clear()
                etEducation.text.clear()
                etWork.text.clear()

            }.addOnFailureListener() { err ->
                Toast.makeText(this,"Error ${err.message}", Toast.LENGTH_LONG).show()
            }
    }
}