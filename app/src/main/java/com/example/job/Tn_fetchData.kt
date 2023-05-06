package com.example.job

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

class Tn_fetchData : AppCompatActivity() {

    private lateinit var applicantRecycleView: RecyclerView
    private lateinit var tvLoadingData: TextView
    private lateinit var applicantList: ArrayList<Tn_ApplicantModel>
    private lateinit var dbRef : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tn_fetch_data)

        applicantRecycleView = findViewById(R.id.rvApplicant)
        applicantRecycleView.layoutManager = LinearLayoutManager(this)
        applicantRecycleView.setHasFixedSize(true)
        tvLoadingData = findViewById(R.id.tvLoadingData)

        applicantList = arrayListOf<Tn_ApplicantModel>()

        getApplicantData()


    }

    private fun getApplicantData(){
        applicantRecycleView.visibility = View.GONE
        tvLoadingData.visibility= View.VISIBLE

        dbRef = FirebaseDatabase.getInstance().getReference("Applicant")

        dbRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                applicantList.clear()
                if(snapshot.exists()){
                    for(applicantSanp in snapshot.children){
                        val applicantData = applicantSanp.getValue(Tn_ApplicantModel::class.java)
                        applicantList.add(applicantData!!)
                    }

                    val mAdapter = ApplicantAdapter(applicantList)
                    applicantRecycleView.adapter = mAdapter

                    mAdapter.setOnItemClickListener(object : ApplicantAdapter.onItemClickListener{
                        override fun onItemClick(position: Int) {
                            val intent = Intent(this@Tn_fetchData, Tn_ApplicantDetails::class.java)

                            //put extras
                            intent.putExtra("applicantId", applicantList[position].applicantId)
                            intent.putExtra("name", applicantList[position].name)
                            intent.putExtra("email", applicantList[position].email)
                            intent.putExtra("phoneNo", applicantList[position].phone)
                            intent.putExtra("jobName", applicantList[position].job)
                            intent.putExtra("position", applicantList[position].position)
                            intent.putExtra("status", applicantList[position].status)

                            startActivity(intent)

                        }

                    })

                    applicantRecycleView.visibility =View.VISIBLE
                    tvLoadingData.visibility= View.GONE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}