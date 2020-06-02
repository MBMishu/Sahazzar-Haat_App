package com.example.shazzerhaat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;


public class receivePage extends Fragment {
    private EditText name, number, nid, address, family,area,district;
    private CheckBox checkBox, checkBox2, checkBox3, checkBox4;
    private Button submit2;

    String receive_outside = "";
    String receive_sick = "";



    final FirebaseDatabase database = FirebaseDatabase.getInstance();

    DatabaseReference ref = database.getReference("need_help");
    //cloud storage
    String district1 = "dhaka";
    String area1= "mirpur";
    String time = "12pm";
    String nid1 = "12345";



    final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference colletRef1 = db.collection("need support");


    public receivePage() {
        // Required empty public constructor
    }
    private FrameLayout parentFrameLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_receive_page, container, false);

        name = view.findViewById(R.id.name2);
        number = view.findViewById(R.id.number2);
        nid = view.findViewById(R.id.nid2);
        address = view.findViewById(R.id.address2);
        area = view.findViewById(R.id.address3);
        district = view.findViewById(R.id.address4);
        family = view.findViewById(R.id.family);


        checkBox = view.findViewById(R.id.checkBox7);
        checkBox2 = view.findViewById(R.id.checkBox8);
        checkBox3 = view.findViewById(R.id.checkBox9);
        checkBox4 = view.findViewById(R.id.checkBox10);


        submit2 =view.findViewById(R.id.submit2);

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss a");
        String dateTime = simpleDateFormat.format(calendar.getTime());
        time = dateTime;


        parentFrameLayout = getActivity().findViewById(R.id.starting_layout);
        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        submit2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

//                sendDataToCloud();
                submitNeedForm();

            }
    });
        //survey

        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (checkBox.isChecked()) {

                    receive_sick = "yes";

                }else
                {
                    Toast.makeText(getActivity(), "select at least one", Toast.LENGTH_SHORT).show();
                }
            }
        });
        checkBox2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (checkBox2.isChecked()) {

                    receive_sick = "no";

                }else
                {
                    Toast.makeText(getActivity(), "select at least one", Toast.LENGTH_SHORT).show();
                }
            }
        });
        checkBox3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (checkBox3.isChecked()) {

                    receive_outside = "yes";

                }else
                {
                    Toast.makeText(getActivity(), "select at least one", Toast.LENGTH_SHORT).show();
                }
            }
        });
        checkBox4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (checkBox4.isChecked()) {

                    receive_outside = "no";

                }else
                {
                    Toast.makeText(getActivity(), "select at least one", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void submitNeedForm() {
        DatabaseReference usersRef = ref.child("users");

        String need_nid = nid.getText().toString();
        String need_name = name.getText().toString();
        String need_number = number.getText().toString();
        String need_address = address.getText().toString();
        String need_area= area.getText().toString();
        String need_district = district.getText().toString();
        String need_family = family.getText().toString();

        if(!TextUtils.isEmpty(need_nid)) {
            if (need_nid.trim().length() >= 10) {
                if (!TextUtils.isEmpty(need_name)) {
                    if (!TextUtils.isEmpty(need_number) && need_number.length()==11) {
                        if (!TextUtils.isEmpty(need_address)) {
                            if (!TextUtils.isEmpty(need_area)) {
                                if (!TextUtils.isEmpty(need_district)) {
                                    if (!TextUtils.isEmpty(need_family)) {
                                        if(!receive_sick.equals("") && !receive_outside.equals("")){
                                            Note note = new Note(need_name,need_number,need_nid,need_address,need_family,need_area,need_district,time,receive_outside,receive_sick);
                                            colletRef1.add(note)
                                                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                        @Override
                                                        public void onSuccess(DocumentReference documentReference) {
                                                            Toast.makeText(getActivity(), "Submission Done", Toast.LENGTH_SHORT).show();
                                                            NotificationCompat.Builder builder = new NotificationCompat.Builder(getActivity(), "general")
                                                                    .setContentTitle("Shazzar Haat")
                                                                    .setSmallIcon(R.drawable.ic_launcher_background)
                                                                    .setAutoCancel(true)
                                                                    .setContentText("Submission Complete.\nWe will contact you soon.");
                                                            NotificationManagerCompat managerCompat = NotificationManagerCompat.from(getActivity());
                                                            managerCompat.notify(999,builder.build());
                                                        }
                                                    })
                                                    .addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception e) {
                                                            Toast.makeText(getActivity(), "Try Again!", Toast.LENGTH_SHORT).show();
                                                        }
                                                    });
                                        }else{
                                            Toast.makeText(getActivity(), "দয়া করে জরিপটি পূরণ করুন", Toast.LENGTH_SHORT).show();
                                        }
                                    }else{
                                        Toast.makeText(getActivity(), "Mention Your Number of Your Family memeber ", Toast.LENGTH_SHORT).show();

                                    }
                                }else {
                                    Toast.makeText(getActivity(), "District field can not be empty", Toast.LENGTH_SHORT).show();
                                }
                            }else{
                                Toast.makeText(getActivity(), "Area field can not be empty", Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(getActivity(), "Address field can not be empty", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(getActivity(), "Number is Error!", Toast.LENGTH_SHORT).show();

                    }
                }else{
                    Toast.makeText(getActivity(), "Name field can not be empty", Toast.LENGTH_SHORT).show();

                }
            }else{
                Toast.makeText(getActivity(), "Nid no Error!", Toast.LENGTH_SHORT).show();

            }
        }else{
            Toast.makeText(getActivity(), "Nid no field can not be empty", Toast.LENGTH_SHORT).show();

        }
    }

    private void sendDataToCloud() {
        DatabaseReference usersRef = ref.child("users");

        String need_nid = nid.getText().toString();
        String need_name = name.getText().toString();
        String need_number = number.getText().toString();
        String need_address = address.getText().toString();
        String need_area= area.getText().toString();
        String need_district = district.getText().toString();
        String need_family = family.getText().toString();

        district1 = need_district;
        area1 = need_area;
        nid1 = need_nid;
        String path1 = district1+"/"+area1+"/"+nid1+"/"+time;
        String path = "need support/users/details/"+path1;
        CollectionReference colletRef = db.collection(path);

        if(!TextUtils.isEmpty(need_nid)) {
            if (need_nid.trim().length() >= 10) {
                if (!TextUtils.isEmpty(need_name)) {
                    if (!TextUtils.isEmpty(need_number) && need_number.length()==11) {
                        if (!TextUtils.isEmpty(need_address)) {
                            if (!TextUtils.isEmpty(need_area)){
                                if (!TextUtils.isEmpty(need_district)){
                                    if (!TextUtils.isEmpty(need_family)){

                                        if(!receive_sick.equals("") && !receive_outside.equals("")){


                                            Note note = new Note(need_name,need_number,need_nid,need_address,need_family,need_area,need_district,time,receive_outside,receive_sick);
                                            colletRef1.add(note);

                                            colletRef.add(note)
                                                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                        @Override
                                                        public void onSuccess(DocumentReference documentReference) {
                                                            Toast.makeText(getActivity(), "We will contact you", Toast.LENGTH_SHORT).show();
                                                        }
                                                    });
//                                            usersRef.child(need_district).child("Area").child(need_area).child("Address").child(need_address).child("NID no").setValue(need_nid);
//                                            usersRef.child(need_district).child("Area").child(need_area).child("Address").child(need_address).child("Name").setValue(need_name);
//                                            usersRef.child(need_district).child("Area").child(need_area).child("Address").child(need_address).child("Number").setValue(need_number);
//                                            usersRef.child(need_district).child("Area").child(need_area).child("Address").child(need_address).child("Sick").setValue(receive_sick);
//                                            usersRef.child(need_district).child("Area").child(need_area).child("Address").child(need_address).child("Outsider").setValue(receive_outside);
//                                            Toast.makeText(getActivity(), "We will contact you", Toast.LENGTH_SHORT).show();

                                        }else{
                                            Toast.makeText(getActivity(), "দয়া করে জরিপটি পূরণ করুন", Toast.LENGTH_SHORT).show();

                                        }

                                    }else{
                                        Toast.makeText(getActivity(), "Mention Your Number of Your Family memeber ", Toast.LENGTH_SHORT).show();

                                    }

                                }else {
                                    Toast.makeText(getActivity(), "District field can not be empty", Toast.LENGTH_SHORT).show();
                                }
                            }else{
                                Toast.makeText(getActivity(), "Area field can not be empty", Toast.LENGTH_SHORT).show();

                            }

                        }else{
                            Toast.makeText(getActivity(), "Address field can not be empty", Toast.LENGTH_SHORT).show();

                        }
                    }else{
                        Toast.makeText(getActivity(), "Number is Error!", Toast.LENGTH_SHORT).show();

                    }
                }else{
                    Toast.makeText(getActivity(), "Name field can not be empty", Toast.LENGTH_SHORT).show();

                }
            }else{
                Toast.makeText(getActivity(), "Nid no field can not be empty", Toast.LENGTH_SHORT).show();

            }
        }else{
            Toast.makeText(getActivity(), "Nid no Erroe!", Toast.LENGTH_SHORT).show();

        }
    }
}
