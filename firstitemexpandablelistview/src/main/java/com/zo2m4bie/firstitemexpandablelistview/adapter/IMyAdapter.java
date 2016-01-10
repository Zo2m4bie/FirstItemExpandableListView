package com.zo2m4bie.firstitemexpandablelistview.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

/**
 * Created by dima on 1/8/16.
 */
public abstract class IMyAdapter<T> extends ArrayAdapter<T> {

    public IMyAdapter(Context context, int resource) {
        super(context, resource);
    }

    public IMyAdapter(Context context, int resource, int textViewResourceId) {
        super(context, resource, textViewResourceId);
    }

    public IMyAdapter(Context context, int resource, T[] objects) {
        super(context, resource, objects);
    }

    public IMyAdapter(Context context, int resource, int textViewResourceId, T[] objects) {
        super(context, resource, textViewResourceId, objects);
    }

    public IMyAdapter(Context context, int resource, List<T> objects) {
        super(context, resource, objects);
    }

    public IMyAdapter(Context context, int resource, int textViewResourceId, List<T> objects) {
        super(context, resource, textViewResourceId, objects);
    }

    public View getView(int position, View convertView, ViewGroup parent, int percent) {

        return getView(position, convertView, parent);
    }
}
