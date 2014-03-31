package com.kepasaka.menudig;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.support.v4.app.ListFragment;
import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class ProductGrid extends ListFragment {

    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";
    private static List<Map<String, String>>items = new ArrayList<Map<String,String>>();

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static ProductGrid newInstance(int sectionNumber,ArrayList<Map<String, String>>items) {
        ProductGrid fragment = new ProductGrid();
        Bundle args = new Bundle();
        ProductGrid.items = items;
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }
	public ProductGrid() {
		
	}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_product, container, false);
        
        ListView grid=(ListView)container.findViewById(R.id.productlist);
        ListAdapter adapter = new SimpleAdapter(this.getActivity(),items,R.layout.list_item,new String[]{"name","description","label"},new int[]{R.id.name,R.id.address,R.id.label});
        grid.setAdapter(adapter);
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(
                getArguments().getInt(ARG_SECTION_NUMBER));
    }


}
