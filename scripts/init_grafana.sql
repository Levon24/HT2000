-- init database
CREATE SCHEMA grafana;
CREATE USER 'grafana'@'localhost' IDENTIFIED BY 'd13932943ee25127';
GRANT ALL PRIVILEGES ON grafana.* to 'grafana'@'localhost';
