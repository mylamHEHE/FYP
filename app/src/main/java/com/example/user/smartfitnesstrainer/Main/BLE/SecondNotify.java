package com.example.user.smartfitnesstrainer.Main.BLE;

import com.vise.xsnow.event.IEvent;

public class SecondNotify implements IEvent {
    private byte[] data;
    private BluetoothLeDevice bluetoothLeDevice;
    private BluetoothGattChannel bluetoothGattChannel;

    public byte[] getData() {
        return data;
    }

    public SecondNotify setData(byte[] data) {
        this.data = data;
        return this;
    }

    public BluetoothLeDevice getBluetoothLeDevice() {
        return bluetoothLeDevice;
    }

    public SecondNotify setBluetoothLeDevice(BluetoothLeDevice bluetoothLeDevice) {
        this.bluetoothLeDevice = bluetoothLeDevice;
        return this;
    }

    public BluetoothGattChannel getBluetoothGattChannel() {
        return bluetoothGattChannel;
    }

    public SecondNotify setBluetoothGattChannel(BluetoothGattChannel bluetoothGattChannel) {
        this.bluetoothGattChannel = bluetoothGattChannel;
        return this;
    }
}
