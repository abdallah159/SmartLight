package com.example.abdallah.smartlight;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
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

    public static boolean isAppRunning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lampImageView = findViewById(R.id.lampImageView);
        lightSwitch = findViewById(R.id.lightSwitchButton);

        lightSwitch.setOnCheckedChangeListener(this);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("SmartLight");


        //To Receive Push Notification from ControlUnit..
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        String channelId = "1";
        String channel2 = "2";

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(channelId,
                    "Channel 1",NotificationManager.IMPORTANCE_HIGH);

            notificationChannel.setDescription("This is BNT");
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setShowBadge(true);
            notificationManager.createNotificationChannel(notificationChannel);

            NotificationChannel notificationChannel2 = new NotificationChannel(channel2,
                    "Channel 2",NotificationManager.IMPORTANCE_MIN);

            notificationChannel.setDescription("This is bTV");
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setShowBadge(true);
            notificationManager.createNotificationChannel(notificationChannel2);

        }


        //On light status change from firebase realtime database..
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isAppRunning = false;
    }
}
