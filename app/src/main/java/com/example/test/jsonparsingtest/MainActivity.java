package com.example.test.jsonparsingtest;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    protected TextView textView;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button=(Button)findViewById(R.id.button);
        textView=(TextView)findViewById(R.id.textView);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FetchJSONData fd=new FetchJSONData();
                fd.execute();
            }
        });
    }

public class FetchJSONData extends AsyncTask<Void,Void,Void>
    {
        String res=null;
        @Override

        protected Void doInBackground(Void... params)
        {
            HttpURLConnection urlConnection=null;
            BufferedReader reader=null;
            try
            {
                String BaseUrl="http://api.openweathermap.org/data/2.5/forecast/daily?q=940343&mode=json&units=metric&cnt=7";
                String apikey="&APPID="+BuildConfig.OPEN_WEATHER_MAP_API_KEY;
                URL url=new URL(BaseUrl.concat(apikey));
                urlConnection=(HttpURLConnection)url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inputStream=urlConnection.getInputStream();
                StringBuffer buffer=new StringBuffer();
                if(inputStream==null)
                    return null;

                reader=new BufferedReader(new InputStreamReader(inputStream));

                String line;

                while((line=reader.readLine())!=null)
                  buffer.append(line+'\n');

                res= buffer.toString();
            }
            catch(Exception e)
            {

            }
            finally
            {
                urlConnection.disconnect();
                try {
                    reader.close();
                }
                catch (Exception e)
                {

                }
            }


            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid)
        {
            super.onPostExecute(aVoid);
            textView.setText(res);
        }
    }
}
