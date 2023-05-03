package com.example.job

import android.app.Dialog
import android.app.appsearch.StorageInfo
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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

    private lateinit var binding : ActivityProfileBinding
    private lateinit var databaseReference: DatabaseReference



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.backBtn.setOnClickListener{
            startActivity(Intent(this,HomeActivity::class.java))
        }

        binding.profileeditBtn.setOnClickListener{

                startActivity(Intent(this,ProfileUpdateActivity::class.java))
        }

        





    }
}