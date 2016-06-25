package com.example.abdelmageed.chatting.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.util.Base64;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;


public class Utils {

    /*http://fontawesome.io/cheatsheet/*/


    public Context context;
    public String FontName;
    String fontPath;

    public Utils(Context context, String FontName) {
        this.context = context;
        this.FontName = FontName;
        fontPath = "fonts/" + FontName;
    }


    public Utils(Context context) {
        this.context = context;
    }

    public void FonTChange(TextView textView) {

        // Loading Font Face
        Typeface tf = Typeface.createFromAsset(context.getAssets(), fontPath);
        // Applying font
        textView.setTypeface(tf);
    }


    public void FonTChange(Button textView) {

        // Loading Font Face
        Typeface tf = Typeface.createFromAsset(context.getAssets(), fontPath);
        // Applying font
        textView.setTypeface(tf);
    }


    public void FonTChange(TextInputLayout textView) {

        // Loading Font Face
        Typeface tf = Typeface.createFromAsset(context.getAssets(), fontPath);
        // Applying font
        textView.setTypeface(tf);
    }

    public void FonTChange(RadioButton radioButton) {
        Typeface font = Typeface.createFromAsset(context.getAssets(), fontPath);
        radioButton.setTypeface(font);
    }

    public void AwSomeFont(TextView textView) {

        String fontPath = "fonts/fontawesome-webfont.ttf";
        // Loading Font Face
        Typeface tf = Typeface.createFromAsset(context.getAssets(), fontPath);
        // Applying FonTChange
        textView.setTypeface(tf);
    }


    public void AwSomeFont(Button textView) {

        String fontPath = "fonts/fontawesome-webfont.ttf";
        // Loading Font Face
        Typeface tf = Typeface.createFromAsset(context.getAssets(), fontPath);
        // Applying font
        textView.setTypeface(tf);

    }


    public void ToastCustom(Context context, String str) {


        Toast toast = Toast.makeText(context, str, Toast.LENGTH_SHORT);
        LinearLayout toastLayout = (LinearLayout) toast.getView();
        TextView toastTV = (TextView) toastLayout.getChildAt(0);

        toastTV.setTextSize(15);


        String fontPath = "fonts/JF_Flat_regular.ttf";
        // Loading Font Face
        Typeface tf = Typeface.createFromAsset(context.getAssets(), fontPath);
        // Applying font
        toastTV.setTypeface(tf);
        toast.show();
    }


    public static boolean isNetworkAvailable(final Context context) {
        final ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }


    public static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }


    //Base 64
    public static String getEncoded64ImageStringFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream);
        byte[] byteFormat = stream.toByteArray();
        // get the base 64 string
        String imgString = Base64.encodeToString(byteFormat, Base64.NO_WRAP);

        return imgString;
    }

}
