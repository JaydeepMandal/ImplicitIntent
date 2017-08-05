package com.example.ee.implicitintent;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText webPage;
    private EditText locEditText;
    private  EditText text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webPage = (EditText) findViewById(R.id.webEditText);
        locEditText = (EditText) findViewById(R.id.locEditText);
        text = (EditText) findViewById(R.id.editText);
    }

    public void openWebsite(View v){
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

    public void openLocation(View v){

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

    public void shareText(View v){

        String txt = String.valueOf(text.getText());
        String mimeType = "text/plain";

        ShareCompat.IntentBuilder
                .from(this)
                .setType(mimeType)
                .setChooserTitle("Share this text with:")
                .setText(txt)
                .startChooser();
    }

}
