package com.example.shazzerhaat;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.core.Tag;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.auth.User;
import com.google.firebase.messaging.FirebaseMessaging;
import com.karan.churi.PermissionManager.PermissionManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static com.example.shazzerhaat.R.id.address;

public class startingPage extends AppCompatActivity {


    private FrameLayout frameLayout;

    String number = "+10655"; // number change to +10655


    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    final FirebaseDatabase database = FirebaseDatabase.getInstance();

    DatabaseReference ref = database.getReference("donner");
//permission
    PermissionManager permissionManager;
    private static final int REQUEST_CALL = 1;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_starting_page);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel("general","general", NotificationManager.IMPORTANCE_DEFAULT);

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

        FirebaseMessaging.getInstance().subscribeToTopic("general")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg = "sucess";
                        if (!task.isSuccessful()) {
                            msg = "failed";
                        }
                       // Toast.makeText(startingPage.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });

        //permission
        permissionManager = new PermissionManager(){};
        permissionManager.checkAndRequestPermissions(this);

        frameLayout = findViewById(R.id.starting_layout);

      //  setFragment(new HomePage());


    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        fragmentTransaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);
        fragmentTransaction.replace(frameLayout.getId(), fragment).addToBackStack("HomePage").commit();

    }


    public void donation(View view) {
        setFragment(new DonationPage());
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            Toast.makeText(getApplicationContext(), "Protrait mode", Toast.LENGTH_SHORT).show();
        } else if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Toast.makeText(getApplicationContext(), "landscape mode", Toast.LENGTH_SHORT).show();
        }
    }


    public void receivePage(View view) {
        setFragment(new receivePage());
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void call(View view) {

        makephonecall();



    }

    private void makephonecall() {
        String s = "tel:" + number;
        Intent phoneCall = new Intent(Intent.ACTION_CALL, Uri.parse(s));

        if (ContextCompat.checkSelfPermission(startingPage.this,Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(startingPage.this,
                    new  String[] {Manifest.permission.CALL_PHONE},REQUEST_CALL);

        }
        startActivity(phoneCall);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        permissionManager.checkResult(requestCode,permissions,grantResults);

        ArrayList<String> granted = permissionManager.getStatus().get(0).granted;
        ArrayList<String> denied = permissionManager.getStatus().get(0).denied;

        if(requestCode == REQUEST_CALL){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                makephonecall();
            }else {
                Toast.makeText(startingPage.this,"Permission Denied",Toast.LENGTH_SHORT).show();
            }
        }

    }

    public void details(View view) {
    }

    public void protectPage(View view) {
        setFragment(new protect());

    }

    public void updatePage(View view) {

        Intent intent = new Intent(startingPage.this,update.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
    }

    public void corona(View view) {

        Intent intent = new Intent(startingPage.this,update.class);
        startActivity(intent);

        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
    }

    public void world_corona(View view) {
        Intent browser = new Intent(Intent.ACTION_VIEW,Uri.parse("https://corona.gov.bd/"));
        startActivity(browser);
    }

    public void popUpWindow(View view) {
        Intent intent = new Intent(getApplicationContext(),popWindow.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);

    }

    public void levelest(View view) {
        Intent intent = new Intent(startingPage.this,LiveTest.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
    }

}
