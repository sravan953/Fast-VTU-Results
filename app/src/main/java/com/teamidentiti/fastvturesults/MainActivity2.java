package com.teamidentiti.fastvturesults;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity2 extends ActionBarActivity {
    static String USN;
    static String SEM;
    static String URL="http://www.fastvturesults.com/result/";
    static int semItemClickY;
    static boolean twoPane=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent i=getIntent();
        USN=i.getStringExtra("USN");

        setContentView(R.layout.activity_main2);
        setToolbar();

        if(findViewById(R.id.twoPane)==null) {
            if(savedInstanceState==null)
                getFragmentManager().beginTransaction().replace(R.id.container, new SemestersFragment()).commit();
        }
        else twoPane=true;
    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu) {
        getMenuInflater().inflate(R.menu.menu_share, menu);

        Intent i=new Intent(Intent.ACTION_SEND);
        String shareString="Check out my results! "+URL+USN;
        shareString+="\nDownload the Fast VTU Results app at - https://play.google.com/store/apps/details?id=com.teamidentiti.fastvturesults";
        i.setAction(Intent.ACTION_SEND);
        i.setType("text/plain");
        i.putExtra(Intent.EXTRA_TEXT, shareString);

        MenuItem item=menu.findItem(R.id.menu_item_share);
        ShareActionProvider p=(ShareActionProvider)MenuItemCompat.getActionProvider(item);
        p.setShareIntent(i);

        return super.onCreateOptionsMenu(menu);
    }

    private void setToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.custom_tool_bar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        }
    }
}