package com.example.logisticare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Point;
import android.location.Location;
import android.os.Bundle;
import android.os.*;
import android.provider.ContactsContract;
import android.view.Display;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.logisticare.Entities.Parcel;
import com.example.logisticare.Entities.User;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       // EditText phoneNumber = (EditText)findViewById(R.id.phoneNumber);



        // spinner for type list
        Spinner spinner = (Spinner) findViewById(R.id.type_spinner);
        ArrayAdapter<CharSequence> adapter;
        adapter = ArrayAdapter.createFromResource(this,R.array.type_list, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);



        //spinner for breakable
        Spinner breakable_spinner = (Spinner) findViewById(R.id.breakable_spinner);
        ArrayAdapter<CharSequence> adapter_breakable;
        adapter_breakable = ArrayAdapter.createFromResource(this,R.array.breakable_list, android.R.layout.simple_spinner_item);
        adapter_breakable.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        breakable_spinner.setAdapter(adapter_breakable);




        //spinner for breakable
        Spinner package_weight_spinner = (Spinner) findViewById(R.id.package_weight_spinner);
        ArrayAdapter<CharSequence> adapter_package_weight;
        adapter_package_weight = ArrayAdapter.createFromResource(this,R.array.Package_weight_list, android.R.layout.simple_spinner_item);
        adapter_package_weight.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        package_weight_spinner.setAdapter(adapter_package_weight);

        TextInputLayout textInputLayout = (TextInputLayout) findViewById(R.id.TextInputLayoutPhoneNumber);
       // textInputLayout.getEditText().setText("23");

        //Button sendButton = (Button) findViewById(R.id.sendButton);
        //sendButton.setOnClickListener(this);
    }


    public String convertDateToString(Date date){
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        return formatter.format(date);
    }




    @Override
    public void onClick(View v) {

        //id
        //TODO: take random id from firebase (maybe no need...)

        //type
        Spinner type_spinner = (Spinner) findViewById(R.id.type_spinner);
        Parcel.PackType type = (Parcel.PackType)type_spinner.getSelectedItem();

        //breakable
        Spinner breakable_spinner = (Spinner) findViewById(R.id.breakable_spinner);
        Boolean breakable = (Boolean) breakable_spinner.getSelectedItem();

        //packageWeight
        Spinner package_weight_spinner = (Spinner) findViewById(R.id.package_weight_spinner);
        Parcel.PackageWeight packageWeight = (Parcel.PackageWeight) package_weight_spinner.getSelectedItem();

        //location
        String locationEditText = findViewById(R.id.locationTextView).toString();
        //TODO: convert string type to location type

        //phoneNumber (receiver)
        String phoneNumber = findViewById(R.id.TextInputEditTextPhoneNumber).toString();
        //TODO: pull the hole User from the firebase

        //dateSend
        Date dateSend = new Date(); //return the current date

        //status
        Parcel.PackStatus status = Parcel.PackStatus.SENT;

        //date recived and reciver set in future.



        //TODO: check why the app fall down in the next line
        Parcel parcel = new Parcel();

        //  Toast toast = new Toast(getApplicationContext());
        Toast.makeText(this, "bla bla bla", Toast.LENGTH_SHORT).show();
    }
}
