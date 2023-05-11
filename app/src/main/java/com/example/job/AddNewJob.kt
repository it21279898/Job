package com.example.job

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.job.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

data class Job(val title: String, val description: String, val id: String)

class AddNewJob : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding;
    private lateinit var auth: FirebaseAuth;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new_job)

        // Initialize Firebase Realtime Database
        val database1 = FirebaseDatabase.getInstance("https://quick-jobs-fe3b2-default-rtdb.firebaseio.com/")

        val addNewJobSubmit = findViewById<Button>(R.id.SubmitBtn);
        // set on-click listener
        addNewJobSubmit.setOnClickListener {
            val database = database1.getReference("Jobs");

            val jobId = generateUniqueId(database);
            val jobTitle = findViewById<EditText>(R.id.JobTitle).text.toString();
            val jobDescription = findViewById<EditText>(R.id.description).text.toString();

            if (jobTitle.isEmpty() || jobDescription.isEmpty()) {
                Toast.makeText(this,"Fields can not be empty", Toast.LENGTH_SHORT).show();
            } else {
                val newJob = Job(jobTitle, jobDescription, jobId);

                // Store data in to database
                database.child(jobId).setValue(newJob);

                Toast.makeText(this,"Job successfully added", Toast.LENGTH_SHORT).show();

                // Redirect to the all jobs activity
                startActivity(Intent(this, AllJobs::class.java));
            }
        }

        val goBackButton = findViewById<ImageButton>(R.id.go_back_btn);
        goBackButton.setOnClickListener {
            val intent = Intent(this, AllJobs::class.java)
            startActivity(intent)
        }
    }

    fun generateUniqueId(databaseRef: DatabaseReference): String {
        val newRef = databaseRef.push()
        return newRef.key ?: ""
    }
}