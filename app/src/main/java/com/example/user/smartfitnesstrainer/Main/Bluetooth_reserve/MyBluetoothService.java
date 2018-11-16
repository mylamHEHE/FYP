package com.example.user.smartfitnesstrainer.Main.Bluetooth_reserve;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import java.io.*;
import java.util.Set;
import java.util.UUID;

import static android.util.JsonToken.NAME;
import static com.google.android.gms.plus.PlusOneDummyView.TAG;
public class MyBluetoothService extends Activity{

    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothDevice mBluetoothDevice;
    private AcceptThread sendThread;
    private final UUID my_uuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private final static int REQUEST_ENABLE_BT = 1;
    int step_count = 0;

    public MyBluetoothService(String address) {
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            Log.d("test", "Device doesn't support Bluetooth");
        }
        Log.d("test", address.toString());
        mBluetoothDevice = mBluetoothAdapter.getRemoteDevice(address);
        if (mBluetoothDevice == null) {
            Log.d("test", "Null Pointer Exception!!!");
        } else {
            Log.d("test", "I am " + mBluetoothDevice.getName());

        }
        if(step_count%2 == 0){
            sendThread = new AcceptThread();
            Log.d("test", "if " + step_count);
        }
        else{
            step_count++;
            Log.d("test", "else " + step_count);
        }
    }

    public boolean isConnecting(){
        if(mBluetoothAdapter.isDiscovering())
            return true;
        if (sendThread.btSocket.isConnected())
            return true;
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private class AcceptThread extends Thread {
        BluetoothServerSocket mmServerSocket;
        BluetoothSocket btSocket = null;

        public AcceptThread() {
            // Use a temporary object that is later assigned to mmServerSocket
            // because mmServerSocket is final.
            BluetoothServerSocket tmp = null;
            try {
                // MY_UUID is the app's UUID string, also used by the client code.
                tmp = mBluetoothAdapter.listenUsingRfcommWithServiceRecord("Smart Fitness Trainer", my_uuid);
                btSocket = mBluetoothDevice.createRfcommSocketToServiceRecord(my_uuid);
                if(btSocket.isConnected())
                    Log.d("test", "Connected -1");
                else
                    Log.d("test", "Not Connected -1");
                btSocket.connect();
                //cancel();
                if(btSocket.isConnected())
                    Log.d("test", "Connected -2");
                else
                    Log.d("test", "Not Connected -2");


            } catch (IOException e) {
                Log.e("test", "Socket's listen() method failed", e);
            }
            mmServerSocket = tmp;
        }

        public void run() {

        }

        // Closes the connect socket and causes the thread to finish.
        public void cancel() {
            try {
                btSocket.close();
            } catch (IOException e) {
            }
        }
    }/*
    private class ConnectedThread extends Thread {
        private final BluetoothSocket mmSocket;
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;

        public ConnectedThread(BluetoothSocket socket) {
            mmSocket = socket;
            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            // 获得socket的流信息
            try {
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            } catch (IOException e) { }

            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }

        public void run() {
            byte[] buffer = new byte[1024];  //缓冲字符数组

            // 保持通讯
            while (true) {
                try {
                    // Read from the InputStream
                    int bytes = mmInStream.read(buffer);
                    // 发送包含的信息给UI
                    String MESSAGE_READ=null;
                    Handler mHandler=null;
                    mHandler.obtainMessage(Integer.parseInt(MESSAGE_READ), bytes, -1, buffer)
                            .sendToTarget();
                } catch (IOException e) {
                    break;
                }
            }
        }

        // 调用该方法去发送信息
        public void write(byte[] bytes) {
            try {
                mmOutStream.write(bytes);
            } catch (IOException e) { }
        }

        // 调用该方法关闭连接
        public void cancel() {
            try {
                mmSocket.close();
            } catch (IOException e) { }
        }
    }

*/
    final BroadcastReceiver blReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if(BluetoothDevice.ACTION_FOUND.equals(action)){
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
            }
        }
    };
}
