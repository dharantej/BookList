package com.example.booklist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class CustomAdapter extends ArrayAdapter<Book> {
    Context ct;
    int res;
    public CustomAdapter(@NonNull Context context, int resource, @NonNull List<Book> objects) {
        super(context, resource, objects);
        ct=context;
        res=resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView= LayoutInflater.from(ct).inflate(res,parent,false);
        Book ob=getItem(position);
        TextView t1=convertView.findViewById(R.id.title);
        TextView t2=convertView.findViewById(R.id.publisher);
        TextView t3=convertView.findViewById(R.id.author);
        t1.setText(ob.title);
        String pub="";
        for(int i=0;i<ob.authors.length;i++)
        {
            pub=pub+ob.authors[i]+"\n";
        }
        t3.setText(pub);
        t2.setText(ob.publisher);
        //t2.setText(ob.publisher);
        //t3.setText(ob.avgrating+"");
        return convertView;
    }
}
