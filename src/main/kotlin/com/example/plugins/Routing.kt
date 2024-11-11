package com.example.plugins


import com.example.CreateStudent
import com.example.repository.StudentRepository
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.*
import io.ktor.server.request.receiveParameters
import io.ktor.server.response.*
import io.ktor.server.routing.routing
import io.ktor.server.resources.*


fun Application.configureRouting() {


    val db = StudentRepository()

    println(CreateStudent)

    routing {

        post<CreateStudent> {
           // val parameter = call.receive<Parameters>()

            val parameter = call.receiveParameters()

            val name = parameter["name"]?:return@post call.respondText(
                text = "MISSING FIELD",
                status = HttpStatusCode.BadRequest
            )

            val age = parameter["age"]?:return@post call.respondText(
                text = "MISSING FIELD",
                status = HttpStatusCode.BadRequest
            )

            try {
                val student = db.insert(name,age.toInt())

                student?.userID.let {
                    call.respond(status = HttpStatusCode.OK,student!!)
                }

            }catch (e: Throwable){
                call.respondText("$e")
            }

        }


        get<CreateStudent> {

            try {
                val studentList = db.getAllStudents()
                call.respond(HttpStatusCode.OK, studentList!!)
            }catch (e: Throwable){
                call.respondText("$e")
            }
        }


        get<CreateStudent.ID> {request->

           // val id = call.parameters["id"] ?: return@get call.respondText("NO ID Found", status = HttpStatusCode.BadRequest)

            val id = request.id

            try {
                val studentById = db.getStudentByID(id.toInt())
                call.respond(HttpStatusCode.OK,studentById!!)
            }catch (e: Throwable){
                call.respondText("$e")
            }

        }


        delete<CreateStudent.ID> {request->
            //val id = call.parameters["id"] ?: return@delete call.respondText("NO ID FOUND", status = HttpStatusCode.BadRequest)
           val id = request.id
            val result = db.deleteByID(id.toInt())

            try {
                if (result == 1){
                    call.respondText("id $id deleted")
                }
                else{
                    call.respondText("id not found")
                }
            }catch (e: Throwable){
                call.respondText("$e")
            }


        }

        put<CreateStudent.ID> {request->

            val id = request.id
            val parameters = call.receiveParameters()

            val name = parameters["name"] ?: return@put call.respondText("MISSING FIELD", status = HttpStatusCode.BadRequest)
            val age = parameters["age"] ?: return@put call.respondText("MISSING FIELD", status = HttpStatusCode.BadRequest)

            try {

               val result = db.update(id.toInt(),name,age.toInt())

                if (result == 1){
                    call.respondText("$id updated successfully")
                }else{
                    call.respondText("something went wrong")
                }

            }catch (e: Throwable){
                call.respondText("$e")
            }



        }


    }


}


