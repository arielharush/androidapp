package com.example.logisticare;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.*;
import android.provider.ContactsContract;
import android.view.Display;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.example.logisticare.Entities.Enums.*;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Spinner type_spinner;
    Spinner breakable_spinner;
    Spinner package_weight_spinner;
    TextView locationTextView;
    TextInputEditText TextInputEditTextPhoneNumber;
    // Acquire a reference to the system Location Manager
    LocationManager locationManager;
    Location parcelLocation;

    // Define a listener that responds to location updates
    LocationListener locationListener;
    //init DB
    static DatabaseReference ParcelsRef;
    static List<Parcel> parcelList;
    static {
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://dblogisticare.firebaseio.com/");
        ParcelsRef = database.getReference("parcels");
       // ParcelsRef.setValue("Hello, World!");
        parcelList = new ArrayList<>();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
getLocation();
    }

    @Override
    public void onClick(View v) {


        // check if the number is valid
        if(TextInputEditTextPhoneNumber.length() <10){

            Toast.makeText(getApplicationContext(),"Invalid phone number",Toast.LENGTH_SHORT).show();
            return;
        }
        Button sendButton = (Button)findViewById(R.id.sendButton);
        sendButton.setClickable(false);
        Parcel parcel = getParcelFromView();
        String key = ParcelsRef.push().getKey();
        parcel.setKey(key);

        ParcelsRef.child(key).setValue(parcel);



        ///  clear the feilds
      clearFileds();

        // send to firebase
        sendButton.setClickable(true);


        // msg to user that parcel added successfuly to the system

        Toast.makeText(getApplicationContext(), "The parcel added successfully.", Toast.LENGTH_SHORT).show();;
    }


    private  void clearFileds(){

        TextInputEditTextPhoneNumber.setText("");
        type_spinner.setSelection(0);
        breakable_spinner.setSelection(0);
        package_weight_spinner.setSelection(0);
    }


    private void initView() {
        // spinner for type
        type_spinner = (Spinner) findViewById(R.id.type_spinner);
        type_spinner.setAdapter(new ArrayAdapter<PackType>(this,android.R.layout.simple_list_item_1,PackType.values()));
        ArrayAdapter<CharSequence> adapter;
     adapter = ArrayAdapter.createFromResource(this,R.array.type_list, android.R.layout.simple_spinner_item);
      adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        type_spinner.setAdapter(adapter);

        //spinner for breakable
        breakable_spinner = (Spinner) findViewById(R.id.breakable_spinner);
     //   breakable_spinner.setAdapter(new ArrayAdapter<Bool>(this,android.R.layout.simple_list_item_1,Bool.values()));
       ArrayAdapter<CharSequence> adapter_breakable;
       adapter_breakable = ArrayAdapter.createFromResource(this,R.array.breakable_list, android.R.layout.simple_spinner_item);
      adapter_breakable.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
       breakable_spinner.setAdapter(adapter_breakable);

        //spinner for package weight
        package_weight_spinner = (Spinner) findViewById(R.id.package_weight_spinner);
        package_weight_spinner.setAdapter(new ArrayAdapter<PackageWeight>(this,android.R.layout.simple_list_item_1,PackageWeight.values()));
        ArrayAdapter<CharSequence> adapter_package_weight;
        adapter_package_weight = ArrayAdapter.createFromResource(this,R.array.Package_weight_list, android.R.layout.simple_spinner_item);
        adapter_package_weight.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        package_weight_spinner.setAdapter(adapter_package_weight);
        parcelLocation = new Location("a");
        //textView for location
        locationTextView = (TextView)findViewById(R.id.locationTextView);

        //TextInputEditText for phone number
        TextInputEditTextPhoneNumber = (TextInputEditText)findViewById(R.id.TextInputEditTextPhoneNumber);

        //TextInputLayout textInputLayout = (TextInputLayout) findViewById(R.id.TextInputLayoutPhoneNumber);
        // textInputLayout.getEditText().setText("23");
        //Button sendButton = (Button) findViewById(R.id.sendButton);
        //sendButton.setOnClickListener(this);
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);


        // Define a listener that responds to location updates
        locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                // Called when a new location is found by the network location provider.
                //    Toast.makeText(getBaseContext(), location.toString(), Toast.LENGTH_LONG).show();
                parcelLocation.setLatitude(location.getLatitude());
                parcelLocation.setLongitude(location.getLongitude());
                locationTextView.setText(getPlace(location));////location.toString());

                // Remove the listener you previously added
                //  locationManager.removeUpdates(locationListener);
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            public void onProviderEnabled(String provider) {
            }

            public void onProviderDisabled(String provider) {
            }
        };
    }




    private void getLocation() {

        //     Check the SDK version and whether the permission is already granted or not.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 5);

        } else {
            // Android version is lesser than 6.0 or the permission is already granted.
         //   stopUpdateButton.setEnabled(true);
          //  getLocationButton.setEnabled(false);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        }

    }


    public String getPlace(Location location) {

        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);

            if (addresses.size() > 0) {
                String cityName = addresses.get(0).getAddressLine(0);
                String stateName = addresses.get(0).getAddressLine(1);
                String countryName = addresses.get(0).getAddressLine(2);
                return cityName;
            }

            return "no place: \n (" + location.getLongitude() + " , " + location.getLatitude() + ")";
        } catch (
                IOException e)

        {
            e.printStackTrace();
        }
        return "IOException ...";
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 5) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission is granted
            //    stopUpdateButton.setEnabled(true);
              //  getLocationButton.setEnabled(false);
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

            } else {
                Toast.makeText(this, "Until you grant the permission, we canot display the location", Toast.LENGTH_SHORT).show();
            }
        }

    }





    private Parcel getParcelFromView() {

        //type
        PackType thisParcelType = PackType.values()[(int) type_spinner.getSelectedItemId()];

        //breakable
      boolean thisParcelBreakable;
      if (breakable_spinner.getSelectedItemId() ==0){
          thisParcelBreakable = true;
      }else{
          thisParcelBreakable = false;
      }

        //packageWeight
        PackageWeight thisParcelPackageWeight =  PackageWeight.values()[(int) package_weight_spinner.getSelectedItemId()];

        //location
        //TODO: convert string type to location type (or maybe remove this field at the view)

        //phoneNumber (receiver)
        String thisParcelPhoneNumber = TextInputEditTextPhoneNumber.getText().toString();

        //dateSend
        Date dateSend = new Date(); //return the current date

        //status
        PackStatus status = PackStatus.SENT;

        //No need date recived and Phone reciver.. its set in future.

        Parcel parcel = new Parcel(null,thisParcelType,thisParcelBreakable,thisParcelPackageWeight,
                           null,thisParcelPhoneNumber,dateSend,status);
        parcel.setLongitude(parcelLocation.getLongitude());
        parcel.setLatitude(parcelLocation.getLatitude());
        parcel.setDateReceived(new Date());
        return parcel;

    }

    //convert date to string
    public String convertDateToString(Date date){
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        return formatter.format(date);
    }



    // create the menu on the bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu,menu);
        return true;
    }




    // func for menu in the bar

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.Parcels:
               // Toast.makeText(this, "ddd", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this, parcelsHistory.class));
                break;
                default:break;
        }
        return true;
    }
}
