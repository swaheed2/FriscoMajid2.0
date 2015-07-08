package com.tqrapps.friscocenter.activities;


import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.tqrapps.friscocenter.R;


public class ImamActivity extends ActionBarActivity {
    TextView imam_about, title;
    Button call, email_masjid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imam);
        Toolbar toolbar= (Toolbar) findViewById(R.id.app_bar);
        try {
            setSupportActionBar(toolbar);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        } catch (Throwable t) {

        }

        Typeface font = Typeface.createFromAsset(this.getAssets(), "fonts/SkaterGirlsRock.ttf");
        imam_about = (TextView)  findViewById(R.id.imam_about);
        imam_about.setText(Html.fromHtml("Imam Dr. Zafar Ali Anjum holds a Doctorate and Masters Degree in Arabic Literature from Aligarh Muslim University, India. He also holds Alim and Fazil Degrees from the reputed Jame ul-Uloom Furqania, Rampur, India and completed a Nine Year course in Islamic Shari`ah with the Ijazah in Sahih Sittah. He is also Hafizul-Quran and holds an Ijazah in Tajweed.\n<br><br>" +
                "Imam Dr. Anjum has taught Arabic, Tajweed, Tafsir, Fiqh, and Philosophy in Islamic institutions both in India and the United States. Dr. Anjum was born and raised in India and came to the United States in 1996, serving as Director and Imam for Islamic Centers in Florida and later Nevada. He also served as  Director of religious studies at IQA. Imam Zafar Anjum joined Islamic Center of Frisco in January 2010"));

        title = (TextView)  findViewById(R.id.title_contact);
        title.setTypeface(font);

        call = (Button)  findViewById(R.id.call_btn);
        email_masjid = (Button)  findViewById(R.id.email_button);

        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:4698794122"));
                startActivity(callIntent);
            }
        });
        email_masjid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto", "imam@friscomasjid.org", null));
                intent.putExtra(Intent.EXTRA_SUBJECT, "Contact from Android App");
                intent.putExtra(Intent.EXTRA_TEXT, "Sent from Frisco Masjid's Android App");
                startActivity(Intent.createChooser(intent, "Choose an Email client :"));
            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will 
        // automatically handle clicks on the Home/Up button, so long 
        // as you specify a parent activity in AndroidManifest.xml. 
        int id = item.getItemId();

        if(id==android.R.id.home){
            NavUtils.navigateUpFromSameTask(this);
        }
        return super.onOptionsItemSelected(item);
    }

} 