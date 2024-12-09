package org.levon24.ht2000.services.usb;

/*
 * User: levon
 * Date: 09.12.2024
 * Time: 10:37
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.usb4java.Device;
import org.usb4java.DeviceHandle;
import org.usb4java.LibUsb;

import java.util.concurrent.atomic.AtomicBoolean;

public class HT2000Device {
  private final static Logger logger = LoggerFactory.getLogger(HT2000Device.class);
  private final byte number;
  private final DeviceHandle handle = new DeviceHandle();
  private final AtomicBoolean claimed = new AtomicBoolean(false);

  public HT2000Device(byte number) {
    this.number = number;
  }

  public boolean open(Device device) {
    int codeOpen = LibUsb.open(device, handle);
    if (codeOpen < 0) {
      logger.error("Can't open usb device: {}. Code: {}. Error: {}.", device, codeOpen, LibUsb.strError(codeOpen));
      return false;
    }

    boolean active = (LibUsb.kernelDriverActive(handle, number) == 1);
    if (active) {
      int codeDetach = LibUsb.detachKernelDriver(handle, number);
      if (codeDetach < 0) {
        logger.error("Can't detach usb device: {}. Code: {}. Error: {}.", device, codeDetach, LibUsb.strError(codeDetach));
        return false;
      }
    }

    int codeClaim = LibUsb.claimInterface(handle, number);
    if (codeClaim < 0) {
      logger.error("Can't claim usb device: {}. Code: {}. Error: {}.", device, codeClaim, LibUsb.strError(codeClaim));
      return false;
    }
    claimed.set(true);

    return true;
  }

  public void close() {
    if (claimed.get()) {
      int codeRelease = LibUsb.releaseInterface(handle, number);
      if (codeRelease < 0) {
        logger.error("Can't release usb device: {}. Code: {}. Error: {}.", handle, codeRelease, LibUsb.strError(codeRelease));
      }
    }

    LibUsb.close(handle);
  }

  public boolean getClaimed() {
    return claimed.get();
  }
}
