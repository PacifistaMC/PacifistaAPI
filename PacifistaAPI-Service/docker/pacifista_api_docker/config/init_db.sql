CREATE DATABASE IF NOT EXISTS pacifista_api;
CREATE DATABASE IF NOT EXISTS pacifista_api_docker;

CREATE USER 'funix'@'%' IDENTIFIED BY 'funix';
GRANT ALL PRIVILEGES ON *.* TO 'funix'@'%';
