# Exposed repeated order by clause reproducer

Start the DB: `docker-compose up -d --build`

Run the the program and look at the log output. 

H2:

```
2025-12-03 18:52:41.440 [main] DEBUG Exposed - SELECT LEADERBOARD_SCORES.ID, LEADERBOARD_SCORES."name", LEADERBOARD_SCORES.SCORE, LEADERBOARD_SCORES.CREATED_AT, LEADERBOARD_SCORES.MODIFIED_AT, LEADERBOARD_SCORES.LEADERBOARD_ID FROM LEADERBOARD_SCORES WHERE LEADERBOARD_SCORES.LEADERBOARD_ID = 1 ORDER BY LEADERBOARD_SCORES.SCORE DESC NULLS LAST
```

Postgres / HikariCP

```
2025-12-03 18:52:41.605 [main] DEBUG Exposed - SELECT leaderboard_scores.id, leaderboard_scores."name", leaderboard_scores.score, leaderboard_scores.created_at, leaderboard_scores.modified_at, leaderboard_scores.leaderboard_id FROM leaderboard_scores WHERE leaderboard_scores.leaderboard_id = 1 ORDER BY leaderboard_scores.score DESC NULLS LAST, leaderboard_scores.score DESC NULLS LAST
```


I'm not sure why it's only repeated twice here. In my production code it's repeated a lot more often.

# Fix: 

see: https://youtrack.jetbrains.com/issue/EXPOSED-950/Order-by-clause-is-repeated-hundredfold

pr: https://github.com/JetBrains/Exposed/pull/2682
