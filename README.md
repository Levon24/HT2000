# HT2000

HT-2000 is a CO2 analyzer equipped with temperature and humidity sensors.
Data from HT-2000 can be retrieved via USB cable.

Here a java application based on spring 2.x for counting CO2, temperature and humidity measures and store it in db.
Application should work on jetty 9x from debian standard repository.

For GUI will be used grafana to showing stored in database data. 

All it should work on Orange PI Zero 2w.

## Database
In directory scripts you could find a script init.sql to init database.
You can run it in command line by command `sudo mysql < init.sql`
This script should create database `ht2000`, user to connect to database and tables.

## USB
To give necessary rights need to add udev rule.
Just copy file `55-ht2000.rules` to directory `/etc/udev/rules.d/`.
After reboot user in group users will have rights to access to HT-2000.

## Build
Test part has checking db and dev functionality.
Run command `mvn clean package` to build war file.
If you want to skip testing use `mvn clean package -DskipTests`.
