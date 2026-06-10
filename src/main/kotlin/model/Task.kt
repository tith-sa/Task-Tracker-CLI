package org.example.model


data class Task(
    val id : Int,
    var title: String,
    var status: TaskStatus = TaskStatus.TODO,
    val dueDate: String
)
