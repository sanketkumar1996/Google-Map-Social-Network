package com.example.sanket.googlemaps;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.io.IOException;

import static com.google.android.gms.wearable.DataMap.TAG;

public class MainActivity extends Activity {


    private final int SPLASH_DISPLAY_LENGTH = 1000;
    ProgressBar progressBar;
    Context context;
    LocationManager locationManager ;
    boolean GpsStatus ;
    TextView textView;
    //AlertDialog.Builder builder;


    @Override
    protected void onResume() {
        super.onResume();

        CheckGpsStatus() ;

        if(GpsStatus == true)
        {
            //check.setText("Location Services Is Enabled");
            new Handler().postDelayed(new Runnable(){
                @Override
                public void run() {
                    // Create an Intent that will start the Menu-Activity.
                    Intent mainIntent = new Intent(MainActivity.this,SignUpActivity.class);
                    MainActivity.this.startActivity(mainIntent);
                    MainActivity.this.finish();
                }
            }, SPLASH_DISPLAY_LENGTH);
            textView.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.VISIBLE);
        }else {
            //check.setText("Location Services Is Disabled");
            //startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
             final AlertDialog.Builder builder =
                    new AlertDialog.Builder(MainActivity.this);
            final String action = Settings.ACTION_LOCATION_SOURCE_SETTINGS;
            final String message = "Enable either GPS or any other location"
                    + " service to find current location.  Click OK to go to"
                    + " location services settings to let you do so.";


            builder.setMessage(message)
                    .setPositiveButton("OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface d, int id) {
                                    startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                                }
                            });

            builder.create().show();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = (ProgressBar) findViewById(R.id.button);
        textView = (TextView) findViewById(R.id.textView);

        context = getApplicationContext();

               /*
                CheckGpsStatus() ;

                if(GpsStatus == true)
                {
                    //check.setText("Location Services Is Enabled");
                    new Handler().postDelayed(new Runnable(){
                        @Override
                        public void run() {
                            // Create an Intent that will start the Menu-Activity.
                            Intent mainIntent = new Intent(MainActivity.this,MapsActivity.class);
                            MainActivity.this.startActivity(mainIntent);
                            MainActivity.this.finish();
                        }
                    }, SPLASH_DISPLAY_LENGTH);
                    progressBar.setVisibility(View.VISIBLE);
                }else {
                    //check.setText("Location Services Is Disabled");
                    //startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    final AlertDialog.Builder builder =
                            new AlertDialog.Builder(MainActivity.this);
                    final String action = Settings.ACTION_LOCATION_SOURCE_SETTINGS;
                    final String message = "Enable either GPS or any other location"
                            + " service to find current location.  Click OK to go to"
                            + " location services settings to let you do so.";


                    builder.setMessage(message)
                            .setPositiveButton("OK",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface d, int id) {
                                            startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                                        }
                                    });

                    builder.create().show();
                }
                */

        /*
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                // Create an Intent that will start the Menu-Activity.
                Intent mainIntent = new Intent(MainActivity.this,MapsActivity.class);
                MainActivity.this.startActivity(mainIntent);
                MainActivity.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
        */
    }

    private void CheckGpsStatus() {
        locationManager = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);

        GpsStatus = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }


}
