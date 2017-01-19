package com.example.sanket.googlemaps;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static com.example.sanket.googlemaps.R.id.editText;

public class AddUserInfo extends AppCompatActivity {
    TextView title;
    EditText data;
    Button done, show;

    public final String TAG = AddUserInfo.class.getSimpleName();
    private DatabaseReference mRef;

   // private FirebaseDatabase mRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user_info);
        Bundle extras = getIntent().getExtras();
            final double lati = extras.getDouble("Lati");
            final double longi = extras.getDouble("Longi");
        //Toast.makeText(getApplicationContext(), "Lati : "+lati+" Longi: "+longi,Toast.LENGTH_SHORT).show();

        title = (TextView)findViewById(R.id.textView);
        data = (EditText)findViewById(editText);
        done = (Button)findViewById(R.id.doneButton) ;
        show = (Button)findViewById(R.id.showDatabase);

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mRef = FirebaseDatabase.getInstance().getReference();
                String dataProvided = data.getText().toString().trim();

                SaveData newData = new SaveData(dataProvided, lati, longi);

                mRef.child("Person").setValue(newData);

                Toast.makeText(getApplicationContext(), "Saved",Toast.LENGTH_SHORT).show();


            }
        });




    }



}
