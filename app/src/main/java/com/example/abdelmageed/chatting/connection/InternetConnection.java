package com.example.abdelmageed.chatting.connection;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


public class InternetConnection {


    private Context _context;

    public InternetConnection(Context context)

    {
        this._context = context;
    }

        public boolean isConnectingToInternet(){

            ConnectivityManager cm = (ConnectivityManager) _context. getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            if (netInfo != null && netInfo.isConnectedOrConnecting()) {
                return true;
            } else {
                return false;
            }
        }
    }

