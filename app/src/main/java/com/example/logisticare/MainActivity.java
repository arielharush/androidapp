package com.example.logisticare;
import androidx.appcompat.app.AppCompatActivity;
import android.app.ListActivity;
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
import android.widget.TextView;
import android.widget.Toast;
import com.example.logisticare.Entities.Parcel;
import com.example.logisticare.Entities.User;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.example.logisticare.Entities.Enums.*;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Spinner type_spinner;
    Spinner breakable_spinner;
    Spinner package_weight_spinner;
    TextView locationTextView;
    TextInputEditText TextInputEditTextPhoneNumber;

    //init DB
    static DatabaseReference ParcelsRef;
    static List<Parcel> parcelList;
    static {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        ParcelsRef = database.getReference("parcels");
        parcelList = new ArrayList<>();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    @Override
    public void onClick(View v) {
        Parcel parcelFromView = getParcelFromView();
        String key = ParcelsRef.push().getKey();
        parcelFromView.setKey(key);
        ParcelsRef.child(key).setValue(parcelFromView);
    }

    private void initView() {
        // spinner for type
        type_spinner = (Spinner) findViewById(R.id.type_spinner);
        type_spinner.setAdapter(new ArrayAdapter<PackType>(this,android.R.layout.simple_list_item_1,PackType.values()));
        ////////////ArrayAdapter<CharSequence> adapter;
        ////////////adapter = ArrayAdapter.createFromResource(this,R.array.type_list, android.R.layout.simple_spinner_item);
        ////////////adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ////////////type_spinner.setAdapter(adapter);

        //spinner for breakable
        breakable_spinner = (Spinner) findViewById(R.id.breakable_spinner);
        breakable_spinner.setAdapter(new ArrayAdapter<Bool>(this,android.R.layout.simple_list_item_1,Bool.values()));
        ////////////ArrayAdapter<CharSequence> adapter_breakable;
        ////////////adapter_breakable = ArrayAdapter.createFromResource(this,R.array.breakable_list, android.R.layout.simple_spinner_item);
        ////////////adapter_breakable.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ////////////breakable_spinner.setAdapter(adapter_breakable);

        //spinner for package weight
        package_weight_spinner = (Spinner) findViewById(R.id.package_weight_spinner);
        package_weight_spinner.setAdapter(new ArrayAdapter<PackageWeight>(this,android.R.layout.simple_list_item_1,PackageWeight.values()));
        ////////////ArrayAdapter<CharSequence> adapter_package_weight;
        ////////////adapter_package_weight = ArrayAdapter.createFromResource(this,R.array.Package_weight_list, android.R.layout.simple_spinner_item);
        ////////////adapter_package_weight.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ////////////package_weight_spinner.setAdapter(adapter_package_weight);

        //textView for location
        locationTextView = (TextView)findViewById(R.id.locationTextView);

        //TextInputEditText for phone number
        TextInputEditTextPhoneNumber = (TextInputEditText)findViewById(R.id.TextInputEditTextPhoneNumber);

        //TextInputLayout textInputLayout = (TextInputLayout) findViewById(R.id.TextInputLayoutPhoneNumber);
        // textInputLayout.getEditText().setText("23");
        //Button sendButton = (Button) findViewById(R.id.sendButton);
        //sendButton.setOnClickListener(this);
    }

    private Parcel getParcelFromView() {

        //type
        PackType thisParcelType = ((PackType)type_spinner.getSelectedItem());

        //breakable
        Bool thisParcelBreakable = ((Bool)breakable_spinner.getSelectedItem());

        //packageWeight
        PackageWeight thisParcelPackageWeight = ((PackageWeight) package_weight_spinner.getSelectedItem());

        //location
        //TODO: convert string type to location type (or maybe remove this field at the view)

        //phoneNumber (receiver)
        String thisParcelPhoneNumber = TextInputEditTextPhoneNumber.getText().toString();

        //dateSend
        Date dateSend = new Date(); //return the current date

        //status
        PackStatus status = PackStatus.SENT;

        //No need date recived and Phone reciver.. its set in future.

        return new Parcel(null,thisParcelType,thisParcelBreakable,thisParcelPackageWeight,
                           null,null,dateSend,status);
    }

    //convert date to string
    public String convertDateToString(Date date){
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        return formatter.format(date);
    }
}
