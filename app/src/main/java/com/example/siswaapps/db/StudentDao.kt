package com.example.siswaapps.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface StudentDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(student: Student)

    @Delete
    suspend fun delete(student: Student)

    @Query("SELECT * FROM student ORDER BY full_name ASC")
    fun getAllStudents(): LiveData<List<Student>>

    @Query("SELECT * FROM student WHERE nis LIKE :query OR full_name LIKE :query ORDER BY full_name ASC")
    fun searchStudents(query: String): LiveData<List<Student>>
}