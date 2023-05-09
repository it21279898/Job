package customClasses

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.job.EditJob
import com.example.job.R

class JobListAdapter(private val jobList: MutableList<Job>) :
    RecyclerView.Adapter<JobListAdapter.JobViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JobViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.job_template, parent, false)
        return JobViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: JobViewHolder, position: Int) {
        val currentJob = jobList[position]
        holder.jobTitleTextView.text = currentJob.title
        holder.jobDescription.text = currentJob.description

        holder.jobEditTemplate.setOnClickListener {
            val intent = Intent(holder.itemView.context, EditJob::class.java)
            intent.putExtra("jobId", currentJob.id)
            intent.putExtra("jobTitle", currentJob.title)
            intent.putExtra("jobDescription", currentJob.description)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount() = jobList.size

    inner class JobViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val jobTitleTextView: TextView = itemView.findViewById(R.id.job_title_text_view)
        val jobEditTemplate: TextView = itemView.findViewById(R.id.edit_btn_template)
        val jobDescription: TextView = itemView.findViewById(R.id.job_template_description)

    }
}
