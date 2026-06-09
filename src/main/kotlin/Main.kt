package org.example

import org.example.migration.CreateTaskTable
import org.example.repository.TaskRepository
import org.example.service.TaskService
import java.time.LocalDate
import java.time.format.DateTimeParseException
import java.util.Scanner

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
fun main() {
    CreateTaskTable.create()

    val taskService = TaskService(TaskRepository())

    val reader = Scanner(System.`in`)

    while (true) {
        println(" ====== Menu ====== ")
        println("1: Add Task")
        val choice = reader.nextLine()

        when (choice) {
            "1" -> {
                println("Add Task")
                print("Input title : ")
                val titleInput = reader.nextLine()

                print("Input dueDate (YYYY-MM-DD) : ")
                val dueDateInput = reader.nextLine()

                try {
                    val dueDate: LocalDate = LocalDate.parse(dueDateInput)
                    taskService.createTask(titleInput, dueDate)
                } catch (e: DateTimeParseException) {
                    println("due date is invalid format!")
                }
            }
        }
    }



}