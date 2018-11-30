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

        import static java.lang.Math.pow;
        import static java.lang.Math.sqrt;

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
try {
    DeviceMirror deviceMirror = ViseBle.getInstance().getDeviceMirror(mDevice);
    for (BluetoothGattService bgs : deviceMirror.getBluetoothGatt().getServices()) {
        Log.d("bletest", String.valueOf(bgs.getUuid()));
        if (bgs.getUuid().toString().equals("0783b03e-8535-b5a0-7140-a304d2495cb0")) {
            writeHead(bgs);

            notifyHead(bgs);
            readHead(bgs);
        }

    }
}
catch (Exception e)
{
    Log.d("error","error");
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
    double x_angle;
    double y,S,dt;
    double x_bias = 0;
    double K_0,K_1;
    double P_00 = 0, P_01 = 0, P_10 = 0, P_11 = 0;
    public double kalmanCalculate(double newAngle, double newRate) {
        //mylam hardcode time
        dt = 100/1000;
        x_angle = dt * (newRate - x_bias);
        P_00 +=  - dt * (P_10 + P_01) + 0.001 * dt;
        P_01 +=  - dt * P_11;
        P_10 +=  - dt * P_11;
        P_11 +=  + 0.003 * dt;

        y = newAngle - x_angle;
        S = P_00 + 0.03;
        K_0 = P_00 / S;
        K_1 = P_10 / S;

        x_angle +=  K_0 * y;
        x_bias  +=  K_1 * y;
        P_00 -= K_0 * P_00;
        P_01 -= K_0 * P_01;
        P_10 -= K_1 * P_00;
        P_11 -= K_1 * P_01;


        return x_angle;
    }

    public void shiftHighByte(String x1,String x2,String y1,String y2,String z1,String z2,String gx1,String gx2){
        int xtemp = Integer.parseInt(x1,2);//<<8+Integer.parseInt(x2,16);
        int xtempshifted=(xtemp<<8) | Integer.parseInt(x2,2);
        double resultx = xtempshifted/2048.0;
        int ytemp = Integer.parseInt(y1,2);//<<8+Integer.parseInt(x2,16);
        int ytempshifted=(ytemp<<8) | Integer.parseInt(y2,2);
        double resulty = ytempshifted/2048.0;
        int ztemp = Integer.parseInt(z1,2);//<<8+Integer.parseInt(x2,16);
        int ztempshifted=(ztemp<<8) | Integer.parseInt(z2,2);
        double resultz = ztempshifted/2048.0;
        //variable for complement filter
        int gtemp = Integer.parseInt(gx1,2);//<<8+Integer.parseInt(x2,16);
        int gtempshifted=(gtemp<<8) | Integer.parseInt(gx2,2);
        double resultg = gtempshifted *9.81 / 2048.0*  Math.PI / (16.4f * 180.0f);

        double angle = Math.atan2(resulty,sqrt(pow(resultx,2)+pow(resultz,2)));
        double offset_angle = 0.06;//hardcode with 90 degree
        double angle2 = Math.atan2(resulty,resultz)-offset_angle;
        double Angy = 0.998*(resultg*100/1000)+0.02*angle2;
        double kang = kalmanCalculate(Angy, resultg);
        Log.d("shb",String.valueOf(resultx)+" "+String.valueOf(resulty)+" "+String.valueOf(resultz)+" "
                +String.valueOf(angle) +" "+String.valueOf(angle2)+" "+String.valueOf(Angy)+" "+String.valueOf(kang));
       // Log.d("shb",Integer.toBinaryString(resultx)+" "+Integer.toBinaryString(resulty)+" "+Integer.toBinaryString(resultz));
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
                    Log.d("result",result);

                    int i = (event.getData()[1] & 0xff) << 8 | (short) (event.getData()[2] << 8);

                    String tmp = "";
                    int id = 0;

                    while(id<result.length())
                    {

                        tmp+=Integer.toBinaryString(Integer.parseInt(result.substring(id,Math.min(id+2, result.length())),16)& 0xffff);
                        Log.d("temp",tmp);
                        tmp+="-";
                        id+=2;
                    }
                    Pattern pattern;
                    pattern=Pattern.compile(Pattern.quote("-"));
                    String[] data =pattern.split(tmp);
                    shiftHighByte(data[1],data[2],data[3],data[4],data[5],data[6],data[7],data[8]);
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
