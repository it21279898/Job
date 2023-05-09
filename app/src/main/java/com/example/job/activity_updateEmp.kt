package com.example.job

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.job.databinding.ActivityUpdateEmpBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class activity_updateEmp : AppCompatActivity() {
    private lateinit var binding: ActivityUpdateEmpBinding
    private lateinit var database: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateEmpBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.backBtn3.setOnClickListener{
            startActivity(Intent(this, EmpMainActivity::class.java))
        }
        binding.updateButton.setOnClickListener {
            val referencePhone = binding.referencePhone.text.toString()
            val updateName = binding.updateName.text.toString()
            val updateOperator = binding.updateOperator.text.toString()
            val updateLocation = binding.updateLocation.text.toString()
            updateData(referencePhone,updateName,updateOperator,updateLocation)
        }
    }
    private fun updateData(phone: String, name: String, operator: String, location: String) {
        database = FirebaseDatabase.getInstance().getReference("Emp")
        val user = mapOf<String,String>(
            "name" to name,
            "operator" to operator,
            "location" to location
        )
        database.child(phone).updateChildren(user).addOnSuccessListener {
            binding.referencePhone.text.clear()
            binding.updateName.text.clear()
            binding.updateOperator.text.clear()
            binding.updateLocation.text.clear()
            Toast.makeText(this,"Successfully Updated",Toast.LENGTH_SHORT).show()
        }.addOnFailureListener{
            Toast.makeText(this,"Failed to Update",Toast.LENGTH_SHORT).show()
        }}
}