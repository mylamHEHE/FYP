package com.example.user.smartfitnesstrainer.Main.BLE;
import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
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


import com.example.user.smartfitnesstrainer.Main.DetailVideo.BluetoothDifferenter;
import com.example.user.smartfitnesstrainer.R;
import com.vise.xsnow.cache.SpCache;
import com.vise.xsnow.event.BusManager;
import com.vise.xsnow.event.Subscribe;
import com.vise.xsnow.event.inner.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Pattern;

import static java.lang.Math.PI;
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
    //Data buffer import
    private ArrayList<Double> buffer_Data = new ArrayList();
    //buffer timer for preventing package loss
    Timer buf_timer= new Timer();
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

    protected void init() {

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

        try {

            DeviceMirror deviceMirror = ViseBle.getInstance().getDeviceMirror(mDevice);
            Log.d("bletest", deviceMirror.getBluetoothLeDevice().getAddress());
            BluetoothDifferenter.FIRST_BLUETOOTH_DEV = deviceMirror.getBluetoothLeDevice();
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
    @Subscribe(threadMode = ThreadMode.SINGLE)
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

    @Subscribe(threadMode = ThreadMode.SINGLE)
    public void showDeviceCallbackData(CallbackDataEvent event) {
        if (event != null) {
            if (event.isSuccess()) {
                Log.d("cutrone",event.getBluetoothGattChannel().getPropertyType().toString());
                if (event.getBluetoothGattChannel() != null && event.getBluetoothGattChannel().getCharacteristic() != null
                        && event.getBluetoothGattChannel().getPropertyType() == PropertyType.PROPERTY_READ) {
                    //  showReadInfo(event.getBluetoothGattChannel().getCharacteristic().getUuid().toString(), event.getData());

                }
            } else {
                Log.d("fail","failing");
                /*((EditText) findViewById(R.id.show_write_characteristic)).setText("");
                ((EditText) findViewById(R.id.show_notify_characteristic)).setText("");
                */
            }
        }
    }

//    double Angy = 0;
    public float help(String a,String b){
        short int16 = (short)(((Integer.parseInt(a,2) & 0xFF) << 8) | (Integer.parseInt(b,2) & 0xFF));
        float f = int16/(float)2048.0;
        Log.d("datax",String.valueOf(f));
        return f;
    }
    public float help2(String a,String b){
        int xtemp = Integer.parseInt(a,2);//<<8+Integer.parseInt(x2,16);
        return (short)(((Integer.parseInt(a,2) & 0xFF) << 8) | (Integer.parseInt(b,2) & 0xFF))* (float)(Math.PI / (16.4f * 180.0f));
    }
//    public void shiftHighByte(String x1,String x2,String y1,String y2,String z1,String z2,String gx1,String gx2){
//        int xtemp = Integer.parseInt(x1,2);//<<8+Integer.parseInt(x2,16);
//        int xtempshifted=(xtemp<<7) | Integer.parseInt(x2,2);
//        Log.d("xtempcheck",String.valueOf(xtempshifted));
//        double resultx = xtempshifted/2048.0;
//        if(resultx>30)return;
//        int ytemp = Integer.parseInt(y1,2);//<<8+Integer.parseInt(x2,16);
//        int ytempshifted=(ytemp<<8) | Integer.parseInt(y2,2);
//        double resulty = ytempshifted/2048.0;
//        int ztemp = Integer.parseInt(z1,2);//<<8+Integer.parseInt(x2,16);
//        int ztempshifted=(ztemp<<8) | Integer.parseInt(z2,2);
//        double resultz = ztempshifted/2048.0;
//
//        double nx = help(x1,x2);
//        double ny = help(y1,y2);
//        double nz = help(z1,z2);
//
//        double ngx = help2(x1,x2);
//        double ngy = help2(y1,y2);
//        double ngz = help2(z1,z2);
//
//        //variable for complement filter
//        int gtemp = Integer.parseInt(gx1,2);//<<8+Integer.parseInt(x2,16);
//        int gtempshifted=(gtemp<<8) | Integer.parseInt(gx2,2);
//        double resultg = gtempshifted *  Math.PI / (16.4f * 180.0f);
//        /*final tuning*/
//        float angle = (float)Math.atan2(ny,sqrt(pow(nx,2)+pow(ny,2)+pow(nz,2)));
//        float LS = (float)Math.atan2(nx,sqrt(pow(nx,2)+pow(ny,2)+pow(nz,2)));
//        float elevation = (float)Math.atan2(sqrt(nx*nx+ny*ny),sqrt(pow(nx,2)+pow(ny,2)+pow(nz,2)));
//
//
//
//        double offset_angle = 0.08;//hardcode with 90 degree
//        Angy = 0.998*(Angy + resultg*100/1000)+0.02*angle;
//
//        Log.d("shb",String.valueOf(nx)+" "+String.valueOf(ny)+" "+String.valueOf(nz)+" "+
//                " tanxz: "+String.valueOf((int)(angle*100))+" tanxyz: "+String.valueOf((int)(LS*100))+ "Avg: " + String.valueOf(((int)(angle*100)+(int)(LS*100))*0.5));
//
//        Intent it = new Intent("tw.android.MY_BROADCAST1");
//        Log.d("broadcast?",String.valueOf((int)(angle*100)));
//        it.putExtra("sender_name", (int)(angle*100));
//        sendBroadcast(it);
//        // Log.d("shb",Integer.toBinaryString(resultx)+" "+Integer.toBinaryString(resulty)+" "+Integer.toBinaryString(resultz));
//    }
    double Kp = 10.0f; // 这里的KpKi是用于调整加速度计修正陀螺仪的速度
    double Ki = 0.008f;
    double halfT = 0.050f; // 采样周期的一半，用于求解四元数微分方程时计算角增量
    double q0 = 1, q1 = 0, q2 = 0, q3 = 0;    // 初始姿态四元数，由上篇博文提到的变换四元数公式得来
    double exInt = 0, eyInt = 0, ezInt = 0;    //当前加计测得的重力加速度在三轴上的分量
//    //与用当前姿态计算得来的重力在三轴上的分量的误差的积分
    public double IMUupdate(String x1,String x2,String y1,String y2,String z1,String z2,String gx1,String gx2,String gy1,String gy2,String gz1,String gz2)//g表陀螺仪，a表加计
    {

        double q0temp, q1temp, q2temp, q3temp;//四元数暂存变量，求解微分方程时要用
        double norm; //矢量的模或四元数的范数
        double vx, vy, vz;//当前姿态计算得来的重力在三轴上的分量
        double ex, ey, ez;//当前加计测得的重力加速度在三轴上的分量
        //与用当前姿态计算得来的重力在三轴上的分量的误差
        double ax = help(x1, x2);
        double ay = help(y1, y2);
        double az = help(z1, z2);
        double gx = help2(gx1, gx2);
        double gy = help2(gy1, gy2);
        double gz = help2(gz1, gz2);

        // for accelerometer

        norm = sqrt(pow(ax,2)+pow(ay,2)+pow(az,2));
        double elevation_acc = Math.asin(sqrt(ax*ax+ay*ay)/norm) * 180 /PI;
        // 先把这些用得到的值算好
        double q0q0 = q0 * q0;
        double q0q1 = q0 * q1;
        double q0q2 = q0 * q2;
        double q1q1 = q1 * q1;
        double q1q3 = q1 * q3;
        double q2q2 = q2 * q2;
        double q2q3 = q2 * q3;
        double q3q3 = q3 * q3;
        if (ax * ay * az == 0)//加计处于自由落体状态时不进行姿态解算，因为会产生分母无穷大的情况
            return elevation_acc;
        norm = sqrt(ax * ax + ay * ay + az * az);//单位化加速度计，
        ax = ax / norm;// 这样变更了量程也不需要修改KP参数，因为这里归一化了
        ay = ay / norm;
        az = az / norm;
        //用当前姿态计算出重力在三个轴上的分量，
        //参考坐标n系转化到载体坐标b系的用四元数表示的方向余弦矩阵第三列即是（博文一中有提到）
        vx = 2 * (q1q3 - q0q2);
        vy = 2 * (q0q1 + q2q3);
        vz = q0q0 - q1q1 - q2q2 + q3q3;
        //计算测得的重力与计算得重力间的误差，向量外积可以表示这一误差
        //原因我理解是因为两个向量是单位向量且sin0等于0
        //不过要是夹角是180度呢~这个还没理解
        ex = (ay * vz - az * vy);
        ey = (az * vx - ax * vz);
        ez = (ax * vy - ay * vx);

        exInt = exInt + (ex * Ki);                                           //对误差进行积分
        eyInt = eyInt + (ey * Ki);
        ezInt = ezInt + (ez * Ki);
        // adjusted gyroscope measurements
        gx = gx + Kp * ex + exInt;  //将误差PI后补偿到陀螺仪，即补偿零点漂移
        gy = gy + Kp * ey + eyInt;
        gz = gz + Kp * ez + ezInt;    //这里的gz由于没有观测者进行矫正会产生漂移，表现出来的就是积分自增或自减
        //下面进行姿态的更新，也就是四元数微分方程的求解
        q0temp = q0;//暂存当前值用于计算
        q1temp = q1;//网上传的这份算法大多没有注意这个问题，在此更正
        q2temp = q2;
        q3temp = q3;
        //采用一阶毕卡解法，相关知识可参见《惯性器件与惯性导航系统》P212
        q0 = q0temp + (-q1temp * gx - q2temp * gy - q3temp * gz) * halfT;
        q1 = q1temp + (q0temp * gx + q2temp * gz - q3temp * gy) * halfT;
        q2 = q2temp + (q0temp * gy - q1temp * gz + q3temp * gx) * halfT;
        q3 = q3temp + (q0temp * gz + q1temp * gy - q2temp * gx) * halfT;
        //单位化四元数在空间旋转时不会拉伸，仅有旋转角度，这类似线性代数里的正交变换
        norm = sqrt(q0 * q0 + q1 * q1 + q2 * q2 + q3 * q3);
        q0 = q0 / norm;
        q1 = q1 / norm;
        q2 = q2 / norm;
        q3 = q3 / norm;

        //四元数到欧拉角的转换，公式推导见博文一
        //其中YAW航向角由于加速度计对其没有修正作用，因此此处直接用陀螺仪积分代替
        double z = Math.atan2(2 * q0 * q3 + 2 * q1 * q2,-2 * q2 * q2 - 2 * q3* q3 + 1)* 57.3; // yaw; // yaw
        double y = Math.asin(-2 * q1 * q3 + 2 * q0* q2)*57.3; // pitch
        double x = Math.atan2(2 * q2 * q3 + 2 * q0 * q1,-2 * q1 * q1 - 2 * q2* q2 + 1)* 57.3; // roll
        Log.d("xmen",Math.abs(z)+" "+Math.abs(y)+" "+Math.abs(x));
        //double r0 = 0, r1 = 1, r2 = 0, r3 = 0;0100
        double[] first_result = multiplyQuaternion(0,0,0,1,q0,-q1,-q2,-q3);
        double[] last_result = multiplyQuaternion(q0, q1, q2, q3, first_result[0],first_result[1],first_result[2],first_result[3]);
        double xss = last_result[1];//Q1
        double yss = last_result[2];//Q2
        double zss = last_result[3];//Q3
////cos
        double elevationGyro = Math.asin(sqrt(xss*xss+yss*yss)/sqrt(pow(xss,2)+pow(yss,2)+pow(zss,2))) * 180 /PI;
//        double elevationGyro = Math.asin(sqrt(pow(Math.sin(x),2) + pow(Math.sin(y), 2))/ (sqrt(pow(Math.sin(x),2) + pow(Math.sin(y), 2)) + pow(Math.sin(y), 2)));
        double rollgyr = Math.atan(sqrt(xss*xss+yss*yss)/(sqrt(pow(xss,2)+pow(yss,2)+pow(zss,2)))) * 180 /PI;
        double rollgyr2 = Math.atan2(2 * yss * zss + 2 * last_result[0] * xss,-2 * xss * xss - 2 * yss* yss + 1)* 180 /PI;


        Log.d("elevation", String.format("accelerometer: %f, gyroscope: %f, average: %f", elevation_acc, elevationGyro, (elevation_acc+elevationGyro)/2));
        Log.d("rolllllllllllll", String.format("pitch: %f, roll: %f rolltest: %f", (elevation_acc+elevationGyro)/2, rollgyr2/1.98, rollgyr2/1.79));
        Log.d("original", String.format("pitch: %f, pitch: %f //roll: %f, roll: %f ", correct(x), x, correct(y), y));
        double result=(elevation_acc+elevationGyro)/2;

        Intent it = new Intent("tw.android.MY_BROADCAST1");
        it.putExtra("first_dev_pitch",x);
        it.putExtra("first_dev_roll",y);

        sendBroadcast(it);
        //timer get buffer average in one sec

        return result;
    }
    private double[] multiplyQuaternion(double a, double b, double c, double d, double e, double f, double g, double h){
        double result[] = new double[4];
        result[0] = (a*e-b*f-c*g-d*h);
        result[1] = (a*f+b*e+c*h-g*d);
        result[2] = (a*g-b*h+c*e+d*f);
        result[3] = (a*h+b*g-c*f+d*e);
        return result;
    }
    private double correct(double input){
        if (input>90)
            return 180-input;
        return input;
    }
    @Subscribe(threadMode = ThreadMode.SINGLE)
    public void showDeviceNotifyData(final NotifyDataEvent event) {

                Log.d("heart", String.valueOf(event.getBluetoothLeDevice().getAddress()));
                try {
                    if(!event.getBluetoothLeDevice().getAddress().equals("45:53:3C:3D:14:D8")) {
                        d9Device(event);
                    }
                    else
                    {
                        d8Device(event);
                    }
                }
                catch(Exception e)
                {

                }
                return;

    }
    private void d8Device(NotifyDataEvent event){


        if (event != null && event.getData() != null && event.getBluetoothLeDevice() != null
                && event.getBluetoothLeDevice().getAddress().equals(mDevice.getAddress())) {
            String result = HexUtil.encodeHexStr(event.getData());

            int i = (event.getData()[1] & 0xff) << 7 | (short) (event.getData()[2] << 8);
            String tmp = "";
            int id = 0;

            while (id < result.length()) {

                tmp += Integer.toBinaryString(Integer.parseInt(result.substring(id, Math.min(id + 2, result.length())), 16) & 0xffff);
                tmp += "-";
                id += 2;
            }

            Pattern pattern;
            pattern = Pattern.compile(Pattern.quote("-"));
            String[] data = pattern.split(tmp);
            byte[] bytes = {0, -128}; // bytes[0] = 0000 0000, bytes[1] = 1000 0000
            //                short int16 = (short)(((Integer.parseInt(data[5],2) & 0xFF) << 8) | (Integer.parseInt(data[6],2) & 0xFF));
            //              float f = int16;
            //            Log.d("datax",String.valueOf(f));
            try {
                //buffer_Data.add(data[1]);

                //shiftHighByte(data[1], data[2], data[3], data[4], data[5], data[6], data[7], data[8]);
                double quad_res=IMUupdate(data[1], data[2], data[3], data[4], data[5], data[6], data[7], data[8], data[9], data[10], data[11], data[12]);
                Log.d("delble1", String.valueOf(quad_res));

            } catch (Exception e) {

            }
        }
    }
    private void d9Device(NotifyDataEvent event){


        if (event != null && event.getData() != null && event.getBluetoothLeDevice() != null
                && event.getBluetoothLeDevice().getAddress().equals(mDevice.getAddress())) {
            String result = HexUtil.encodeHexStr(event.getData());

            int i = (event.getData()[1] & 0xff) << 7 | (short) (event.getData()[2] << 8);
            String tmp = "";
            int id = 0;

            while (id < result.length()) {

                tmp += Integer.toBinaryString(Integer.parseInt(result.substring(id, Math.min(id + 2, result.length())), 16) & 0xffff);
                tmp += "-";
                id += 2;
            }

            Pattern pattern;
            pattern = Pattern.compile(Pattern.quote("-"));
            String[] data = pattern.split(tmp);
            byte[] bytes = {0, -128}; // bytes[0] = 0000 0000, bytes[1] = 1000 0000
            //                short int16 = (short)(((Integer.parseInt(data[5],2) & 0xFF) << 8) | (Integer.parseInt(data[6],2) & 0xFF));
            //              float f = int16;
            //            Log.d("datax",String.valueOf(f));
            try {
                //buffer_Data.add(data[1]);

                //shiftHighByte(data[1], data[2], data[3], data[4], data[5], data[6], data[7], data[8]);
                double quad_res=IMUupdate(data[1], data[2], data[3], data[4], data[5], data[6], data[7], data[8], data[9], data[10], data[11], data[12]);
                Log.d("delble2", String.valueOf(quad_res));
                Intent it = new Intent("tw.android.MY_BROADCAST1");
                it.putExtra("first_dev",quad_res);

                sendBroadcast(it);
            } catch (Exception e) {

            }
        }
    }
    private void timerTaskforBuffer(double result){
        Log.d("timer","hei");


    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
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
