package com.tqrapps.friscocenter.activities;

import android.app.ProgressDialog;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.SaveCallback;
import com.tqrapps.friscocenter.R;

public class SuggestionsActivity extends ActionBarActivity {

    EditText mName,mMessage;
    Button mSend;
    TextView mThankYouText;
    ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggestions);
        Toolbar toolbar= (Toolbar) findViewById(R.id.app_bar);
        try {
            setSupportActionBar(toolbar);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        } catch (Throwable t) {

        }

        mProgressDialog = new ProgressDialog(this);
        mName = (EditText) findViewById(R.id.nameEditText);
        mMessage = (EditText) findViewById(R.id.messageEditText);
        mSend = (Button) findViewById(R.id.sendButton);
        mSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProgressDialog.show();
                String name = mName.getText().toString();
                String message = mMessage.getText().toString();
                if(!name.equals("") && !message.equals("")){
                    ParseObject suggestion = new ParseObject("Suggestion");
                    suggestion.put("name", name);
                    suggestion.put("message", message);
                    suggestion.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            mProgressDialog.dismiss();
                            if(e == null){
                                mThankYouText.setText("Thank You for your message!");
                                mThankYouText.setTextColor(getResources().getColor(R.color.colorPrimary));
                                mThankYouText.setVisibility(View.VISIBLE);
                                mName.setText("");
                                mMessage.setText("");
                            }
                            else{
                                mThankYouText.setText(e.getMessage());
                                mThankYouText.setTextColor(getResources().getColor(R.color.red));
                                mThankYouText.setVisibility(View.VISIBLE);
                            }
                        }
                    });
                }
                else{
                    mThankYouText.setText("Must Enter Name and Message");
                    mThankYouText.setTextColor(getResources().getColor(R.color.red));
                    mThankYouText.setVisibility(View.VISIBLE);
                }
            }
        });
        mThankYouText = (TextView) findViewById(R.id.thankyouText);





    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        return true;
    }
}
