package com.example.abdallah.smartlight;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    ImageView lampImageView;
    Switch lightSwitch;

    FirebaseDatabase firebaseDatabase ;
    DatabaseReference databaseReference ;

    String lightStatus ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lampImageView = findViewById(R.id.lampImageView);
        lightSwitch = findViewById(R.id.lightSwitchButton);

        lightSwitch.setOnCheckedChangeListener(this);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("SmartLight");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                lightStatus = dataSnapshot.child("Light_Status").getValue().toString();
                Toast.makeText(MainActivity.this, ""+lightStatus, Toast.LENGTH_LONG).show();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }



    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked)
        {
            lampImageView.setImageResource(R.drawable.lamp_on);
            Toast.makeText(this, "Light is turned on", Toast.LENGTH_SHORT).show();
        }
        else if (!isChecked)
        {
            lampImageView.setImageResource(R.drawable.lamp_off);
            Toast.makeText(this, "Light is Turned off", Toast.LENGTH_SHORT).show();
        }
    }
}
