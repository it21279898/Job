package com.example.job

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CvAdapter (private val cvList: ArrayList<CvModel>) :
    RecyclerView.Adapter<CvAdapter.ViewHolder>(){

    private lateinit var mListener : onItemClickListener

    interface onItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(clickListener: onItemClickListener){
        mListener = clickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView  = LayoutInflater.from(parent.context).inflate(R.layout.cv_application_list, parent, false)
        return ViewHolder(itemView, mListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentApplicant = cvList[position]
        holder.cvAppName.text = currentApplicant.name
    }

    override fun getItemCount(): Int {
        return cvList.size
    }
    class ViewHolder(itemView: View, clickListener: onItemClickListener) : RecyclerView.ViewHolder(itemView){

        val cvAppName : TextView = itemView.findViewById(R.id.tvCvName)

        init {
            itemView.setOnClickListener{
                clickListener.onItemClick(adapterPosition)
            }
        }
    }




}