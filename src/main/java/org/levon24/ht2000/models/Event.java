package org.levon24.ht2000.models;

/*
 * User: levon
 * Date: 08.12.2024
 * Time: 19:27
 */

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "events")
public class Event {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Long id;

  @Column(name = "device")
  private Byte device;

  @Column(name = "timestamp")
  private Timestamp timestamp;

  @Column(name = "co2")
  private Short co2;

  @Column(name = "temperature")
  private Double temperature;

  @Column(name = "humidity")
  private Double humidity;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Byte getDevice() {
    return device;
  }

  public void setDevice(Byte device) {
    this.device = device;
  }

  public Timestamp getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(Timestamp timestamp) {
    this.timestamp = timestamp;
  }

  public Short getCo2() {
    return co2;
  }

  public void setCo2(Short co2) {
    this.co2 = co2;
  }

  public Double getTemperature() {
    return temperature;
  }

  public void setTemperature(Double temperature) {
    this.temperature = temperature;
  }

  public Double getHumidity() {
    return humidity;
  }

  public void setHumidity(Double humidity) {
    this.humidity = humidity;
  }

  @Override
  public String toString() {
    return "Event{" +
      "id=" + id +
      ", device=" + device +
      ", timestamp=" + timestamp +
      ", co2=" + co2 +
      ", temperature=" + temperature +
      ", humidity=" + humidity +
      '}';
  }
}
