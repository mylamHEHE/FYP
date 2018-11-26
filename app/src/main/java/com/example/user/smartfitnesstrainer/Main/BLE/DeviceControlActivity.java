package com.example.user.smartfitnesstrainer.Main.BLE;

        import android.app.Activity;
        import android.app.AlertDialog;
        import android.bluetooth.BluetoothGattCharacteristic;
        import android.bluetooth.BluetoothGattService;
        import android.os.AsyncTask;
        import android.os.Bundle;
        import android.support.annotation.Nullable;
        import android.support.v7.app.AppCompatActivity;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.Menu;
        import android.view.MenuItem;
        import android.view.View;
        import android.view.Window;
        import android.widget.EditText;
        import android.widget.ExpandableListView;
        import android.widget.SimpleExpandableListAdapter;
        import android.widget.TextView;


        import com.example.user.smartfitnesstrainer.R;
        import com.vise.xsnow.cache.SpCache;
        import com.vise.xsnow.event.BusManager;
        import com.vise.xsnow.event.Subscribe;

        import java.util.ArrayList;
        import java.util.HashMap;
        import java.util.List;
        import java.util.Map;
        import java.util.regex.Pattern;

        import static java.lang.Math.atan;

/**
 * 设备数据操作相关展示界面
 */
public class DeviceControlActivity extends Activity {

    private static final String LIST_NAME = "NAME";
    private static final String LIST_UUID = "UUID";
    public static final String WRITE_CHARACTERISTI_UUID_KEY = "write_uuid_key";
    public static final String NOTIFY_CHARACTERISTIC_UUID_KEY = "notify_uuid_key";
    public static final String WRITE_DATA_KEY = "write_data_key";

    private SimpleExpandableListAdapter simpleExpandableListAdapter;
    private TextView mConnectionState;
    private TextView mGattUUID;
    private TextView mGattUUIDDesc;
    private TextView mDataAsString;
    private TextView mDataAsArray;
    private EditText mInput;
    private EditText mOutput;

    private SpCache mSpCache;
    private static final byte[] WRITE_HEADER = new byte[] { 0x23,0x00 };
    //设备信息
    private BluetoothLeDevice mDevice;
    //输出数据展示
    private StringBuilder mOutputInfo = new StringBuilder();
    private List<BluetoothGattService> mGattServices = new ArrayList<>();
    //设备特征值集合
    private List<List<BluetoothGattCharacteristic>> mGattCharacteristics = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_device_control);
        BusManager.getBus().register(this);
        init();
    }

    private void init() {

        mDevice = getIntent().getParcelableExtra("ble");

        mSpCache = new SpCache(this);


        BluetoothDeviceManager.getInstance().connect(mDevice);
        Log.d("bluettohd",String.valueOf(BluetoothDeviceManager.getInstance().isConnected(mDevice)));

 //       showDefaultInfo();

    }
    private void readHead(BluetoothGattService bgs){
        BluetoothDeviceManager.getInstance().bindChannel(mDevice, PropertyType.PROPERTY_READ, bgs.getUuid(), bgs.getCharacteristics().get(0).getUuid(), null);
        BluetoothDeviceManager.getInstance().read(mDevice);


    }
    private void writeHead(BluetoothGattService bgs){
        mSpCache.put(WRITE_CHARACTERISTI_UUID_KEY + mDevice.getAddress(), bgs.getCharacteristics().get(1).getUuid().toString());
        BluetoothDeviceManager.getInstance().bindChannel(mDevice, PropertyType.PROPERTY_WRITE, bgs.getUuid(), bgs.getCharacteristics().get(1).getUuid(), null);
       // ((EditText) findViewById(R.id.show_write_characteristic)).setText(bgs.getCharacteristics().get(1).getUuid().toString());
        BluetoothDeviceManager.getInstance().write(mDevice, HexUtil.decodeHex(new char[]{'2','3','0','0'}));


    }
    private void notifyHead(BluetoothGattService bgs){
        mSpCache.put(NOTIFY_CHARACTERISTIC_UUID_KEY + mDevice.getAddress(), bgs.getCharacteristics().get(0).getUuid().toString());
       // ((EditText) findViewById(R.id.show_notify_characteristic)).setText(bgs.getCharacteristics().get(0).getUuid().toString());
        BluetoothDeviceManager.getInstance().bindChannel(mDevice, PropertyType.PROPERTY_NOTIFY, bgs.getUuid(), bgs.getCharacteristics().get(0).getUuid(), null);
        BluetoothDeviceManager.getInstance().registerNotify(mDevice, false);

    }
    private void testBluetoothAvability(){
        Log.d("bletest", String.valueOf(ViseBle.getInstance().getConnectState(mDevice)));

        DeviceMirror deviceMirror = ViseBle.getInstance().getDeviceMirror(mDevice);
        for(BluetoothGattService bgs: deviceMirror.getBluetoothGatt().getServices()) {
            Log.d("bletest", String.valueOf(bgs.getUuid()));
            if(bgs.getUuid().toString().equals("0783b03e-8535-b5a0-7140-a304d2495cb0"))
            {
               writeHead(bgs);

               notifyHead(bgs);
                readHead(bgs);
            }

        }

    }
    @Subscribe
    public void showConnectedDevice(ConnectEvent event) {
        if (event != null) {
            if (event.isSuccess()) {
                Log.d("abcd","abcd");
                ToastUtil.showToast(DeviceControlActivity.this, "Connect Success!");
//                mConnectionState.setText("true");
                testBluetoothAvability();
                invalidateOptionsMenu();
                if (event.getDeviceMirror() != null && event.getDeviceMirror().getBluetoothGatt() != null) {
                    finish();
               //     simpleExpandableListAdapter = displayGattServices(event.getDeviceMirror().getBluetoothGatt().getServices());
                }
            } else {
                if (event.isDisconnected()) {
                    ToastUtil.showToast(DeviceControlActivity.this, "Disconnect!");
                } else {
                    ToastUtil.showToast(DeviceControlActivity.this, "Connect Failure!");
                }
        //        mConnectionState.setText("false");
                invalidateOptionsMenu();
          //      clearUI();
            }
        }
    }

    @Subscribe
    public void showDeviceCallbackData(CallbackDataEvent event) {
        if (event != null) {
            if (event.isSuccess()) {
                Log.d("cutrone",event.getBluetoothGattChannel().getPropertyType().toString());
                if (event.getBluetoothGattChannel() != null && event.getBluetoothGattChannel().getCharacteristic() != null
                        && event.getBluetoothGattChannel().getPropertyType() == PropertyType.PROPERTY_READ) {
                  //  showReadInfo(event.getBluetoothGattChannel().getCharacteristic().getUuid().toString(), event.getData());

                }
            } else {
                /*((EditText) findViewById(R.id.show_write_characteristic)).setText("");
                ((EditText) findViewById(R.id.show_notify_characteristic)).setText("");
                */
            }
        }
    }

    @Subscribe
    public void showDeviceNotifyData(final NotifyDataEvent event) {
    //get Data From Device - non-blockingUI

        new AsyncTask<Void,Void,Void>(){
            @Override
            protected Void doInBackground(Void... params) {

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (event != null && event.getData() != null && event.getBluetoothLeDevice() != null
                        && event.getBluetoothLeDevice().getAddress().equals(mDevice.getAddress())) {
                    String result = HexUtil.encodeHexStr(event.getData());
                    int i = (event.getData()[1] & 0xff) << 8 | (short) (event.getData()[2] << 8);
                   //x

                    Log.d("Accele",String.valueOf(i));
                    String tmp = "";
                    int id = 0;

                    while(id<result.length())
                    {
                        tmp+=String.valueOf(Integer.parseInt(result.substring(id,Math.min(id+2, result.length())),16)& 0xffff);
                        tmp+="-";
                        id+=2;
                    }
                    Pattern pattern;
                    pattern=Pattern.compile(Pattern.quote("-"));
                    String[] data =pattern.split(tmp);

                  /*  Double aDouble= Math.atan2((Integer.parseInt(data[9],16)+Integer.parseInt(data[10],16))
                            ,
                            ((Integer.parseInt(data[7],16)+Integer.parseInt(data[8],16))/2.0));
                    Log.d("x::::"," "+String.valueOf(aDouble));
*/
                    //int i1x = (Integer.parseInt(data[1],16)) << 8;
                    try {
                        /* Accelemometer */
                        int a1x = (Integer.parseInt(data[1],16)<<8)+(Integer.parseInt(data[2],16));
                        int a1y = (Integer.parseInt(data[3],16)<<8)+(Integer.parseInt(data[4],16));
                        int a1z = (Integer.parseInt(data[5],16)<<8)+(Integer.parseInt(data[6],16));
                        /* Gyroscope */
                        double i1x = (Integer.parseInt(data[7], 16) + Integer.parseInt(data[8], 16)) / 2.0 * 9.81 / 256;
                        double i1y = (Integer.parseInt(data[9], 16) + Integer.parseInt(data[10], 16)) / 2.0 * 9.81 / 256;
                        //i1y += (Integer.parseInt(data[4],16));
                        //int i1z = (Integer.parseInt(data[5],16)) << 8;
                        double i1z = (Integer.parseInt(data[11], 16) + Integer.parseInt(data[12], 16)) / 2.0 * 9.81 / 256;

                        //i1z += (Integer.parseInt(data[6],16));

                        Log.d("ax ay az", String.valueOf(a1x/256.0) + " " + String.valueOf( a1y/256.0) + " " + String.valueOf((int) a1z/256));
                    }
                    catch (Exception e)
                    {

                    }
                }

                return null;
            }

        }.execute();

    }

    @Override
    protected void onResume() {
        invalidateOptionsMenu();
        super.onResume();
    }
/*
    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.connect, menu);
        if (BluetoothDeviceManager.getInstance().isConnected(mDevice)) {
            menu.findItem(R.id.menu_connect).setVisible(false);
            menu.findItem(R.id.menu_disconnect).setVisible(true);
            mConnectionState.setText("true");
            DeviceMirror deviceMirror = ViseBle.getInstance().getDeviceMirror(mDevice);
            if (deviceMirror != null) {
                simpleExpandableListAdapter = displayGattServices(deviceMirror.getBluetoothGatt().getServices());
            }
            showDefaultInfo();
        } else {
            menu.findItem(R.id.menu_connect).setVisible(true);
            menu.findItem(R.id.menu_disconnect).setVisible(false);
            mConnectionState.setText("false");
            clearUI();
        }
        if (ViseBle.getInstance().getConnectState(mDevice) == ConnectState.CONNECT_PROCESS) {
            menu.findItem(R.id.menu_refresh).setActionView(R.layout.actionbar_progress_indeterminate);
        } else {
            menu.findItem(R.id.menu_refresh).setActionView(null);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_connect://连接设备
                if (!BluetoothDeviceManager.getInstance().isConnected(mDevice)) {
                    BluetoothDeviceManager.getInstance().connect(mDevice);
                    invalidateOptionsMenu();
                }
                break;
            case R.id.menu_disconnect://断开设备
                if (BluetoothDeviceManager.getInstance().isConnected(mDevice)) {
                    BluetoothDeviceManager.getInstance().disconnect(mDevice);
                    invalidateOptionsMenu();
                }
                break;
        }
        return true;
    }

    @Override
    protected void onDestroy() {
        BusManager.getBus().unregister(this);
        super.onDestroy();
    }
*/
    /**
     * 根据GATT服务显示该服务下的所有特征值
     *
     * @param gattServices GATT服务
     * @return
     */
    /*
    private SimpleExpandableListAdapter displayGattServices(final List<BluetoothGattService> gattServices) {
        for(BluetoothGattService bgs: gattServices) {
            Log.d("bgs",String.valueOf(bgs.getUuid()));
        }
        if (gattServices == null) return null;
        String uuid;
        final String unknownServiceString = getResources().getString(R.string.unknown_service);
        final String unknownCharaString = getResources().getString(R.string.unknown_characteristic);
        final List<Map<String, String>> gattServiceData = new ArrayList<>();
        final List<List<Map<String, String>>> gattCharacteristicData = new ArrayList<>();

        mGattServices = new ArrayList<>();
        mGattCharacteristics = new ArrayList<>();

        // Loops through available GATT Services.
        for (final BluetoothGattService gattService : gattServices) {
            final Map<String, String> currentServiceData = new HashMap<>();
            uuid = gattService.getUuid().toString();
            //mylam currentServiceData.put(LIST_NAME, GattAttributeResolver.getAttributeName(uuid, unknownServiceString));
            currentServiceData.put(LIST_UUID, uuid);
            gattServiceData.add(currentServiceData);

            final List<Map<String, String>> gattCharacteristicGroupData = new ArrayList<>();
            final List<BluetoothGattCharacteristic> gattCharacteristics = gattService.getCharacteristics();
            final List<BluetoothGattCharacteristic> charas = new ArrayList<>();

            // Loops through available Characteristics.
            for (final BluetoothGattCharacteristic gattCharacteristic : gattCharacteristics) {
                charas.add(gattCharacteristic);
                final Map<String, String> currentCharaData = new HashMap<>();
                uuid = gattCharacteristic.getUuid().toString();
                //mylam currentCharaData.put(LIST_NAME, GattAttributeResolver.getAttributeName(uuid, unknownCharaString));
                currentCharaData.put(LIST_UUID, uuid);
                gattCharacteristicGroupData.add(currentCharaData);
            }

            mGattServices.add(gattService);
            mGattCharacteristics.add(charas);
            gattCharacteristicData.add(gattCharacteristicGroupData);
        }

        final SimpleExpandableListAdapter gattServiceAdapter = new SimpleExpandableListAdapter(this, gattServiceData, android.R.layout
                .simple_expandable_list_item_2, new String[]{LIST_NAME, LIST_UUID}, new int[]{android.R.id.text1, android.R.id.text2},
                gattCharacteristicData, android.R.layout.simple_expandable_list_item_2, new String[]{LIST_NAME, LIST_UUID}, new
                int[]{android.R.id.text1, android.R.id.text2});
        return gattServiceAdapter;
    }

    private void showReadInfo(String uuid, byte[] dataArr) {
        mGattUUID.setText(uuid != null ? uuid : getString(R.string.no_data));
        //mylam mGattUUIDDesc.setText(GattAttributeResolver.getAttributeName(uuid, getString(R.string.unknown)));
        mDataAsArray.setText(HexUtil.encodeHexStr(dataArr));
        mDataAsString.setText(new String(dataArr));
    }

    private void showDefaultInfo() {
        mGattUUID.setText(R.string.no_data);
        mGattUUIDDesc.setText(R.string.no_data);
        mDataAsArray.setText(R.string.no_data);
        mDataAsString.setText(R.string.no_data);
        mInput.setText(mSpCache.get(WRITE_DATA_KEY + mDevice.getAddress(), ""));
        mOutput.setText("");
        ((EditText) findViewById(R.id.show_write_characteristic)).setText(mSpCache.get(WRITE_CHARACTERISTI_UUID_KEY + mDevice.getAddress(), ""));
        ((EditText) findViewById(R.id.show_notify_characteristic)).setText(mSpCache.get(NOTIFY_CHARACTERISTIC_UUID_KEY + mDevice.getAddress(), ""));
        mOutputInfo = new StringBuilder();
    }

    private void clearUI() {
        mGattUUID.setText(R.string.no_data);
        mGattUUIDDesc.setText(R.string.no_data);
        mDataAsArray.setText(R.string.no_data);
        mDataAsString.setText(R.string.no_data);
        mInput.setText("");
        mOutput.setText("");
        ((EditText) findViewById(R.id.show_write_characteristic)).setText("");
        ((EditText) findViewById(R.id.show_notify_characteristic)).setText("");
        mOutputInfo = new StringBuilder();
        simpleExpandableListAdapter = null;
        mSpCache.remove(WRITE_CHARACTERISTI_UUID_KEY + mDevice.getAddress());
        mSpCache.remove(NOTIFY_CHARACTERISTIC_UUID_KEY + mDevice.getAddress());
        mSpCache.remove(WRITE_DATA_KEY + mDevice.getAddress());
    }
*/
    /**
     * 显示GATT服务展示的信息
     */

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
                //    ((EditText) findViewById(R.id.show_write_characteristic)).setText(characteristic.getUuid().toString());
                    BluetoothDeviceManager.getInstance().bindChannel(mDevice, PropertyType.PROPERTY_WRITE, service.getUuid(), characteristic.getUuid(), null);
                } else if ((charaProp & BluetoothGattCharacteristic.PROPERTY_READ) > 0) {
                    BluetoothDeviceManager.getInstance().bindChannel(mDevice, PropertyType.PROPERTY_READ, service.getUuid(), characteristic.getUuid(), null);
                    BluetoothDeviceManager.getInstance().read(mDevice);
                }
                if ((charaProp & BluetoothGattCharacteristic.PROPERTY_NOTIFY) > 0) {
                    mSpCache.put(NOTIFY_CHARACTERISTIC_UUID_KEY + mDevice.getAddress(), characteristic.getUuid().toString());
                   // ((EditText) findViewById(R.id.show_notify_characteristic)).setText(characteristic.getUuid().toString());
                    BluetoothDeviceManager.getInstance().bindChannel(mDevice, PropertyType.PROPERTY_NOTIFY, service.getUuid(), characteristic.getUuid(), null);
                    BluetoothDeviceManager.getInstance().registerNotify(mDevice, false);
                } else if ((charaProp & BluetoothGattCharacteristic.PROPERTY_INDICATE) > 0) {
                    mSpCache.put(NOTIFY_CHARACTERISTIC_UUID_KEY + mDevice.getAddress(), characteristic.getUuid().toString());
                  //  ((EditText) findViewById(R.id.show_notify_characteristic)).setText(characteristic.getUuid().toString());
                    BluetoothDeviceManager.getInstance().bindChannel(mDevice, PropertyType.PROPERTY_INDICATE, service.getUuid(), characteristic.getUuid(), null);
                    BluetoothDeviceManager.getInstance().registerNotify(mDevice, true);
                }
                return true;
            }
        });
    }

    private boolean isHexData(String str) {
        if (str == null) {
            return false;
        }
        char[] chars = str.toCharArray();
        if ((chars.length & 1) != 0) {
            return false;
        }
        for (char ch : chars) {
            if (ch >= '0' && ch <= '9') continue;
            if (ch >= 'A' && ch <= 'F') continue;
            if (ch >= 'a' && ch <= 'f') continue;
            return false;
        }
        return true;
    }

}
