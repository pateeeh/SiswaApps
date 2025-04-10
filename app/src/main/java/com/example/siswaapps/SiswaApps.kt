package com.example.siswaapps

import android.app.Application
import com.example.siswaapps.db.StudentDatabase
import com.example.siswaapps.repo.StudentRepository

class SiswaApps: Application() {
    // untuk inisialisasi database dan repository
    val database by lazy { StudentDatabase.getDatabase(this) }
    val repository by lazy { StudentRepository(database.studentDao()) }
}