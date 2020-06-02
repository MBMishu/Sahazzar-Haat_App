package com.example.shazzerhaat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;

import java.io.IOException;
import java.util.Locale;

public class popWindow extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_pop_window);

        loadLoacle();
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.9),(int)(height*.6));

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.x = 0;
        params.y = -20;

        getWindow().setAttributes(params);
    }


    public void gofacebook(View view) {
        Intent browser = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/SahazzarHaat"));
        startActivity(browser);
    }

    public void goMessenger(View view) throws IOException {

        Intent browser = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.messenger.com/t/SahazzarHaat"));
        startActivity(browser);

    }

    public void goWebsite(View view) {
        Intent browser = new Intent(Intent.ACTION_VIEW, Uri.parse("https://mbmishu.github.io/sahazzarhaat/"));
        startActivity(browser);
    }

    public void getBangla(View view) {
        setLocate("bn");
        recreate();
        Intent intent = new Intent(popWindow.this,MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void getEnglish(View view) {
        setLocate("en");
        recreate();
        Intent intent = new Intent(popWindow.this,MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void setLocate(String language) {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Configuration configuration = new Configuration();
        configuration.locale = locale;

        getBaseContext().getResources().updateConfiguration(configuration,getBaseContext().getResources().getDisplayMetrics());

        //save settings
        SharedPreferences.Editor editor = getSharedPreferences("Settings",Activity.MODE_PRIVATE).edit();
        editor.putString("My_Lang",language);
        editor.apply();

    }

    //load languae
    public void loadLoacle(){
        SharedPreferences preferences = getSharedPreferences("Settings", Activity.MODE_PRIVATE);
        String lang = preferences.getString("My_Lang","");
        setLocate(lang);
    }
}
