package com.example.siswaapps

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.siswaapps.db.Student
import com.example.siswaapps.repo.StudentRepository
import kotlinx.coroutines.launch

class StudentViewModel(private val repository: StudentRepository) : ViewModel() {
    fun getAllStudents(): LiveData<List<Student>> = repository.getAllStudents()

    fun searchStudents(query: String): LiveData<List<Student>> = repository.searchStudents(query)

    fun getStudentById(id: Int): LiveData<Student> {
        return repository.getStudentById(id)
    }

    fun insert(student: Student) {
        viewModelScope.launch {
            repository.insert(student)
        }
    }

    fun delete(student: Student) {
        viewModelScope.launch {
            repository.delete(student)
        }
    }

    fun update(student: Student) {
        viewModelScope.launch {
            repository.update(student)
        }
    }

}