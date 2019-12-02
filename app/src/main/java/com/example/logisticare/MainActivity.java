package com.example.logisticare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.os.*;
import android.view.Display;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.logisticare.Entities.Parcel;
import com.google.android.material.textfield.TextInputLayout;

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

        TextInputLayout textInputLayout = (TextInputLayout) findViewById(R.id.ee);
       // textInputLayout.getEditText().setText("23");
    }


    @Override
    public void onClick(View v) {
      //  Toast toast = new Toast(getApplicationContext());
        Toast mToast = Toast.makeText(this, "", Toast.LENGTH_SHORT);
        Parcel parcel = new Parcel();


        mToast.setText(parcel.toString());
        mToast.show();
    }
}
