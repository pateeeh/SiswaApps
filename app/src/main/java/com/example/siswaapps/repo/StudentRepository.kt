package com.example.siswaapps.repo

import androidx.lifecycle.LiveData
import com.example.siswaapps.db.Student
import com.example.siswaapps.db.StudentDao

class StudentRepository(private val studentDao: StudentDao) {

    fun getAllStudents(): LiveData<List<Student>> = studentDao.getAllStudents()

    fun getStudentById(id: Int): LiveData<Student> {
        return studentDao.getStudentById(id)
    }

    fun searchStudents(query: String): LiveData<List<Student>> {
        val searchQuery = "%$query%"
        return studentDao.searchStudents(searchQuery)
    }

    suspend fun insert(student: Student) {
        studentDao.insert(student)
    }

    suspend fun update(student: Student) {
        studentDao.update(student)
    }

    suspend fun delete(student: Student) {
        studentDao.delete(student)
    }

}