package com.teamidentiti.fastvturesults;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.teamidentiti.fastvturesults.ProvidersAndHelpers.TableContract;

/**
 * Created by Sravan on 2/9/2015.
 */
public class ResultsTextAdapter<E> extends CursorAdapter {
    Context c;

    public ResultsTextAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.results_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        view.setTag(viewHolder);
        c=context;
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder viewHolder=(ViewHolder)view.getTag();
        viewHolder.t1.setText(cursor.getString(cursor.getColumnIndex(TableContract.ResultsContract.COLUMN_SUBJECT))+
                " | " + cursor.getString(cursor.getColumnIndex(TableContract.ResultsContract.COLUMN_CODE)));
        viewHolder.t2.setText(cursor.getString(cursor.getColumnIndex(TableContract.ResultsContract.COLUMN_INT)));
        viewHolder.t3.setText(cursor.getString(cursor.getColumnIndex(TableContract.ResultsContract.COLUMN_EXT)));
        viewHolder.t4.setText(cursor.getString(cursor.getColumnIndex(TableContract.ResultsContract.COLUMN_TOT)));
        viewHolder.t5.setText(cursor.getString(cursor.getColumnIndex(TableContract.ResultsContract.COLUMN_RES)));
        if(cursor.getString(cursor.getColumnIndex(TableContract.ResultsContract.COLUMN_RES)).equals("F"))
            viewHolder.i.setBackgroundColor(c.getResources().getColor(R.color.colorSubjectFail));
        else
            viewHolder.i.setBackgroundColor(c.getResources().getColor(R.color.colorSubjectPass));
    }

    private static class ViewHolder {
        private final TextView t1;
        private final TextView t2;
        private final TextView t3;
        private final TextView t4;
        private final TextView t5;
        private final ImageView i;

        private ViewHolder(View view) {
            t1 = ((TextView)view.findViewById(R.id.text1));
            t2 = ((TextView)view.findViewById(R.id.text2));
            t3 = ((TextView)view.findViewById(R.id.text3));
            t4 = ((TextView)view.findViewById(R.id.text4));
            t5 = ((TextView)view.findViewById(R.id.text5));
            i=(ImageView)view.findViewById(R.id.resultColor);
        }
    }
}