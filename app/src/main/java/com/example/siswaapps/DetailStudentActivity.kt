package com.example.siswaapps

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.siswaapps.databinding.ActivityDetailStudentBinding
import com.example.siswaapps.db.Student

class DetailStudentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailStudentBinding
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
        binding = ActivityDetailStudentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        studentId = intent.getIntExtra(EXTRA_STUDENT_ID, 0)

        // Observasi data siswa berdasarkan ID
        observeStudentData()

        binding.btnDelete.setOnClickListener {
            deleteDialog()
        }

        binding.btnUpdate.setOnClickListener {
            val intent = Intent(this, AddUpdateStudentActivity::class.java).apply {
                putExtra(AddUpdateStudentActivity.EXTRA_STUDENT_ID, studentId)
                putExtra(AddUpdateStudentActivity.EXTRA_STUDENT_NIS, binding.tvNis.text.toString())
                putExtra(AddUpdateStudentActivity.EXTRA_STUDENT_NAME, binding.tvName.text.toString())
            }
            startActivity(intent)
        }
    }

    private fun observeStudentData() {
        studentViewModel.getStudentById(studentId).observe(this) { student ->
            if (student != null) {
                binding.tvNis.text = student.nis
                binding.tvName.text = student.fullName
            }
        }
    }

    private fun deleteDialog() {
        AlertDialog.Builder(this)
            .setTitle("Hapus Data Siswa")
            .setMessage("Yakin mau hapus data siswa ini?")
            .setPositiveButton("Ya") { _, _ ->
                deleteStudent()
            }
            .setNegativeButton("Tidak", null)
            .show()
    }

    private fun deleteStudent() {
        val student = Student(id = studentId, nis = binding.tvNis.text.toString(), fullName = binding.tvName.text.toString())
        studentViewModel.delete(student)
        Toast.makeText(this, "Data siswa berhasil dihapus", Toast.LENGTH_SHORT).show()
        finish()
    }
}