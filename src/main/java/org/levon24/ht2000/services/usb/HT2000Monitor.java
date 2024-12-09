package org.levon24.ht2000.services.usb;

/*
 * User: levon
 * Date: 08.12.2024
 * Time: 19:52
 */

import org.levon24.ht2000.usb.HT2000Manager;
import org.levon24.ht2000.services.db.EventService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.usb4java.Device;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.ArrayList;
import java.util.List;

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
  private void destroy() {
    for (HT2000Device ht2000Device : ht2000Devices) {
      ht2000Device.close();
    }
  }
}
