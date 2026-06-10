package org.example.repository

import org.example.config.DatabaseConfig
import org.example.model.Task
import org.example.model.TaskStatus
import java.sql.Statement

class TaskRepository {


    fun saveTask(title: String, status: TaskStatus, dueDate: String): Task {
        val sql = """
            INSERT INTO tasks (title,status,dueDate) 
            VALUES (?,?,?) 
        """.trimIndent()

        DatabaseConfig.getConnection().use { connection ->
            connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS).use { statement ->
                statement.setString(1, title)
                statement.setString(2, status.name)
                statement.setString(3, dueDate)

                statement.executeUpdate()

                statement.generatedKeys.use { generatedKeys ->
                    if (generatedKeys.next()) {
                        val generatedId = generatedKeys.getInt(1)
                        return Task(
                            id = generatedId,
                            title = title,
                            status = status,
                            dueDate = dueDate
                        )
                    } else {
                        throw Exception("Creating task failed, no ID obtained.")
                    }
                }
            }
        }
    }

    fun getAllTask(): List<Task> {
       val sql = """
           SELECT * FROM tasks
       """.trimIndent()

        val listTasks = mutableListOf<Task>()
        DatabaseConfig.getConnection().use { connection ->
            connection.prepareStatement(sql).use { statement ->
                statement.executeQuery().use { resultSet ->
                    while (resultSet.next()) {
                        val statusStr = resultSet.getString("status") ?: "TODO"
                        val statusEnum = try {
                            TaskStatus.valueOf(statusStr.uppercase())
                        } catch (e: IllegalArgumentException) {
                            TaskStatus.TODO
                        }
                        listTasks.add(Task(
                            id = resultSet.getInt("id"),
                            title = resultSet.getString("title"),
                            status = statusEnum,
                            dueDate = resultSet.getString("dueDate")
                        ))
                    }
                }
            }
        }
        return listTasks
    }

    fun getTaskById(id: Int): Task? {
        val sql = """
            SELECT * FROM tasks WHERE id = ?
        """.trimIndent()

        DatabaseConfig.getConnection().use { connection ->
            connection.prepareStatement(sql).use { statement ->
                statement.setInt(1, id)
                statement.executeQuery().use { resultSet ->
                    if (resultSet.next()) {
                        val statusStr = resultSet.getString("status") ?: "TODO"
                        val statusEnum = try {
                            TaskStatus.valueOf(statusStr.uppercase())
                        } catch (e: IllegalArgumentException) {
                            TaskStatus.TODO
                        }
                        return Task(
                            id = resultSet.getInt("id"),
                            title = resultSet.getString("title"),
                            status = statusEnum,
                            dueDate = resultSet.getString("dueDate")
                        )
                    }
                    return null
                }
            }
        }
    }

    fun updateStatusTask(id: Int, newStatus: TaskStatus): Boolean {
        val sql = """
            UPDATE tasks SET status = ? WHERE id = ?
        """.trimIndent()

        DatabaseConfig.getConnection().use { connection ->
            connection.prepareStatement(sql).use { statement ->
                statement.setString(1, newStatus.name)
                statement.setInt(2, id)

                val rowsUpdated = statement.executeUpdate()
                return rowsUpdated > 0
            }
        }
    }

    fun deleteTask(id: Int): Boolean {
        val sql = """
            DELETE FROM tasks WHERE id = ?
        """.trimIndent()

        DatabaseConfig.getConnection().use { connection ->
            connection.prepareStatement(sql).use { statement ->
                statement.setInt(1, id)
                val rowsDeleted = statement.executeUpdate()
                return rowsDeleted > 0
            }
        }
    }

}
