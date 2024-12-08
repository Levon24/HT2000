-- init database
CREATE SCHEMA co2counter;
CREATE USER 'co2counter'@'localhost' IDENTIFIED BY 'd13932943ee25167';
GRANT ALL PRIVILEGES ON co2counter.* to 'co2counter'@'localhost';
