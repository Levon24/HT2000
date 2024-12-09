-- init database
CREATE SCHEMA ht2000;
CREATE USER 'ht2000'@'localhost' IDENTIFIED BY 'd13932943ee25167';
GRANT ALL PRIVILEGES ON ht2000.* to 'ht2000'@'localhost';

-- change db
USE ht2000;

-- init tables
SOURCE tables/events.sql
