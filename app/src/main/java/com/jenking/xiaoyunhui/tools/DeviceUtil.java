package com.jenking.xiaoyunhui.tools;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class DeviceUtil {
    public static String getDeviceID(Context context) {
        String ANDROID_ID = "";
        TelephonyManager manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(context,new String[]{Manifest.permission.READ_PHONE_STATE},100);
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return "123456789012345";
        }
//        ANDROID_ID = manager.getDeviceId();

        return ANDROID_ID;
    }
    public static PackageInfo getAppInfo(Context context) {
        PackageManager manager = context.getPackageManager();
        int code = 0;
        PackageInfo info = null;
        try {
            info = manager.getPackageInfo(context.getPackageName(), 0);
            code = info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return info;
    }

    public static String getIP(Context context){
        NetworkInfo networkInfo = ((ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        if (networkInfo!=null&&networkInfo.isConnected()){
            if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE){
                try{
                    for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();){
                        NetworkInterface networkInterface = en.nextElement();
                        for (Enumeration<InetAddress> enumIpAddr = networkInterface.getInetAddresses(); enumIpAddr.hasMoreElements();){
                            InetAddress inetAddress = enumIpAddr.nextElement();
                            if (!inetAddress.isLoopbackAddress()&&inetAddress instanceof Inet4Address){
                                return inetAddress.getHostAddress();
                            }
                        }
                    }
                }catch (SocketException se){
                    se.printStackTrace();
                }

            }else if(networkInfo.getType()== ConnectivityManager.TYPE_WIFI){
                WifiManager wifiManager = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
                WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                String ipAddress = intIP2StringIP(wifiInfo.getIpAddress());//得到iPv4
                return ipAddress;
            }else{
                return "";
            }
        }else{
            return "";
        }

        return "";
    }

    public static String intIP2StringIP(int ip){
        return (ip & 0xFF)+"."+
                ((ip>>8)&0xFF)+"."+
                ((ip>>16)&0xFF)+"."+
                ((ip>>24)&0xFF);
    }
}
