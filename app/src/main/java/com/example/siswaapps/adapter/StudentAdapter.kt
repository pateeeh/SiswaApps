package com.example.siswaapps.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.siswaapps.DetailStudentActivity
import com.example.siswaapps.databinding.ItemStudentBinding
import com.example.siswaapps.db.Student

class StudentAdapter : ListAdapter<Student, StudentAdapter.StudentViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val binding = ItemStudentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StudentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        val student = getItem(position)
        holder.bind(student)
    }

    class StudentViewHolder(private val binding: ItemStudentBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(student: Student) {
            binding.tvNis.text = student.nis
            binding.tvName.text = student.fullName

            binding.root.setOnClickListener {
                val context = binding.root.context
                val intent = Intent(context, DetailStudentActivity::class.java).apply {
                    putExtra(DetailStudentActivity.EXTRA_STUDENT_ID, student.id)
                    putExtra(DetailStudentActivity.EXTRA_STUDENT_NIS, student.nis)
                    putExtra(DetailStudentActivity.EXTRA_STUDENT_NAME, student.fullName)
                }
                context.startActivity(intent)
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Student>() {
        override fun areItemsTheSame(oldItem: Student, newItem: Student): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Student, newItem: Student): Boolean {
            return oldItem == newItem
        }
    }
}