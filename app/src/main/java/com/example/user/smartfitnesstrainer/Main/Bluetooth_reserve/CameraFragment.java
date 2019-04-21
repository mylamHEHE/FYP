package com.example.user.smartfitnesstrainer.Main.Bluetooth_reserve;

import android.Manifest;
import android.annotation.SuppressLint;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.user.smartfitnesstrainer.Main.BLE.BleUtil;
import com.example.user.smartfitnesstrainer.Main.BLE.BluetoothLeDevice;
import com.example.user.smartfitnesstrainer.Main.BLE.ViseBle;
import com.example.user.smartfitnesstrainer.R;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.vise.xsnow.permission.OnPermissionCallback;
import com.vise.xsnow.permission.PermissionManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import static android.app.Activity.RESULT_OK;


public class CameraFragment extends android.support.v4.app.Fragment {
    SurfaceView cameraPreview;
    TextView txtResult;
    private MyBluetoothService bluetooth;
    private SecondBLE secondBLE;
   // private BroadcastReceiver _refreshReceiver = new MyReceiver();
    BarcodeDetector barcodeDetector;
    private static final int QR_CODE_SCAN = 1;
    public int address_request_code = 2;
    public String bleAdress ;
    CameraSource cameraSource;
    private final static int REQUEST_OPEN_BT_CODE = 1;
    private final static int REQUEST_LOCATION = 1;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    private ArrayList<BluetoothLeDevice> blueto = new ArrayList<>();
    private int hardcode = 0;
    FloatingActionButton toScan;
    final int RequestCameraPermissionID = 1001;
    private RecyclerView connectedBle;
    private LinearLayout emptyView;
    private BluetoothDeviceAdapter bluetoothDeviceAdapter;
    int step_count = 0;
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_LOCATION: {
                if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
// <-- Start Beemray here
                } else {
                    getActivity().finish();
// Permission was denied or request was cancelled
                }
                break;
            }
            case RequestCameraPermissionID: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.checkSelfPermission(getContext(),
                            Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    try {
                        cameraSource.start(cameraPreview.getHolder());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
            }
        }
    }
    private void notifySet(){
        try {
            Log.d("inde",String.valueOf(ViseBle.getInstance().getDeviceMirrorPool().getDeviceList().size()));

            blueto.clear();
            for(BluetoothLeDevice ble :ViseBle.getInstance().getDeviceMirrorPool().getDeviceList()) {
                ViseBle.getInstance().getDeviceMirror(ble).getBatteryLevel();
                blueto.add(ble);
                Log.d("bleConnx",ble.getName());

            }
        }
        catch (Exception e){
            Log.d("device","null");
        }
    }
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_item_list,container,false);

        toScan = view.findViewById(R.id.addDevice);
        toScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), QRCodeScanActivity.class);
                startActivityForResult(intent,QR_CODE_SCAN);
            }
        });

        /*
        IntentFilter filter = new IntentFilter("SOMEACTION");
        getActivity().registerReceiver(_refreshReceiver, filter);
        */
        //checkBluetoothPermission();
        checkLocationPermission();
        connectedBle = (RecyclerView) view.findViewById(R.id.recycler);
        emptyView =  view.findViewById(R.id.empty_view);
        notifySet();
        bluetoothDeviceAdapter = new BluetoothDeviceAdapter(getContext(),blueto);
        connectedBle.setAdapter(bluetoothDeviceAdapter);
        connectedBle.setLayoutManager(new LinearLayoutManager(getContext()));
// ...


        /*
        cameraPreview = (SurfaceView) view.findViewById(R.id.cameraPreview);
        txtResult = (TextView) view.findViewById(R.id.txtResult);

        barcodeDetector = new BarcodeDetector.Builder(getContext())
                .setBarcodeFormats(Barcode.QR_CODE)
                .build();

        cameraSource = new CameraSource
                .Builder(getContext(), barcodeDetector)
                .setAutoFocusEnabled(true)
                .setRequestedPreviewSize(640, 480)
                .build();
        cameraPreview.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                if (ActivityCompat.checkSelfPermission(getContext(),
                        Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    //Request permission
                    ActivityCompat.requestPermissions(getActivity(),
                            new String[]{Manifest.permission.CAMERA},RequestCameraPermissionID);
                    return;
                }
                try {
                    cameraSource.start(cameraPreview.getHolder());
                } catch (IOException e) {
                    e.printStackTrace();
                }
//                if (ActivityCompat.checkSelfPermission(getContext(),
//                        android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
//                        ActivityCompat.checkSelfPermission(getContext(),
//                                android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                    requestPermissions(getActivity(),
//                            new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION,
//                                    android.Manifest.permission.ACCESS_FINE_LOCATION},
//                            REQUEST_LOCATION);
//                } else {
//                    Log.e("DB", "PERMISSION GRANTED");
//                }

                bluetooth = new MyBluetoothService("45:53:3C:3D:14:D8",getContext(),getActivity());
                bluetooth.init();

            }

            @Override
            public void surfaceChanged(SurfaceHolder surfaceHolder, int i , int j, int k){

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceHolder){

            }
        });
        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {

            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> qrcodes = detections.getDetectedItems();
                if(qrcodes.size()!=0)
                {
                    txtResult.post(new Runnable(){
                        @Override
                        public void run() {
                            //Create vibrate

                            if(bluetooth == null){
                                Vibrator vibrator = (Vibrator) getContext().getSystemService(Context.VIBRATOR_SERVICE);
                                vibrator.vibrate(1000);
                                txtResult.setText(qrcodes.valueAt(0).displayValue);
                                bluetooth = new MyBluetoothService(qrcodes.valueAt(0).displayValue,getContext(),getActivity());
                                bluetooth.init();
                            }
                        }
                    });

                }
            }

        });
        */
        return view;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("reequest",String.valueOf(resultCode)+" "+String.valueOf(RESULT_OK));
        if (requestCode == QR_CODE_SCAN && resultCode == RESULT_OK && data != null) {
            
            bleAdress = data.getStringExtra("address");
            if(ViseBle.getInstance().getDeviceMirrorPool()==null||(ViseBle.getInstance().getDeviceMirrorPool()!=null
                    &&ViseBle.getInstance().getDeviceMirrorPool().getDeviceList().size()==0)) {
                try {
                    for (BluetoothLeDevice x : ViseBle.getInstance().getDeviceMirrorPool().getDeviceList()) {
                        Log.d("bleadr", x.getAddress());
                    }
                }
                catch (Exception e)
                {

                }
                bluetooth = new MyBluetoothService(bleAdress, getContext(), getActivity());

                        bluetooth.init();

            }
            else if (ViseBle.getInstance().getDeviceMirrorPool().getDeviceList().size()==1)
            {
                secondBLE = new SecondBLE(bleAdress, getContext(), getActivity());

                        secondBLE.init();

            }

        }
    }
    @Override
    public void onResume() {
        super.onResume();
        notifySet();
        bluetoothDeviceAdapter.notifyDataSetChanged();
        if (blueto.isEmpty()) {
            connectedBle.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        }
        else {
            connectedBle.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);

        }
    }

    private void checkBluetoothPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //校验是否已具有模糊定位权限
            if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                PermissionManager.instance().with(getActivity()).request(new OnPermissionCallback() {
                    @Override
                    public void onRequestAllow(String permissionName) {
                        enableBluetooth();
                    }

                    @Override
                    public void onRequestRefuse(String permissionName) {
                        getActivity().finish();
                    }

                    @Override
                    public void onRequestNoAsk(String permissionName) {
                        getActivity().finish();
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
        if (!BleUtil.isBleEnable(getContext())) {
            BleUtil.enableBluetooth(getActivity(), 1);
        } else {
            boolean isSupport = BleUtil.isSupportBle(getContext());
            boolean isOpenBle = BleUtil.isBleEnable(getContext());

        }
    }

    public void checkLocationPermission() {

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.d("open","open");
            PermissionManager.instance().with(getActivity()).request(new OnPermissionCallback() {
                @Override
                public void onRequestAllow(String permissionName) {
                    final Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    getActivity().startActivityForResult(intent,REQUEST_LOCATION);
                }

                @Override
                public void onRequestRefuse(String permissionName) {
                   // getActivity().finish();
                }

                @Override
                public void onRequestNoAsk(String permissionName) {
                   // getActivity().finish();
                }
            }, Manifest.permission.ACCESS_FINE_LOCATION);
        }
        else {
            Log.d("opened","opened");
        }
    }




}
