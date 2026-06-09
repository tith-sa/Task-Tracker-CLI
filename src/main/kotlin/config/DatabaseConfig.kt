package org.example.config

import java.sql.Connection
import java.sql.DriverManager

object DatabaseConfig {
    private const val URL = "jdbc:sqlite:task.sqlite"

    fun getConnection(): Connection {
        return DriverManager.getConnection(
            URL
        )
    }
}