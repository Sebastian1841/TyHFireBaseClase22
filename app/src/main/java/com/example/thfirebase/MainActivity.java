package com.example.thfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    DatabaseReference mydb;
    TextView Temperatura, Humedad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d("FirebaseData", "onCreate called");
        Temperatura = findViewById(R.id.Temp);
        Humedad = findViewById(R.id.Hum);
        mydb = FirebaseDatabase.getInstance().getReference().child("Sensor");
        try{
            mydb.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Log.d("FirebaseData", "onDataChange called");
                    if (Temperatura == null || Humedad == null) {
                        Log.e("FirebaseData", "TextViews not initialized");
                        return;
                    }
                    if (dataSnapshot.exists()) {
                        if (dataSnapshot.child("temp").exists() && dataSnapshot.child("hum").exists()) {
                            String temdata = dataSnapshot.child("temp").getValue(String.class);
                            String humdata = dataSnapshot.child("hum").getValue(String.class);

                            if (temdata != null && humdata != null) {
                                Temperatura.setText(temdata);
                                Humedad.setText(humdata);
                            } else {
                                // Manejar el caso donde temdata o humdata son nulos
                            }
                        } else {
                            // Manejar el caso donde los hijos "temp" o "hum" no existen
                        }
                    } else {
                        Log.d("FirebaseData", "DataSnapshot does not exist");
                    }
                }



                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.e("Firebasedata", "onCancelled: " + error.getMessage());
                }
            });
        }catch (Exception e){
            Log.e("FirebaseData", "Error: " + e.getMessage());
        }
    }
}