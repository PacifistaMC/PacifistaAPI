ALTER TABLE player_shop_item DROP COLUMN minecraftusername;

ALTER TABLE player_shop_item ADD COLUMN minecraft_username VARCHAR(200) not null default 'FunixGaming';
ALTER TABLE player_shop_item ADD COLUMN buyer_name VARCHAR(250);

ALTER TABLE admin_shop_item DROP COLUMN buyer_name;
