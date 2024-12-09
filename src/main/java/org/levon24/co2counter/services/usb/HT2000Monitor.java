package org.levon24.co2counter.services.usb;

/*
 * User: levon
 * Date: 08.12.2024
 * Time: 19:52
 */

import org.levon24.co2counter.devices.HT2000Device;
import org.levon24.co2counter.services.db.EventService;
import org.springframework.stereotype.Service;
import org.usb4java.Device;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class HT2000Monitor {
  private final EventService eventService;

  public HT2000Monitor(EventService eventService) {
    this.eventService = eventService;
  }

  @PostConstruct
  public void init() {
    HT2000Device connection = new HT2000Device();
    List<Device> devices = connection.scan();
  }
}
