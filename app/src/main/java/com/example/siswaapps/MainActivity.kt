package com.example.siswaapps

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.siswaapps.adapter.StudentAdapter
import com.example.siswaapps.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var studentAdapter: StudentAdapter
    private val studentViewModel: StudentViewModel by viewModels{
        StudentViewModelFactory((application as SiswaApps).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.fabAdd.setOnClickListener {
            val intent = Intent(this, AddUpdateStudentActivity::class.java)
            startActivity(intent)
        }

        getListStudent()
        searchStudent()
    }

    private fun getListStudent() {
        studentAdapter = StudentAdapter()
        binding.rvStudent.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = studentAdapter
        }

        studentViewModel.getAllStudents().observe(this) { students ->
            studentAdapter.submitList(students)
        }
    }

    private fun searchStudent() {
        binding.searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                val query = newText?.trim() ?: ""
                if (query.isNotEmpty()) {
                    studentViewModel.searchStudents(query).observe(this@MainActivity) { students ->
                        studentAdapter.submitList(students)
                    }
                } else {
                    studentViewModel.getAllStudents().observe(this@MainActivity) { students ->
                        studentAdapter.submitList(students)
                    }
                }
                return true
            }
        })
    }
}