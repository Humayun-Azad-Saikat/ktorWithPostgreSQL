package com.example.repository

import com.example.dao.StudentDao
import com.example.data.Student
import com.example.data.StudentTable
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.statements.InsertStatement
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.update


//StudentDao just to implement sql queries in kotlin


class StudentRepository: StudentDao {
    override suspend fun insert(name: String, age: Int): Student? {
        var statement: InsertStatement<Number>? = null
        DataBaseFactory.dbQuery {
            statement = StudentTable.insert {
                it[StudentTable.name] = name
                it[StudentTable.age] = age
            }
        }
        return resultRowToStudent(statement?.resultedValues?.get(0))
    }

    override suspend fun getAllStudents(): List<Student>? {
       return DataBaseFactory.dbQuery {
            StudentTable.selectAll().mapNotNull {
                resultRowToStudent(it)
            }
        }
    }


    override suspend fun getStudentByID(userID: Int): Student? {

        return DataBaseFactory.dbQuery {
            StudentTable
                .selectAll()
                .where { StudentTable.userID eq userID  }
                .mapNotNull { resultRowToStudent(it) }
                .firstOrNull() as Student?
        }
    }


    override suspend fun deleteByID(userID: Int): Int {
       return DataBaseFactory.dbQuery {
            StudentTable.deleteWhere{ StudentTable.userID eq userID }
        }
    }

    override suspend fun update(userID: Int, name: String, age: Int): Int {
        return DataBaseFactory.dbQuery {
            StudentTable.update({ StudentTable.userID eq userID }){
                it[StudentTable.name] = name
                it[StudentTable.age] = age
            }
        }
    }


    //The function resultRowToPlayer is used for making our rows into the Student object.
    // [simply to say: just for passing student object in .mapOrNotNull{} block]
    private fun resultRowToStudent(row: ResultRow?): Student? {
        if (row != null){
            return Student(
                userID = row[StudentTable.userID],
                name = row[StudentTable.name],
                age = row[StudentTable.age]
            )
        }
        return null
    }

}