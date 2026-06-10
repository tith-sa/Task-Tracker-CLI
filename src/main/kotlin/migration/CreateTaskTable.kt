package org.example.migration

import org.example.config.DatabaseConfig


object CreateTaskTable {

    fun create(){
        val sql ="""
            CREATE TABLE IF NOT EXISTS tasks (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            title VARCHAR (255) NOT NULL,
            status VARCHAR(50) NOT NULL,
            dueDate VARCHAR(50) NOT NULL
            )
        """.trimIndent()

        DatabaseConfig
            .getConnection()
            .use { connection ->

                connection
                    .createStatement()
                    .execute(sql)

                println("Table created")
            }
    }


}