package com.example.android.newsapp;

import android.app.Activity;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.philomena.android.newsapp.R;

import java.util.ArrayList;

public class MyNewsListAdapter extends ArrayAdapter<String> {

    private Activity context;

    private ArrayList<MyNews> newsArrayList;
    private LayoutInflater inf;

    public MyNewsListAdapter(Activity context, ArrayList<MyNews> newsArrayList) {
        super(context, R.layout.design);
        // TODO Auto-generated constructor stub

        this.context = context;
        this.newsArrayList = newsArrayList;
        inf = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return newsArrayList.size();
    }

    public View getView(final int position, View view, ViewGroup parent) {
        // LayoutInflater inflater = context.getLayoutInflater();
        LinearLayout newsLayout = (LinearLayout) inf.inflate(R.layout.design, parent, false);
        TextView nameText = newsLayout.findViewById(R.id.name);
        TextView descriptionText = newsLayout.findViewById(R.id.description);
        TextView dateText = newsLayout.findViewById(R.id.date);

        //get list of news using position
        MyNews currentNews = newsArrayList.get(position);
        nameText.setText(currentNews.getNewsName());
        descriptionText.setText(currentNews.getNewsTitle());
        dateText.setText(currentNews.getNewsDate());
        newsLayout.setTag(position);





        return newsLayout;

    }





}
