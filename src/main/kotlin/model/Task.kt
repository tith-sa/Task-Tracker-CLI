package org.example.model

import java.time.LocalDate

data class Task(
    val id : Int,
    var title: String,
    var status: TaskStatus = TaskStatus.TODO,
    val dueDate: LocalDate
)
