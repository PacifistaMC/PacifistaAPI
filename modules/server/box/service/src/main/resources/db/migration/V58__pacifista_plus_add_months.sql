ALTER TABLE pacifista_plus_subscription ADD COLUMN months INTEGER NOT NULL DEFAULT 1;

ALTER TABLE shop_articles ADD COLUMN markdown_description VARCHAR(10000) not null default '';

DROP TABLE claim_phantom_prevention_blocks;
