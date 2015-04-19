package com.teamidentiti.fastvturesults.Adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.teamidentiti.fastvturesults.ProvidersAndHelpers.TableContract;
import com.teamidentiti.fastvturesults.R;

/**
 * Created by Sravan on 2/9/2015.
 */
public class SemestersTextAdapter extends CursorAdapter {
    Context c;

    public SemestersTextAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.semesters_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        view.setTag(viewHolder);
        c=context;
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder viewHolder = (ViewHolder)view.getTag();
        viewHolder.t1.setText("Semester "+cursor.getString(cursor.getColumnIndex(TableContract.SemestersContract.COLUMN_SEMESTER)));
        viewHolder.t2.setText(capitalize(cursor.getString(cursor.getColumnIndex(TableContract.SemestersContract.COLUMN_CLASS))));
        viewHolder.t3.setText(cursor.getString(cursor.getColumnIndex(TableContract.SemestersContract.COLUMN_PERCENTAGE)));
        if(cursor.getString(cursor.getColumnIndex(TableContract.SemestersContract.COLUMN_CLASS)).equals("FAIL"))
            viewHolder.i.setBackgroundColor(c.getResources().getColor(R.color.colorSubjectFail));
        else
            viewHolder.i.setBackgroundColor(c.getResources().getColor(R.color.colorSubjectPass));
    }

    private String capitalize(String s) {
        s=s.toLowerCase();
        char oldChar=s.charAt(0);
        char firstChar=Character.toUpperCase(oldChar);
        s=s.replaceFirst(oldChar+"", firstChar+"");
        return s;
    }

    private class ViewHolder {
        private final TextView t1;
        private final TextView t2;
        private final TextView t3;
        private final ImageView i;

        private ViewHolder(View view) {
            t1=(TextView)view.findViewById(R.id.text1);
            t2=(TextView)view.findViewById(R.id.text2);
            t3=(TextView)view.findViewById(R.id.text3);
            i=(ImageView)view.findViewById(R.id.resultColor);
        }
    }
}