package com.example.dao

import com.example.data.Student

interface StudentDao {

    suspend fun insert(
        name: String,
        age: Int
    ): Student?

    suspend fun getAllStudents(): List<Student>?

    suspend fun getStudentByID(userID: Int): Student?

    suspend fun deleteByID(userID: Int): Int

    suspend fun update(
        userID: Int,
        name: String,
        age: Int
    ): Int


}
