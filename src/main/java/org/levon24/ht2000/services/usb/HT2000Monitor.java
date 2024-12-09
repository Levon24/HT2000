package org.levon24.ht2000.services.usb;

/*
 * User: levon
 * Date: 08.12.2024
 * Time: 19:52
 */

import org.levon24.ht2000.models.Event;
import org.levon24.ht2000.usb.HT2000Manager;
import org.levon24.ht2000.services.db.EventService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.usb4java.Device;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class HT2000Monitor {
  private final static Logger logger = LoggerFactory.getLogger(HT2000Monitor.class);
  private final static HT2000Manager manager = HT2000Manager.getInstance();
  private final List<HT2000Device> ht2000Devices = new ArrayList<>();
  private final EventService eventService;

  @Autowired
  public HT2000Monitor(EventService eventService) {
    this.eventService = eventService;
  }

  @PostConstruct
  private void init() {
    byte pos = 0;

    final List<Device> devices = manager.scan();
    for (Device device : devices) {
      final HT2000Device ht2000Device = new HT2000Device(pos);
      if (ht2000Device.open(device)) {
        ht2000Devices.add(ht2000Device);
        logger.info("Device: {} opened.", device);
      }
      pos++;
    }
  }

  @PreDestroy
  private void close() {
    for (HT2000Device ht2000Device : ht2000Devices) {
      ht2000Device.close();
    }

    ht2000Devices.clear();
  }

  @Scheduled(initialDelay = 5, fixedRate = 30, timeUnit = TimeUnit.SECONDS)
  private void readState() {
    boolean reconnect = false;

    for (HT2000Device ht2000Device : ht2000Devices) {
      final HT2000State ht2000State = ht2000Device.readState();
      if (ht2000State == null) {
        reconnect = true;
      } else {
        final Event event = new Event();
        event.setDevice(ht2000Device.getNumber());
        event.setTimestamp(ht2000State.timestamp());
        event.setCo2(ht2000State.co2());
        event.setTemperature(ht2000State.temperature());
        event.setHumidity(ht2000State.humidity());

        final Event s = eventService.save(event);
        logger.info("Saved: {}.", s);
      }
    }

    if (reconnect) {
      logger.info("Reconnecting...");
      close();
      init();
    }
  }
}
