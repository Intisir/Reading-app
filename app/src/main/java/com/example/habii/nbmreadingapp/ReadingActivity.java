package com.example.habii.nbmreadingapp;

import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;

import org.apache.commons.io.IOUtils;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ReadingActivity extends AppCompatActivity {

    PDFView pdfView;

    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reading);

        Bundle extras = getIntent().getExtras();
        if(extras != null){

            url = extras.getString("booklink");
        }

        pdfView = (PDFView)findViewById(R.id.pdfreader);

        new PDFStream().execute(url);


    }

    class PDFStream extends AsyncTask<String,Void,byte[]>{
        @Override
        protected byte[] doInBackground(String... strings) {
            InputStream inputStream = null;

            try {
                URL url = new URL(strings[0]);

                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();

                if(httpURLConnection.getResponseCode() == 200){

                    inputStream = new BufferedInputStream(httpURLConnection.getInputStream());

                }

            } catch (IOException e) {

                return null;
            }

            try {
                return IOUtils.toByteArray(inputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }


        @Override
        protected void onPostExecute(byte[] bytes) {
            pdfView.fromBytes(bytes).load();
        }
    }

}
