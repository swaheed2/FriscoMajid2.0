package com.tqrapps.friscocenter.activities;


import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;


import hotchemi.android.rate.AppRate;
import hotchemi.android.rate.OnClickButtonListener;
import it.neokree.materialtabs.MaterialTab;
import it.neokree.materialtabs.MaterialTabHost;
import it.neokree.materialtabs.MaterialTabListener;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.parse.ParseObject;
import com.tqrapps.friscocenter.fragments.EventsFragment;
import com.tqrapps.friscocenter.fragments.FacebookFragment;
import com.tqrapps.friscocenter.fragments.FacebookYouth;
import com.tqrapps.friscocenter.fragments.PrayerFragment;
import com.tqrapps.friscocenter.fragments.NavigationDrawerFragment;
import com.tqrapps.friscocenter.R;
import com.tqrapps.friscocenter.logging.L;


public class MainActivity extends ActionBarActivity implements MaterialTabListener{

    public static final int MOVIES_SEARCH_RESULTS = 0;
    public static final int EVENT_TAB = 1;
    public static final int MOVIES_HITS = 2;
    public static final int MOVIES_UPCOMING = 3;
    public static final int TAB_COUNT = 4;
    private Toolbar toolbar;
    private MaterialTabHost tabHost;
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_main);


        AppRate.with(this)
                .setLaunchTimes(3) // default 10
                .setInstallDays(1) // default 10, 0 means install day.
                .setRemindInterval(2) // default 1
                .setShowNeutralButton(true) // default true
                .setDebug(false) // default false
                .setOnClickButtonListener(new OnClickButtonListener() { // callback listener.
                    @Override
                    public void onClickButton(int which) {
                        //Log.d(MainActivity.class.getName(), Integer.toString(which));
                    }
                })
                .monitor();

        toolbar = (Toolbar) findViewById(R.id.app_bar);

        try {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        } catch (Throwable t) {

        }
        NavigationDrawerFragment drawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), toolbar);


        tabHost = (MaterialTabHost) findViewById(R.id.materialTabHost);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setOffscreenPageLimit(4);

        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        viewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                tabHost.setSelectedNavigationItem(position);

            }
        });
        for (int i = 0; i < adapter.getCount(); i++) {
            tabHost.addTab(
                    tabHost.newTab()
                            .setIcon(adapter.getIcon(i))
                            .setTabListener(this));
        }
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

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onTabSelected(MaterialTab materialTab) {
        viewPager.setCurrentItem(materialTab.getPosition());
    }


    @Override
    public void onTabReselected(MaterialTab materialTab) {
    }


    @Override
    public void onTabUnselected(MaterialTab materialTab) {
    }




    private class ViewPagerAdapter extends FragmentStatePagerAdapter {


        int icons[] = {R.drawable.ic_action_home,R.drawable.events_icon,
                R.drawable.ic_action_articles,
                R.drawable.ic_action_personal,};

        FragmentManager fragmentManager;

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
            fragmentManager = fm;
        }

        public Fragment getItem(int num) {
            Fragment fragment = null;
            L.m("getItem called for " + num);
            switch (num) {
                case MOVIES_SEARCH_RESULTS:

                    fragment = PrayerFragment.newInstance("", "");
                    break;
                case EVENT_TAB:
                    fragment = EventsFragment.newInstance("", "");
                    break;
                case MOVIES_HITS:
                    fragment = FacebookFragment.newInstance("", "");
                    break;
                case MOVIES_UPCOMING:
                    fragment = FacebookYouth.newInstance("", "");
                    break;
            }
            return fragment;

        }

        @Override
        public int getCount() {
            return TAB_COUNT;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return getResources().getStringArray(R.array.tabs)[position];
        }

        private Drawable getIcon(int position) {
            return getResources().getDrawable(icons[position]);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Logs 'install' and 'app activate' App Events.
        AppEventsLogger.activateApp(this);
    }

    @Override
    protected void onPause() {
        super.onPause();

        // Logs 'app deactivate' App Event.
        AppEventsLogger.deactivateApp(this);
    }
} 