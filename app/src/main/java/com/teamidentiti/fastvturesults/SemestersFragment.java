package com.teamidentiti.fastvturesults;


import android.app.Fragment;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.teamidentiti.fastvturesults.ProvidersAndHelpers.Provider;
import com.teamidentiti.fastvturesults.ProvidersAndHelpers.TableContract;
import com.teamidentiti.fastvturesults.Services.SemestersService;

import org.jsoup.select.Elements;

public class SemestersFragment extends Fragment implements AdapterView.OnItemClickListener, LoaderManager.LoaderCallbacks<Cursor> {
    private ListView list;
    private TextView error;
    private ProgressBar progress;

    private SemestersTextAdapter adapter;

    private String selection= TableContract.SemestersContract.COLUMN_USN+"=?";
    private String[] selectionArgs={MainActivity2.USN};

    private int rows=0;
    private Elements tableCells;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_semester, null);
        list=(ListView)view.findViewById(R.id.list);
        list.setVisibility(View.GONE);
        list.setOnItemClickListener(this);
        progress=(ProgressBar)view.findViewById(R.id.progress);
        error=(TextView)view.findViewById(R.id.error);
        error.setVisibility(View.GONE);

        adapter=new SemestersTextAdapter(getActivity(), null, 0);
        list.setAdapter(adapter);

        getActivity().getLoaderManager().initLoader(0, null, this);
        return view;
    }

    @Override
    public void onDestroyView () {
        super.onDestroyView();
        // Destroy loader so as to load fresh data
        getActivity().getLoaderManager().destroyLoader(0);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getActivity(), Provider.CONTENT_URI_SEMESTERS, null, selection, selectionArgs, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        // If no results are returned, it means there is no data in the database
        if(data.getCount()==0)
            makeURL();
        else {
            adapter.swapCursor(data);
            animate();
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.swapCursor(null);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        TextView semester=(TextView)view.findViewById(R.id.text1);
        switch(semester.getText().toString().replace("Semester ", "")) {
            case "1": MainActivity2.SEM="1"; break;
            case "2": MainActivity2.SEM="2"; break;
            case "3": MainActivity2.SEM="3"; break;
            case "4": MainActivity2.SEM="4"; break;
            case "5": MainActivity2.SEM="5"; break;
            case "6": MainActivity2.SEM="6"; break;
            case "7": MainActivity2.SEM="7"; break;
            case "8": MainActivity2.SEM="8"; break;
        }
        MainActivity2.semItemClickY=(view.getTop()+view.getBottom())/2;
        if(!MainActivity2.twoPane) {
            getFragmentManager().beginTransaction()
                    .setCustomAnimations(R.animator.fade_in, R.animator.fade_out,
                            R.animator.fade_in, R.animator.fade_out)
                    .replace(R.id.container, new ResultsFragment()).addToBackStack(null).commit();
        }
        else
            getFragmentManager().beginTransaction()
                    .setCustomAnimations(R.animator.fade_in, R.animator.fade_out,
                            R.animator.fade_in, R.animator.fade_out)
                    .replace(R.id.twoPane, new ResultsFragment()).commit();
    }

    private void makeURL() {
        String URL=MainActivity2.URL+MainActivity2.USN;
        Intent i=new Intent(getActivity(), SemestersService.class);
        i.putExtra("URL", URL);
        i.putExtra("USN", MainActivity2.USN);
        i.putExtra("SEM", MainActivity2.SEM);
        getActivity().startService(i);
    }

    private void animate() {
        progress.setVisibility(View.GONE);
        list.setVisibility(View.VISIBLE);
    }
}
