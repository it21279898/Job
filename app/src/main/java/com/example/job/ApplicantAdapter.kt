package com.example.job

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.job.Tn_ApplicantModel

class ApplicantAdapter(private val applicantList: ArrayList<Tn_ApplicantModel>) :
    RecyclerView.Adapter<ApplicantAdapter.ViewHolder>(){

    private lateinit var mListener : onItemClickListener

    interface onItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(clickListener: onItemClickListener){
        mListener = clickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ApplicantAdapter.ViewHolder {
        val itemView  = LayoutInflater.from(parent.context).inflate(R.layout.applicant_list_item, parent, false)
        return ViewHolder(itemView, mListener)
    }

    override fun onBindViewHolder(holder: ApplicantAdapter.ViewHolder, position: Int) {
        val currentApplicant = applicantList[position]
        holder.tvAppName.text = currentApplicant.name
    }

    override fun getItemCount(): Int {
        return applicantList.size
    }
    class ViewHolder(itemView: View, clickListener: onItemClickListener) : RecyclerView.ViewHolder(itemView){

        val tvAppName : TextView = itemView.findViewById(R.id.tvApplicantName)

        init {
            itemView.setOnClickListener{
                clickListener.onItemClick(adapterPosition)
            }
        }
    }




}