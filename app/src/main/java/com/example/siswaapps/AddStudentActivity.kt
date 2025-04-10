package com.example.siswaapps

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.siswaapps.databinding.ActivityAddStudentBinding
import kotlin.text.isEmpty
import kotlin.text.trim
import android.widget.Toast
import com.example.siswaapps.db.Student


class AddStudentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddStudentBinding

    private val studentViewModel: StudentViewModel by viewModels {
        StudentViewModelFactory((application as SiswaApps).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddStudentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        addStudent()
    }

    private fun addStudent() {
        binding.btnAdd.setOnClickListener {
            val nis = binding.edtNis.text.toString().trim()
            val fullName = binding.edtName.text.toString().trim()

            if (nis.isEmpty() || fullName.isEmpty()) {
                Toast.makeText(this, "NIS dan Nama Lengkap harus diisi", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val student = Student(nis = nis, fullName = fullName)
            studentViewModel.insert(student)

            binding.edtNis.text.clear()
            binding.edtName.text.clear()
            Toast.makeText(this, "Data siswa berhasil disimpan", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}