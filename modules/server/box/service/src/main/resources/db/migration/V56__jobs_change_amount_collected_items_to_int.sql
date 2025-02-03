ALTER TABLE job_player_task
    ALTER COLUMN collected_task_items DROP DEFAULT;

ALTER TABLE job_player_task
    ALTER COLUMN collected_task_items
        SET DATA TYPE INT
        USING (CASE WHEN collected_task_items THEN 99999 ELSE 0 END);

ALTER TABLE job_player_task
    ALTER COLUMN collected_task_items SET DEFAULT 0;
