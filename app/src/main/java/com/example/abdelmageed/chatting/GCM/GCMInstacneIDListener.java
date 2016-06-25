package com.example.abdelmageed.chatting.GCM;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.android.gms.iid.InstanceIDListenerService;


public class GCMInstacneIDListener extends InstanceIDListenerService {

    SharedPreferences preferences;
    SharedPreferences.Editor prefEditor;

    @Override
    public void onTokenRefresh() {
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        prefEditor = preferences.edit();
        prefEditor.putBoolean("token_sent", false).apply();
        startService(new Intent(this, RegistrationServices.class));
    }
}