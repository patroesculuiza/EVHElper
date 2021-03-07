package com.example.albunsifinal;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.maps.MapView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Random;

import static androidx.core.content.ContextCompat.startActivity;

public class TestPins extends Thread{


    public void run(){
        String stringnume="Name";
        String stringadresa="Address";
        String stringcN="Lat";
        String stringcE="Long";
        String stringDetaliiStatieFormatate="DetaliiStatieFormatate";

        HashMap<String, Object> map=new HashMap<>();
        map.put(stringnume,"random name");
        map.put(stringadresa,"random address");

        for(int i=0;i<10;i++) {
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

        //Toast.makeText(AddtoDB.this,"ADDED!",Toast.LENGTH_SHORT).show();
        //startActivity(new Intent(MapsActivity.this, ViewDB.class));
        //finish();
        //startActivity(getIntent());


        //w8 10 sec
         try {
              Thread.sleep(2000);
           } catch (InterruptedException e) {
              e.printStackTrace();
          }
        //sleep(10000);


        //delete random pins
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        Query applesQuery = ref.child("Statii").orderByChild("Name").equalTo("random name");

        applesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {
                    appleSnapshot.getRef().removeValue();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("tag","onCancelled", databaseError.toException());
            }
        });

       // context.startActivity(intent);



    }
}
