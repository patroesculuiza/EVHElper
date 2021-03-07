package com.example.albunsifinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class FeedbackActivity extends AppCompatActivity {
    private EditText text;
    private Button buton;
    private Button test;
    private Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle("FEEDBACK");
        text=findViewById(R.id.textbox);
        buton=findViewById(R.id.buton);
        test=findViewById(R.id.test);
        back=findViewById(R.id.back);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FeedbackActivity.this,MainActivity.class));
                finish();
            }
        });

        buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text_raport = text.getText().toString();

                HashMap<String, Object> map = new HashMap<>();

                map.put("Mesaj",text_raport);

                FirebaseDatabase.getInstance().getReference().child("Feedback").push().updateChildren(map);

                Toast.makeText(FeedbackActivity.this,"Multumim pentru feedback!",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(FeedbackActivity.this,MainActivity.class));

            }
        });


        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text_raport ="text pentru test";

                HashMap<String, Object> map = new HashMap<>();

                map.put("test",text_raport);

                FirebaseDatabase.getInstance().getReference().child("Feedback").push().updateChildren(map);
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                Query applesQuery = ref.child("Feedback").orderByChild("test").equalTo("text pentru test");

                applesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {
                            String text = appleSnapshot.child("test").getValue(String.class);
                            if(text.equals("text pentru test"))
                                Toast.makeText(FeedbackActivity.this,"Message successfully stored in DB!", Toast.LENGTH_SHORT).show();
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


    }
}
