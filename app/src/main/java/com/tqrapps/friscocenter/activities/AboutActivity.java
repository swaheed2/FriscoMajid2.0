package com.tqrapps.friscocenter.activities;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.tqrapps.friscocenter.R;

public class AboutActivity extends ActionBarActivity {
    TextView about_text, about_title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        Toolbar toolbar= (Toolbar) findViewById(R.id.app_bar);

        try {
            setSupportActionBar(toolbar);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        } catch (Throwable t) {

        }
        about_text = (TextView) findViewById(R.id.about_text);
        about_title = (TextView) findViewById(R.id.about_title);

        about_text.setText(Html.fromHtml("Located approximately 27 miles north of downtown Dallas, the city of Frisco has a population of more than 100,000 people. It is one of fastest growing cities in the nation. Since 1990, Frisco’s population has grown by more than 450 percent. This growth also attracted a large number of Muslims families to Frisco.\n <br/><br/>" +
                "Since a Masjid is the center of community activities and education, its need was acutely felt by the Muslims living in Frisco. The Islamic Center of Frisco was established in May 2007 to fulfill community needs.\n<br/><br/>" +
                "\n" +
                "ICF not only provides prayer facilities for prayers, it also offers various Islamic education services including our successful naazra program and Sunday school, a vibrant youth group, educational seminars, Youth and Adult Education Classees, summer school, nikkah services, and Islamic counseling."));

        Typeface font = Typeface.createFromAsset(this.getAssets(), "fonts/SkaterGirlsRock.ttf");
        about_title.setTypeface(font);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_sub, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        //noinspection SimplifiableIfStatement
        if(id==android.R.id.home){
            NavUtils.navigateUpFromSameTask(this);
        }
        return super.onOptionsItemSelected(item);
    }
}
