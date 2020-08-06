package com.example.android.newsapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.AsyncTaskLoader;
import androidx.loader.content.Loader;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {
    TextView error;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
           getSupportLoaderManager().initLoader(0, null, (LoaderManager.LoaderCallbacks<String>) this).forceLoad();
    }

    @Override
    public Loader<String> onCreateLoader(int id, Bundle args) {
        return new FetchData(this);
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String data) {

        // Create a JSONObject from data once loading is finished
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(data);

            final ArrayList<MyNews> newslist = new ArrayList<>();
            JSONObject objectJSONObject = jsonObject.getJSONObject("response");

            JSONArray newsArray = objectJSONObject.getJSONArray("results");

            //Loop through News Array
            for (int i = 0; i < newsArray.length(); i++) {

                JSONObject CurrentlyRetrievedNews = newsArray.getJSONObject(i);

                String NewsName = CurrentlyRetrievedNews.getString("sectionName");

                String NewsTitle = CurrentlyRetrievedNews.getString("webTitle");

                String NewsDate = CurrentlyRetrievedNews.getString("webPublicationDate");

                String NewsUrl = CurrentlyRetrievedNews.getString("webUrl");

                MyNews news = new MyNews(NewsName, NewsTitle, NewsDate, NewsUrl);
                //Add current  object to array list
                newslist.add(news);
            }
            //Find our list view
            ListView list = findViewById(R.id.lsnews);
            //Create the adpter object and parse the news list

            MyNewsListAdapter adapter = new MyNewsListAdapter(MainActivity.this, newslist);

            //Add the adapter to the list view
            list.setAdapter(adapter);

            //Set a listener to the list once a particular news item on the list is clicked
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //Get the positin of the news item that is clicked
                    MyNews myNews = newslist.get(position);
                    //launch an intent to open the url
                    Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(myNews.getNewsUrl()));
                    startActivity(i);
                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onLoaderReset(Loader<String> loader) {
    }

    private static class FetchData extends AsyncTaskLoader<String> {
         Context context;
        public FetchData(Context context) {
            super(context);
            this.context=context;
        }

        private String uriBuilder() {
            Uri.Builder builder = new Uri.Builder();
            builder.scheme("https")
                    .authority("content.guardianapis.com")
                    .appendPath("search")
                    .appendQueryParameter("q", "debates")
                    .appendQueryParameter("api-key", "test");
            String myUrl = builder.build().toString();
            return myUrl; }

        @Override
        public String loadInBackground() {
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            String jsonResponse = null;
            String line;
            try {
                URL url = new URL(uriBuilder());
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();


              if (urlConnection.getResponseCode() == 200){
                    // Read the input stream into a String
                    InputStream inputStream = urlConnection.getInputStream();
                    StringBuffer loading = new StringBuffer();
                    if (inputStream == null) return null;

                    reader = new BufferedReader(new InputStreamReader(inputStream));
                    while ((line = reader.readLine()) != null) loading.append(line);

                    if (loading.length() == 0) return null;
                    jsonResponse = loading.toString();
               }

              else {
                   Toast.makeText(context, "Error In connection.", Toast.LENGTH_LONG).show();
               }
            } catch (IOException e) {
                Log.e("MainActivity", "Error while loading ", e);

                return null;
            } finally {
                if (urlConnection != null) urlConnection.disconnect();
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e("MainActivity", "Try again", e);
                    }
                }
            }
            //We return a string with json data format
            return jsonResponse;
        }

        @Override
        public void deliverResult(String data) {
            try {
                super.deliverResult(data);
            }
            catch (Exception d){
                Toast.makeText(context, "No Network connection.Please Switch on mobile data or connect to wifi", Toast.LENGTH_LONG).show();
            }

        }
    }
}
