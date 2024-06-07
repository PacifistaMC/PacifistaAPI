ALTER TABLE admin_shop_player_limit DROP COLUMN moneyGenerated;
ALTER TABLE admin_shop_player_limit ADD COLUMN money_generated double precision not null default 0.0;
