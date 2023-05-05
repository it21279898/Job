package com.example.job

import android.app.Dialog
import android.app.appsearch.StorageInfo
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.job.databinding.ActivityProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.StorageReference
import kotlin.text.Typography.times

class profileActivity : AppCompatActivity() {
    //view binding
    private lateinit var binding : ActivityProfileBinding

    private lateinit var firebaseAuth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()



        binding.backBtn.setOnClickListener{
            startActivity(Intent(this,HomeActivity::class.java))
        }

        binding.profileeditBtn.setOnClickListener{
            startActivity(Intent(this,ProfileUpdateActivity::class.java))
        }
    }



//    private fun readData(userID: String){
//        databaseReference = FirebaseDatabase.getInstance().getReference("https://quick-jobs-fe3b2-default-rtdb.firebaseio.com/User")
//        databaseReference.child(userID).get().addOnSuccessListener {
//            if(it.exists()){
//                val name = it.child("name").value
//                val email=it.child("email").value
//                val username =it.child("username").value
//                val password = it.child("password").value
//
//                binding.readnameTv.text=name.toString()
//                binding.reademailTv.text=email.toString()
//                binding.readusernameTv.text=username.toString()
//                binding.readpasswordTv.text=password.toString()
//            }
//        }.addOnFailureListener {
//            Toast.makeText(this,"Something went wrong",Toast.LENGTH_SHORT).show()
//        }
//    }
}