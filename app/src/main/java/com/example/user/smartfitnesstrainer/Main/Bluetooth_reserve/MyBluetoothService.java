package com.example.user.smartfitnesstrainer.Main.Bluetooth_reserve;
import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.user.smartfitnesstrainer.Main.BLE.BleUtil;
import com.example.user.smartfitnesstrainer.Main.BLE.BluetoothDeviceManager;
import com.example.user.smartfitnesstrainer.Main.BLE.BluetoothLeDevice;
import com.example.user.smartfitnesstrainer.Main.BLE.BluetoothLeDeviceStore;
import com.example.user.smartfitnesstrainer.Main.BLE.DeviceAdapter;
import com.example.user.smartfitnesstrainer.Main.BLE.DeviceControlActivity;
import com.example.user.smartfitnesstrainer.Main.BLE.DeviceDetailActivity;
import com.example.user.smartfitnesstrainer.Main.BLE.DeviceMirror;
import com.example.user.smartfitnesstrainer.Main.BLE.IScanCallback;
import com.example.user.smartfitnesstrainer.Main.BLE.PropertyType;
import com.example.user.smartfitnesstrainer.Main.BLE.ScanCallback;
import com.example.user.smartfitnesstrainer.Main.BLE.ViseBle;
import com.example.user.smartfitnesstrainer.R;
import com.vise.log.ViseLog;
import com.vise.xsnow.event.BusManager;
import com.vise.xsnow.permission.OnPermissionCallback;
import com.vise.xsnow.permission.PermissionManager;

import java.util.ArrayList;

public class MyBluetoothService {
    String address = "";
    Activity activity;
    private BluetoothLeDevice mDevice;
    private boolean gotDevice=false;
    Context context;
    public MyBluetoothService(String address,Context context,Activity activity) {
        this.address = address;
        this.activity = activity;
        BluetoothDeviceManager.getInstance().init(context);
        BusManager.getBus().register(this);
    }
    private static final int MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION = 100;
    private ListView deviceLv;
    private TextView scanCountTv;

    //设备扫描结果展示适配器
    private DeviceAdapter adapter;

    private BluetoothLeDeviceStore bluetoothLeDeviceStore = new BluetoothLeDeviceStore();

    /**
     * 扫描回调
*/
    private ScanCallback sc = new ScanCallback(new IScanCallback() {
        @Override
        public void onDeviceFound(final BluetoothLeDevice bluetoothLeDevice) {
            Log.i("fsd", "Founded Scan Device:" + bluetoothLeDevice);
            bluetoothLeDeviceStore.addDevice(bluetoothLeDevice);
            Log.d("bts", bluetoothLeDeviceStore.toString());
//scanning jump


            if (bluetoothLeDeviceStore != null) {
                if (address.equals(bluetoothLeDevice.getAddress())&&!gotDevice) {
                    gotDevice=true;
                    stopScan();
                    Intent intent = new Intent(activity, DeviceDetailActivity.class);
                    intent.putExtra(DeviceDetailActivity.EXTRA_DEVICE, bluetoothLeDevice);
                    activity.startActivity(intent);

                    Log.d("mylam2", "connect success");
                }

            }

        //    Thread thread = new Thread(runnable);
          //  thread.start();
        }

        @Override
        public void onScanFinish(BluetoothLeDeviceStore bluetoothLeDeviceStore) {
            ViseLog.i("scan finish " + bluetoothLeDeviceStore);
        }

        @Override
        public void onScanTimeout() {
            ViseLog.i("scan timeout");
        }

    });


    public void init() {

        //adapter = new DeviceAdapter(context);
        //deviceLv.setAdapter(adapter);
        startScan();
        /*if(address.equals("45:53:3C:3D:14:D   8")){
           /* Intent intent = new Intent(MyBluetoothService.this, DeviceDetailActivity.class);
            intent.putExtra(DeviceDetailActivity.EXTRA_DEVICE, address);
            startActivity(intent);

           Log.d("hello address",address);
           Intent intent = new Intent(activity, DeviceDetailActivity.class);
           intent.putExtra(DeviceDetailActivity.EXTRA_DEVICE, device);
           activity.startActivity(intent);
        }
*/
//        deviceLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
//                //点击某个扫描到的设备进入设备详细信息界面
//                BluetoothLeDevice device = (BluetoothLeDevice) adapter.getItem(position);
//                if (device == null) return;
//
//            }
//        });
    }
/*
    @Override
    protected void onResume() {
        super.onResume();
        checkBluetoothPermission();
        startScan();
        invalidateOptionsMenu();
    }
*/
//    @Override
//    protected void onPause() {
//        super.onPause();
//        stopScan();
//        invalidateOptionsMenu();
//        bluetoothLeDeviceStore.clear();
//    }


    /**
     * 菜单栏的显示
     *
     * @param menu 菜单
     * @return 返回是否拦截操作
     */
    /*
    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.scan, menu);
        if (sc != null && !sc.isScanning()) {
            menu.findItem(R.id.menu_stop).setVisible(false);
            menu.findItem(R.id.menu_scan).setVisible(true);
            menu.findItem(R.id.menu_refresh).setActionView(null);
        } else {
            menu.findItem(R.id.menu_stop).setVisible(true);
            menu.findItem(R.id.menu_scan).setVisible(false);
            menu.findItem(R.id.menu_refresh).setActionView(R.layout.actionbar_progress_indeterminate);
        }
        return true;
    }
*/
    /**
     * 点击菜单栏的处理
     *
     * @param item
     * @return
     */
    /*
    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_scan://开始扫描

                break;
            case R.id.menu_stop://停止扫描
                stopScan();
                break;
        }
        return true;
    }
*/
    /**
     * 开始扫描
     */
/*
    private void showGattServices() {
        if (simpleExpandableListAdapter == null) {
            return;
        }
        final AlertDialog.Builder builder = new AlertDialog.Builder(DeviceControlActivity.this);
        View view = LayoutInflater.from(DeviceControlActivity.this).inflate(R.layout.item_gatt_services, null);
        ExpandableListView expandableListView = (ExpandableListView) view.findViewById(R.id.dialog_gatt_services_list);
        expandableListView.setAdapter(simpleExpandableListAdapter);
        builder.setView(view);
        final AlertDialog dialog = builder.show();
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                dialog.dismiss();
                final BluetoothGattService service = mGattServices.get(groupPosition);
                final BluetoothGattCharacteristic characteristic = mGattCharacteristics.get(groupPosition).get(childPosition);
                final int charaProp = characteristic.getProperties();
                if ((charaProp & BluetoothGattCharacteristic.PROPERTY_WRITE) > 0) {
                    mSpCache.put(WRITE_CHARACTERISTI_UUID_KEY + mDevice.getAddress(), characteristic.getUuid().toString());
                    ((EditText) findViewById(R.id.show_write_characteristic)).setText(characteristic.getUuid().toString());
                    BluetoothDeviceManager.getInstance().bindChannel(mDevice, PropertyType.PROPERTY_WRITE, service.getUuid(), characteristic.getUuid(), null);
                } else if ((charaProp & BluetoothGattCharacteristic.PROPERTY_READ) > 0) {
                    BluetoothDeviceManager.getInstance().bindChannel(mDevice, PropertyType.PROPERTY_READ, service.getUuid(), characteristic.getUuid(), null);
                    BluetoothDeviceManager.getInstance().read(mDevice);
                }
                if ((charaProp & BluetoothGattCharacteristic.PROPERTY_NOTIFY) > 0) {
                    mSpCache.put(NOTIFY_CHARACTERISTIC_UUID_KEY + mDevice.getAddress(), characteristic.getUuid().toString());
                    ((EditText) findViewById(R.id.show_notify_characteristic)).setText(characteristic.getUuid().toString());
                    BluetoothDeviceManager.getInstance().bindChannel(mDevice, PropertyType.PROPERTY_NOTIFY, service.getUuid(), characteristic.getUuid(), null);
                    BluetoothDeviceManager.getInstance().registerNotify(mDevice, false);
                } else if ((charaProp & BluetoothGattCharacteristic.PROPERTY_INDICATE) > 0) {
                    mSpCache.put(NOTIFY_CHARACTERISTIC_UUID_KEY + mDevice.getAddress(), characteristic.getUuid().toString());
                    ((EditText) findViewById(R.id.show_notify_characteristic)).setText(characteristic.getUuid().toString());
                    BluetoothDeviceManager.getInstance().bindChannel(mDevice, PropertyType.PROPERTY_INDICATE, service.getUuid(), characteristic.getUuid(), null);
                    BluetoothDeviceManager.getInstance().registerNotify(mDevice, true);
                }
                return true;
            }
        });
    }
*/
    private void startScan() {

        ViseBle.getInstance().startScan(sc);
    }


    private void stopScan() {
        ViseBle.getInstance().stopScan(sc);
    }

    /**
     * 更新扫描到的设备个数
     *
     * @param count
     */
    /*
    private void updateItemCount(final int count) {
        scanCountTv.setText(getString(R.string.formatter_item_count, String.valueOf(count)));
    }
    private void checkBluetoothPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //校验是否已具有模糊定位权限
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                PermissionManager.instance().with(this).request(new OnPermissionCallback() {
                    @Override
                    public void onRequestAllow(String permissionName) {
                        enableBluetooth();
                    }

                    @Override
                    public void onRequestRefuse(String permissionName) {
                        finish();
                    }

                    @Override
                    public void onRequestNoAsk(String permissionName) {
                        finish();
                    }
                }, Manifest.permission.ACCESS_COARSE_LOCATION);
            } else {
                enableBluetooth();
            }
        } else {
            enableBluetooth();
        }
    }

    private void enableBluetooth() {
        if (!BleUtil.isBleEnable(this)) {
            BleUtil.enableBluetooth(this, 1);
        } else {
            boolean isSupport = BleUtil.isSupportBle(this);
            boolean isOpenBle = BleUtil.isBleEnable(this);

            invalidateOptionsMenu();

        }
    }
*/
    /**
     * 更新已经连接到的设备
     */

    /**
     * 更新已经连接的设备个数
     *
     * @param count
     */

    /**
     * 显示项目信息
     */

}
