package org.example

import org.example.migration.CreateTaskTable
import org.example.model.TaskStatus
import org.example.repository.TaskRepository
import org.example.service.TaskService
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
        println("2: Get All Task")
        println("3: Get Task by Status")
        println("4: Get Task By Id")
        println("5: Mark In_Progress Task")
        println("6: Mark Done Task")
        println("7: Delete Task")
        println("8: update Task")
        val choice = reader.nextLine()

        when (choice) {
            "1" -> {
                println("Add Task")
                print("Input title : ")
                val titleInput = reader.nextLine()

                print("Input dueDate (YYYY-MM-DD) : ")
                val dueDateInput = reader.nextLine()

                taskService.createTask(titleInput,dueDateInput)
            }
            "2" -> {
                println("List All Task")
                taskService.getAllTasks()

            }
            "3" ->{
                println("Get Todo Task")
                val statuses = TaskStatus.entries
                statuses.forEachIndexed { index, status ->
                    println("${index + 1}. ${status.name}")
                }
                print("Enter option number : ")
                val option = reader.nextLine().trim().toIntOrNull()

                if (option != null && option in 1..statuses.size) {
                    val selectedStatus = statuses[option - 1]

                  taskService.getListTasks(selectedStatus)
                } else {
                    println("Error: Wrong option number!")
                }
            }
            "4" -> {
                println("Get Task By Id")
                print("Input Id : ")
                val id = reader.nextLine().trim().toInt()
                taskService.getTaskById(id)
            }
            "5" ->{
                println("Mark In_progress Task")
                print("Input ID : ")
                val inputId = reader.nextLine().trim().toInt()
                taskService.markInProgress(inputId)
            }
            "6" -> {
                println("Mark Done Task")
                print("Input ID : ")
                val inputId = reader.nextLine().trim().toInt()
                taskService.markDone(inputId)
            }
            "7" -> {
                println("Delete Task")
                print("Input ID : ")
                val id = reader.nextLine().trim().toInt()
                taskService.deleteTask(id)
            }
            "8" -> {
                println("update Task")
                print("Input ID : ")
                val id = reader.nextLine().trim().toInt()

                print("Input Title : ")
                val title = reader.nextLine().trim()

                print("Input Due Date (YYYY-MM-DD) : ")
                val dueDate = reader.nextLine().trim()

                taskService.updateTask(id,title,dueDate)
            }
            else -> {
                break
            }
        }
    }
}