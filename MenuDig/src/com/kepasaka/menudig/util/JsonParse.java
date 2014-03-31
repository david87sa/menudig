package com.kepasaka.menudig.util;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.kepasaka.menudig.CategoryGrid;
import com.kepasaka.menudig.ImageAdapter;
import com.kepasaka.menudig.R;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class JsonParse extends AsyncTask<String, String, JSONObject> {
	
	GridView grid;
	  TextView name;
	  TextView address;
	  TextView label;
	  
	  JSONArray android = null;
	  Activity activity = null;
	  
	//JSON Node Names
	  private static final String ROOT = "android";
	  private static final String TAG_NAME = "name";
	  private static final String TAG_ADDRESS = "description";
	  private static final String TAG_LABEL = "photo";
	  
	  ArrayList<HashMap<String, String>> oslist = new ArrayList<HashMap<String, String>>();
	  
	  
    private ProgressDialog pDialog;
    
    public JsonParse(Activity act) {
		this.activity=act;
	}
    
   @Override
     protected void onPreExecute() {
         super.onPreExecute();
         name = (TextView)activity.findViewById(R.id.name);
		 address = (TextView)activity.findViewById(R.id.address);
		 label = (TextView)activity.findViewById(R.id.label);
         pDialog = new ProgressDialog(activity);
         pDialog.setMessage(activity.getResources().getString(R.string.getting_commerce));
         pDialog.setIndeterminate(false);
         pDialog.setCancelable(true);
         pDialog.show();
   }
   @Override
     protected JSONObject doInBackground(String... args) {
     JsonParser jParser = new JsonParser();
     // Getting JSON from URL
     JSONObject json = jParser.getJSONFromUrl("http://kweizar.kepasaka.com/newpage/MenuDig/web/app_dev.php/Menu/Show/3");
     return json;
   }
    @Override
      protected void onPostExecute(JSONObject json) {
      pDialog.dismiss();
      try {

    	  JSONArray categories = json.getJSONArray("categories");
		for (int i=0;i<categories.length();i++) {
			
			JSONObject category = categories.getJSONObject(i);
			
			String name = category.getString(TAG_NAME);
	        String address = category.getString(TAG_ADDRESS);
	        
	     // Adding value HashMap key => value
	         HashMap<String, String> map = new HashMap<String, String>();
	         map.put(TAG_NAME, name);
	         map.put(TAG_ADDRESS, address);
	         
	         oslist.add(map);
		}
        grid=(GridView)activity.findViewById(R.id.categoriesgrid);
        ImageAdapter adapter = new ImageAdapter(activity,oslist);
        grid.setAdapter(adapter);
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                	// update the main content by replacing fragments
                    FragmentManager fragmentManager = ((ActionBarActivity)activity).getSupportFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.container, CategoryGrid.newInstance(position + 1))
                            .commit();
                }
            });
     } catch (JSONException e) {
       e.printStackTrace();
     }
    }
 }