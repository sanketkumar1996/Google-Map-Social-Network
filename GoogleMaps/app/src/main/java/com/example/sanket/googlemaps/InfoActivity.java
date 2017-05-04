package com.example.sanket.googlemaps;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class InfoActivity extends AppCompatActivity {

    TextView headText, descText;
    Button back;
    ProgressBar progressBar;
    DatabaseReference db;
    FirebaseDatabase mFirebaseInstance;
    FirebaseUser auth;
    String user;
    private StorageReference mStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        headText = (TextView) findViewById(R.id.headingText);
        descText = (TextView) findViewById(R.id.descriptionText);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        auth = FirebaseAuth.getInstance().getCurrentUser();
        user = auth.getUid();
        db = FirebaseDatabase.getInstance().getReference("user/"+user);
        mFirebaseInstance = FirebaseDatabase.getInstance();
        mStorage = FirebaseStorage.getInstance().getReference();

        back = (Button) findViewById(R.id.back);
        progressBar.setVisibility(View.VISIBLE);

        Bundle extras = getIntent().getExtras();
        Double lati = extras.getDouble("Lati");
        Double longi = extras.getDouble("Longi");

        db.child(user).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(getApplicationContext(),user.getTitle().toString()+" "+user.getLatlang(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),"Unable to Load",Toast.LENGTH_SHORT).show();


            }
        });



        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(getApplicationContext(), MapsActivity.class));
            }
        });
    }
}
