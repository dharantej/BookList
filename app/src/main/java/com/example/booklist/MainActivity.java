package com.example.booklist;

import androidx.appcompat.app.AppCompatActivity;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity  {

    String url="https://www.googleapis.com/books/v1/volumes?q=";
    Bundle args;
    static CustomAdapter ca;
    static TextView tv;
    static ProgressBar pb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        args=savedInstanceState;
        setContentView(R.layout.activity_main);
        ListView lv=findViewById(R.id.list_item);
        ca=new CustomAdapter(this,R.layout.listlayout,new ArrayList<Book>());
        lv.setAdapter(ca);
    }

    public void search(View view) {
        EditText et=findViewById(R.id.editTextTextPersonName);
        String se=et.getText().toString();
        String surl=url+se+"&maxResults=10";
        tv=findViewById(R.id.text);
        pb=findViewById(R.id.progress);
        ConnectivityManager cm= (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo ni=cm.getActiveNetworkInfo();
        if(ni!=null&&ni.isConnectedOrConnecting()) {
            new MyLoader().execute(surl);
        }
        else
        {
            pb.setVisibility(View.INVISIBLE);
            tv.setText("Check your network connection");
        }
    }


}