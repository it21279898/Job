package com.example.job

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.job.databinding.ActivityUploadEmpBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class UploadEmpActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUploadEmpBinding
    private lateinit var database: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUploadEmpBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.backBtn1.setOnClickListener{
            startActivity(Intent(this,EmpMainActivity::class.java))
        }



        binding.saveButton.setOnClickListener {
            val name = binding.uploadName.text.toString()
            val operator = binding.uploadOperator.text.toString()
            val location = binding.uploadLocation.text.toString()
            val phone = binding.uploadPhone.text.toString()

            // Check if the phone number is valid
            if (!isValidPhoneNumber(phone)) {
                binding.uploadPhone.error = "Invalid phone number"
                return@setOnClickListener
            }

            database = FirebaseDatabase.getInstance().getReference("Emp")
            val users = Emp(name, operator, location, phone)
            database.child(phone).setValue(users).addOnSuccessListener {
                binding.uploadName.text.clear()
                binding.uploadOperator.text.clear()
                binding.uploadLocation.text.clear()
                binding.uploadPhone.text.clear()
                Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show()
                val intent = Intent(this@UploadEmpActivity,ReadDataEmpActivity2::class.java)
                startActivity(intent)
                finish()
            }.addOnFailureListener {
                Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
            }
        }


    }



    // Phone number validation function
    private fun isValidPhoneNumber(phone: String): Boolean {
        return phone.matches(Regex("^\\d{10}$"))
    }

}