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

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.sql.Timestamp;
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

  public HT2000State readState() {
    // https://github.com/gsuberland/HT2000Lib
    final ByteBuffer buffer = ByteBuffer.allocateDirect(61);

    int code = LibUsb.controlTransfer(handle, (byte) (LibUsb.ENDPOINT_IN | LibUsb.REQUEST_TYPE_CLASS | LibUsb.RECIPIENT_INTERFACE),
      (byte) 0x01, (short) 0x0105, (short) 0, buffer, 5000);
    if (code < 0) {
      logger.error("Can't transfer state from usb device: {}. Code: {}. Error: {}.", handle, code, LibUsb.strError(code));
      return null;
    }

    buffer.order(ByteOrder.BIG_ENDIAN);

    long ts = Integer.toUnsignedLong(buffer.getInt(1)) - 2004198720;
    Timestamp timestamp = new Timestamp(ts * 1000);
    double temperature = (buffer.getShort(7) - 400) / 10.0;
    double humidity = buffer.getShort(9) / 10.0;
    short co2 = buffer.getShort(24);
    logger.info("Timestamp: {}, CO2: {}, Temperature: {}, Humidity: {}.", timestamp, co2, temperature, humidity);

    return new HT2000State(timestamp, co2, temperature, humidity);
  }

  public byte getNumber() {
    return number;
  }

  public boolean getClaimed() {
    return claimed.get();
  }
}
