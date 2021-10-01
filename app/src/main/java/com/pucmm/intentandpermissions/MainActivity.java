package com.pucmm.intentandpermissions;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private boolean hasCamera = false;
    private static Switch cameraSwitch;
    private static Switch storageSwitch;
    private static Switch phoneSwitch;
    private static Switch contactsSwitch;
    private static Switch locationSwitch;
    private static final Integer REQUEST_CODE_CAMERA = 1;
    private static final Integer REQUEST_CODE_STORAGE = 2;
    private static final Integer REQUEST_CODE_PHONE = 3;
    private static final Integer REQUEST_CODE_CONTACTS = 4;
    private static final Integer REQUEST_CODE_LOCATION = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cameraSwitch = findViewById(R.id.cameraSwitch);
        storageSwitch = findViewById(R.id.storageSwitch);
        phoneSwitch = findViewById(R.id.phoneSwitch);
        contactsSwitch = findViewById(R.id.contactsSwitch);
        locationSwitch = findViewById(R.id.locationSwitch);

        if (getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            hasCamera = true;
        }

        if (ContextCompat.checkSelfPermission(this.getApplicationContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            cameraSwitch.setChecked(true);
        } else cameraSwitch.setChecked(false);

        if (ContextCompat.checkSelfPermission(this.getApplicationContext(), Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            phoneSwitch.setChecked(true);
        } else phoneSwitch.setChecked(false);

        if (ContextCompat.checkSelfPermission(this.getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            storageSwitch.setChecked(true);
        } else storageSwitch.setChecked(false);

        if (ContextCompat.checkSelfPermission(this.getApplicationContext(), Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            contactsSwitch.setChecked(true);
        } else contactsSwitch.setChecked(false);

        if (ContextCompat.checkSelfPermission(this.getApplicationContext(), Manifest.permission.ACCESS_BACKGROUND_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationSwitch.setChecked(true);
        } else locationSwitch.setChecked(false);
    }

    public void requestForPermission(View view) {
        if (view.getId() == cameraSwitch.getId() && hasCamera) {
            showToastMessage(checkForPermission(view, Manifest.permission.CAMERA, REQUEST_CODE_CAMERA));
        } else if (view.getId() == storageSwitch.getId()) {
            showToastMessage(checkForPermission(view, Manifest.permission.READ_EXTERNAL_STORAGE, REQUEST_CODE_STORAGE));
        } else if (view.getId() == phoneSwitch.getId()) {
            showToastMessage(checkForPermission(view, Manifest.permission.CALL_PHONE, REQUEST_CODE_PHONE));
        } else if (view.getId() == contactsSwitch.getId()) {
            showToastMessage(checkForPermission(view, Manifest.permission.READ_CONTACTS, REQUEST_CODE_CONTACTS));
        } else if (view.getId() == locationSwitch.getId()) {
            showToastMessage(checkForPermission(view, Manifest.permission.ACCESS_BACKGROUND_LOCATION, REQUEST_CODE_LOCATION));
        }
    }

    public boolean checkForPermission(View view, String permissionType, int requestCode) {
        Switch theSwitch = findViewById(view.getId());
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(), permissionType) == PackageManager.PERMISSION_GRANTED) {
            theSwitch.setChecked(true);
            return true;
        } else {
            ActivityCompat.requestPermissions(MainActivity.this, new String[] { permissionType }, requestCode);
            return false;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case 1: //REQUEST_CODE_CAMERA
                if (grantResults[0] == -1) {
                    Switch theSwitch = findViewById(cameraSwitch.getId());
                    theSwitch.setChecked(false);
                }
                break;
            case 2: //REQUEST_CODE_STORAGE
                if (grantResults[0] == -1) {
                    Switch theSwitch = findViewById(storageSwitch.getId());
                    theSwitch.setChecked(false);
                }
                break;
            case 3: //REQUEST_CODE_PHONE
                if (grantResults[0] == -1) {
                    Switch theSwitch = findViewById(phoneSwitch.getId());
                    theSwitch.setChecked(false);
                }
                break;
            case 4: //REQUEST_CODE_CONTACTS
                if (grantResults[0] == -1) {
                    Switch theSwitch = findViewById(contactsSwitch.getId());
                    theSwitch.setChecked(false);
                }
                break;
            case 5: //REQUEST_CODE_LOCATION
                if (grantResults[0] == -1) {
                    Switch theSwitch = findViewById(locationSwitch.getId());
                    theSwitch.setChecked(false);
                }
                break;
        }
    }

    public void showToastMessage(boolean condition) {
        if (condition) {
            Toast.makeText(this.getApplicationContext(), getString(R.string.permissionGrantedContent), Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this.getApplicationContext(), getString(R.string.permissionNotGrantedContent), Toast.LENGTH_LONG).show();
        }
    }

    public void goToSendIntentActivity(View view) {
        Intent intent = new Intent(this, SendIntentActionActivity.class);
        startActivity(intent);
    }

    public void exitApp(View view) {
        finish();
        moveTaskToBack(true);
    }
}