package com.example.shazzerhaat;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.TextUtils;
import android.util.Log;
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
import com.google.firebase.database.core.Tag;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import static androidx.constraintlayout.widget.Constraints.TAG;


public class DonationPage extends Fragment {


    private EditText name, number, nid, address, amount, others, trxnId;
    private CheckBox checkBox, checkBox2, checkBox3, checkBox4, checkBox5,checkBox6;
    private Button submit;

    String donner_money="no";
    String donner_otherOption="no";
    String donner_food = "";
    String donner_sick = "";


    final FirebaseDatabase database = FirebaseDatabase.getInstance();

    DatabaseReference ref = database.getReference("donner");
    //cloud storage

    String time = "";


    final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference colletRef1 = db.collection("give support");


    public DonationPage() {
        // Required empty public constructor
    }

    private FrameLayout parentFrameLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_donation_page, container, false);

        name = view.findViewById(R.id.name);
        number = view.findViewById(R.id.number);
        nid = view.findViewById(R.id.nid);
        address = view.findViewById(R.id.address);
        amount = view.findViewById(R.id.amount);
        others = view.findViewById(R.id.others);
        trxnId = view.findViewById(R.id.trxnId);

        checkBox = view.findViewById(R.id.checkBox);
        checkBox2 = view.findViewById(R.id.checkBox2);
        checkBox3 = view.findViewById(R.id.checkBox3);
        checkBox4 = view.findViewById(R.id.checkBox4);
        checkBox5 = view.findViewById(R.id.checkBox5);
        checkBox6 = view.findViewById(R.id.checkBox6);

        submit =view.findViewById(R.id.submit);
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

        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (checkBox.isChecked()) {

                    donner_money = "yes";

                }else
                {
                    Toast.makeText(getActivity(), "select at least one", Toast.LENGTH_SHORT).show();
                }
            }
        });
        checkBox2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                if(checkBox2.isChecked()){

                    others.setEnabled(true);
                    donner_otherOption = "yes";

                }
            }
        });
        //survey
        checkBox3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                if(checkBox3.isChecked()){

                    donner_sick = "yes";

                }
            }
        });
        checkBox4.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                if(checkBox4.isChecked()){

                    donner_sick = "no";
                    //usersRef.child(donner_nid).child("sick").setValue(donner_sick);
                }
            }
        });
        checkBox5.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                if(checkBox5.isChecked()){

                    donner_food = "yes";
                    // usersRef.child(donner_nid).child("sanitizer").setValue(donner_food);
                }
            }
        });
        checkBox6.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                if(checkBox6.isChecked()){

                    donner_food = "no";

                }
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                submitForm();
            }
        });
    }

    private void submitForm() {
        DatabaseReference usersRef = ref.child("users");


        String donner_amount = amount.getText().toString();
        String donner_trxnId = trxnId.getText().toString();
        String donner = name.getText().toString();
        String donner_number = number.getText().toString();
        String donner_address = address.getText().toString();
        String donner_other = others.getText().toString();
        String donner_nid = nid.getText().toString();


        //check data

        if(!TextUtils.isEmpty(donner_nid) || TextUtils.isEmpty(donner_nid)){

                if(!TextUtils.isEmpty(donner)){
                    if(!TextUtils.isEmpty(donner_number) && donner_number.length()==11){
                        if(!TextUtils.isEmpty(donner_address)){
                            //add another things


                            if(donner_money.equals("yes") || donner_otherOption.equals("yes") || donner_money.equals("no")){
                                amount.setEnabled(true);

                                if(!TextUtils.isEmpty(donner_amount)){

                                    if(!TextUtils.isEmpty(donner_trxnId)){
                                        if(!donner_sick.equals("") && !donner_food.equals("")){

                                            Note_donation note = new Note_donation(donner,donner_number,donner_address,donner_amount,donner_other,donner_trxnId,time,donner_sick,donner_food);
                                            colletRef1.add(note)
                                                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                        @Override
                                                        public void onSuccess(DocumentReference documentReference) {
                                                            Toast.makeText(getActivity(), "Thank You So Much ❤", Toast.LENGTH_SHORT).show();
                                                        }
                                                    });

//                                            usersRef.child(donner_nid).child("name").setValue(donner);
//                                            usersRef.child(donner_nid).child("number").setValue(donner_number);
//                                            usersRef.child(donner_nid).child("address").setValue(donner_address);
//                                            usersRef.child(donner_nid).child("amount").setValue(donner_amount);
//                                            usersRef.child(donner_nid).child("TrxnID").setValue(donner_trxnId);
//                                            usersRef.child(donner_nid).child("others").setValue(donner_other);
//                                            usersRef.child(donner_nid).child("sick").setValue(donner_sick);
//                                            usersRef.child(donner_nid).child("sanitizer").setValue(donner_food);
//
//                                            Toast.makeText(getActivity(), "Thank You So Much ❤", Toast.LENGTH_SHORT).show();

                                        }else{
                                            Toast.makeText(getActivity(), "দয়া করে জরিপটি পূরণ করুন", Toast.LENGTH_SHORT).show();
                                        }


                                    }else{
                                        Toast.makeText(getActivity(), "TrxnID field can not be empty", Toast.LENGTH_SHORT).show();
                                    }

                                }else{
                                    Toast.makeText(getActivity(), "Amount field can not be empty", Toast.LENGTH_SHORT).show();
                                }

                            }else{
                                Toast.makeText(getActivity(), "select a money or other option above", Toast.LENGTH_SHORT).show();

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

            }
        else{
            Toast.makeText(getActivity(), "Nid field can not be empty", Toast.LENGTH_SHORT).show();

        }

    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();

        fragmentTransaction.setCustomAnimations(R.anim.slide_in_right,R.anim.slide_out_left);

        fragmentTransaction.replace(parentFrameLayout.getId(),fragment).addToBackStack(null).commit();

    }





}