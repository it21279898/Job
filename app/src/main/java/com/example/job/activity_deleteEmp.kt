package com.example.job

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.job.databinding.ActivityDeleteEmpBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class activity_deleteEmp : AppCompatActivity() {
    private lateinit var binding: ActivityDeleteEmpBinding
    private lateinit var database: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDeleteEmpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backBtn2.setOnClickListener{
            startActivity(Intent(this,EmpMainActivity::class.java))
        }


        binding.deleteButton.setOnClickListener {
            val phone = binding.deletePhone.text.toString()
            if (phone.isNotEmpty())
                deleteData(phone)
            else
                Toast.makeText(this, "Please enter the phone number", Toast.LENGTH_SHORT).show()
        }
    }
    private fun deleteData(phone: String){
        database = FirebaseDatabase.getInstance().getReference("Emp")
        database.child(phone).removeValue().addOnSuccessListener {
            binding.deletePhone.text.clear()
            Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener {
            Toast.makeText(this, "Unable to delete", Toast.LENGTH_SHORT).show()
        }
    }
}