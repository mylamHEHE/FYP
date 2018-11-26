package com.example.user.smartfitnesstrainer.Main.BLE;

import android.app.Service;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.content.Intent;
import android.util.Log;

import java.util.UUID;
/*
public class BatteryLevel extends Service {

    private static final UUID Battery_Service_UUID = UUID.fromString("0000180F-0000-1000-8000-00805f9b34fb");
    private static final UUID Battery_Level_UUID = UUID.fromString("00002a19-0000-1000-8000-00805f9b34fb");

    public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {

        if(status == BluetoothGatt.GATT_SUCCESS) {
            broadcastUpdate(ACTION_DATA_AVAILABLE, characteristic);
        }
    }


    private void broadcastUpdate(final String action, final BluetoothGattCharacteristic characteristic) {

        final Intent intent = new Intent(action);
        Log.v("characteristic.getStringValue(0) = " , characteristic.getIntValue(BluetoothGattCharacteristic.FORMAT_UINT8, 0));
        intent.putExtra(DeviceControl.EXTRAS_DEVICE_BATTERY, characteristic.getIntValue(BluetoothGattCharacteristic.FORMAT_UINT8, 0));
        sendBroadcast(intent);
    }

    public void getbattery() {

        BluetoothGattService batteryService = mBluetoothGatt.getService(Battery_Service_UUID);
        if(batteryService == null) {
            Log.d(TAG, "Battery service not found!");
            return;
        }

        BluetoothGattCharacteristic batteryLevel = batteryService.getCharacteristic(Battery_Level_UUID);
        if(batteryLevel == null) {
            Log.d(TAG, "Battery level not found!");
            return;
        }
        mBluetoothGatt.readCharacteristic(batteryLevel);
        Log.v(TAG, "batteryLevel = " + mBluetoothGatt.readCharacteristic(batteryLevel));
    }*/