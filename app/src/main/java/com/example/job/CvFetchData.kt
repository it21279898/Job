package com.example.job

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

class CvFetchData : AppCompatActivity() {

    private lateinit var cvRecycleView: RecyclerView
    private lateinit var tvLoadingData: TextView
    private lateinit var cvList: ArrayList<CvModel>
    private lateinit var dbcvRef : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cv_fetch_data)

        cvRecycleView = findViewById(R.id.cvApplicant)
        cvRecycleView.layoutManager = LinearLayoutManager(this)
        cvRecycleView.setHasFixedSize(true)
        tvLoadingData = findViewById(R.id.tvLoadData)

        cvList = arrayListOf<CvModel>()

        getCvData()


    }

    private fun getCvData(){
        cvRecycleView.visibility = View.GONE
        tvLoadingData.visibility= View.VISIBLE

        dbcvRef = FirebaseDatabase.getInstance().getReference("CV")

        dbcvRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                cvList.clear()
                if(snapshot.exists()){
                    for(applicantSanp in snapshot.children){
                        val cvData = applicantSanp.getValue(CvModel::class.java)
                        cvList.add(cvData!!)
                    }

                    val mAdapter = CvAdapter(cvList)
                    cvRecycleView.adapter = mAdapter

                    mAdapter.setOnItemClickListener(object : CvAdapter.onItemClickListener{
                        override fun onItemClick(position: Int) {
                            val intent = Intent(this@CvFetchData, CvDetails::class.java)

                            //put extras
                            intent.putExtra("cvId", cvList[position].cvId)
                            intent.putExtra("name", cvList[position].name)
                            intent.putExtra("email", cvList[position].email)
                            intent.putExtra("nic", cvList[position].nic)
                            intent.putExtra("phoneNo", cvList[position].phone)
                            intent.putExtra("skill", cvList[position].skills)
                            intent.putExtra("education", cvList[position].education)
                            intent.putExtra("work", cvList[position].work)

                            startActivity(intent)

                        }

                    })

                    cvRecycleView.visibility = View.VISIBLE
                    tvLoadingData.visibility= View.GONE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}