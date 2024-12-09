package org.levon24.ht2000.services.usb;

/*
 * User: levon
 * Date: 09.12.2024
 * Time: 21:07
 */

import java.sql.Timestamp;

public record HT2000State(Timestamp timestamp, short co2, double temperature, double humidity) {

}
