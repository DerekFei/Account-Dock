package com.example.derek.accdock;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class Transfer extends Activity {
    private Spinner fromSpinner;
    private Spinner toSpinner;
    //adapter
    private ArrayAdapter<String> adapter;
    //for list
    private String allAccName;
    private String accNameArray[];
    private List<String> AccList = new ArrayList<String>();
    private TextView fromBalance,toBalance;

    //for result
    private String resAccName,desAccName,transferNotes,resCurrency,desCurrency;
    private float transferAmount,resBalance,desBalance;

   //others
    private EditText amountText,notesText;
    private TextView errorText;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer);
        //get All ACC NAME
        SharedPreferences sharedPref = getSharedPreferences("accName", Context.MODE_PRIVATE);
        allAccName=sharedPref.getString("accName","");
        //split names and put in array
        accNameArray=allAccName.split(",");

        //ACC LIST
        for(int i=0;i<accNameArray.length;i++) {
            AccList.add(accNameArray[i]);
        }
        fromBalance = (TextView) findViewById(R.id.fromBalanceText);
        toBalance = (TextView) findViewById(R.id.toBalanceText);
        fromSpinner = (Spinner) findViewById(R.id.fromSpinner);
        toSpinner = (Spinner) findViewById(R.id.toSpinner);

        //adapter setup
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, AccList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fromSpinner.setAdapter(adapter);
        toSpinner.setAdapter(adapter);
        //from Spinner Item Selected Listener
        fromSpinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                //get resAcc info and show
                SharedPreferences sharedPref2 = getSharedPreferences(adapter.getItem(arg2), Context.MODE_PRIVATE);
                resAccName = adapter.getItem(arg2);
                resCurrency = sharedPref2.getString("accCurrency", "");
                resBalance = sharedPref2.getFloat("accBalance", 8.8f);
                java.text.DecimalFormat df = new java.text.DecimalFormat("#.00");
                fromBalance.setText(getString(R.string.Balance) + df.format(resBalance) + "(" + resCurrency + ")");

                //show from spinner
                arg0.setVisibility(View.VISIBLE);
            }

            //When nothing selected
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
                fromBalance.setText(getString(R.string.Balance));
                arg0.setVisibility(View.VISIBLE);
            }
        });

        /*from TouchListener to select items*/
        fromSpinner.setOnTouchListener(new Spinner.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                /**
                 *
                 */
                return false;
            }
        });
        /*from Spinner focus change listener*/
        fromSpinner.setOnFocusChangeListener(new Spinner.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                // TODO Auto-generated method stub

            }
        });


        //to Spinner Item Selected Listener
        toSpinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                //get desAcc info and show
                SharedPreferences sharedPref2 = getSharedPreferences(adapter.getItem(arg2), Context.MODE_PRIVATE);
                desAccName = adapter.getItem(arg2);
                desCurrency =sharedPref2.getString("accCurrency", "");
                desBalance=sharedPref2.getFloat("accBalance", 8.8f);
                java.text.DecimalFormat df = new java.text.DecimalFormat("#.00");
                toBalance.setText(getString(R.string.Balance) + df.format(desBalance)+"("+ desCurrency+")");

                //show to spinner
                arg0.setVisibility(View.VISIBLE);
            }

            //When nothing selected
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
                toBalance.setText(getString(R.string.Balance));
                arg0.setVisibility(View.VISIBLE);
            }
        });

        /*to TouchListener to select items*/
       toSpinner.setOnTouchListener(new Spinner.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                /**
                 *
                 */
                return false;
            }
        });
        /*from Spinner focus change listener*/
        toSpinner.setOnFocusChangeListener(new Spinner.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                // TODO Auto-generated method stub

            }
        });







    }//end onCreate

    //done button clicked
    public void doneTransfer(View view){
        //GET amount and notes
        amountText=(EditText)findViewById(R.id.amountText);
        notesText=(EditText)findViewById(R.id.transferNotesText);

        transferNotes=notesText.getText().toString();
        errorText=(TextView)findViewById(R.id.errorText);
        String error="";
        errorText.setText(error);
        if(resAccName==desAccName){
            error=error+getString(R.string.CannotBetweenSameAcc)+"\n";
        }else{};

        if(resCurrency.equals(desCurrency)){}else{
            error=error+getString(R.string.CannotBetweenCurrency)+"\n";
        }
        try{
            transferAmount= Float.valueOf(amountText.getText().toString());
        if(resBalance<transferAmount) {
            error = error + getString(R.string.NoEnoughFund)+"\n";

        }
        }catch(Exception e){
            error = error + getString(R.string.AmountCannotBlank)+"\n";
        }
        if(transferNotes.contains("*")||transferNotes.contains("#")){
            error=error+getString(R.string.NotesCannotContain);
        }

        errorText.setText(error);

        if (error.equals("")){
            try {
                java.text.DecimalFormat df = new java.text.DecimalFormat("#.00");
                //add to FromACC sharedPref
                float newResBalance = resBalance - transferAmount;
                SharedPreferences sharedPreffrom = getSharedPreferences(resAccName, Context.MODE_PRIVATE);
                String fromtransactionRecordA = sharedPreffrom.getString("Transactions", "");
                SharedPreferences.Editor editor = sharedPreffrom.edit();
                editor.putFloat("accBalance", newResBalance);
                editor.putString("timeModified", getStringDateShort());
                editor.putString("Transactions", fromtransactionRecordA + "\n" + getStringDateShort() + "*Withdraw*" + df.format(transferAmount) + "*(" + resCurrency + ")\nNotes: TransferOut " + transferNotes + "\nNew Balance*" + df.format(newResBalance) + "*\n#");
                editor.apply();

                //add to ToACC sharedPref
                float newdesBalance = desBalance + transferAmount;
                SharedPreferences sharedPrefto = getSharedPreferences(desAccName, Context.MODE_PRIVATE);
                String totransactionRecordA = sharedPrefto.getString("Transactions", "");
                SharedPreferences.Editor editor2 = sharedPrefto.edit();
                editor2.putFloat("accBalance", newdesBalance);
                editor2.putString("timeModified", getStringDateShort());
                editor2.putString("Transactions", totransactionRecordA + "\n" + getStringDateShort() + "*Deposit*" + df.format(transferAmount) + "*(" + desCurrency + ")\nNotes: TransferIn " + transferNotes + "\nNew Balance*" + df.format(newdesBalance) + "*\n#");
                editor2.apply();
                //make toast and finalized
                Toast.makeText(getApplicationContext(), getString(R.string.Success), Toast.LENGTH_SHORT).show();
                final Intent backtoMain = new Intent(this, MainActivity.class);
                startActivity(backtoMain);
                finish();
            }catch(Exception e){
                Toast.makeText(getApplicationContext(), getString(R.string.Format_Error), Toast.LENGTH_SHORT).show();
                errorText.setText(getString(R.string.AmountCannotBlank));
                error="";//clear string error
            }

        }else{
            Toast.makeText(getApplicationContext(), getString(R.string.Format_Error), Toast.LENGTH_SHORT).show();
            error="";//clear string error
        }


    }

    //clear button clicked
    public void clearTransfer(View view){
        amountText=(EditText)findViewById(R.id.amountText);
        notesText=(EditText)findViewById(R.id.transferNotesText);
        amountText.setText("");
        notesText.setText("");
    }

    //cancle button clicked
    public void cancleTransfer(View view){
        final Intent backToMain= new Intent(this,MainActivity.class);
        new AlertDialog.Builder(this).setTitle(getString(R.string.DiscardChanges))
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(getString(R.string.Exit), new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // After Clicked Exit

                        startActivity(backToMain);
                        Transfer.this.finish();
                    }
                })
                .setNegativeButton(getString(R.string.No), new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Nothing happened after NO clicked
                    }
                }).show();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_transfer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
      /*  if (id == R.id.action_settings) {
            return true;
        }
*/
        return super.onOptionsItemSelected(item);
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
    //current time getter
    public static String getStringDateShort() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        String dateString = formatter.format(currentTime);
        return dateString;
    }

}
