package com.example.job

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

class CvDetails : AppCompatActivity() {

    private lateinit var tvIds: TextView
    private lateinit var tvName: TextView
    private lateinit var tvEmail: TextView
    private lateinit var tvNic: TextView
    private lateinit var tvPhoneNumber: TextView
    private lateinit var tvSkills: TextView
    private lateinit var tvEdu: TextView
    private lateinit var tvWork: TextView
    private lateinit var btnUpdateS : Button
    private lateinit var btnDeleteS : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cv_details)

        initView()
        setValuesToViews()

        btnUpdateS.setOnClickListener {
            openUpdateDialog(
                intent.getStringExtra("cvId").toString(),
                intent.getStringExtra("name").toString()
            )
        }

        btnDeleteS.setOnClickListener {
            deleteRecord(
                intent.getStringExtra("cvId").toString()
            )
        }
    }

    private fun initView(){
        tvIds = findViewById(R.id.tvIDs)
        tvName = findViewById(R.id.tvNameS)
        tvEmail = findViewById(R.id.tvEmailS)
        tvNic = findViewById(R.id.tvNic)
        tvPhoneNumber = findViewById(R.id.tvphoneS)
        tvSkills = findViewById(R.id.tvSkills)
        tvEdu = findViewById(R.id.tvEdus)
        tvWork  = findViewById(R.id.work1)

        btnUpdateS = findViewById(R.id.btnUpdateS)
        btnDeleteS = findViewById(R.id.btnDeleteS)

    }
    private  fun setValuesToViews(){
        tvIds.text = intent.getStringExtra("cvId")
        tvName.text = intent.getStringExtra("name")
        tvEmail.text = intent.getStringExtra("email")
        tvNic.text = intent.getStringExtra("nic")
        tvPhoneNumber.text = intent.getStringExtra("phone")
        tvSkills.text = intent.getStringExtra("skills")
        tvEdu.text = intent.getStringExtra("education")
        tvWork.text = intent.getStringExtra("work")

    }

    private fun deleteRecord(
        id: String
    ){
        val dbRef = FirebaseDatabase.getInstance().getReference("CV").child(id)
        val mTask = dbRef.removeValue()

        mTask.addOnSuccessListener {
            Toast.makeText(this, "CV data deleted", Toast.LENGTH_LONG).show()

            val intent = Intent(this, CvFetchData::class.java)
            finish()
            startActivity(intent)
        }.addOnFailureListener{ error ->
            Toast.makeText(this, "Deleting Err ${error.message}", Toast.LENGTH_LONG).show()
        }
    }

    private fun openUpdateDialog(
        cvId : String,
        name: String
    ){
        val mDialog = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val mDialogView = inflater.inflate(R.layout.cv_update_details, null)

        mDialog.setView(mDialogView)

        val cvTName = mDialogView.findViewById<EditText>(R.id.etcvName)
        val cvTEmail = mDialogView.findViewById<EditText>(R.id.etcvEmail)
        val cvTNic = mDialogView.findViewById<EditText>(R.id.etcvNic)
        val cvTNumber = mDialogView.findViewById<EditText>(R.id.etcvNumber)
        val cvTSkills = mDialogView.findViewById<EditText>(R.id.etcvSkill)
        val cvTEducation = mDialogView.findViewById<EditText>(R.id.etcvEdu)
        val cvTWork = mDialogView.findViewById<EditText>(R.id.etcvWork)
        val updateBtn = mDialogView.findViewById<Button>(R.id.btncvUpdateData)

        cvTName.setText(intent.getStringExtra("name").toString())
        cvTEmail.setText(intent.getStringExtra("email").toString())
        cvTNic.setText(intent.getStringExtra("nic").toString())
        cvTNumber.setText(intent.getStringExtra("phone").toString())
        cvTSkills.setText(intent.getStringExtra("skills").toString())
        cvTEducation.setText(intent.getStringExtra("education").toString())
        cvTWork.setText(intent.getStringExtra("work").toString())

        mDialog.setTitle("Updating $cvTName Record")

        val alertDialog = mDialog.create()
        alertDialog.show()

        updateBtn.setOnClickListener {
            updateCvData(
                cvId,
                cvTName.text.toString(),
                cvTEmail.text.toString(),
                cvTNic.text.toString(),
                cvTNumber.text.toString(),
                cvTSkills.text.toString(),
                cvTEducation.text.toString(),
                cvTWork.text.toString()

            )
            Toast.makeText(applicationContext,"CV Data Updated", Toast.LENGTH_LONG).show()

            //setting updated data to our textView
            tvName.text = cvTName.text.toString()
            tvEmail.text = cvTEmail.text.toString()
            tvNic.text = cvTEmail.text.toString()
            tvPhoneNumber.text = cvTNumber.text.toString()
            tvSkills.text = cvTSkills.text.toString()
            tvEdu.text = cvTEducation.text.toString()
            tvWork.text = cvTWork.text.toString()

            alertDialog.dismiss()
        }

    }

    private fun updateCvData(
        id:String,
        name:String,
        email:String,
        nic:String,
        phone:String,
        skills:String,
        education:String,
        work:String
    ){
        val dbRef = FirebaseDatabase.getInstance().getReference("CV").child(id)
        val cvInfo = CvModel(id,name,email,nic,phone,skills,education,work)

        dbRef.setValue(cvInfo)
    }
}