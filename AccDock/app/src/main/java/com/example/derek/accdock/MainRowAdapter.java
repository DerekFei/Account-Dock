package com.example.derek.accdock;

/**
 * Created by Derek on 2015-08-07.
 */
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.SimpleAdapter;

import java.util.List;
import java.util.Map;

;

public class MainRowAdapter extends SimpleAdapter{
    public MainRowAdapter(Context context, List<? extends Map<String,?>> data, int resource, String[] from, int[] to) {
        super(context, data, R.layout.main_acc_row2, from, to);
    }


}
