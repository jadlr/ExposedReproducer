package org.example

import org.jetbrains.exposed.v1.core.ReferenceOption
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.core.dao.id.LongIdTable
import org.jetbrains.exposed.v1.dao.LongEntity
import org.jetbrains.exposed.v1.dao.LongEntityClass
import org.jetbrains.exposed.v1.datetime.CurrentTimestamp
import org.jetbrains.exposed.v1.datetime.timestamp
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
object LeaderboardScoresTable : LongIdTable("leaderboard_scores") {
    val name = varchar("name", length = 32)
    val score = long("score").nullable()
    val createdAt = timestamp("created_at").defaultExpression(CurrentTimestamp)
    val modifiedAt = timestamp("modified_at").defaultExpression(CurrentTimestamp)

    val leaderboard =
        reference(
            name = "leaderboard_id",
            refColumn = LeaderboardsTable.id,
            onDelete = ReferenceOption.CASCADE
        )
}

@OptIn(ExperimentalTime::class)
class LeaderboardScoreEntity(id: EntityID<Long>) : LongEntity(id) {
    companion object Companion : LongEntityClass<LeaderboardScoreEntity>(LeaderboardScoresTable)

    var name by LeaderboardScoresTable.name
    var score by LeaderboardScoresTable.score
    var createdAt by LeaderboardScoresTable.createdAt
    var modifiedAt by LeaderboardScoresTable.modifiedAt

    var leaderboard by LeaderboardEntity referencedOn LeaderboardScoresTable.leaderboard
}