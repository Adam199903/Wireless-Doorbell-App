package com.adam.iotappv2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.DownloadManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import static com.adam.iotappv2.Notification.CHANNEL_1_ID;

public class MainScreen extends AppCompatActivity {
    private NotificationManagerCompat notificationManager;
    TextView motion_alert;
    TextView user_username;
    DatabaseReference databaseReference;
    String PIRstatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.main_screen_design);

        notificationManager = NotificationManagerCompat.from(this);

        motion_alert = findViewById(R.id.motion_status);
        user_username = findViewById(R.id.username_field);



        getUserData();
        checkMotionSensor();

    }

    private void checkMotionSensor() {

        String getUsername = user_username.getText().toString().trim();

        databaseReference = FirebaseDatabase.getInstance().getReference("users");
        Query checkSensor = databaseReference.orderByChild("username").equalTo(getUsername);
        checkSensor.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()) {
                    PIRstatus = dataSnapshot.child(getUsername).child("motion_detector").getValue().toString();

                    if(PIRstatus.equals("0")){
                        motion_alert.setText("no motion");
                    }
                    else{
                        motion_alert.setText("Motion detected");
                        notification();
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void notification() {

        String title = "Door App";
        String message = "Someone is at the front door!";

        Intent intent = new Intent(this, MainScreen.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this,
                0, intent, 0);

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_1_ID)
                .setSmallIcon(R.drawable.alert_icon)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setColor(Color.rgb(64, 130, 109))
                .setContentIntent(contentIntent)
                .setAutoCancel(true)
                .build();

        notificationManager.notify(1, notification);


    }

    private void getUserData() {

        Intent intent = getIntent();
        String MainScreenUsername = intent.getStringExtra("username");
        user_username.setText(MainScreenUsername);

    }


    public void logOut(View view){
        Intent intent = new Intent (MainScreen.this, Login.class);
        startActivity(intent);
        finish();
    }

}