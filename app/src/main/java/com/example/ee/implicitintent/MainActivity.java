package com.example.ee.implicitintent;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private final int REQUEST_CALL_PHONE = 2;

    private EditText webPage;
    private EditText locEditText;
    private  EditText text;
    private  EditText phoneEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webPage = (EditText) findViewById(R.id.webEditText);
        locEditText = (EditText) findViewById(R.id.locEditText);
        text = (EditText) findViewById(R.id.editText);
        phoneEditText = (EditText) findViewById(R.id.pnoneEditText);
    }

    public void openWebsite(View v){ //method to open website
        String url = webPage.getText().toString();
        if(url.equals("")){
            url = String.valueOf(webPage.getHint());
        }
        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW,uri);
        if(intent.resolveActivity(getPackageManager())!=null){
            startActivity(intent);
        }
        else{
            Toast.makeText(getApplicationContext(), "No app to handle", Toast.LENGTH_SHORT).show();
        }
    }

    public void openLocation(View v){ //method to open location

        String location = locEditText.getText().toString();
        if(location.equals("")){
            location = String.valueOf(locEditText.getHint());
        }

        Uri uri = Uri.parse("geo:0,0?q="+location);

        Intent intent = new Intent(Intent.ACTION_VIEW,uri);

        if(intent.resolveActivity(getPackageManager())!=null){

            startActivity(intent);

        }
        else {

            Toast.makeText(getApplicationContext(), "No app to handle", Toast.LENGTH_SHORT).show();

        }
    }

    public void shareText(View v){ //method to share text

        String txt = String.valueOf(text.getText());
        String mimeType = "text/plain";

        ShareCompat.IntentBuilder
                .from(this)
                .setType(mimeType)
                .setChooserTitle("Share this text with:")
                .setText(txt)
                .startChooser();
    }

    public void openDialer(View v){ //method to open dialer app

        String phoneNo = phoneEditText.getText().toString();
        if(phoneNo.startsWith("+")||phoneNo.startsWith("0")){
            Toast.makeText(getApplicationContext(), "Don't enter national code", Toast.LENGTH_SHORT).show();
        }
        else if(phoneNo.length()!=10){
            Toast.makeText(getApplicationContext(), "Enter Exactly 10 digits", Toast.LENGTH_SHORT).show();
        }
        else{
            Uri uri = Uri.parse("tel:"+phoneNo);
            Intent intent = new Intent(Intent.ACTION_DIAL,uri);
            if(intent.resolveActivity(getPackageManager())!=null){
                startActivity(intent);
            }
            else {
                Toast.makeText(getApplicationContext(), "No app to handle", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void openCallHead(View v){ //call to callPhone() method on button click

        //code to handle permission given in manifest specially for android M and above

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {   //to check build version in higher than android Marshmallow

            //permission is already available
            if(checkSelfPermission(Manifest.permission.CALL_PHONE)== PackageManager.PERMISSION_GRANTED){
                callPhone();
            }

            //permission is not available
            else{
                //user didn't gave permission during first popup of request permission
                if(shouldShowRequestPermissionRationale(Manifest.permission.CALL_PHONE)){
                    Toast.makeText(getApplicationContext(), "Required to directly call", Toast.LENGTH_SHORT).show();
                }
                //request for CELL_PHONE permission
                requestPermissions(new String[]{Manifest.permission.CALL_PHONE},REQUEST_CALL_PHONE);
            }

        }

    }

    //permission handler method
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if(requestCode==REQUEST_CALL_PHONE){
            //request had been granted
            if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
                callPhone();
            }
        }
    }

    public void callPhone(){ //method to directly call phone no.

        String phoneNo = phoneEditText.getText().toString();
        if(phoneNo.startsWith("+")||phoneNo.startsWith("0")){
            Toast.makeText(getApplicationContext(), "Don't enter national code", Toast.LENGTH_SHORT).show();
        }
        else if(phoneNo.length()!=10){
            Toast.makeText(getApplicationContext(), "Enter Exactly 10 digits", Toast.LENGTH_SHORT).show();
        }
        else{
            Uri uri = Uri.parse("tel:"+phoneNo);
            Intent intent = new Intent(Intent.ACTION_CALL,uri);
            if(intent.resolveActivity(getPackageManager())!=null){
                startActivity(intent);
            }
            else {
                Toast.makeText(getApplicationContext(), "Cant Call", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
