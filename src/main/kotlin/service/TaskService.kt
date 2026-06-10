package org.example.service

import org.example.model.Task
import org.example.model.TaskStatus
import org.example.repository.TaskRepository
import org.example.utils.isBefore
import java.time.LocalDate

class TaskService (
    private val taskRepository: TaskRepository
) {

   fun createTask(title: String, dueDate: String): Task? {
       if (title.isBlank()) {
           println("title cannot be blank")
           return null
       }
       if (dueDate.isBefore()) {
           println("due date cannot be before the current date")
           return null
       }
       val status = TaskStatus.TODO
       val savedTask = taskRepository.saveTask(title,status,dueDate)

       println("Added task successfully")
       return savedTask
   }

    fun getAllTasks() {
        return taskRepository.getAllTask()
            .forEach {
                println("ID: ${it.id} | Title: ${it.title} | Status: ${it.status} | DueDate: ${it.dueDate}")
            }
    }

    fun getListTasks(status: TaskStatus) {
        return taskRepository.getAllTask()
            .filter { it.status == status }
            .forEach {
                println("ID: ${it.id} | Title: ${it.title} | Status: ${it.status} | DueDate: ${it.dueDate}")
            }
    }

    fun getTaskById(id: Int) : Task? {
        val task = taskRepository.getTaskById(id)
        if (task == null) {
            println("Error: Task ID $id not found in the database.")
        } else {
            println("ID: ${task.id} | Title: ${task.title} | Status: ${task.status} | Due Date: ${task.dueDate}")
        }
        return task
    }

    fun updateTask(id: Int, newTitle: String, newDueDate: String): Boolean {
        val currentTask = taskRepository.getTaskById(id)
        if (currentTask == null) {
            println("Error: Task ID $id not found. Cannot update.")
            return false
        }
        val finalTitle = newTitle.ifBlank {
            currentTask.title
        }
        val finalDueDate = newDueDate.ifBlank {
            currentTask.dueDate
        }
        if (finalDueDate.isBefore()) {
            println("Title cannot be before the current date")
            return false
        }
        val status = currentTask.status
        if (currentTask.status == TaskStatus.DONE) {
            println("The Task cannot update.It's already done.")
            return false
        }

        val updatedTask = taskRepository.updateTask(id, finalTitle, status,finalDueDate)
        println("Updated task successfully")
        return updatedTask


    }

    fun markInProgress(id: Int, status: TaskStatus = TaskStatus.IN_PROGRESS): Boolean {
        val task = taskRepository.getTaskById(id)
        if (task == null) {
            println("Error: Task ID $id not found in the database.")
            return false
        }
        if (task.status == TaskStatus.IN_PROGRESS) {
            println("Info: Task ID $id is already IN_PROGRESS. No changes made.")
            return false
        }

        if (task.status == TaskStatus.DONE) {
            println("Warning: Cannot change status. Task ID $id is already completed (DONE).")
            return false
        }
        val title = task.title
        val dueDate = task.dueDate
        val updatedStatus = taskRepository.updateTask(id,title,status,dueDate)
        println("Mark in_progress task status Successfully")
        return updatedStatus
    }

    fun markDone(id: Int, status: TaskStatus = TaskStatus.DONE): Boolean {
        val task = taskRepository.getTaskById(id)
        if (task == null) {
            println("Error: Task ID $id not found in the database.")
            return false
        }
        if (task.status == status) {
            println("Info: Task ID $id is already Done. No changes made.")
            return false
        }
        if (task.status == TaskStatus.TODO) {
            println("Processing: Cannot changing task status from TODO to DONE...")
            return false
        }
        val title = task.title
        val dueDate = task.dueDate
        val updatedStatus = taskRepository.updateTask(id,title,status,dueDate)
        println("Mark done task status Successfully")
        return updatedStatus
    }

    fun deleteTask(id: Int) : Boolean {
        val task = taskRepository.getTaskById(id)
        if (task == null) {
            println("Error: Task with ID $id not found in the database.")
            return false
        }
        if (task.status == TaskStatus.IN_PROGRESS) {
            println("task in_progress can't be deleted")
            return false
        }
        val deleted = taskRepository.deleteTask(id)
        println("Deleted task successfully: $deleted")
        return deleted
    }

}