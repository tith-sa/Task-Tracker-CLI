package org.example.repository

import org.example.config.DatabaseConfig
import org.example.model.Task
import org.example.model.TaskStatus
import java.sql.Statement
import java.time.LocalDate

class TaskRepository {

    fun saveTask(title: String,dueDate: LocalDate): Task {
        val sql = """
            INSERT INTO tasks (title,status,dueDate) 
            VALUES (?,?,?) 
        """.trimIndent()

        DatabaseConfig.getConnection().use { connection ->
            connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS).use { statement ->
                statement.setString(1, title)
                statement.setInt(2, TaskStatus.TODO.ordinal)
                statement.setString(3, dueDate.toString())

                statement.executeUpdate()

                statement.generatedKeys.use { generatedKeys ->
                    if (generatedKeys.next()) {
                        val generatedId = generatedKeys.getInt(1)
                        return Task(
                            id = generatedId,
                            title = title,
                            status = TaskStatus.TODO,
                            dueDate = dueDate
                        )
                    } else {
                        throw Exception("Creating task failed, no ID obtained.")
                    }
                }
            }
        }
    }
}
