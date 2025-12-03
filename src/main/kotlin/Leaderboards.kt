package org.example

import org.jetbrains.exposed.v1.core.SortOrder
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.core.dao.id.LongIdTable
import org.jetbrains.exposed.v1.dao.LongEntity
import org.jetbrains.exposed.v1.dao.LongEntityClass
import org.jetbrains.exposed.v1.datetime.CurrentTimestamp
import org.jetbrains.exposed.v1.datetime.timestamp
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
object LeaderboardsTable : LongIdTable("leaderboards") {
    val key = varchar("key", length = 40).uniqueIndex()

    val adminKey = varchar("admin_key", length = 40).uniqueIndex()
    val title = varchar("title", length = 32)

    val createdAt = timestamp("created_at").defaultExpression(CurrentTimestamp)
    val modifiedAt = timestamp("modified_at").defaultExpression(CurrentTimestamp)
}

@OptIn(ExperimentalTime::class)
class LeaderboardEntity(id: EntityID<Long>) : LongEntity(id) {
    companion object Companion : LongEntityClass<LeaderboardEntity>(LeaderboardsTable)

    var key by LeaderboardsTable.key

    var adminKey by LeaderboardsTable.adminKey
    var title by LeaderboardsTable.title
    var createdAt by LeaderboardsTable.createdAt
    var modifiedAt by LeaderboardsTable.modifiedAt

    val scores by LeaderboardScoreEntity referrersOn LeaderboardScoresTable.leaderboard orderBy (LeaderboardScoresTable.score to SortOrder.DESC_NULLS_LAST)
}