package com.tqrapps.friscocenter.fragments;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.gc.materialdesign.views.ButtonRectangle;
import com.tqrapps.friscocenter.R;
import com.tqrapps.friscocenter.pojo.PrayersToday;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.joda.time.DateTime;
import org.joda.time.LocalTime;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PrayerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PrayerFragment extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    TextView todaysDate, fajrTime, fajrIqamah, dhuhrTime, dhuhrIqamah, asrTime, asrIqamah, maghribTime, maghribIqamah,
            ishaTime, ishaIqamah, timeText, iqamahText, sunrise_iqamah, sunrise_time;
    TextView fajrText, dhuhrText, asrText, maghribText, ishaText, sunriseText;
    Context mContext;
    ButtonRectangle prev, today, next;
    ProgressDialog progressDialog;
    Calendar calMain = Calendar.getInstance();
    int dayInt, position, nextMonthPosition;
    String todayDate, nextMonthName, monthName;
    PrayersToday[] monthlyPrayer = new PrayersToday[31];
    PrayersToday[] nextMonth = new PrayersToday[31];
    Boolean nextB = true;

    LocalTime time;

    LocalTime nextPrayerTimes[] = new LocalTime[12];
    String nextPrayerNames[] = new String[12];
    String iqamahOrTimes[] = new String[12];
    int nextPrayerTimeMin = 0;
    int nextPrayerTimeHours = 0;
    int nextPrayerNameIndex = 0;
    private int nextPrayerTimeSeconds = 0;
    CountDownTimer countDownTimer;


    int once = 0;
    private TextView nextPrayerTimeText;


    public PrayerFragment() {
        // Required empty public constructor
    }

    public static PrayerFragment newInstance(String param1, String param2) {
        PrayerFragment fragment = new PrayerFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View pt = inflater.inflate(R.layout.prayer_times_frag, container, false);
        setHasOptionsMenu(true);
        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Aller_Bd.ttf");
        timeText = (TextView) pt.findViewById(R.id.time);
        iqamahText = (TextView) pt.findViewById(R.id.iqamah);
        todaysDate = (TextView) pt.findViewById(R.id.todayDate);
        fajrTime = (TextView) pt.findViewById(R.id.fajr_time);
        fajrIqamah = (TextView) pt.findViewById(R.id.fajr_iqamah);
        sunrise_time = (TextView) pt.findViewById(R.id.sunrise_time);
        sunrise_iqamah = (TextView) pt.findViewById(R.id.sunrise_iqamah);
        dhuhrTime = (TextView) pt.findViewById(R.id.dhuhr_time);
        dhuhrIqamah = (TextView) pt.findViewById(R.id.dhuhr_iqamah);
        asrTime = (TextView) pt.findViewById(R.id.asr_time);
        asrIqamah = (TextView) pt.findViewById(R.id.asr_iqamah);
        maghribTime = (TextView) pt.findViewById(R.id.magrhib_time);
        maghribIqamah = (TextView) pt.findViewById(R.id.magrhib_iqamah);
        ishaTime = (TextView) pt.findViewById(R.id.isha_time);
        ishaIqamah = (TextView) pt.findViewById(R.id.isha_iqamah);
        fajrText = (TextView) pt.findViewById(R.id.fajrText);
        dhuhrText = (TextView) pt.findViewById(R.id.dhuhrText);
        asrText = (TextView) pt.findViewById(R.id.asrText);
        maghribText = (TextView) pt.findViewById(R.id.maghribText);
        ishaText = (TextView) pt.findViewById(R.id.ishaText);
        sunriseText = (TextView) pt.findViewById(R.id.sunriseText);
        nextPrayerTimeText = (TextView) pt.findViewById(R.id.nextPrayerTimeText);


        calMain = Calendar.getInstance();
        dayInt = calMain.get(Calendar.DAY_OF_MONTH);
        position = dayInt;
        nextMonthPosition = 1;
        nextMonthName = (getMonthForInt(calMain.get(Calendar.MONTH) + 1));
        monthName = (getMonthForInt(calMain.get(Calendar.MONTH)));
        today = (ButtonRectangle) pt.findViewById(R.id.today);
        prev = (ButtonRectangle) pt.findViewById(R.id.prev);
        next = (ButtonRectangle) pt.findViewById(R.id.next);


        timeText.setTypeface(font);
        iqamahText.setTypeface(font);
        todaysDate.setTypeface(font);
        fajrText.setTypeface(font);
        dhuhrText.setTypeface(font);
        asrText.setTypeface(font);
        maghribText.setTypeface(font);
        ishaText.setTypeface(font);
        sunriseText.setTypeface(font);
        fajrIqamah.setTypeface(font);
        fajrTime.setTypeface(font);
        dhuhrIqamah.setTypeface(font);
        dhuhrTime.setTypeface(font);
        asrTime.setTypeface(font);
        asrIqamah.setTypeface(font);
        maghribIqamah.setTypeface(font);
        maghribTime.setTypeface(font);
        ishaIqamah.setTypeface(font);
        ishaTime.setTypeface(font);
        sunrise_iqamah.setTypeface(font);
        sunrise_time.setTypeface(font);

        DateTime dt = new DateTime();  // current time
        int min = dt.getMinuteOfHour();     // gets the min of hour
        int hour = dt.getHourOfDay(); // gets hour of day
        int seconds = dt.getSecondOfMinute();
        time = new LocalTime(hour, min, seconds);


        mContext = pt.getContext();
        new SetData().execute();
        //setData();


        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new prevButton().execute();
            }
        });

        today.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setToday();
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new nextButton().execute();
            }
        });
        return pt;
    }


    private class SetData extends AsyncTask<String, Void, String> {
        Calendar cal = Calendar.getInstance();
        int dayInt = cal.get(Calendar.DAY_OF_MONTH);
        String day = String.valueOf(cal.get(Calendar.DAY_OF_MONTH));

        @Override
        protected String doInBackground(String... params) {
            try {
                for (int i = 0; i < 31; i++) {
                    monthlyPrayer[i] = new PrayersToday();
                }
                todayDate = monthName + " " + String.valueOf(day + ", " + String.valueOf(cal.get(Calendar.YEAR)));


                DefaultHttpClient httpclient = new DefaultHttpClient();
                int currentMonth = calMain.get(Calendar.MONTH);
                // Log.d("month", "currentMonth " + currentMonth);

                HttpGet httppost = null;
                httppost = getHtppPost(currentMonth);

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
                    if (count < 9) {
                        date = pt[0].substring(1, 2);
                    } else
                        date = pt[0].substring(0, 2);

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
                    count++;
                }

                setTimeToNextPrayer();
            } catch (FileNotFoundException e) {
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
            if (once == 0 && nextB) {
                PrayersToday nextPrayer = monthlyPrayer[position - 1];
                String todayDate = monthName + " " + String.valueOf(nextPrayer.getDate() + ", " + String.valueOf(calMain.get(Calendar.YEAR)));
                Calendar c = Calendar.getInstance();
                Date date = new Date(calMain.get(Calendar.YEAR), calMain.get(Calendar.MONTH), position - 1);
                c.setTime(date);
                fajrTime.setText(nextPrayer.getFajrTime());
                fajrIqamah.setText(nextPrayer.getFajrIqamah());
                sunrise_iqamah.setText((nextPrayer.getSunrise()));
                sunrise_time.setText((nextPrayer.getSunrise()));
                dhuhrTime.setText(nextPrayer.getDhuhrTime());
                dhuhrIqamah.setText(nextPrayer.getDhuhrIqamah());
                asrTime.setText(nextPrayer.getAsrTime());
                asrIqamah.setText(nextPrayer.getAsrIqamah());
                maghribTime.setText(nextPrayer.getMaghribTime());
                maghribIqamah.setText(nextPrayer.getMaghribIqamah());
                ishaTime.setText(nextPrayer.getIshaTime());
                ishaIqamah.setText(nextPrayer.getIshaIqamah());
                if (position == dayInt) {
                    todaysDate.setTextColor(getResources().getColor(R.color.colorPrimary));
                    String dayOfWeek = getDayOfWeek(calMain.get(Calendar.DAY_OF_WEEK));
                    todaysDate.setText("Today's Date: " + dayOfWeek + " | " + todayDate);
                } else {
                    todaysDate.setTextColor(getResources().getColor(R.color.black));
                    String dayOfWeek = getDayOfWeek(c.get(Calendar.DAY_OF_WEEK));
                    todaysDate.setText("               Date: " + dayOfWeek + " | " + todayDate);
                }

                setCountdown();
            } else if (once == 0) {
                //Log.d("Here","here");
                PrayersToday nextPrayer = monthlyPrayer[position - 1];
                String todayDate = monthName + " " + String.valueOf(nextPrayer.getDate() + ", " + String.valueOf(calMain.get(Calendar.YEAR)));
                Calendar c = Calendar.getInstance();
                Date date = new Date(calMain.get(Calendar.YEAR), calMain.get(Calendar.MONTH), position - 1);
                c.setTime(date);
                fajrTime.setText(nextPrayer.getFajrTime());
                fajrIqamah.setText(nextPrayer.getFajrIqamah());
                sunrise_iqamah.setText((nextPrayer.getSunrise()));
                sunrise_time.setText((nextPrayer.getSunrise()));
                dhuhrTime.setText(nextPrayer.getDhuhrTime());
                dhuhrIqamah.setText(nextPrayer.getDhuhrIqamah());
                asrTime.setText(nextPrayer.getAsrTime());
                asrIqamah.setText(nextPrayer.getAsrIqamah());
                maghribTime.setText(nextPrayer.getMaghribTime());
                maghribIqamah.setText(nextPrayer.getMaghribIqamah());
                ishaTime.setText(nextPrayer.getIshaTime());
                ishaIqamah.setText(nextPrayer.getIshaIqamah());
                if (position == dayInt) {
                    todaysDate.setTextColor(getResources().getColor(R.color.colorPrimary));
                    String dayOfWeek = getDayOfWeek(calMain.get(Calendar.DAY_OF_WEEK));
                    todaysDate.setText("Today's Date: " + dayOfWeek + " | " + todayDate);
                } else {
                    todaysDate.setTextColor(getResources().getColor(R.color.black));
                    String dayOfWeek = getDayOfWeek(c.get(Calendar.DAY_OF_WEEK));
                    todaysDate.setText("Date: " + dayOfWeek + " | " + todayDate);
                }
            }
            //setCurrentPrayer();
            once++;


        }


    }

    private class nextButton extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            if (once == 0) {
                progressDialog = new ProgressDialog(mContext);
                progressDialog.setMessage("Updating...");
                progressDialog.show();
            }

        }

        @Override
        protected String doInBackground(String... strings) {
            if (once == 0) {
                new SetData().execute();
                //Log.d("SetData","Set Data executed");
            }
            return null;

        }

        @Override
        protected void onPostExecute(String s) {
            if (progressDialog != null) {
                progressDialog.dismiss();
            }

            position++;
            int numDays = calMain.getActualMaximum(Calendar.DAY_OF_MONTH);
            if (position < numDays + 1 && position > 0 && once != 0) {
                PrayersToday nextPrayer = monthlyPrayer[position - 1];
                String todayDate = monthName + " " + String.valueOf(nextPrayer.getDate() + ", " + String.valueOf(calMain.get(Calendar.YEAR)));
                Calendar c = Calendar.getInstance();
                Date date = new Date(calMain.get(Calendar.YEAR), calMain.get(Calendar.MONTH), position - 1);
                c.setTime(date);
                fajrTime.setText(nextPrayer.getFajrTime());
                fajrIqamah.setText(nextPrayer.getFajrIqamah());
                sunrise_iqamah.setText((nextPrayer.getSunrise()));
                sunrise_time.setText((nextPrayer.getSunrise()));
                dhuhrTime.setText(nextPrayer.getDhuhrTime());
                dhuhrIqamah.setText(nextPrayer.getDhuhrIqamah());
                asrTime.setText(nextPrayer.getAsrTime());
                asrIqamah.setText(nextPrayer.getAsrIqamah());
                maghribTime.setText(nextPrayer.getMaghribTime());
                maghribIqamah.setText(nextPrayer.getMaghribIqamah());
                ishaTime.setText(nextPrayer.getIshaTime());
                ishaIqamah.setText(nextPrayer.getIshaIqamah());
                if (position == dayInt) {
                    todaysDate.setTextColor(getResources().getColor(R.color.colorPrimary));
                    String dayOfWeek = getDayOfWeek(calMain.get(Calendar.DAY_OF_WEEK));
                    todaysDate.setText("Today's Date: " + dayOfWeek + " | " + todayDate);
                } else {
                    todaysDate.setTextColor(getResources().getColor(R.color.black));
                    String dayOfWeek = getDayOfWeek(c.get(Calendar.DAY_OF_WEEK));
                    todaysDate.setText("Date: " + dayOfWeek + " | " + todayDate);
                }

            } else if (position > numDays) {

                if ((nextMonth[nextMonthPosition - 1] != null) && (nextMonth[nextMonthPosition - 1].getFajrIqamah().length() > 2)) {

                    Calendar c = Calendar.getInstance();
                    c.set(calMain.get(Calendar.YEAR), c.get(Calendar.MONTH) + 1, nextMonthPosition);
//                    if(calMain.get(Calendar.MONTH) == 11){
//                        c.add(Calendar.YEAR, 1);
//                    }

                    String nextMonthDate = nextMonthName + " " + String.valueOf(nextMonthPosition + ", " + String.valueOf(c.get(Calendar.YEAR)));
                    PrayersToday today = nextMonth[nextMonthPosition - 1];
                    fajrTime.setText(today.getFajrTime());
                    fajrIqamah.setText(today.getFajrIqamah());
                    sunrise_iqamah.setText((today.getSunrise()));
                    sunrise_time.setText((today.getSunrise()));
                    dhuhrTime.setText(today.getDhuhrTime());
                    dhuhrIqamah.setText(today.getDhuhrIqamah());
                    asrTime.setText(today.getAsrTime());
                    asrIqamah.setText(today.getAsrIqamah());
                    maghribTime.setText(today.getMaghribTime());
                    maghribIqamah.setText(today.getMaghribIqamah());
                    ishaTime.setText(today.getIshaTime());
                    ishaIqamah.setText(today.getIshaIqamah());
                    todaysDate.setTextColor(getResources().getColor(R.color.black));
                    String dayOfWeek = getDayOfWeek(c.get(Calendar.DAY_OF_WEEK));
                    todaysDate.setText("Date: " + dayOfWeek + " | " + nextMonthDate);
                    if (nextMonthPosition > 27) {
                        nextMonthPosition = 1;
                    }
                    nextMonthPosition++;

                } else
                    new SetNextMonth().execute();
            }
            //   Log.d("","nextMonthPosition after Next: " + nextMonthPosition);

        }

    }

    private class prevButton extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            if (once == 0) {
                nextB = false;
                progressDialog = new ProgressDialog(mContext);
                progressDialog.setMessage("Updating...");
                progressDialog.show();
            }
        }

        @Override
        protected String doInBackground(String... strings) {
            if (once == 0) {
                new SetData().execute();
            }
            return null;

        }

        @Override
        protected void onPostExecute(String s) {
            if (progressDialog != null) {
                progressDialog.dismiss();
            }
            int numDays = calMain.getActualMaximum(Calendar.DAY_OF_MONTH);
            if (nextMonthPosition > 1) {
                nextMonthPosition--;
                Calendar c = Calendar.getInstance();
                c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH) + 1, nextMonthPosition);
                String nextMonthDate = nextMonthName + " " + String.valueOf(nextMonthPosition + ", " + String.valueOf(c.get(Calendar.YEAR)));
                PrayersToday today = nextMonth[nextMonthPosition - 1];
                fajrTime.setText(today.getFajrTime());
                fajrIqamah.setText(today.getFajrIqamah());
                sunrise_iqamah.setText((today.getSunrise()));
                sunrise_time.setText((today.getSunrise()));
                dhuhrTime.setText(today.getDhuhrTime());
                dhuhrIqamah.setText(today.getDhuhrIqamah());
                asrTime.setText(today.getAsrTime());
                asrIqamah.setText(today.getAsrIqamah());
                maghribTime.setText(today.getMaghribTime());
                maghribIqamah.setText(today.getMaghribIqamah());
                ishaTime.setText(today.getIshaTime());
                ishaIqamah.setText(today.getIshaIqamah());
                todaysDate.setTextColor(getResources().getColor(R.color.black));
                String dayOfWeek = getDayOfWeek(c.get(Calendar.DAY_OF_WEEK));
                todaysDate.setText("Date: " + dayOfWeek + " | " + nextMonthDate);

                if (nextMonthPosition > 27 || nextMonthPosition < 1) {
                    nextMonthPosition = 1;
                }
            } else {
                position--;
                if (position < numDays + 1 && position > 0 && once != 0) {
                    PrayersToday nextPrayer = monthlyPrayer[position - 1];
                    String todayDate = monthName + " " + String.valueOf(nextPrayer.getDate() + ", " + String.valueOf(calMain.get(Calendar.YEAR)));
                    Calendar c = Calendar.getInstance();
                    Date date = new Date(calMain.get(Calendar.YEAR), calMain.get(Calendar.MONTH), position - 1);
                    c.setTime(date);
                    fajrTime.setText(nextPrayer.getFajrTime());
                    fajrIqamah.setText(nextPrayer.getFajrIqamah());
                    sunrise_iqamah.setText((nextPrayer.getSunrise()));
                    sunrise_time.setText((nextPrayer.getSunrise()));
                    dhuhrTime.setText(nextPrayer.getDhuhrTime());
                    dhuhrIqamah.setText(nextPrayer.getDhuhrIqamah());
                    asrTime.setText(nextPrayer.getAsrTime());
                    asrIqamah.setText(nextPrayer.getAsrIqamah());
                    maghribTime.setText(nextPrayer.getMaghribTime());
                    maghribIqamah.setText(nextPrayer.getMaghribIqamah());
                    ishaTime.setText(nextPrayer.getIshaTime());
                    ishaIqamah.setText(nextPrayer.getIshaIqamah());
                    if (position == dayInt) {
                        todaysDate.setTextColor(getResources().getColor(R.color.colorPrimary));
                        String dayOfWeek = getDayOfWeek(calMain.get(Calendar.DAY_OF_WEEK));
                        todaysDate.setText("Today's Date: " + dayOfWeek + " | " + todayDate);
                    } else {
                        todaysDate.setTextColor(getResources().getColor(R.color.black));
                        String dayOfWeek = getDayOfWeek(c.get(Calendar.DAY_OF_WEEK));
                        todaysDate.setText("Date: " + dayOfWeek + " | " + todayDate);
                    }
                } else
                    position = dayInt;

            }
        }
    }

    public void setToday() {
        position = dayInt;
        nextMonthPosition = 1;
        if (position < 32 && position > 0) {
            PrayersToday nextPrayer = monthlyPrayer[position - 1];
            String month = getMonthForInt(calMain.get(Calendar.MONTH));
            if (once != 0) {
                String todayDate = month + " " + String.valueOf(nextPrayer.getDate() + ", " + String.valueOf(calMain.get(Calendar.YEAR)));
                fajrTime.setText(nextPrayer.getFajrTime());
                fajrIqamah.setText(nextPrayer.getFajrIqamah());
                sunrise_iqamah.setText((nextPrayer.getSunrise()));
                sunrise_time.setText((nextPrayer.getSunrise()));
                dhuhrTime.setText(nextPrayer.getDhuhrTime());
                dhuhrIqamah.setText(nextPrayer.getDhuhrIqamah());
                asrTime.setText(nextPrayer.getAsrTime());
                asrIqamah.setText(nextPrayer.getAsrIqamah());
                maghribTime.setText(nextPrayer.getMaghribTime());
                maghribIqamah.setText(nextPrayer.getMaghribIqamah());
                ishaTime.setText(nextPrayer.getIshaTime());
                ishaIqamah.setText(nextPrayer.getIshaIqamah());
                todaysDate.setTextColor(getResources().getColor(R.color.colorPrimary));
                String dayOfWeek = getDayOfWeek(calMain.get(Calendar.DAY_OF_WEEK));
                todaysDate.setText("Today's Date: " + dayOfWeek + " | " + todayDate);
            }

        } else
            position = dayInt;
    }

    private class SetNextMonth extends AsyncTask<String, Void, String> {
        protected String doInBackground(String... params) {

            try {
                for (int i = 0; i < 31; i++) {
                    nextMonth[i] = new PrayersToday();

                }
                DefaultHttpClient httpclient = new DefaultHttpClient();
                int nextMonthInt = calMain.get(Calendar.MONTH);
                if (nextMonthInt != 11)
                    nextMonthInt++;
                else
                    nextMonthInt = 0;
                HttpGet httppost = getHtppPost(nextMonthInt);
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity ht = response.getEntity();
                BufferedHttpEntity buf = new BufferedHttpEntity(ht);
                InputStream is = buf.getContent();
                BufferedReader r = new BufferedReader(new InputStreamReader(is));
                String line, date;
                int count = 0;
                while ((line = r.readLine()) != null) {
                    String[] pt = line.split("\t+");
                    if (count < 9) {
                        date = pt[0].substring(1, 2);
                    } else
                        date = pt[0].substring(0, 2);

                    nextMonth[count].setDate((date));
                    nextMonth[count].setFajrTime((pt[2]));
                    nextMonth[count].setFajrIqamah(pt[3]);
                    nextMonth[count].setSunrise((pt[4]));
                    nextMonth[count].setDhuhrTime((pt[5]));
                    nextMonth[count].setDhuhrIqamah((pt[6]));
                    nextMonth[count].setAsrTime((pt[7]));
                    nextMonth[count].setAsrIqamah((pt[8]));
                    nextMonth[count].setMaghribTime((pt[9]));
                    nextMonth[count].setMaghribIqamah((pt[10]));
                    nextMonth[count].setIshaTime((pt[11]));
                    nextMonth[count].setIshaIqamah((pt[12]));
                    count++;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(mContext);
            progressDialog.setMessage("Getting Next Month...");
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(String result) {
            Calendar c = Calendar.getInstance();
            c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH) + 1, nextMonthPosition);
            String nextMonthDate = nextMonthName + " " + String.valueOf(nextMonthPosition + ", " + String.valueOf(c.get(Calendar.YEAR)));
            PrayersToday today = nextMonth[nextMonthPosition - 1];
            fajrTime.setText(today.getFajrTime());
            fajrIqamah.setText(today.getFajrIqamah());
            sunrise_iqamah.setText((today.getSunrise()));
            sunrise_time.setText((today.getSunrise()));
            dhuhrTime.setText(today.getDhuhrTime());
            dhuhrIqamah.setText(today.getDhuhrIqamah());
            asrTime.setText(today.getAsrTime());
            asrIqamah.setText(today.getAsrIqamah());
            maghribTime.setText(today.getMaghribTime());
            maghribIqamah.setText(today.getMaghribIqamah());
            ishaTime.setText(today.getIshaTime());
            ishaIqamah.setText(today.getIshaIqamah());
            todaysDate.setTextColor(getResources().getColor(R.color.black));
            String dayOfWeek = getDayOfWeek(c.get(Calendar.DAY_OF_WEEK));
            todaysDate.setText("Date: " + dayOfWeek + " | " + nextMonthDate);
            if (nextMonthPosition > 27) {
                nextMonthPosition = 1;
            }
            nextMonthPosition++;
            if (progressDialog != null) {
                progressDialog.dismiss();
            }
        }

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

    public String getMonthForInt(int num) {
        String month = "error";
        DateFormatSymbols dfs = new DateFormatSymbols();
        String[] months = dfs.getMonths();
        if (num == 12) {
            num = 0;
        }
        if (num >= 0 && num <= 11) {
            month = months[num];
        }
        return month;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu items for use in the action bar
        inflater.inflate(R.menu.refreshmenu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.actionbar_refresh) {
            new SetData().execute();
            Toast.makeText(getActivity().getApplicationContext(), "Updated", Toast.LENGTH_SHORT).show();
            return true;
        } else if (id == R.id.actionbar_share) {
            shareIt();
            return true;
        }
        return false;
    }

    void shareIt() {
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT,
                "Frisco Masjid Android App");
        sharingIntent.putExtra(Intent.EXTRA_TEXT, "Get Frisco Masjid's Android App from Android Playstore: https://play.google.com/store/apps/details?id=com.tqrapps.friscocenter");
        startActivity(Intent.createChooser(sharingIntent, "Share Via"));
    }

    //  HttpGet httppost = new HttpGet("http://ahqiplano.org/friscomasjid/"+currentMonth +".txt");
    public HttpGet getHtppPost(int currentMonth) {
        HttpGet httppost = null;
        if (currentMonth == 0) {
            httppost = new HttpGet("https://drive.google.com/uc?export=download&id=0B0fZ1fbGsSxZVXl6TnFLXzNNSUU");
        } else if (currentMonth == 1) {
            httppost = new HttpGet("https://drive.google.com/uc?export=download&id=0B0fZ1fbGsSxZUmVEdXJSc1BBdHM");
        } else if (currentMonth == 2) {
            httppost = new HttpGet("https://drive.google.com/uc?export=download&id=0B0fZ1fbGsSxZUktRbDZzRGVtVWc");
        } else if (currentMonth == 3) {
            httppost = new HttpGet("https://drive.google.com/uc?export=download&id=0B0fZ1fbGsSxZX2RTQmhON3pydlU");
        } else if (currentMonth == 4) {
            httppost = new HttpGet("https://drive.google.com/uc?export=download&id=0B0fZ1fbGsSxZZ0JhREk2cnpQTDA");
        } else if (currentMonth == 5) {
            httppost = new HttpGet("https://drive.google.com/uc?export=download&id=0B0fZ1fbGsSxZU05veUNXa2I5SFk");
        } else if (currentMonth == 6) {
            httppost = new HttpGet("https://drive.google.com/uc?export=download&id=0B0fZ1fbGsSxZMllKRVBwaXFRalE");
        } else if (currentMonth == 7) {
            httppost = new HttpGet("https://drive.google.com/uc?export=download&id=0B0fZ1fbGsSxZNEJCQkRRcDNFcWM");
        } else if (currentMonth == 8) {
            httppost = new HttpGet("https://drive.google.com/uc?export=download&id=0B0fZ1fbGsSxZTDdCUTdJRWFzOGc");
        } else if (currentMonth == 9) {
            httppost = new HttpGet("https://drive.google.com/uc?export=download&id=0B0fZ1fbGsSxZVndlSWtNbndnaDA");
        } else if (currentMonth == 10) {
            httppost = new HttpGet("https://drive.google.com/uc?export=download&id=0B0fZ1fbGsSxZZ1M5WUxTc3NKODg");
        } else if (currentMonth == 11) {
            httppost = new HttpGet("https://drive.google.com/uc?export=download&id=0B0fZ1fbGsSxZak81MFd1TXpLYXc");
        }
        return httppost;
    }

    private void setCountdown() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        final long miliseconds = (nextPrayerTimeHours * 3600000) + (60000 * nextPrayerTimeMin) + (nextPrayerTimeSeconds * 1000);
        final String FORMAT = "%2d:%2d:%2d";
        countDownTimer = new CountDownTimer(miliseconds, 1000) {

            public void onTick(long millisUntilFinished) {
                nextPrayerTimeText.setText(nextPrayerNames[nextPrayerNameIndex] + "  " + iqamahOrTimes[nextPrayerNameIndex] + "  " + String.format("%02d:%02d:%02d",
                        TimeUnit.MILLISECONDS.toHours(millisUntilFinished),
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) -
                                TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millisUntilFinished)),
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));

            }

            public void onFinish() {
            }
        }.start();
    }

    private void setTimeToNextPrayer() {
        for (int i = 0; i < nextPrayerTimes.length; i++) {
            nextPrayerTimes[i] = new LocalTime(0, 0, 0);
            nextPrayerNames[i] = "";
        }

        PrayersToday todayPrayer = monthlyPrayer[position - 1];

        int countNextPrayer = 0;
        String tempPrayer = todayPrayer.getFajrTime();
        int hour = Integer.parseInt(tempPrayer.substring(0, tempPrayer.indexOf(":")));
        int min = Integer.parseInt(tempPrayer.substring(tempPrayer.indexOf(":") + 1, tempPrayer.length()));
        nextPrayerTimes[countNextPrayer] = new LocalTime(hour, min, 0);
        nextPrayerNames[countNextPrayer] = "FAJR";
        iqamahOrTimes[countNextPrayer] = "time in";
        countNextPrayer++;

        tempPrayer = todayPrayer.getFajrIqamah();
        hour = Integer.parseInt(tempPrayer.substring(0, tempPrayer.indexOf(":")));
        min = Integer.parseInt(tempPrayer.substring(tempPrayer.indexOf(":") + 1, tempPrayer.length()));
        nextPrayerTimes[countNextPrayer] = new LocalTime(hour, min, 0);
        nextPrayerNames[countNextPrayer] = "FAJR";
        iqamahOrTimes[countNextPrayer] = "iqamah in";
        countNextPrayer++;

        tempPrayer = todayPrayer.getSunrise();
        hour = Integer.parseInt(tempPrayer.substring(0, tempPrayer.indexOf(":")));
        min = Integer.parseInt(tempPrayer.substring(tempPrayer.indexOf(":") + 1, tempPrayer.length()));
        nextPrayerTimes[countNextPrayer] = new LocalTime(hour, min, 0);
        nextPrayerNames[countNextPrayer] = "SUNRISE";
        iqamahOrTimes[countNextPrayer] = "time in";
        countNextPrayer++;

        tempPrayer = todayPrayer.getDhuhrTime();
        hour = Integer.parseInt(tempPrayer.substring(0, tempPrayer.indexOf(":")));
        min = Integer.parseInt(tempPrayer.substring(tempPrayer.indexOf(":") + 1, tempPrayer.length()));
        if (hour == 12)
            nextPrayerTimes[countNextPrayer] = new LocalTime(hour, min, 0);
        else
            nextPrayerTimes[countNextPrayer] = new LocalTime(hour + 12, min, 0);
        nextPrayerNames[countNextPrayer] = "DHUHR";
        iqamahOrTimes[countNextPrayer] = "time in";
        countNextPrayer++;

        tempPrayer = todayPrayer.getDhuhrIqamah();
        hour = Integer.parseInt(tempPrayer.substring(0, tempPrayer.indexOf(":")));
        min = Integer.parseInt(tempPrayer.substring(tempPrayer.indexOf(":") + 1, tempPrayer.length()));
        nextPrayerTimes[countNextPrayer] = new LocalTime(hour + 12, min, 0);
        nextPrayerNames[countNextPrayer] = "DHUHR";
        iqamahOrTimes[countNextPrayer] = "iqamah in";
        countNextPrayer++;

        tempPrayer = todayPrayer.getAsrTime();
        hour = Integer.parseInt(tempPrayer.substring(0, tempPrayer.indexOf(":")));
        min = Integer.parseInt(tempPrayer.substring(tempPrayer.indexOf(":") + 1, tempPrayer.length()));
        nextPrayerTimes[countNextPrayer] = new LocalTime(hour + 12, min, 0);
        nextPrayerNames[countNextPrayer] = "ASR";
        iqamahOrTimes[countNextPrayer] = "time in";
        countNextPrayer++;

        tempPrayer = todayPrayer.getAsrIqamah();
        hour = Integer.parseInt(tempPrayer.substring(0, tempPrayer.indexOf(":")));
        min = Integer.parseInt(tempPrayer.substring(tempPrayer.indexOf(":") + 1, tempPrayer.length()));
        nextPrayerTimes[countNextPrayer] = new LocalTime(hour + 12, min, 0);
        nextPrayerNames[countNextPrayer] = "ASR";
        iqamahOrTimes[countNextPrayer] = "iqamah in";
        countNextPrayer++;

        tempPrayer = todayPrayer.getMaghribTime();
        hour = Integer.parseInt(tempPrayer.substring(0, tempPrayer.indexOf(":")));
        min = Integer.parseInt(tempPrayer.substring(tempPrayer.indexOf(":") + 1, tempPrayer.length()));
        nextPrayerTimes[countNextPrayer] = new LocalTime(hour + 12, min, 0);
        nextPrayerNames[countNextPrayer] = "MAGHRIB";
        iqamahOrTimes[countNextPrayer] = "time in";
        countNextPrayer++;

        tempPrayer = todayPrayer.getMaghribIqamah();
        hour = Integer.parseInt(tempPrayer.substring(0, tempPrayer.indexOf(":")));
        min = Integer.parseInt(tempPrayer.substring(tempPrayer.indexOf(":") + 1, tempPrayer.length()));
        nextPrayerTimes[countNextPrayer] = new LocalTime(hour + 12, min, 0);
        nextPrayerNames[countNextPrayer] = "MAGHRIB";
        iqamahOrTimes[countNextPrayer] = "iqamah in";
        countNextPrayer++;

        tempPrayer = todayPrayer.getIshaTime();
        hour = Integer.parseInt(tempPrayer.substring(0, tempPrayer.indexOf(":")));
        min = Integer.parseInt(tempPrayer.substring(tempPrayer.indexOf(":") + 1, tempPrayer.length()));
        nextPrayerTimes[countNextPrayer] = new LocalTime(hour + 12, min, 0);
        nextPrayerNames[countNextPrayer] = "ISHA";
        iqamahOrTimes[countNextPrayer] = "time in";
        countNextPrayer++;

        tempPrayer = todayPrayer.getIshaIqamah();
        hour = Integer.parseInt(tempPrayer.substring(0, tempPrayer.indexOf(":")));
        min = Integer.parseInt(tempPrayer.substring(tempPrayer.indexOf(":") + 1, tempPrayer.length()));
        nextPrayerTimes[countNextPrayer] = new LocalTime(hour + 12, min, 0);
        nextPrayerNames[countNextPrayer] = "ISHA";
        iqamahOrTimes[countNextPrayer] = "iqamah in";
        countNextPrayer++;


        for (int i = 0; i < nextPrayerTimes.length; i++) {
            //Log.d("Time","Time Before IF: " + time.toString());
            if (time.isBefore(nextPrayerTimes[i])) {
                //Log.d("Time","Time: " + time.toString());
                //Log.d("Time","Next: " + nextPrayerTimes[i].toString());
                nextPrayerTimeMin = nextPrayerTimes[i].getMinuteOfHour() - time.getMinuteOfHour();
                nextPrayerTimeSeconds = nextPrayerTimes[i].getSecondOfMinute() - time.getSecondOfMinute();
                nextPrayerTimeHours = nextPrayerTimes[i].getHourOfDay() - time.getHourOfDay();
                nextPrayerNameIndex = i;
                break;
            }

        }

        if (nextPrayerTimeHours == 0 && nextPrayerTimeMin == 0) {

            PrayersToday tomorrowPrayer = monthlyPrayer[position];
            //Log.d("Time","Last If: " + tomorrowPrayer.getFajrTime());
            String fajrPrayer = tomorrowPrayer.getFajrTime();
            int hourTom = Integer.parseInt(fajrPrayer.substring(0, fajrPrayer.indexOf(":")));
            int minTom = Integer.parseInt(fajrPrayer.substring(fajrPrayer.indexOf(":") + 1, fajrPrayer.length()));
            LocalTime nextDayFajrTime = new LocalTime(hourTom, minTom, 0);
            nextPrayerTimeMin = nextDayFajrTime.getMinuteOfHour() + time.getMinuteOfHour();
            nextPrayerTimeSeconds = nextDayFajrTime.getSecondOfMinute() + time.getSecondOfMinute();
            nextPrayerTimeHours = nextDayFajrTime.getHourOfDay() + (24 - time.getHourOfDay());
            nextPrayerNameIndex = 0;

        }
    }

}
