@file:Suppress("DEPRECATION")

package com.example.job

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.job.databinding.ActivityAddcategoryBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class Addcategory : AppCompatActivity() {

    //view binding
    private lateinit var binding: ActivityAddcategoryBinding

    //firebase auth
    private lateinit var auth: FirebaseAuth

    //progress dialog
    @Suppress("DEPRECATION")
    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddcategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        //configure progress dialog
        @Suppress("DEPRECATION")
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Please wait...")
        progressDialog.setCanceledOnTouchOutside(false)

        //handle click, go button
        binding.backBtn.setOnClickListener{
            @Suppress("DEPRECATION")
            onBackPressed()
        }

        binding.submitBtn.setOnClickListener{
            validateDate()
        }
    }

    private var category = ""

    private fun validateDate() {
        //validate data
        category = binding.categoryEt.text.toString().trim()

        if (category.isEmpty()){
            Toast.makeText(this, "Enter Category....", Toast.LENGTH_SHORT).show()
        }
        else{
            addCategoryFirebase()
        }
    }

    private fun addCategoryFirebase() {
        //show program
        progressDialog.show()

        //get timestamp
        val timestamp = System.currentTimeMillis()

        //setup data to add in firebase db
        val hashMap = HashMap<String, Any>()
        hashMap["id"] = "$timestamp"
        hashMap["category"] = category
        hashMap["timestamp"] = timestamp
        hashMap["uid"] = "${auth.uid}"

        //add to firebase db: Database root > category > categoryId > category info
        val ref = FirebaseDatabase.getInstance().getReference("Categories")
        ref. child("$timestamp")
            .setValue(hashMap)
            .addOnSuccessListener {
                //added successfully
                progressDialog.dismiss()
                Toast.makeText(this, "Added Successfully...", Toast.LENGTH_SHORT).show()

            }
            .addOnFailureListener { e->

                //failed to add
                progressDialog.dismiss()
                Toast.makeText(this, "Failed to add due to ${e.message}", Toast.LENGTH_SHORT).show()

            }
    }
}