package com.example.xlwapp.receiver;

import android.content.Intent;
import android.util.Log;

import com.huawei.hms.push.HmsMessageService;

public class MyPushService extends HmsMessageService {
    @Override
    public void onNewToken(String token) {
        super.onNewToken(token);
        Log.e("testtoken", token);
        sendTokenToDisplay(token);
    }

    private void sendTokenToDisplay(String token) {
        Intent intent = new Intent("com.huawei.push.codelab.ON_NEW_TOKEN");
        intent.putExtra("token", token);
        sendBroadcast(intent);
    }
}