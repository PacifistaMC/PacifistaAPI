CREATE DATABASE IF NOT EXISTS pacifista_api;
CREATE DATABASE IF NOT EXISTS pacifista_api_docker;

CREATE USER 'pacifista'@'%' IDENTIFIED BY 'pacifista';
GRANT ALL PRIVILEGES ON *.* TO 'pacifista'@'%';