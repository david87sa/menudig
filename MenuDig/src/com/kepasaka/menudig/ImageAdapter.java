package com.kepasaka.menudig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class ImageAdapter extends BaseAdapter {
    private Context mContext;
    private List<HashMap<String, String>> content = new ArrayList<HashMap<String,String>>();

    public ImageAdapter(Context c,ArrayList<HashMap<String,String>>items) {
        mContext = c;
        this.content=items;
    }

    public int getCount() {
        return this.content.size();
    }

    public Object getItem(int position) {
        return this.content.get(position);
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder view;
        if (convertView == null) {  // if it's not recycled, initialize some attributes
        	convertView = LayoutInflater.from(mContext).inflate(R.layout.gridlist_item, null);
        	
        	view=new ViewHolder();
        	view.name = (TextView)convertView.findViewById(R.id.categoryName);
            view.description = (TextView)convertView.findViewById(R.id.categoryDescription);
            view.icon = (ImageView)convertView.findViewById(R.id.categoryIcon);
            
            convertView.setTag(view);
            
        } else {
            view = (ViewHolder) convertView.getTag();
        }

        view.name.setText(this.content.get(position).get("name"));
        view.description.setText(this.content.get(position).get("description"));
        view.icon.setImageResource(R.drawable.ic_launcher);
        return convertView;
    }

    public static class ViewHolder
    {
        public ImageView icon;
        public TextView name;
        public TextView description;
    }
}
