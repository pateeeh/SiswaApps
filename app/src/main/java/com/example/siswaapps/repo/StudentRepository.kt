package com.example.siswaapps.repo

import androidx.lifecycle.LiveData
import com.example.siswaapps.db.Student
import com.example.siswaapps.db.StudentDao

class StudentRepository(private val studentDao: StudentDao) {

    fun getAllStudents(): LiveData<List<Student>> = studentDao.getAllStudents()

    fun searchStudents(query: String): LiveData<List<Student>> {
        val searchQuery = "%$query%"
        return studentDao.searchStudents(searchQuery)
    }

    suspend fun insert(student: Student) {
        studentDao.insert(student)
    }
}