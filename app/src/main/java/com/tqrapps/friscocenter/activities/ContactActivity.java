package com.tqrapps.friscocenter.activities;


import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.share.widget.LikeView;
import com.gc.materialdesign.views.ButtonRectangle;
import com.tqrapps.friscocenter.R;


public class ContactActivity extends ActionBarActivity {
    TextView address, title;
    ButtonRectangle call, email_masjid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        Toolbar toolbar= (Toolbar) findViewById(R.id.app_bar);
        try {
            setSupportActionBar(toolbar);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        } catch (Throwable t) {

        }

        LikeView likeView = (LikeView) findViewById(R.id.like_view);
        likeView.setObjectIdAndType(
                "https://www.facebook.com/FriscoMasjid",
                LikeView.ObjectType.PAGE);

        address = (TextView) findViewById(R.id.address);
        address.setText(Html.fromHtml("<h4>ICF</h4><h4>11137 Frisco Street<br>Frisco, TX 75034</h4>"));

        title = (TextView) findViewById(R.id.title_contact);
        Typeface font = Typeface.createFromAsset(this.getAssets(), "fonts/SkaterGirlsRock.ttf");
        title.setTypeface(font);

        call = (ButtonRectangle) findViewById(R.id.call_btn);
        email_masjid = (ButtonRectangle) findViewById(R.id.email_button);

        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:462524532"));
                startActivity(callIntent);
            }
        });
        email_masjid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto", "Shura@friscomasjid.org", null));
                intent.putExtra(Intent.EXTRA_SUBJECT, "Contact from Android App");
                intent.putExtra(Intent.EXTRA_TEXT, "Sent from Frisco Masjid's Android App");
                startActivity(Intent.createChooser(intent, "Choose an Email client :"));
            }
        });

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