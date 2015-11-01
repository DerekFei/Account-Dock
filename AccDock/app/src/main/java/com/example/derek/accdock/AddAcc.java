package com.example.derek.accdock;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;


public class AddAcc extends Activity {
    //Data&Ctrl Widget
    private EditText newNameText;
    private EditText newBalanceText;

    private String newName;
    private float newBalance;
    private String currency;
    private int colorNum;
    private String nameAdding;


    private Button doneButton;
    private Button clearButton;
    private Button cancelButton;
   //ListWidget
    private List<String> Currencylist = new ArrayList<String>();
    private List<String> Taglist = new ArrayList<String>();
    private TextView CurrencyChoice;
    private TextView tagChoice;
    private Spinner CurrencySpinner;
    private Spinner tagSpinner;
            //adapter of currency
    private ArrayAdapter<String> adapter;
    //daapter of tag
    private ArrayAdapter<String> tagadapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_acc);
        //CURRENCY LIST
        Currencylist.add("AUD");
        Currencylist.add("BEF");
        Currencylist.add("BRC");
        Currencylist.add("CAD");
        Currencylist.add("CNY");
        Currencylist.add("DEM");
        Currencylist.add("DMK");
        Currencylist.add("ECU");
        Currencylist.add("FRF");
        Currencylist.add("GRP");
        Currencylist.add("HKD");
        Currencylist.add("IDR");
        Currencylist.add("INR");
        Currencylist.add("ITL");
        Currencylist.add("JPY");
        Currencylist.add("KOW");
        Currencylist.add("MEP");
        Currencylist.add("MYR");
        Currencylist.add("NLG");
        Currencylist.add("NTD");
        Currencylist.add("NZD");
        Currencylist.add("PHP");
        Currencylist.add("RUR");
        Currencylist.add("SAR");
        Currencylist.add("SEK");
        Currencylist.add("SGD");
        Currencylist.add("SPP");
        Currencylist.add("SWF");
        Currencylist.add("THB");
        Currencylist.add("USD");
        Currencylist.add("Others");


        CurrencyChoice = (TextView) findViewById(R.id.urCChoice);
        CurrencySpinner = (Spinner) findViewById(R.id.currencySpinner);

        //TagList
        Taglist.add(getString(R.string.Red));
        Taglist.add(getString(R.string.Orange));
        Taglist.add(getString(R.string.Black));
        Taglist.add(getString(R.string.Blue));
        Taglist.add(getString(R.string.Green));
        Taglist.add(getString(R.string.Purple));
        Taglist.add(getString(R.string.Yellow));
        Taglist.add(getString(R.string.Pink));
        tagChoice = (TextView) findViewById(R.id.tagChoice);
        tagSpinner = (Spinner) findViewById(R.id.tagSpinner);


        //CURRENCY ADAPTER SETUP
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Currencylist);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        CurrencySpinner.setAdapter(adapter);
        //Tag Adapter Setup
        tagadapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Taglist);
        tagadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tagSpinner.setAdapter(tagadapter);
        //currency Spinner Item Selected Listener
        CurrencySpinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                CurrencyChoice.setText(getString(R.string.CurrencyChoice) + adapter.getItem(arg2));
                currency = adapter.getItem(arg2);
                //show currency spinner
                arg0.setVisibility(View.VISIBLE);
            }
          //When nothing selected
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
                CurrencyChoice.setText("Please Select a Currency Type");
                arg0.setVisibility(View.VISIBLE);
            }
        });

        /*Currency TouchListener to select items*/
        CurrencySpinner.setOnTouchListener(new Spinner.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                /**
                 *
                 */
                return false;
            }
        });
        /*Currency Spinner focus change listener*/
        CurrencySpinner.setOnFocusChangeListener(new Spinner.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                // TODO Auto-generated method stub

            }
        });


        //tag
        //tag Spinner Item Selected Listener
        tagSpinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                tagChoice.setText(getString(R.string.TagChoice) + tagadapter.getItem(arg2));
                switch (tagadapter.getItem(arg2)) {
                    case "Red":
                        colorNum = 1;
                        break;
                    case "Orange":
                        colorNum = 2;
                        break;
                    case "Black":
                        colorNum = 3;
                        break;
                    case "Blue":
                        colorNum = 4;
                        break;
                    case "Green":
                        colorNum = 5;
                        break;
                    case "Purple":
                        colorNum = 6;
                        break;
                    case "Yellow":
                        colorNum = 7;
                        break;
                    case "Pink":
                        colorNum = 8;
                        break;
                    default:
                }
                //show tag spinner
                arg0.setVisibility(View.VISIBLE);
            }

            //When nothing selected
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
                tagChoice.setText("Tag Color: ");
                arg0.setVisibility(View.VISIBLE);
            }
        });

        /*tag TouchListener to select items*/
        tagSpinner.setOnTouchListener(new Spinner.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                /**
                 *
                 */
                return false;
            }
        });
        /*tag Spinner focus change listener*/
        tagSpinner.setOnFocusChangeListener(new Spinner.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                // TODO Auto-generated method stub

            }
        });



    }



    //CTRL Buttons

    //CheckName if exist method
    public boolean checkName(){
        SharedPreferences sharedPrefacc= getSharedPreferences("accName", Context.MODE_PRIVATE);
        nameAdding=sharedPrefacc.getString("accName","");
        String nameCheck[]=nameAdding.split(",");
        for (int i=0;i<nameCheck.length;i++){
            if( nameCheck[i].equals(newName)){
                return true;
            }

        }return false;
    }

    public void doneAdding (View view){
        //get the Name and the balance
        try {
            EditText newNameText=(EditText)findViewById(R.id.newNameText);
            EditText newBalanceText=(EditText)findViewById(R.id.newBalanceText);

            newName = newNameText.getText().toString();
            newBalance = Float.parseFloat(newBalanceText.getText().toString());
            final Intent backtoMain = new Intent(this,MainActivity.class);

            //check if exist first
            if(checkName()){
                Toast.makeText(getApplicationContext(),getString(R.string.NameAlreadyExist),Toast.LENGTH_LONG).show();
            }else {
                //save accName
                SharedPreferences sharedPrefacc = getSharedPreferences("accName", Context.MODE_PRIVATE);
                SharedPreferences.Editor editoracc = sharedPrefacc.edit();
                editoracc.putString("accName", newName + "," + nameAdding);
                editoracc.apply();
                //save info

                SharedPreferences sharedPref = getSharedPreferences(newName, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("accName", newName);
                editor.putFloat("accBalance", newBalance);
                editor.putString("accCurrency", currency);
                editor.putInt("accTag", colorNum);
                editor.putInt("statPeriod", 30);
                editor.putString("timeModified", getStringDateShort());
                java.text.DecimalFormat df=new java.text.DecimalFormat("#.00");
                editor.putString("Transactions", getStringDateShort() + "*Deposit*" + newBalance + "*(" + currency + ")\nNotes: New Account Created" + "\nNew Balance*" + df.format(newBalance) + "*\n#");
                editor.apply();
                Toast.makeText(getApplicationContext(),getString(R.string.Success),Toast.LENGTH_LONG).show();
                //startActivity;

                startActivity(backtoMain);
                finish();
            }


        }catch(Exception e){
            Toast.makeText(getApplicationContext(),getString(R.string.EnterNameAndInitialBalance),Toast.LENGTH_LONG).show();}

    }
    public void clearAdding(View view){
        EditText newNameText=(EditText)findViewById(R.id.newNameText);
        EditText newBalanceText=(EditText)findViewById(R.id.newBalanceText);
        newNameText.setText("");
        newBalanceText.setText("");
    }
    public void cancelAdding(View view){
        //ConfirmDialog
        final Intent backToMain= new Intent(this,MainActivity.class);
        new AlertDialog.Builder(this).setTitle(getString(R.string.DiscardChanges))
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(getString(R.string.Exit), new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // After Clicked Exit

                        startActivity(backToMain);
                        AddAcc.this.finish();
                    }
                })
                .setNegativeButton(getString(R.string.No), new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Nothing happened after NO clicked
                    }
                }).show();
// super.onBackPressed();
    }
    //get current date
    public static String getStringDateShort() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        String dateString = formatter.format(currentTime);
        return dateString;
    }

//return button clicked
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        Intent backToMain = new Intent(this,MainActivity.class);
        if(keyCode==KeyEvent.KEYCODE_BACK){

            startActivity(backToMain);
            finish();

        }
        return false;
    }

}
