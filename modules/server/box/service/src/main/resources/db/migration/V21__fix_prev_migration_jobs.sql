ALTER TABLE job_player_task ADD COLUMN is_task_completed BOOLEAN DEFAULT FALSE NOT NULL;
ALTER TABLE job_player DROP COLUMN is_task_completed;