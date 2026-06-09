package org.example.service

import org.example.model.Task
import org.example.repository.TaskRepository
import java.time.LocalDate
import java.time.format.DateTimeParseException

class TaskService (
    private val taskRepository: TaskRepository
) {

   fun createTask(title: String, dueDate: LocalDate): Task? {
       if (title.isBlank()) {
           println("title cannot be blank")
           return null
       }


       val today = LocalDate.now()
       if (dueDate.isBefore(today)) {
           println("due date cannot be before the current date")
           return null
       }
       val savedTask = taskRepository.saveTask(title,dueDate)

       println("Added task successfully")
       return savedTask

   }

}