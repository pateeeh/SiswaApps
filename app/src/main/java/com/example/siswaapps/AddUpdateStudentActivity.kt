package com.example.siswaapps

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import kotlin.text.isEmpty
import kotlin.text.trim
import android.widget.Toast
import com.example.siswaapps.databinding.ActivityAddUpdateStudentBinding
import com.example.siswaapps.db.Student


class AddUpdateStudentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddUpdateStudentBinding
    private val studentViewModel: StudentViewModel by viewModels {
        StudentViewModelFactory((application as SiswaApps).repository)
    }
    private var studentId: Int = 0

    companion object {
        const val EXTRA_STUDENT_ID = "extra_student_id"
        const val EXTRA_STUDENT_NIS = "extra_student_nis"
        const val EXTRA_STUDENT_NAME = "extra_student_name"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddUpdateStudentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        checkUpdateMode()
        saveOrUpdate()
    }

    private fun checkUpdateMode() {
        studentId = intent.getIntExtra(EXTRA_STUDENT_ID, 0)
        val nis = intent.getStringExtra(EXTRA_STUDENT_NIS)
        val name = intent.getStringExtra(EXTRA_STUDENT_NAME)

        if (studentId != 0 && nis != null && name != null) {
            binding.edtNis.setText(nis)
            binding.edtName.setText(name)
            binding.btnAdd.text = "Update"
        } else {
            binding.btnAdd.text = "Add"
        }
    }

    private fun saveOrUpdate() {
        binding.btnAdd.setOnClickListener {
            val nis = binding.edtNis.text.toString().trim()
            val fullName = binding.edtName.text.toString().trim()

            if (nis.isEmpty() || fullName.isEmpty()) {
                Toast.makeText(this, "NIS dan Nama Lengkap harus diisi", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (studentId != 0) {
                val updatedStudent = Student(id = studentId, nis = nis, fullName = fullName)
                studentViewModel.update(updatedStudent)
                Toast.makeText(this, "Data siswa berhasil diupdate", Toast.LENGTH_SHORT).show()
            } else {
                val newStudent = Student(nis = nis, fullName = fullName)
                studentViewModel.insert(newStudent)
                Toast.makeText(this, "Data siswa berhasil disimpan", Toast.LENGTH_SHORT).show()
            }
            finish()
        }
    }
}