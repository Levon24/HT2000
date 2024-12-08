package org.levon24.co2counter.devices;

/*
 * User: levon
 * Date: 08.12.2024
 * Time: 19:42
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.usb4java.Context;
import org.usb4java.DeviceList;
import org.usb4java.LibUsb;

public class HT2000Device {
  private final static Logger logger = LoggerFactory.getLogger(HT2000Device.class);
  private final static Context context = new Context();
  private final static int init = LibUsb.init(context);

  public void scan() {
    if (init != LibUsb.SUCCESS) {
      logger.error("Can't init usb library. Code: {}. Error: {}.", init, LibUsb.strError(init));
      return;
    }

    final DeviceList devices = new DeviceList();


  }
}
