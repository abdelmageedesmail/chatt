package com.example.abdelmageed.chatting.activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.abdelmageed.chatting.R;
import com.example.abdelmageed.chatting.connection.GPSTracker;
import com.example.abdelmageed.chatting.connection.InternetConnection;
import com.example.abdelmageed.chatting.fragment.FragmentDrawer;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    GPSTracker mGPS;
    SharedPreferences sh;
    public static final String mypreference = "Mypref";
    String userId,friendID,friendName,friendColleauge;
    Toolbar mToolbar;
    ImageView imgarraw;
    StringRequest stringRequest;
    RequestQueue requestQueue;
    public static String lon,lat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        mToolbar=(Toolbar)findViewById(R.id.tool);
        imgarraw=(ImageView)findViewById(R.id.backarraw);
        imgarraw.setImageResource(R.mipmap.arraw);
        imgarraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mGPS = new GPSTracker(getApplicationContext());


        sh = getSharedPreferences(mypreference, Context.MODE_PRIVATE);
        userId = sh.getString("userId", null);

//------------------------------------------------------------------------------------------


        class GetGBS extends TimerTask {
            public void run() {
                System.out.println("Hello World!");

                if (mGPS.canGetLocation) {
                    mGPS.getLocation();
                    //mGPS.onLocationChanged(mGPS.getLocation());
                    Log.i("Lat  ", mGPS.getLatitude() + "       Lon" + mGPS.getLongitude());
                    String lon = String.valueOf(mGPS.getLongitude());
                    String lat = String.valueOf(mGPS.getLatitude());
                    new driverTask(userId, lat, lon).execute();
                }

            }

        }
        // And From your main() method or any other method
        Timer timer = new Timer();
        timer.schedule(new GetGBS(), 0, 5000);

        getContact();
    }




    public void getContact(){
        stringRequest=new StringRequest(Request.Method.POST, "http://emtyazna.com/mohamed/chating/index.php/activities/getContacts", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.print(response);
                try {
                    JSONObject object=new JSONObject(response);
                    friendID=object.getString("userId");
                    friendName=object.getString("userId");
                    friendColleauge=object.getString("userCollegeId");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> param=new HashMap<String,String>();
                String lon=String.valueOf(mGPS.getLongitude());
                String lat=String.valueOf(mGPS.getLatitude());
                param.put("userId",userId);
                param.put("userLang",lon);
                param.put("userLat",lat);
                return param;
            }
        };
        requestQueue= Volley.newRequestQueue(MapsActivity.this);
        requestQueue.add(stringRequest);
    }




    @Override
    public void onBackPressed() {
        startActivity(new Intent(MapsActivity.this,InnerActivity.class));
        finish();
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        if (mGPS.canGetLocation) {
            mGPS.getLocation();
            //mGPS.onLocationChanged(mGPS.getLocation());
            Log.i("Lat  ", mGPS.getLatitude() + "       Lon" + mGPS.getLongitude());
            lon = String.valueOf(mGPS.getLongitude());
            lat = String.valueOf(mGPS.getLatitude());
            SharedPreferences.Editor editor=sh.edit();
            editor.putString("latUser",lat);
            editor.putString("lonUser",lon);
            editor.commit();
        }
        mGPS = new GPSTracker(MapsActivity.this);
        try {
            MapsInitializer.initialize(getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMap.setMyLocationEnabled(true);
        LatLng sydney = new LatLng(mGPS.getLatitude(), mGPS.getLongitude());
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in my Location"));
        mMap.setBuildingsEnabled(true);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            return;
        }
        mMap.setMyLocationEnabled(true);
        CameraUpdate zoom = CameraUpdateFactory.zoomTo(15);
        mMap.animateCamera(zoom);
    }
    class driverTask extends AsyncTask<Void, Void, String> {

        String id, lat, lon;


        public driverTask(final String id, final String lat, final String lon) {
            this.id = id;
            this.lat = lat;
            this.lon = lon;
        }

        @Override
        protected String doInBackground(Void... params) {
            HttpURLConnection urlConnection = null;
            String response = null;

            try {
                URL url = new URL("http://emtyazna.com/mohamed/chating/index.php/activities/sendUserPlace");
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("POST");
                urlConnection.setDoOutput(true);
                urlConnection.setDoInput(true);
                Uri.Builder builder = new Uri.Builder()
                        .appendQueryParameter("userId", id)
                        .appendQueryParameter("userLang", lon)
                        .appendQueryParameter("userLat", lat);

                String query = builder.build().getEncodedQuery();
                OutputStream os = urlConnection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(query);
                writer.flush();
                writer.close();
                os.close();
                urlConnection.connect();
                os.close();
                InputStream in = urlConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                }
                response = sb.toString();
                Log.v("debug", response);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                urlConnection.disconnect();
            }
            return response;
        }



        @Override
        protected void onPostExecute(String response) {
            super.onPostExecute(response);
            if (response.contains("false")) {
                Log.i("error", "error");
            } else if (response.contains("id")) {

            }
        }
    }



    private Location getMyLocation() {
        // Get location from GPS if it's available
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
        }
        Location myLocation = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        // Location wasn't found, check the next most accurate place for the current location
        if (myLocation == null) {
            Criteria criteria = new Criteria();
            criteria.setAccuracy(Criteria.ACCURACY_COARSE);
            // Finds a provider that matches the criteria
            String provider = lm.getBestProvider(criteria, true);
            // Use the provider to get the last known location
            myLocation = lm.getLastKnownLocation(provider);
        }
        return myLocation;
    }


    protected boolean isOnline() {

        InternetConnection detector = new InternetConnection(MapsActivity.this);

        if (detector.isConnectingToInternet() == true) {

            return true;

        } else {

            return false;
        }


    }

}
