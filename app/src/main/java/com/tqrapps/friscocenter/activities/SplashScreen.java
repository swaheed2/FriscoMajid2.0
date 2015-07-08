package com.tqrapps.friscocenter.activities;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.tqrapps.friscocenter.R;
import com.tqrapps.friscocenter.pojo.PrayersToday;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormatSymbols;
import java.util.Calendar;

public class SplashScreen extends Activity {

    PrayersToday[] monthlyPrayer = new PrayersToday[31];
    private static int SPLASH_TIME_OUT = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                // new SetData().execute();
                Intent i = new Intent(SplashScreen.this, MainActivity.class);
                startActivity(i);
                finishSplash( );
            }
        }, SPLASH_TIME_OUT);
    }

    private void finishSplash() {
        this.finish();
    }

    private class SetData extends AsyncTask<String, Void, String> {
        Calendar cal = Calendar.getInstance();
        String day = String.valueOf(cal.get(Calendar.DAY_OF_MONTH));
        String todayDate;
        Calendar calMain = Calendar.getInstance();
        String monthName = (getMonthForInt(calMain.get(Calendar.MONTH)));
        @Override
        protected String doInBackground(String... params) {
            try {
                for(int i=0; i<31; i++){
                    monthlyPrayer[i] = new PrayersToday();
                }
                todayDate = monthName + " " + String.valueOf(day + ", " + String.valueOf(cal.get(Calendar.YEAR)));


                DefaultHttpClient httpclient = new DefaultHttpClient();
                int currentMonth = calMain.get(Calendar.MONTH);
                Log.d("month", "currentMonth " + currentMonth);
                HttpGet httppost = new HttpGet("http://ahqiplano.org/friscomasjid/"+currentMonth +".txt");
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity ht = response.getEntity();
                BufferedHttpEntity buf = new BufferedHttpEntity(ht);
                InputStream is = buf.getContent();
                BufferedReader r = new BufferedReader(new InputStreamReader(is));
                String line;
                String date;
                int count = 0;
                while ((line = r.readLine()) != null) {
                    String[] pt = line.split("\t+");
                    if(count < 9){
                        date = pt[0].substring(1,2);
                    }
                    else
                        date = pt[0].substring(0,2);
                    if(day.equals(date)){
                        monthlyPrayer[count].setDate((date));
                        monthlyPrayer[count].setFajrTime((pt[2]));
                        monthlyPrayer[count].setFajrIqamah(pt[3]);
                        monthlyPrayer[count].setSunrise((pt[4]));
                        monthlyPrayer[count].setDhuhrTime((pt[5]));
                        monthlyPrayer[count].setDhuhrIqamah((pt[6]));
                        monthlyPrayer[count].setAsrTime((pt[7]));
                        monthlyPrayer[count].setAsrIqamah((pt[8]));
                        monthlyPrayer[count].setMaghribTime((pt[9]));
                        monthlyPrayer[count].setMaghribIqamah((pt[10]));
                        monthlyPrayer[count].setIshaTime((pt[11]));
                        monthlyPrayer[count].setIshaIqamah((pt[12]));
                        break;
                    }
                    else
                        count++;
                }
            }
            catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPreExecute() {
        }
        @Override
        protected void onPostExecute(String result) {
            Intent i = new Intent(SplashScreen.this, MainActivity.class);
            int dayInt = cal.get(Calendar.DAY_OF_MONTH);
            PrayersToday today = monthlyPrayer[dayInt-1];
            String fajrTime = today.getFajrTime();
            String fajrIqamah = today.getFajrIqamah();
            String sunrise_iqamah = (today.getSunrise());
            String dhuhrTime = today.getDhuhrTime();
            String dhuhrIqamah = today.getDhuhrIqamah();
            String asrTime = today.getAsrTime();
            String asrIqamah = today.getAsrIqamah();
            String maghribTime = today.getMaghribTime();
            String maghribIqamah = today.getMaghribIqamah();
            String ishaTime = today.getIshaTime();
            String ishaIqamah = today.getIshaIqamah();
            String dayOfWeek = getDayOfWeek(cal.get(Calendar.DAY_OF_WEEK));
            i.putExtra("fajrTime",fajrTime);
            i.putExtra("fajrIqamah", fajrIqamah);
            i.putExtra("sunrise_iqamah", sunrise_iqamah);
            i.putExtra("dhuhrTime", dhuhrTime);
            i.putExtra("dhuhrIqamah" , dhuhrIqamah);
            i.putExtra("asrTime", asrTime);
            i.putExtra("asrIqamah", asrIqamah);
            i.putExtra("maghribTime" ,maghribTime );
            i.putExtra("maghribIqamah" ,maghribIqamah );
            i.putExtra("ishaTime" , ishaTime);
            i.putExtra("ishaIqamah" ,ishaIqamah );
            i.putExtra("dayOfWeek",dayOfWeek );
            Log.d("out","out");
            startActivity(i);
            // close this activity
            finish();
        }

    }

    public String getMonthForInt(int num) {
        String month = "error";
        DateFormatSymbols dfs = new DateFormatSymbols();
        String[] months = dfs.getMonths();
        if (num == 12){
            num = 0;
        }
        if (num >= 0 && num <= 11 ) {
            month = months[num];
        }
        return month;
    }

    private String getDayOfWeek(int value) {
        String day = "";
        switch (value) {
            case 1:
                day = "Sun";
                break;
            case 2:
                day = "Mon";
                break;
            case 3:
                day = "Tue";
                break;
            case 4:
                day = "Wed";
                break;
            case 5:
                day = "Thu";
                break;
            case 6:
                day = "Fri";
                break;
            case 7:
                day = "Sat";
                break;
        }

        return day;
    }

}