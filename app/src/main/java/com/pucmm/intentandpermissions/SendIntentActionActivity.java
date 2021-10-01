package com.pucmm.intentandpermissions;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class SendIntentActionActivity extends AppCompatActivity {

    private static Button storageIntentButton;
    private static Button locationIntentButton;
    private static Button cameraIntentButton;
    private static Button phoneIntentButton;
    private static Button contactsIntentButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_intent_action);

        storageIntentButton = findViewById(R.id.storageIntentButton);
        locationIntentButton = findViewById(R.id.locationIntentButton);
        cameraIntentButton = findViewById(R.id.cameraIntentButton);
        phoneIntentButton = findViewById(R.id.phoneIntentButton);
        contactsIntentButton = findViewById(R.id.contactsIntentButton);
    }

    public void checkPermissionBeforeIntent(View view) {

        if (view.getId() == storageIntentButton.getId()) {
            if (ContextCompat.checkSelfPermission(this.getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                sendIntent(1, "Nothing");
            } else Toast.makeText(this.getApplicationContext(), getString(R.string.permissionNotGrantedContent), Toast.LENGTH_LONG).show();
        } else if (view.getId() == locationIntentButton.getId()) {
            if (ContextCompat.checkSelfPermission(this.getApplicationContext(), Manifest.permission.ACCESS_BACKGROUND_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                sendIntent(2, "Nothing");
            } else Toast.makeText(this.getApplicationContext(), getString(R.string.permissionNotGrantedContent), Toast.LENGTH_LONG).show();
        } else if (view.getId() == cameraIntentButton.getId()) {
            if (ContextCompat.checkSelfPermission(this.getApplicationContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                sendIntent(3, "Nothing");
            } else Toast.makeText(this.getApplicationContext(), getString(R.string.permissionNotGrantedContent), Toast.LENGTH_LONG).show();
        } else if (view.getId() == phoneIntentButton.getId()) {
            if (ContextCompat.checkSelfPermission(this.getApplicationContext(), Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                sendIntent(4, "809-555-1234");
            } else Toast.makeText(this.getApplicationContext(), getString(R.string.permissionNotGrantedContent), Toast.LENGTH_LONG).show();
        } else if (view.getId() == contactsIntentButton.getId()) {
            if (ContextCompat.checkSelfPermission(this.getApplicationContext(), Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
                sendIntent(5, "Nothing");
            } else Toast.makeText(this.getApplicationContext(), getString(R.string.permissionNotGrantedContent), Toast.LENGTH_LONG).show();
        }
    }

    public void sendIntent(int permissionCase, String content) {
        Intent intentToSend = new Intent();

        switch (permissionCase) {
            case 1:
                intentToSend.setAction(Intent.ACTION_GET_CONTENT);
                intentToSend.setType("file/*");
                break;
            case 2:
                intentToSend = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:19.459149464010324,-70.66938554636815"));
                break;
            case 3:
                intentToSend.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                break;
            case 4:
                intentToSend = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + content));
                break;
            case 5:
                intentToSend.setAction(Intent.ACTION_PICK);
                intentToSend.setData(ContactsContract.Contacts.CONTENT_URI);
                break;
        }

        try {
            startActivity(intentToSend);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }
}