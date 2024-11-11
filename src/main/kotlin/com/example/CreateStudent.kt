package com.example

import io.ktor.resources.*

@Resource("/student")
class CreateStudent(){

    @Resource("{id}")
    class ID(val parent: CreateStudent = CreateStudent(),val id: Long)
}