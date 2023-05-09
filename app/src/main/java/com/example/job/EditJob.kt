package com.example.job

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase

class EditJob : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_job);

        val btn_click_me = findViewById(R.id.go_back_btn) as ImageButton
        // set on-click listener
        btn_click_me.setOnClickListener {
            setContentView(R.layout.activity_all_jobs)
        }

        // Set the data to the fields
        val jobTitle = findViewById<EditText>(R.id.JobTitle);
        val jobDescription = findViewById<EditText>(R.id.description);

        println("JOB ID: " +intent.getStringExtra("jobId").toString());
        jobTitle.setText(intent.getStringExtra("jobTitle"));
        jobDescription.setText(intent.getStringExtra("jobDescription"));

        // Initialize Firebase Realtime Database
        val databaseInit = FirebaseDatabase.getInstance("https://quick-jobs-fe3b2-default-rtdb.firebaseio.com/")

        // Edit job
        val editJobButton = findViewById<Button>(R.id.editButton);
        // set on-click listener
        editJobButton.setOnClickListener {
            val database = databaseInit.getReference("Jobs");

            val jobTitle = findViewById<EditText>(R.id.JobTitle).text.toString();
            val jobDescription = findViewById<EditText>(R.id.description).text.toString();

            if (jobTitle.isEmpty() || jobDescription.isEmpty()) {
                Toast.makeText(this,"Fields can not be empty", Toast.LENGTH_SHORT).show();
            } else {
                val updateData =
                    mapOf<String, String>("title" to jobTitle, "description" to jobDescription);

                // Get job data from firebase
                var oldTitle = intent.getStringExtra("jobId").toString();
                database.child(oldTitle).updateChildren(updateData)
                    .addOnSuccessListener {
                        // Update successful
                        Toast.makeText(this, "Job successfully updated", Toast.LENGTH_SHORT).show();
                    }
                    .addOnFailureListener { e ->
                        // Update failed
                        Toast.makeText(this, "Error on job update", Toast.LENGTH_SHORT).show();
                    }

                // Redirect to the all jobs activity
                startActivity(Intent(this, AllJobs::class.java));
            }
        }

        // Delete job
        val deleteButton = findViewById<Button>(R.id.deleteButton);
        // set on-click listener
        deleteButton.setOnClickListener {
            val database = databaseInit.getReference("Jobs");
            var jobId = intent.getStringExtra("jobId").toString();
            database.child(jobId).removeValue();

            Toast.makeText(this,"Job successfully deleted", Toast.LENGTH_SHORT).show();
            // Redirect to the all jobs activity
            startActivity(Intent(this, AllJobs::class.java));
        }

        val goBackButton = findViewById<ImageButton>(R.id.go_back_btn);
        goBackButton.setOnClickListener {
            val intent = Intent(this, AllJobs::class.java)
            startActivity(intent)
        }
    }
}