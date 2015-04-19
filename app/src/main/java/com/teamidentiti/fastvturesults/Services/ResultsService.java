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
public class ResultsService extends IntentService {
    String headerElement1;
    String headerElement2;
    String headerElement3;
    String headerElement4;
    String USN;
    String SEM;

    public ResultsService() {
        super("Fast VTU Results");
    }
    public ResultsService(String name) {
        super("Fast VTU Results");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        USN=intent.getStringExtra("USN");
        SEM=intent.getStringExtra("SEM");
        String URL=intent.getStringExtra("URL");
        fetchData(URL);
    }

    private void fetchData(String URL) {
        try {
            Document doc = Jsoup.connect(URL).userAgent("Chrome/41.0.2228.0").get();
            // Get data for header element that displays name, attempts etc
            Elements headers = doc.getElementsByClass("text-center");
            headerElement1 = doc.title();
            headers = headers.select("p");
            headerElement2 = headers.get(0).text();
            headerElement3 = headers.get(1).text();
            headerElement4 = headers.get(2).text();

            // Get results data
            Elements table = doc.getElementsByClass("table-hover");
            Elements tableRows = table.select("tr");
            tableRows.remove(0);
            int rows = tableRows.size();
            Elements tableCells = tableRows.select("td");

            parseData(rows, tableCells);
        } catch (IOException e) {}
    }

    private void parseData(int rows, Elements tableCells) {
        try {
            // Remove last "Analysis" column
            for (int i = 0; i < rows; i++) {
                int toRemove = i * 6;
                tableCells.remove(toRemove + 6);
            }

            // Store data in database
            insertDb(rows, tableCells);
        } catch (Exception e) {}
    }

    private void insertDb(int rows, Elements tableCells) {
        ContentValues values;
        int j=0;
        // i<rows, refer ResultsDbHelper
        for(int i=0;i<rows;i++) {
            values=new ContentValues();
            values.put(TableContract.ResultsContract.COLUMN_USN, USN);
            values.put(TableContract.ResultsContract.COLUMN_SEMESTER, SEM);
            values.put(TableContract.ResultsContract.COLUMN_HEADER1, headerElement1);
            values.put(TableContract.ResultsContract.COLUMN_HEADER2, headerElement2);
            values.put(TableContract.ResultsContract.COLUMN_HEADER3, headerElement3);
            values.put(TableContract.ResultsContract.COLUMN_HEADER4, headerElement4);
            values.put(TableContract.ResultsContract.COLUMN_SUBJECT, tableCells.get(j++).text());
            values.put(TableContract.ResultsContract.COLUMN_CODE, tableCells.get(j++).text());
            values.put(TableContract.ResultsContract.COLUMN_INT, tableCells.get(j++).text());
            values.put(TableContract.ResultsContract.COLUMN_EXT, tableCells.get(j++).text());
            values.put(TableContract.ResultsContract.COLUMN_TOT, tableCells.get(j++).text());
            values.put(TableContract.ResultsContract.COLUMN_RES, tableCells.get(j++).text());
            getApplicationContext().getContentResolver().insert(Provider.CONTENT_URI_RESULTS, values);
        }
    }
}
