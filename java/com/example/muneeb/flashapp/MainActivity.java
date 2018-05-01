package com.example.muneeb.flashapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private CameraManager cameraManager; //Calling the CameraManager to use it's functionalities
    private String cameraID; //Setting the ID
    private Button btnOnOff; //ON/OFF Button
    private Boolean isTorchOn; //To check the Status of Flash
    private MediaPlayer mediaPlayer; //To use some functionalities of MediaPlayer
    private ImageView ivBulb; //ImageView to change the Images

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d("FlashLightActivity", "onCreate()");
        setContentView(R.layout.activity_main);

        btnOnOff = (Button) findViewById(R.id.btn_onOff);

        ivBulb = (ImageView) findViewById(R.id.iv_bulb);

        isTorchOn = false;

        //Checking whether the device supports FLASH
        Boolean isFlashAvailable = getApplicationContext().getPackageManager()
                .hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);

        //If no Flash
        if (!isFlashAvailable) {
            AlertDialog alert = new AlertDialog.Builder(MainActivity.this)
                    .create();
            alert.setTitle("Error !!");
            alert.setMessage("Your device doesn't support flash light!");
            alert.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // closing the application
                    finish();
                    System.exit(0);
                }
            });
            alert.show();
            return;
        }

            cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
            try {
                cameraID = cameraManager.getCameraIdList()[0];
            } catch (CameraAccessException e) {
                e.printStackTrace();
            }

        //Setting OnClickListener on the ON/OFF Button
            btnOnOff.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        if (isTorchOn) { //If the flash is on 
                            turnOffFlashLight(); //turn off the flash
                            isTorchOn = false;
                        } else {
                            turnOnFlashLight(); //If the flash is off, turn it on
                            isTorchOn = true; 
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
/* 
* ***
* Creating a Function to Turn the Flash ON
* ***
*/
    public void turnOnFlashLight() {

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                cameraManager.setTorchMode(cameraID, true);
                playOnOffSound();
                ivBulb.setImageResource(R.drawable.lightbulb2);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

/* 
* ***
* Creating a Function to Turn the Flash OFF
* ***
*/
    public void turnOffFlashLight() {

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                cameraManager.setTorchMode(cameraID, false);
                playOnOffSound();
                ivBulb.setImageResource(R.drawable.darkbulb2); 
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

/* 
* ***
* Creating a Function to Play Sounds
* ***
*/
    private void playOnOffSound(){
        mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.light_switched_on);
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

            @Override
            public void onCompletion(MediaPlayer mp) {
                // TODO Auto-generated method stub
                mp.release();
            }
        });
        mediaPlayer.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(isTorchOn){
            turnOffFlashLight();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(isTorchOn){
            turnOffFlashLight();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(isTorchOn){
            turnOnFlashLight();
        }
    }
}
//END
