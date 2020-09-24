package org.example.yj.draggridtag.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.example.yj.draggridtag.R;

import java.util.List;

public class DragGridAdapter extends ArrayAdapter<String> {
    private List<String> list;
    private Context context;
    public DragGridAdapter(Context context, List<String> objects) {
        super(context, 0, objects);
        this.list = objects;
        this.context  = context;
    }

    public List<String> getList() {
        return list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.drag_grid_item, null);
        }

        try {

            TextView TextView = (TextView) view.findViewById(R.id.drag_grid_item);
            TextView.setText(list.get(position));
        } catch (SecurityException e) {
            e.printStackTrace();

        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }

        return view;
    }
}