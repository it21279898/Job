package com.example.job

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import customClasses.JobListAdapter

class AllJobs : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_jobs)

        // Load all the jobs
        val databaseRef = FirebaseDatabase
            .getInstance("https://quick-jobs-fe3b2-default-rtdb.firebaseio.com/")
            .getReference("Jobs");
        println(databaseRef);

        val jobList = mutableListOf<customClasses.Job>();
        val recyclerView = findViewById<RecyclerView>(R.id.recyler_view_job_all);

        // Set the layout manager
        val layoutManager = LinearLayoutManager(applicationContext);
        recyclerView.layoutManager = layoutManager;

        // Adapter config
        val adapter = JobListAdapter(jobList);
        recyclerView.adapter = adapter;

        databaseRef.addValueEventListener(object : ValueEventListener {
            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChange(snapshot: DataSnapshot) {
                for (jobSnapshot in snapshot.children) {
                    val job = jobSnapshot.getValue(customClasses.Job::class.java);
                    if (job != null) {
                        jobList.add(job);
                    };
                }
                println(jobList);
                adapter.notifyDataSetChanged();
            }

            override fun onCancelled(error: DatabaseError) {
                println("Error on all jobs loading");
            }
        })

        // Redirect to add new job
        val redirectToAddNewJob = findViewById<FloatingActionButton>(R.id.floating_action_button);
        redirectToAddNewJob.setOnClickListener {
            val intent = Intent(this, AddNewJob::class.java)
            startActivity(intent)
        }
    }
}