package com.teamidentiti.fastvturesults.Services;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Intent;

import com.teamidentiti.fastvturesults.ProvidersAndHelpers.Provider;
import com.teamidentiti.fastvturesults.ProvidersAndHelpers.TableContract;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * Created by Sravan on 3/22/2015.
 */
public class SemestersService extends IntentService {
    String USN;

    public SemestersService() {
        super("Fast VTU Results");
    }

    public SemestersService(String name) {
        super("Fast VTU Results");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String url=intent.getStringExtra("URL");
        USN=intent.getStringExtra("USN");
        fetchData(url);
    }

    private void fetchData(String url) {
        try {
            Document doc = Jsoup.connect(url).userAgent("Chrome/41.0.2228.0").get();
            Elements table=doc.getElementById("scell").getElementsByClass("table-hover");
            Elements tableRows=table.select("tr");
            tableRows.remove(0);
            int rows=tableRows.size();
            Elements tableCells=tableRows.select("td");

            parseData(rows, tableCells);
        } catch (IOException e) {}
    }

    private void parseData(int rows, Elements tableCells) {
        try {
            for(int i=0;i<rows;i++) {
                int toRemove=i*3;
                tableCells.remove(toRemove + 1);
                tableCells.remove(toRemove + 1);
                tableCells.remove(toRemove + 3);
                tableCells.remove(toRemove + 3);
                tableCells.remove(toRemove + 3);
            }

            insertDb(rows, tableCells);
        } catch(Exception e) {}
    }

    private void insertDb(int rows, Elements tableCells) {
        ContentValues values;
        int j=0;
        // i<rows, refer SemestersDbHelper
        for(int i=0;i<rows;i++) {
            values=new ContentValues();
            values.put(TableContract.SemestersContract.COLUMN_USN, USN);
            values.put(TableContract.SemestersContract.COLUMN_SEMESTER, tableCells.get(j++).text());
            values.put(TableContract.SemestersContract.COLUMN_CLASS, tableCells.get(j++).text());
            values.put(TableContract.SemestersContract.COLUMN_PERCENTAGE, tableCells.get(j++).text());
            getApplicationContext().getContentResolver().insert(Provider.CONTENT_URI_SEMESTERS, values);
        }
    }
}
