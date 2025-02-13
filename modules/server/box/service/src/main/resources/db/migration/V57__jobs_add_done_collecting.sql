ALTER TABLE job_player_task ADD COLUMN done_collecting_items BOOLEAN NOT NULL DEFAULT FALSE;

UPDATE job_player_task SET done_collecting_items = TRUE WHERE collected_task_items = 99999;
