package com.teamidentiti.fastvturesults;

import android.animation.Animator;
import android.app.Fragment;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.teamidentiti.fastvturesults.ProvidersAndHelpers.Provider;
import com.teamidentiti.fastvturesults.ProvidersAndHelpers.TableContract;
import com.teamidentiti.fastvturesults.Services.ResultsService;

/**
 * Created by Sravan on 3/15/2015.
 */
public class ResultsFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    private View rootView;
    private TextView header;
    private TextView error;
    private ListView list;
    private ProgressBar progress;

    private ResultsTextAdapter adapter;

    private int rows;
    private String headerElement1;
    private String headerElement2;
    private String headerElement3;
    private String headerElement4;
    private final String selection= TableContract.ResultsContract.COLUMN_USN + "=? AND "+TableContract.ResultsContract.COLUMN_SEMESTER + "=?";
    private final String[] selectionArgs={MainActivity2.USN, MainActivity2.SEM};

    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_results, null);
        header=(TextView)view.findViewById(R.id.header);
        list=(ListView)view.findViewById(R.id.list);
        progress=(ProgressBar)view.findViewById(R.id.progress);
        error=(TextView)view.findViewById(R.id.error);
        header.setVisibility(View.GONE);
        list.setVisibility(View.GONE);
        error.setVisibility(View.GONE);

        // Create an adapter without a cursor
        adapter = new ResultsTextAdapter(getActivity(), null, 0);
        list.setAdapter(adapter);

        // Initialize loader
        getActivity().getLoaderManager().initLoader(1, null, this);
        rootView=view;
        return view;
    }

    @Override
    public void onDestroyView () {
        super.onDestroyView();
        // Destroy loader so as to load fresh data
        getActivity().getLoaderManager().destroyLoader(1);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getActivity(), Provider.CONTENT_URI_RESULTS, null, selection, selectionArgs, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        // If no results are returned, it means there is no data in the database
        if(data.getCount()==0) {
            makeURL();
        }
        else {
            adapter.swapCursor(data);
            data.moveToFirst();
            header.setText(data.getString(data.getColumnIndex(TableContract.ResultsContract.COLUMN_HEADER1)) + " | " +
                    data.getString(data.getColumnIndex(TableContract.ResultsContract.COLUMN_HEADER2)) + " | " +
                    data.getString(data.getColumnIndex(TableContract.ResultsContract.COLUMN_HEADER3)) + " | " +
                    data.getString(data.getColumnIndex(TableContract.ResultsContract.COLUMN_HEADER4)));
            animate();
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.swapCursor(null);
    }

    private void makeURL() {
        String BASE_URL="http://www.fastvturesults.com/result/";
        String URL="";
        switch(MainActivity2.SEM) {
            case "1": URL=BASE_URL+MainActivity2.USN+"/180012113"; break;
            case "2": URL=BASE_URL+MainActivity2.USN+"/360024225"; break;
            case "3": URL=BASE_URL+MainActivity2.USN+"/540036337"; break;
            case "4": URL=BASE_URL+MainActivity2.USN+"/720048449"; break;
            case "5": URL=BASE_URL+MainActivity2.USN+"/900060561"; break;
            case "6": URL=BASE_URL+MainActivity2.USN+"/1080072673"; break;
            case "7": URL=BASE_URL+MainActivity2.USN+"/1260084785"; break;
            case "8": URL=BASE_URL+MainActivity2.USN+"/1440096897"; break;
        }
        Intent i=new Intent(getActivity(), ResultsService.class);
        i.putExtra("URL", URL);
        i.putExtra("USN", MainActivity2.USN);
        i.putExtra("SEM", MainActivity2.SEM);
        getActivity().startService(i);
    }

    private void animate() {
        progress.setVisibility(View.GONE);
        header.setVisibility(View.VISIBLE);
        list.setVisibility(View.VISIBLE);
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP) {
            Display display=getActivity().getWindowManager().getDefaultDisplay();
            Point size=new Point();
            display.getSize(size);
            int width = size.x;
            int height = size.y;
            int radius=height;
            int cx=width/2;
            Animator anim = ViewAnimationUtils.createCircularReveal(rootView, cx, MainActivity2.semItemClickY, 0, radius);
            anim.start();
        }
        else {
            list.setAlpha(0.0f);
            list.animate().alpha(1.0f).start();
        }
    }
}
