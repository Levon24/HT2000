CREATE TABLE events (
  id BIGINT NOT NULL AUTO_INCREMENT,
  device TINYINT NOT NULL,
  timestamp TIMESTAMP NOT NULL,
  co2 INT,
  temperature DOUBLE,
  humidity DOUBLE,
  PRIMARY KEY (id)
);
