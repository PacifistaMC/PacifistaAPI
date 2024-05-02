ALTER TABLE discord_link ADD COLUMN linking_key VARCHAR(250) constraint UK_discord_link_linking_key unique;
