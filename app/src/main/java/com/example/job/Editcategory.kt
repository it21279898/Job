package com.example.job

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase

class Editcategory : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editcategory)

        // Set the data to the fields
        val currentCategory = findViewById<EditText>(R.id.CatTitle)
        val newTitle = findViewById<EditText>(R.id.description12)

        println("Cate ID: " +intent.getStringExtra("cateId").toString())
        currentCategory.setText(intent.getStringExtra("currCateTitle"))
        newTitle.setText(intent.getStringExtra("cateTitle"))

        // Initialize Firebase Realtime Database
        val databaseInit = FirebaseDatabase.getInstance("https://quick-jobs-fe3b2-default-rtdb.firebaseio.com/")

        // Edit job
        val editJobButton = findViewById<Button>(R.id.editButton12)
        // set on-click listener
        editJobButton.setOnClickListener {
            val database = databaseInit.getReference("Categories")

            val currentCategory = findViewById<EditText>(R.id.CatTitle).text.toString()
            val newTitle = findViewById<EditText>(R.id.description12).text.toString()

            val updateData = mapOf("title" to currentCategory, "newCatTitle" to newTitle)

            // Get category data from firebase
            val oldTitle = intent.getStringExtra("cateId").toString()
            database.child(oldTitle).updateChildren(updateData)
                .addOnSuccessListener {
                    // Update successful
                    Toast.makeText(this,"Category successfully updated", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    // Update failed
                    Toast.makeText(this,"Error on category update", Toast.LENGTH_SHORT).show()
                }

            // Redirect to the all categories activity
//            startActivity(Intent(this, Allcategory::class.java))
        }

        // Delete job
        val deleteButton = findViewById<Button>(R.id.deleteButton12)
        // set on-click listener
        deleteButton.setOnClickListener {
            val database = databaseInit.getReference("Categories")
            val cateId = intent.getStringExtra("cateId").toString()
            database.child(cateId).removeValue()

            Toast.makeText(this,"Category successfully deleted", Toast.LENGTH_SHORT).show()
            // Redirect to the all category activity
//            startActivity(Intent(this, Allcategory::class.java))
        }
    }
}