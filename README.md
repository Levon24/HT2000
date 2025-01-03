# HT2000

HT-2000 is a CO2 analyzer equipped with temperature and humidity sensors.
Data from HT-2000 can be retrieved via USB cable.

Here a java application based on spring 2.x for counting CO2, temperature and humidity measures and store it in db.
Application should work on jetty 9x from debian standard repository.

For GUI will be used grafana to showing stored in database data.
![screenshot](img/grafana.png)

All it should works on Orange PI Zero 2 with 2gb+ ram memory.

## Database
Current database is MariaDB (fork of MySQL).
In directory scripts you could find a script init.sql to init database.
You can run it in command line by command `sudo mysql < init.sql`.
This script should create database `ht2000`, user to connect to database and tables.

## USB
To give necessary rights need to add udev rule.
Just copy file `55-ht2000.rules` to directory `/etc/udev/rules.d/`.
After reboot user in group users will have rights to access to HT-2000.

## Build
Build process preparing a war file for jetty application server.
Test part will check db and functionality, so you need to install mysql db on pc or in docker environment.
Run command `mvn clean package` to build war file.
If you want to skip testing use `mvn clean package -DskipTests`.

## Grafana
Grafana can be installed from grafana repository.
Here is docs https://grafana.com/docs/grafana/latest/setup-grafana/installation/debian/

After install you can use mariadb as grafana db too.
The next step is - adding datasource to connect to ht2000 database.
After that you can create dashboard HT2000 monitoring or simple import example from [file](grafana/HT2000-1735536781738.json).

## Hardware
So all project you can see here ![photo](img/photo.jpg)
* First it is device HT2000.
* Orange PI 2w
* UPS 5v

## Wifi
When orange pi lost connection to WiFi network manager did not reconnect to WiFi.
So, you need manually run following commands:
```
nmcli device set wlan0 autoconnect yes
sudo nmcli connection modify {YOU-WIFI-NETWORK-NAME} connection.autoconnect yes
```

## References
Thanks for following guys who researched this device:
* https://github.com/eschava/HT2000-java
* https://github.com/gsuberland/HT2000Lib
* https://github.com/temper8/HT2000Viewer
* https://github.com/tomvanbraeckel/slab_ht2000/
* https://globalblindspot.blogspot.com/2016/08/reverse-engineering-slab-ht2000-co2_11.html

## License
Released under MIT license.
