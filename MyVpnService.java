package com.example.vpnservice;

import android.app.PendingIntent;
import android.content.Intent;
import android.net.VpnService;
import android.os.ParcelFileDescriptor;
import android.util.Log;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class MyVpnService extends VpnService {
    private static final String TAG = "MyVpnService";
    private ParcelFileDescriptor vpnInterface = null;

    // Supported countries
    public static final List<String> SUPPORTED_COUNTRIES = Arrays.asList("Moldova", "United States");

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (vpnInterface == null) {
            startVpn();
        }
        return START_STICKY;
    }

    private void startVpn() {
        try {
            Builder builder = new Builder();

            // Generate a new VPN IP address
            String vpnIp = generateVpnIp();

            builder.setSession("SecureTunnel")
                   .setMtu(1500)
                   .addAddress(vpnIp, 24)
                   .addRoute("0.0.0.0", 0)  // Route all traffic
                   .addDnsServer("8.8.8.8");

            vpnInterface = builder.establish();
            Log.d(TAG, "VPN started with IP: " + vpnIp);
        } catch (Exception e) {
            Log.e(TAG, "Failed to start VPN", e);
        }
    }

    private String generateVpnIp() {
        Random rand = new Random();
        return String.format("10.0.%d.%d", rand.nextInt(254) + 1, rand.nextInt(254) + 1);
    }

    @Override
    public void onDestroy() {
        try {
            if (vpnInterface != null) {
                vpnInterface.close();
                vpnInterface = null;
            }
        } catch (IOException e) {
            Log.e(TAG, "Error closing VPN interface", e);
        }
        super.onDestroy();
    }
}
