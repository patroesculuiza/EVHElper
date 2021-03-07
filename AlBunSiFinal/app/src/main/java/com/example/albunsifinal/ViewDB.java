package com.example.albunsifinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ViewDB extends AppCompatActivity {

    private Button back;
    private ListView lw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_d_b);
        setTitle("STATIONS DATABASE");

        back=findViewById(R.id.back);
        lw=findViewById(R.id.listview);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ViewDB.this, MapsActivity.class));

            }



        });

        final ArrayList<String>list=new ArrayList<>();
        final ArrayAdapter adapter=new ArrayAdapter<String>(this,R.layout.list_item,list);
        lw.setAdapter(adapter);

        DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child("Statii");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();
                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                    Information info=snapshot.getValue(Information.class);
                    String txt=info.getName()+"\n"+info.getAddress()+"\n"+info.getLat()+"\n"+info.getLong()+"\n"+info.getDetaliiStatieFormatate();
                    list.add(txt);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
