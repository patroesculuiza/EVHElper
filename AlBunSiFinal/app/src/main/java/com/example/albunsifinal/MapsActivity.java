package com.example.albunsifinal;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.hardware.Camera;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.privacystreams.core.Callback;
import com.github.privacystreams.core.UQI;
import com.github.privacystreams.core.purposes.Purpose;
import com.github.privacystreams.location.Geolocation;
import com.github.privacystreams.location.LatLon;
import com.github.privacystreams.utils.Globals;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

import static java.lang.Double.parseDouble;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private FusedLocationProviderClient fusedLocationClient;

//butoanele din dreapta sus
    private Button addtodb;
    private Button viewdb;
    private Button back;
    private Button test;
    public double coordN=0;
    public double coordE=0;
    public String CoordN;
    public String CoordE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);



        //afiseaza locatia curenta a user-ului
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        if(ActivityCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            getCurrentLocation();
        }else{
            ActivityCompat.requestPermissions(MapsActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},44);
        }


        addtodb=findViewById(R.id.addtodb);
        viewdb=findViewById(R.id.viewdb);
        back=findViewById(R.id.back);
        test=findViewById(R.id.test);


        //butonu de teste automate
        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String stringnume="Name";
                String stringadresa="Address";
                String stringcN="Lat";
                String stringcE="Long";
                String stringDetaliiStatieFormatate="DetaliiStatieFormatate";

                HashMap<String, Object> map=new HashMap<>();
                map.put(stringnume,"random name");
                map.put(stringadresa,"random address");

                int cnt=10;
                for(int i=0;i<cnt;i++) {
                    //generez coordonate random
                    Random r = new Random();
                    int rlong = r.nextInt(180) - 90;
                    Random r2 = new Random();
                    int rlat = r2.nextInt(360) - 180;

                    String dlong = String.valueOf(rlong);
                    String dlat = String.valueOf(rlat);

                    map.put(stringcN, dlat);
                    map.put(stringcE, dlong);
                    map.put(stringDetaliiStatieFormatate, "random detalii");

                    FirebaseDatabase.getInstance().getReference().child("Statii").push().updateChildren(map);
                }

                //Toast.makeText(MapsActivity.this,"10 pins added!",Toast.LENGTH_SHORT).show();



                int cnt2=0;
                //delete random pins
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                Query applesQuery = ref.child("Statii").orderByChild("Name").equalTo("random name");

                applesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.getChildrenCount()==10)
                            Toast.makeText(MapsActivity.this,"10 pins added to DB and then removed successfully!",Toast.LENGTH_SHORT).show();
                        for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {
                            appleSnapshot.getRef().removeValue();

                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.e("tag","onCancelled", databaseError.toException());
                    }
                });

            }




        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MapsActivity.this , MainActivity.class));
                finish();

            }
        });

        addtodb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(coordE!=0&&coordN!=0) {
                   Intent intent = new Intent(MapsActivity.this, AddtoDB.class);
                   intent.putExtra("coordN", CoordN);
                   intent.putExtra("coordE", CoordE);
                   startActivity(intent);
                   finish();
               }
               else{
                   Toast.makeText(MapsActivity.this, "No marker selected!!!", Toast.LENGTH_SHORT).show();
               }



            }
        });

        viewdb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MapsActivity.this , ViewDB.class));
                finish();
            }
        });


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == 44){
            if(grantResults.length >= 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                getCurrentLocation();
            }
        }
    }

    private void getCurrentLocation(){
        fusedLocationClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                Location location = task.getResult();
                if(location != null){
                    try{
                        Geocoder geocoder = new Geocoder(MapsActivity.this, Locale.getDefault());
                        List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(),1);


                        LatLng loc = new LatLng(addresses.get(0).getLatitude(), addresses.get(0).getLongitude());

                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(loc,10));  //zoom the camera
                        mMap.addMarker(new MarkerOptions().position(loc).title("I am here!"));
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(loc));

                    }catch (IOException e){
                        e.printStackTrace();
                    }
                }
            }
        });
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {

                MarkerOptions markerOptions = new MarkerOptions();    //creating marker
                markerOptions.position(latLng);   //set marker position
                markerOptions.draggable(true);
                markerOptions.title(latLng.latitude + ", " + latLng.longitude);  //set lat lng as title
                //mMap.clear();  //delete the previously clicked position


                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,10));  //zoom the camera

                mMap.addMarker(markerOptions);  //add marker on map
                //preiau coordonatele markerului pt a lea adauga la baza de date
                coordN=latLng.latitude;
                CoordN=String.valueOf(coordN);
                coordE=latLng.longitude;
                CoordE=String.valueOf(coordE);





            }
        });
//adaug pe harta toate markerele din baza de date si le numesc dupa numele pe care il are tatia in baza de date
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child("Statii");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                    Information info=snapshot.getValue(Information.class);
                    LatLng marker=new LatLng( parseDouble(info.getLat()) ,parseDouble(info.getLong()));



                    //TEST
                    mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
                        @Override
                        public View getInfoWindow(Marker marker) {
                            View v = getLayoutInflater().inflate(R.layout.activity_bug_reporting,null);
                           // return v;
                            return  null;
                        }

                        @Override
                        public View getInfoContents(Marker marker) {

                            Context mContext = getApplicationContext();

                            LinearLayout info = new LinearLayout(mContext);
                            info.setOrientation(LinearLayout.VERTICAL);

                            TextView title = new TextView(mContext);
                            title.setTextColor(Color.BLACK);
                            title.setGravity(Gravity.CENTER);
                            title.setTypeface(null, Typeface.BOLD);
                            title.setText(marker.getTitle());

                            TextView snippet = new TextView(mContext);
                            snippet.setTextColor(Color.GRAY);
                            snippet.setText(marker.getSnippet());

                            info.addView(title);
                            info.addView(snippet);

                            return info;
                        }
                    });

                    //TEST


                    mMap.addMarker(new MarkerOptions().position(marker).title(info.getName()).snippet(info.getDetaliiStatieFormatate()));
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

}

    public static void sleep(int amt) // In milliseconds
    {
        long a = System.currentTimeMillis();
        long b = System.currentTimeMillis();
        while ((b - a) <= amt)
        {
            b = System.currentTimeMillis();
        }
    }

}
