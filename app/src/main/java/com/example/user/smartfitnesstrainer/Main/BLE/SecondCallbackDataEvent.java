package com.example.user.smartfitnesstrainer.Main.BLE;

import com.vise.xsnow.event.IEvent;

public class SecondCallbackDataEvent implements IEvent {
    private byte[] data;
    private boolean isSuccess;
    private BluetoothLeDevice bluetoothLeDevice;
    private BluetoothGattChannel bluetoothGattChannel;

    public SecondCallbackDataEvent setSuccess(boolean success) {
        isSuccess = success;
        return this;
    }

    public byte[] getData() {
        return data;
    }

    public SecondCallbackDataEvent setData(byte[] data) {
        this.data = data;
        return this;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public BluetoothLeDevice getBluetoothLeDevice() {
        return bluetoothLeDevice;
    }

    public SecondCallbackDataEvent setBluetoothLeDevice(BluetoothLeDevice bluetoothLeDevice) {
        this.bluetoothLeDevice = bluetoothLeDevice;
        return this;
    }

    public BluetoothGattChannel getBluetoothGattChannel() {
        return bluetoothGattChannel;
    }

    public SecondCallbackDataEvent setBluetoothGattChannel(BluetoothGattChannel bluetoothGattChannel) {
        this.bluetoothGattChannel = bluetoothGattChannel;
        return this;
    }
}
