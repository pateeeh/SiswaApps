package com.example.siswaapps

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
    private var studentNis: String = ""
    private var studentName: String = ""

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
        studentNis = intent.getStringExtra(EXTRA_STUDENT_NIS) ?: ""
        studentName = intent.getStringExtra(EXTRA_STUDENT_NAME) ?: ""

        binding.tvNis.text = studentNis
        binding.tvName.text = studentName

        binding.btnDelete.setOnClickListener {
            deleteDialog()
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
        val student = Student(id = studentId, nis = studentNis, fullName = studentName)
        studentViewModel.delete(student)
        Toast.makeText(this, "Data siswa berhasil dihapus", Toast.LENGTH_SHORT).show()
        finish()
    }
}