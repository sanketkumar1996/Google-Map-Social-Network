package com.example.sanket.googlemaps;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

/**
 * Created by sanket on 2/2/17.
 */

public class AddImage extends Activity {
    private StorageReference mStorage;
    EditText heading, description;
    TextView head, desc;
    Button mSelectImage, doButton;
    String headTitle;
    private ImageView imageView;
    private static final int GALLERY_INT = 2;
    private ProgressDialog mProgressDialog;
    DatabaseReference db;
    FirebaseDatabase mFirebaseInstance;
    FirebaseUser auth;
    String userID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user_info);
        auth = FirebaseAuth.getInstance().getCurrentUser();
        //user = auth.getUid();
        db = FirebaseDatabase.getInstance().getReference("user/");
        mFirebaseInstance = FirebaseDatabase.getInstance();
        mStorage = FirebaseStorage.getInstance().getReference();
        head = (TextView) findViewById(R.id.headTextView);
        desc = (TextView) findViewById(R.id.descTextView);
        heading = (EditText) findViewById(R.id.headEditText);
        description = (EditText) findViewById(R.id.descEditText);

        imageView = (ImageView) findViewById(R.id.imageView);

        mProgressDialog = new ProgressDialog(this);

        doButton = (Button) findViewById(R.id.doButton);
        doButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                headTitle = heading.getText().toString();
                Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                intent.putExtra("AddedImage", headTitle);
                finish();
                startActivity(intent);
            }
        });

        mSelectImage = (Button) findViewById(R.id.selectImage);

        mSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(heading.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(),"Enter heading",Toast.LENGTH_SHORT).show();
                }
                if(description.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(),"Enter description",Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent intent = new Intent((Intent.ACTION_PICK));
                    intent.setType("image/*");
                    startActivityForResult(intent, GALLERY_INT);
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == GALLERY_INT && resultCode == RESULT_OK){

            Uri uri = data.getData();
            StorageReference filepath = mStorage.child("Photos").child(uri.getLastPathSegment());


            filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    //and displaying a success toast
                    Toast.makeText(getApplicationContext(), "File Uploaded ", Toast.LENGTH_LONG).show();
                    headTitle = heading.getText().toString();
                    Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                    Bundle extras = getIntent().getExtras();
                    Double latiRecieved = extras.getDouble("Lati");
                    Double longiRecieved = extras.getDouble("Longi");
                    intent.putExtra("latiOnWhichMarker",latiRecieved);
                    intent.putExtra("longiOnWhichMarker",longiRecieved);
                    intent.putExtra("heading",heading.getText().toString());
                    startActivity(intent);

                    User user = new User();

                    LatLng latLngRecieved = new LatLng(latiRecieved, longiRecieved);

                    user.setTitle(heading.getText().toString());
                    user.setDesc(description.getText().toString());
                    user.setLatlang(latLngRecieved);
                    user.setUrls(""+taskSnapshot.getDownloadUrl());

                    //String userId = db.push().getKey();

                    userID = heading.getText().toString();
                    db.child(userID).setValue(user);

                    /*try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                        imageView.setImageBitmap(bitmap);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }*/

                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    //calculating progress percentage
                    double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                    //displaying percentage in progress dialog
                    mProgressDialog.setMessage("Uploaded " + ((int) progress) + "%...");
                    mProgressDialog.show();
                }
            });
        }
    }
}
