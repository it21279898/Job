package com.example.job

import android.app.Activity
import android.app.ProgressDialog
import android.content.ContentValues
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.Menu
import android.widget.PopupMenu
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import com.example.job.databinding.ActivityProfileBinding
import com.example.job.databinding.ActivityProfileUpdateBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase

class ProfileUpdateActivity : AppCompatActivity() {

    private lateinit var binding :ActivityProfileUpdateBinding
    private lateinit var firebaseAuth:FirebaseAuth
    private var imageUri : Uri?=null
    private lateinit var progressDialog:ProgressDialog



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        progressDialog= ProgressDialog(this)
        progressDialog.setTitle("Please wait")
        progressDialog.setCanceledOnTouchOutside(false)


        firebaseAuth= FirebaseAuth.getInstance()
        loadUserInfor()

        binding.backBtn.setOnClickListener{
            onBackPressed()
        }
        binding.profileIv.setOnClickListener{
            showImageAttachMenu()
        }

        binding.updateBtn.setOnClickListener{
            validateData()
            startActivity(Intent(this,profileActivity::class.java))
        }

    }

    private var name =""
    private var username =""
    private var email =""
    private var password =""
    private fun validateData() {
        name =binding.nameEt.text.toString().trim()

        if(name.isEmpty() && username.isEmpty()&&email.isEmpty()&&password.isEmpty()){
            Toast.makeText(this,"Enter the all blank",Toast.LENGTH_SHORT).show()
        }


        val reference = FirebaseDatabase.getInstance().getReference("User")
        reference.child(firebaseAuth.uid!!)
            .updateChildren(hashMapOf())



    }




    private fun loadUserInfor() {
        val ref = FirebaseDatabase.getInstance().getReference("User")
        ref.child(firebaseAuth.uid!!)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {

                    val reEmail="${snapshot.child("reEmail").value}"
                    val rePassword="${snapshot.child("rePassword").value}"
                    val reUsername="${snapshot.child("reUsername").value}"
                    val rename="${snapshot.child("rename").value}"

                    binding.nameEt.setText(rename)
                    binding.EmalEt.setText(reEmail)
                    binding.usernameEt.setText(reUsername)
                    binding.passwordEt.setText(rePassword)
                }

                override fun onCancelled(error: DatabaseError) {

                }
            })
    }


    private fun showImageAttachMenu(){

        val popupMenu=androidx.appcompat.widget.PopupMenu(this,binding.profileIv)
        popupMenu.menu.add(Menu.NONE,0,0,"Camera")
        popupMenu.menu.add(Menu.NONE,0,0,"Gallery")
        popupMenu.show()

        popupMenu.setOnMenuItemClickListener { item->
            val id=item.itemId
            if (id==0){
                pickImageCamera()
            }
            else if (id==1){
                pickImageGallaery()
            }


            true

        }
    }

    private fun pickImageGallaery() {
        var intent = Intent(Intent.ACTION_PICK)
        intent.type="image/*"
        gallertActivityResultLauncheer.launch(intent)

    }

    private fun pickImageCamera() {

        val values = ContentValues()
        values.put(MediaStore.Images.Media.TITLE,"Temp_Title")
        values.put(MediaStore.Images.Media.DESCRIPTION,"Temp_Descrption")

        imageUri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,values)

        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri)
        cameraActivityResultLauncher.launch(intent)


    }

    private val cameraActivityResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult(),
        ActivityResultCallback<ActivityResult> {result ->
            if(result.resultCode==Activity.RESULT_OK){
                val data = result.data
                //imageUri=data!!.data

                binding.profileIv.setImageURI(imageUri)
            }
            else{
                Toast.makeText(this,"Cancelled",Toast.LENGTH_SHORT).show()
            }

        }
    )

    private val gallertActivityResultLauncheer =registerForActivityResult(
        ActivityResultContracts.StartActivityForResult(),
        ActivityResultCallback<ActivityResult> {result ->
            if(result.resultCode==Activity.RESULT_OK){
                val data = result.data
                imageUri=data!!.data

                binding.profileIv.setImageURI(imageUri)
            }
            else{
                Toast.makeText(this,"Cancelled",Toast.LENGTH_SHORT).show()
            }

        }
    )

}