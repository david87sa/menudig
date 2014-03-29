package com.kepasaka.menudig.util;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import com.kepasaka.menudig.MainActivity;

import android.R;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class JsonParse extends AsyncTask<String, String, JSONObject> {
	
	ListView list;
	  TextView name;
	  TextView address;
	  TextView label;
	  
	  JSONArray android = null;
	  Activity activity = null;
	  
	//JSON Node Names
	  private static final String TAG_OS = "android";
	  private static final String TAG_VER = "ver";
	  private static final String TAG_NAME = "name";
	  private static final String TAG_API = "api";
	  
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
         pDialog.setMessage("Getting Data ...");
         pDialog.setIndeterminate(false);
         pDialog.setCancelable(true);
         pDialog.show();
   }
   @Override
     protected JSONObject doInBackground(String... args) {
     JsonParser jParser = new JsonParser();
     // Getting JSON from URL
     JSONObject json = jParser.getJSONFromUrl("");
     return json;
   }
    @Override
      protected void onPostExecute(JSONObject json) {
      pDialog.dismiss();
      try {
         // Getting JSON Array from URL
         android = json.getJSONArray(TAG_OS);
         for(int i = 0; i < android.length(); i++){
         JSONObject c = android.getJSONObject(i);
         // Storing  JSON item in a Variable
         String ver = c.getString(TAG_VER);
         String name = c.getString(TAG_NAME);
         String api = c.getString(TAG_API);
         // Adding value HashMap key => value
         HashMap<String, String> map = new HashMap<String, String>();
         map.put(TAG_VER, ver);
         map.put(TAG_NAME, name);
         map.put(TAG_API, api);
         oslist.add(map);
         list=(ListView)activity.findViewById(R.id.list);
         ListAdapter adapter = new SimpleAdapter(activity, oslist,
             R.layout.list_content,
             new String[] { TAG_VER,TAG_NAME, TAG_API }, new int[] {
                 R.id.vers,R.id.name, R.id.api});
         list.setAdapter(adapter);
         list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                 @Override
                 public void onItemClick(AdapterView<?> parent, View view,
                                         int position, long id) {
                     Toast.makeText(MainActivity.this, "You Clicked at "+oslist.get(+position).get("name"), Toast.LENGTH_SHORT).show();
                 }
             });
         }
     } catch (JSONException e) {
       e.printStackTrace();
     }
    }
 }