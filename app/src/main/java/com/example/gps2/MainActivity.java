package com.example.gps2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements LocationListener {

    ImageView imageView;
    TextView textView;
    Button button;

    double latitude,longlatitude;

    LocationManager locationManager;
    Geocoder geocoder;
    List<Address> addresses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.imageView);
        textView = findViewById(R.id.textView);
        button = findViewById(R.id.button);


        //AIzaSyB4dLPH61PFFIeQyGDLglHrS1c2hEnF5wg

        geocoder = new Geocoder(this,Locale.ENGLISH);

        //run time permision

        //Runtime permissions
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION
            },100);
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLocation();
                Toast.makeText(MainActivity.this, "Clik", Toast.LENGTH_SHORT).show();
                //


            }
        });


    }

    @SuppressLint("MissingPermission")
    private void getLocation() {

        try {
            locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,500,5,MainActivity.this);

            // geocder





        }catch (Exception e){
            e.printStackTrace();
        }

    }


    @Override
    public void onLocationChanged(@NonNull Location location) {
        Toast.makeText(this, ""+location.getLatitude()+","+location.getLongitude(), Toast.LENGTH_SHORT).show();

        latitude = (double) location.getLatitude();
        longlatitude = (double) location.getLongitude();

        try {
            Geocoder geocoder = new Geocoder(MainActivity.this, Locale.getDefault());

            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
            String address = addresses.get(0).getAddressLine(0);


            Toast.makeText(MainActivity.this,"" +addresses.get(0), Toast.LENGTH_SHORT).show();
           // Toast.makeText(MainActivity.this, "Clik", Toast.LENGTH_SHORT).show();


            textView.setText(address);

        }catch (Exception e){
            e.printStackTrace();
        }

    }




    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {

    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {

    }


    public void START_MAP(View view) {


        Intent intent = new Intent(MainActivity.this,MapsActivity.class);

              intent.putExtra("latitude",latitude);
              intent.putExtra("longlatitude",longlatitude);
        Toast.makeText(MainActivity.this, ""+latitude, Toast.LENGTH_SHORT).show();
        Toast.makeText(MainActivity.this, ""+longlatitude, Toast.LENGTH_SHORT).show();
              startActivity(intent);


    }
}