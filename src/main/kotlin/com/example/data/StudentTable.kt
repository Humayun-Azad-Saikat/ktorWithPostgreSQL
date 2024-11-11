package com.example.data

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object StudentTable: Table() {

    val userID: Column<Int> = integer("userID").autoIncrement()
    val name: Column<String> = varchar("name",500)
    val age: Column<Int> = integer("age")

    override val primaryKey: PrimaryKey = PrimaryKey(userID)

}