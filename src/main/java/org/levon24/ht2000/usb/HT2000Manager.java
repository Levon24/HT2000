package org.levon24.ht2000.usb;

/*
 * User: levon
 * Date: 08.12.2024
 * Time: 19:42
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.usb4java.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HT2000Manager {
  private final static Logger logger = LoggerFactory.getLogger(HT2000Manager.class);
  private final static short HT2000_VENDOR_ID = (short) 0x10c4;
  private final static short HT2000_PRODUCT_ID = (short) 0x82cd;
  private final static Context context = new Context();
  private final static int init = LibUsb.init(context);
  private static HT2000Manager instance;

  private HT2000Manager() {
    logger.info("Initialized");
  }

  public static synchronized HT2000Manager getInstance() {
    if (instance == null) {
      instance = new HT2000Manager();
    }
    return instance;
  }

  public List<Device> scan() {
    if (init < 0) {
      logger.error("Can't init usb library. Code: {}. Error: {}.", init, LibUsb.strError(init));
      return Collections.emptyList();
    }

    final DeviceList devices = new DeviceList();
    int codeList = LibUsb.getDeviceList(context, devices);
    if (codeList < 0) {
      logger.error("Can't get usb devices list. Code: {}. Error: {}.", codeList, LibUsb.strError(codeList));
      return Collections.emptyList();
    }

    try {
      final List<Device> list = new ArrayList<>();

      for (Device device : devices) {
        final DeviceDescriptor deviceDescriptor = new DeviceDescriptor();
        int codeDescriptor = LibUsb.getDeviceDescriptor(device, deviceDescriptor);
        if (codeDescriptor < 0) {
          logger.error("Can't get usb device descriptor. Code: {}. Error: {}.", codeDescriptor, LibUsb.strError(codeDescriptor));
          continue;
        }

        if (deviceDescriptor.idVendor() == HT2000_VENDOR_ID && deviceDescriptor.idProduct() == HT2000_PRODUCT_ID) {
          list.add(device);
          logger.info("Device: {} found.", device);
        }
      }

      return list;
    } finally {
      LibUsb.freeDeviceList(devices, false);
    }
  }
}
