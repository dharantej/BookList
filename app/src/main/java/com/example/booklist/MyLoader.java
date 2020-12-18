package com.example.booklist;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.os.AsyncTask;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MyLoader extends AsyncTask<String,Integer,List<Book>> {
    String url;
    List<Book> ll;
    @Override
    protected List<Book> doInBackground(String... strings) {
        url=strings[0];
            ll=new ArrayList<>();
            try {
                URL myurl=new URL(url);
                HttpURLConnection httpURLConnection= (HttpURLConnection) myurl.openConnection();
                httpURLConnection.setReadTimeout(15000);
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.setConnectTimeout(15000);
                httpURLConnection.connect();
                int res;
                res=httpURLConnection.getResponseCode();
                if(res!=200)
                {
                    httpURLConnection.disconnect();
                    return ll;
                }
                InputStream is=httpURLConnection.getInputStream();
                BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(is));
                StringBuffer stringBuffer=new StringBuffer();
                String line;
                while((line=bufferedReader.readLine())!=null)
                {
                    stringBuffer.append(line);
                }
                JSONObject jsonObject=new JSONObject(stringBuffer.toString());
                JSONArray jsonArray=jsonObject.getJSONArray("items");
                int i=0;
                while (i<jsonArray.length())
                {
                    JSONObject jsonObject1=jsonArray.getJSONObject(i);
                    JSONObject jsonObject2=jsonObject1.getJSONObject("volumeInfo");
                    String t=jsonObject2.getString("title");
                    JSONArray jsonArray1=jsonObject2.getJSONArray("authors");
                    int j=0;
                    String p[]=new String[jsonArray1.length()];
                    while (j<jsonArray1.length())
                    {
                        p[j]=jsonArray1.getString(j);
                        j++;
                    }
                    String p1;
                    if(jsonObject2.getString("publisher")==null)
                    {
                        p1="";
                    }
                    else
                    {
                        p1=jsonObject2.getString("publisher");
                    }
                    //String l=jsonObject2.getString("webReaderLink");
                    ll.add(new Book(t,p,p1,""));
                    i++;
                }
                bufferedReader.close();
                is.close();
                httpURLConnection.disconnect();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return ll;

    }

    @Override
    protected void onPostExecute(List<Book> books) {
        super.onPostExecute(books);
        MainActivity.ca.clear();
        MainActivity.pb.setVisibility(View.INVISIBLE);
        if(books.size()==0)
        {
            MainActivity.tv.setVisibility(View.VISIBLE);
        }
        else
        MainActivity.ca.addAll(books);
    }
}
