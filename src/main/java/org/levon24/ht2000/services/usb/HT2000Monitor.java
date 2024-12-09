package org.levon24.ht2000.services.usb;

/*
 * User: levon
 * Date: 08.12.2024
 * Time: 19:52
 */

import org.levon24.ht2000.devices.HT2000Manager;
import org.levon24.ht2000.services.db.EventService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.usb4java.Device;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class HT2000Monitor {
  private final static Logger logger = LoggerFactory.getLogger(HT2000Monitor.class);
  private final static HT2000Manager manager = HT2000Manager.getInstance();
  private final EventService eventService;

  public HT2000Monitor(EventService eventService) {
    this.eventService = eventService;
  }

  @PostConstruct
  public void init() {
    final List<Device> devices = manager.scan();
    for (Device device : devices) {
      logger.info("Device: {}.", device);
    }
  }
}
