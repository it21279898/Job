package com.example.job

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.job.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

data class Job(val title: String, val description: String)

class AddNewJob : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding;
    private lateinit var auth: FirebaseAuth;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new_job)

//        auth = FirebaseAuth.getInstance();

        //Auth

        // Initialize Firebase Realtime Database
        val database1 = FirebaseDatabase.getInstance("https://quick-jobs-fe3b2-default-rtdb.firebaseio.com/")

        val addNewJobSubmit = findViewById<Button>(R.id.SubmitBtn);
        // set on-click listener
        addNewJobSubmit.setOnClickListener {
            val jobTitle = findViewById<EditText>(R.id.JobTitle).text.toString();
            val jobDescription = findViewById<EditText>(R.id.description).text.toString();

            val newJob = Job(jobTitle, jobDescription)
            val database = database1.getReference("user");

            database.child("jobData").push().setValue(newJob)
        }
    }
}