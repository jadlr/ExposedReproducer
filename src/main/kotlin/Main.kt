package org.example

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.jdbc.Database
import org.jetbrains.exposed.v1.jdbc.SchemaUtils
import org.jetbrains.exposed.v1.jdbc.transactions.transaction

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
fun main() {
    val db = Database.connect("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1", driver = "org.h2.Driver")
    transaction(db) {
        SchemaUtils.create(LeaderboardsTable, LeaderboardScoresTable)

        LeaderboardEntity.new {
            key = "abc123"
            adminKey = "123abc"
            title = "Math Quiz"
        }

        val board = LeaderboardEntity.find { LeaderboardsTable.adminKey eq "123abc" }.firstOrNull()
        board?.scores?.forEach {
            println(it.name)
        }
    }

    val config = HikariConfig().apply {
        jdbcUrl = "jdbc:postgresql://localhost:5432/example_db?user=example_app&password=test"
        driverClassName = "org.postgresql.Driver"
        maximumPoolSize = 10
    }

    val datasource = HikariDataSource(config)
    val postgresDB = Database.connect(datasource = datasource)

    transaction(postgresDB) {
        SchemaUtils.create(LeaderboardsTable, LeaderboardScoresTable)

        LeaderboardEntity.new {
            key = "abc123"
            adminKey = "123abc"
            title = "Math Quiz"
        }

        val board = LeaderboardEntity.find { LeaderboardsTable.adminKey eq "123abc" }.firstOrNull()
        board?.scores?.forEach {
            println(it.name)
        }
    }
}