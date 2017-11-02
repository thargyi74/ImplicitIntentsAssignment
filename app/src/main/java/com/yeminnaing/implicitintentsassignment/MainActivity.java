package com.yeminnaing.implicitintentsassignment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.CalendarContract;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    public static final String URI_TO_OPEN_IN_MAP = "http://maps.google.com/maps?daddr=";

    private static final int PERMISSION_REQUEST_PHONE_CALL = 100;

    private static final int PERMISSION_REQUEST_CAMERA = 101;
    private static final int REQUEST_IMAGE_CAPTURE = 102;

    private static final int REQUEST_IMAGE_FROM_DEVICE = 103;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @OnClick(R.id.tv_share)
    public void onTapShare(View view) {
        sendViaShareIntent("This is massage");
    }

    private void sendViaShareIntent(String msg) {
        Intent shareCompatIntent = ShareCompat.IntentBuilder.from(this)
                .setType("text/plain")
                .setText(msg)
                .getIntent();
        startActivity(shareCompatIntent);
    }

    @OnClick(R.id.tv_map)
    public void onTapMap(View view){
        navigateOnMapIntent("Thuwunna");

    }
    private void navigateOnMapIntent(String location){
        String uriToOpen = URI_TO_OPEN_IN_MAP + location;
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(uriToOpen));
        startActivity(intent);
    }

    @OnClick(R.id.tv_phone_call)
    public void onTapPhoneCall(View view){
        makePhoneCall("09450067632");
    }
    private void makePhoneCall(String PhNumber) {
        PhNumber = PhNumber.replaceAll(" ", "");
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + PhNumber));

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CALL_PHONE},
                    PERMISSION_REQUEST_PHONE_CALL);
        }
        startActivity(intent);
    }

    @OnClick(R.id.tv_email)
    public void onTapEmail(View view){
        sendEmail("example@gmail.com", "This is title", "This is message body");
    }
    private void sendEmail(String emailAddress, String title, String message){
        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + emailAddress));
        intent.putExtra(Intent.EXTRA_SUBJECT, title);
        intent.putExtra(Intent.EXTRA_TEXT, message);
        startActivity(intent);
    }

    @OnClick(R.id.tv_camera)
    public void onTapCamera(View view){
        capturePhotoWithCamera();

    }

    private void capturePhotoWithCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA},
                    PERMISSION_REQUEST_CAMERA);
        }
        startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
    }

    @OnClick(R.id.tv_gallery)
    public void onTapGallery(View view){
        selectPhotoFromDevice();

    }
    private void selectPhotoFromDevice() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_IMAGE_FROM_DEVICE);
    }

    @OnClick(R.id.tv_calendar)
    public void onTapCalendar(View view){
        saveEventInCalendar("Title", "This is description", "Thuwuanna");

    }
    public void saveEventInCalendar(String title, String description, String location) {
        Intent intent = new Intent(Intent.ACTION_INSERT);
        intent.setData(CalendarContract.Events.CONTENT_URI);
        intent.putExtra(CalendarContract.Events.TITLE, title);
        intent.putExtra(CalendarContract.Events.DESCRIPTION, description);
        intent.putExtra(CalendarContract.Events.EVENT_LOCATION, location);
        startActivity(intent);
    }



}
