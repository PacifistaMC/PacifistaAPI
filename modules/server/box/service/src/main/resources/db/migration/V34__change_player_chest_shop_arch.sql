ALTER TABLE player_chest_shop DROP COLUMN itemSerialized;
ALTER TABLE player_chest_shop ADD COLUMN itemSerialized VARCHAR(250);

ALTER TABLE player_chest_shop DROP COLUMN price;

ALTER TABLE player_chest_shop ADD COLUMN price_buy double precision not null default 0.0;
ALTER TABLE player_chest_shop ADD COLUMN price_sell double precision not null default 0.0;

