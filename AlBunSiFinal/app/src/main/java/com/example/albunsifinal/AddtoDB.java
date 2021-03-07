package com.example.albunsifinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class AddtoDB extends AppCompatActivity {

    private EditText name;
    private EditText address;
    private Button add;
    private Button test;

    private Switch type2;
    private Switch supercharger;
    private Switch wall;
    private RadioButton da;
    private RadioButton nu;
    private EditText nrlocuriparcare;
    private EditText kW;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addto_d_b);
        setTitle("ADD MARKER TO DATABSE");

        name=findViewById(R.id.name);
        address=findViewById(R.id.address);
        add=findViewById(R.id.addtodb);
        test=findViewById(R.id.test);

        type2=findViewById(R.id.type2);
        supercharger=findViewById(R.id.supercharger);
        wall=findViewById(R.id.wall);
        da=findViewById(R.id.Da);
        nu=findViewById(R.id.Nu);
        nrlocuriparcare=findViewById(R.id.nrlocuriparcare);
        kW=findViewById(R.id.output);

        final String coordN = getIntent().getStringExtra("coordN");
        final String coordE = getIntent().getStringExtra("coordE");

        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name.setText("miciricipici");
                address.setText("adrmiciricipici");
                kW.setText("0");
                nrlocuriparcare.setText("0");
                type2.setChecked(true);
                wall.setChecked(true);
                supercharger.setChecked(true);
                da.setChecked(true);
                nu.setChecked(false);

                String txt_name=name.getText().toString();
                String txt_adresa=address.getText().toString();

                String tipuriMufe="";

                if (type2.isChecked()){
                    tipuriMufe += "Type2";
                }

                if (wall.isChecked()){
                    if (tipuriMufe!="")
                        tipuriMufe+=", Wall";
                    else
                        tipuriMufe+="Wall";
                }

                if (supercharger.isChecked()){
                    if (tipuriMufe!="")
                        tipuriMufe+=", Supercharger";
                    else
                        tipuriMufe+="Supercharger";
                }


                int outputKW=0;

                outputKW= Integer.parseInt(kW.getText().toString());

                String estePazita="Nu";

                if (da.isChecked())
                    estePazita = "Da";

                if (nu.isChecked())
                    estePazita = "Nu";


                int NrLocuriParcare=0;

                NrLocuriParcare= Integer.parseInt(nrlocuriparcare.getText().toString());

                String  DetaliiStatieFormatate = "Mufe: "+tipuriMufe+"\nOutput kW/h: "+outputKW+"kW/h\nPazita: "+estePazita+ "\nNr locuri parcare: "+NrLocuriParcare;;





                if(txt_name.isEmpty()||txt_adresa.isEmpty()){
                    Toast.makeText(AddtoDB.this,"Invalid name or address!", Toast.LENGTH_SHORT).show();
                } else {
                    String stringnume = "Name";
                    String stringadresa = "Address";
                    String stringcN = "Lat";
                    String stringcE = "Long";

                    String stringDetaliiStatieFormatate = "DetaliiStatieFormatate";

                    HashMap<String, Object> map = new HashMap<>();
                    map.put(stringnume, txt_name);
                    map.put(stringadresa, txt_adresa);
                    map.put(stringcN, coordN);
                    map.put(stringcE, coordE);
                    map.put(stringDetaliiStatieFormatate, DetaliiStatieFormatate);

                    FirebaseDatabase.getInstance().getReference().child("Statii").push().updateChildren(map);
                }

                DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                Query applesQuery = ref.child("Statii").orderByChild("Name").equalTo("miciricipici");

                applesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {
                            Information info=appleSnapshot.getValue(Information.class);
                            if(info.getName().equals("miciricipici")&&info.getAddress().equals("adrmiciricipici")||info.getDetaliiStatieFormatate().equals("Mufe: Type2, Wall, Supercharger\nOutput kW/h: 0kW/h\nPazita: Da\nNr locuri parcare: 0"))
                                Toast.makeText(AddtoDB.this,"Marker data correctly stored!", Toast.LENGTH_SHORT).show();
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

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt_name=name.getText().toString();
                String txt_adresa=address.getText().toString();

                String tipuriMufe="";

                if (type2.isChecked()){
                    tipuriMufe += "Type2";
                }

                if (wall.isChecked()){
                    if (tipuriMufe!="")
                        tipuriMufe+=", Wall";
                    else
                        tipuriMufe+="Wall";
                }

                if (supercharger.isChecked()){
                    if (tipuriMufe!="")
                        tipuriMufe+=", Supercharger";
                    else
                        tipuriMufe+="Supercharger";
                }


                int outputKW=0;

                outputKW= Integer.parseInt(kW.getText().toString());

                String estePazita="Nu";

                if (da.isChecked())
                    estePazita = "Da";

                if (nu.isChecked())
                    estePazita = "Nu";


                int NrLocuriParcare=0;

                NrLocuriParcare= Integer.parseInt(nrlocuriparcare.getText().toString());

                String  DetaliiStatieFormatate = "Mufe: "+tipuriMufe+"\nOutput kW/h: "+outputKW+"kW/h\nPazita: "+estePazita+ "\nNr locuri parcare: "+NrLocuriParcare;





                if(txt_name.isEmpty()||txt_adresa.isEmpty()){
                    Toast.makeText(AddtoDB.this,"Invalid name or address!", Toast.LENGTH_SHORT).show();
                } else{ String stringnume="Name";
                    String stringadresa="Address";
                    String stringcN="Lat";
                    String stringcE="Long";

                    String stringDetaliiStatieFormatate="DetaliiStatieFormatate";

                    HashMap<String, Object> map=new HashMap<>();
                   map.put(stringnume,txt_name);
                    map.put(stringadresa,txt_adresa);
                    map.put(stringcN,coordN);
                    map.put(stringcE,coordE);
                    map.put(stringDetaliiStatieFormatate,DetaliiStatieFormatate);

                    FirebaseDatabase.getInstance().getReference().child("Statii").push().updateChildren(map);

                    Toast.makeText(AddtoDB.this,"ADDED!",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(AddtoDB.this, MapsActivity.class));

                }
            }
        });

    }
}
