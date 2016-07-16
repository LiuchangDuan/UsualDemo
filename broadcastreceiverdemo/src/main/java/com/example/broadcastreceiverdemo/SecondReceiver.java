package com.example.broadcastreceiverdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class SecondReceiver extends BroadcastReceiver {

    private static final String TAG = "OrderedBroadcast";

    @Override
    public void onReceive(Context context, Intent intent) {
//        String msg = intent.getStringExtra("msg");
        String msg = getResultExtras(true).getString("msg");
        Log.i(TAG, "SecondReceiver : " + msg);
//        abortBroadcast();
        Bundle bundle = new Bundle();
        bundle.putString("msg", msg + "@SecondReceiver");
        setResultExtras(bundle);
    }

}
