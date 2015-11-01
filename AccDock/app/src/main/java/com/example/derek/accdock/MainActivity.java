package com.example.derek.accdock;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View;
import android.content.Intent;


import android.app.Activity;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


public class MainActivity extends Activity {

    private ListView accListView;
    String[] accName;
    String allAccName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try{
            //list and adapter
            setContentView(R.layout.activity_main);
            accListView = (ListView)findViewById(R.id.acclistView);
            //simple adapter

            SimpleAdapter adapter = new SimpleAdapter(
                    this,
                    getData(),
                    R.layout.main_acc_row2,
                    new String[]{"tag","name","balance","currency","date"},
                    new int[]{R.id.tagImg,R.id.nameText,R.id.balanceText,R.id.currencyText,R.id.dateText});


            accListView.setAdapter(adapter);

            //setListView On Item Clicked listener
            final Intent accDetail = new Intent(this,AccDetail.class);
            accListView.setOnItemClickListener(
                    new AdapterView.OnItemClickListener() {
                        public void onItemClick(AdapterView<?> parent, View view, int postion, long id) {
                            accDetail.putExtra("accName",accName[postion]);
                            finish();
                            startActivity(accDetail);
                        }
                    });

            //setListView On Item LongClicked listener
            final Intent Refresh = new Intent(this,MainActivity.class);
            accListView.setOnItemLongClickListener(
                    new AdapterView.OnItemLongClickListener(){
                        @Override
                        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                            //confirm dialog
                            final int POSITION_F=position;
                            new AlertDialog.Builder(MainActivity.this).setTitle(getResources().getString(R.string.mainDeleteContent))
                                    .setIcon(android.R.drawable.ic_dialog_alert)
                                    .setPositiveButton(getResources().getString(R.string.Yes), new DialogInterface.OnClickListener() {

                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            // After Clicked Yes
                                            //Delete Account in detail
                                            SharedPreferences sharedPref = getSharedPreferences(accName[POSITION_F], Context.MODE_PRIVATE);

                                            sharedPref.edit().clear().commit();
                                            //delete account from account name list
                                            SharedPreferences sharedPrefacc = getSharedPreferences("accName", Context.MODE_PRIVATE);
                                            SharedPreferences.Editor editor = sharedPrefacc.edit();
                                            //sharedPrefacc.edit().clear().commit();
                                            //get the new account name list from method accNameAfterDelete
                                            editor.putString("accName", accNameAfterDelete(accName[POSITION_F]));
                                            editor.apply();
                                            Toast.makeText(MainActivity.this,getResources().getString(R.string.Success), Toast.LENGTH_SHORT).show();
                                            finish();
                                            startActivity(Refresh);

                                        }
                                    })
                                    .setNegativeButton(getResources().getString(R.string.No), new DialogInterface.OnClickListener() {

                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            //Nothing happened after NO clicked
                                        }
                                    }).show();
                            return true;
                        }
                    }
            );

        }catch(Exception e){}



    }
    //Remove from list view
    private String accNameAfterDelete (String deleted){
       List<String> list=new LinkedList<String>();
    //add all names to list except the one has been deleted
        for(int i=0;i<accName.length;i++) {
            if (!accName[i].equals(deleted)) {
                list.add(accName[i]);
            }
        }
        //list to array
        String newAccName []={};
        newAccName=list.toArray(newAccName);
        //declare a new String to pass back
         String newAllAccName="";
        //array to a whole string
        for(int i=0;i<newAccName.length;i++){
            newAllAccName=newAllAccName+newAccName[i]+",";
        }
        return newAllAccName;

    }
    //add to list View
    private List<Map<String,Object>>getData(){
        List<Map<String,Object>> mData= new ArrayList<Map<String,Object>>();;
        SharedPreferences sharedPrefacc= getSharedPreferences("accName", Context.MODE_PRIVATE);
        allAccName=sharedPrefacc.getString("accName","");
        accName=allAccName.split(",");
        for(int i =0; i<accName.length; i++) {
            SharedPreferences sharedPref = getSharedPreferences(accName[i], Context.MODE_PRIVATE);
            Map<String,Object> item = new HashMap<String,Object>();
            //check if has at least one account
            if(!sharedPref.getString("accName","").equals("")) {
                //get data
                switch (sharedPref.getInt("accTag", 1)) {

                    case 1:
                        item.put("tag", R.drawable.red_bar);
                        break;
                    case 2:
                        item.put("tag", R.drawable.orange_bar);
                        break;
                    case 3:
                        item.put("tag", R.drawable.black_bar);
                        break;
                    case 4:
                        item.put("tag", R.drawable.blue_bar);
                        break;
                    case 5:
                        item.put("tag", R.drawable.green_bar);
                        break;
                    case 6:
                        item.put("tag", R.drawable.purple_bar);
                        break;
                    case 7:
                        item.put("tag", R.drawable.yellow_bar);
                        break;
                    case 8:
                        item.put("tag", R.drawable.pink_bar);
                        break;
                    default:
                }
                item.put("name", sharedPref.getString("accName", ""));

                java.text.DecimalFormat df = new java.text.DecimalFormat("#.00");

                item.put("balance", df.format(sharedPref.getFloat("accBalance", 8.8f)));
                item.put("currency", sharedPref.getString("accCurrency", ""));
                item.put("date", sharedPref.getString("timeModified", ""));
                mData.add(item);
            }
        }
        return mData;
    }
//add account button clicked
    public void addAcc(View view){
        Intent AddAcc = new Intent(this,AddAcc.class);
        finish();
        startActivity(AddAcc);
    }
    //transfer button clicked
    public void transferClicked(View view){
        Intent Transfer = new Intent(this,Transfer.class);
        finish();
        startActivity(Transfer);

    }


//return button clicked(or twice)
private long exitTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {

            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.DoubleClickExit),
                        Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

//return button clicked twice ends


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        super.onCreateOptionsMenu(menu);
        //about menu
        MenuItem mnu1 = menu.add(0, 0, 0, getString(R.string.aboutText));
        {
            mnu1.setShowAsAction(
                    MenuItem.SHOW_AS_ACTION_IF_ROOM);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
       RelativeLayout main_view=(RelativeLayout)findViewById(R.id.mainLayout);
        //about menu action
        switch (item.getItemId()) {
            case 0:
                final Intent accDetail = new Intent(this,MainActivity.class);

                //SET WIDGET in Layout
                final TextView aboutText=new TextView(this);
                aboutText.setText(getString(R.string.aboutTextContent));

                //set Layout
                final LinearLayout aboutLayout =new LinearLayout(this);
                aboutLayout.setOrientation(LinearLayout.VERTICAL);
                aboutLayout.setPadding(20, 20, 20, 20);
                aboutLayout.addView(aboutText);

                new AlertDialog.Builder(this)
                        .setTitle(getString(R.string.aboutText))
                        .setIcon(android.R.drawable.ic_dialog_info)
                        .setView(aboutLayout)



                        .setNegativeButton(getString(R.string.Exit), null)

                        .show();
                return true;
        }
            return false;
    }
}
