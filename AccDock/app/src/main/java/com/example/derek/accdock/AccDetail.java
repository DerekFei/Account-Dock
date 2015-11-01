package com.example.derek.accdock;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class AccDetail extends Activity {
    String nameDetail;
    Float balanceDetail;
    String currencyDetail;
    int tagDtail,statPeriod;;
    String dateDtail;
    Float balanceChange;
    String remark;
   static String[] transactionRecord;
    String transactionRecordA;


    TextView nameText,balanceText,currencyText,dateText,transactionText,depositStatText,withdrawStatText;
    ImageView tagIMG;


    String accName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acc_detail);

        Bundle accNameR=getIntent().getExtras();
        if(accNameR==null){return;}
        accName=accNameR.getString("accName");
//display acc info
        //declare views
        SharedPreferences sharedPref = getSharedPreferences(accName, Context.MODE_PRIVATE);
        tagIMG=(ImageView)findViewById(R.id.tagDtail);
        nameText=(TextView)findViewById(R.id.accNameText);
        balanceText=(TextView)findViewById(R.id.balanceText);
        currencyText=(TextView)findViewById(R.id.currencyText);
        dateText=(TextView)findViewById(R.id.dateMText);
        transactionText=(TextView)findViewById(R.id.transText);

       //get info from sharedPref
        nameDetail=sharedPref.getString("accName", "");
        balanceDetail=sharedPref.getFloat("accBalance", 8.8f);
        currencyDetail=sharedPref.getString("accCurrency", "");
        dateDtail= sharedPref.getString("timeModified", "");
        statPeriod=sharedPref.getInt("statPeriod",30);
        transactionRecordA=sharedPref.getString("Transactions", "");

        //display
        nameText.setText(nameDetail);
        java.text.DecimalFormat df=new java.text.DecimalFormat("#.00");
        balanceText.setText(df.format(balanceDetail));
        currencyText.setText("("+currencyDetail+")");
        dateText.setText(dateDtail);
        transactionText.setText(transactionRecordA);



//right tag switch
        switch(sharedPref.getInt("accTag",1)){

            case 1:
                tagIMG.setImageDrawable(getDrawable(R.drawable.red_bar));
                balanceText.setTextColor(Color.argb(255, 165, 1, 1));
                currencyText.setTextColor(Color.argb(255,165,1,1));
                break;
            case 2:
                tagIMG.setImageDrawable(getDrawable(R.drawable.orange_bar));
                balanceText.setTextColor(Color.argb(255, 238, 101, 0));
                currencyText.setTextColor(Color.argb(255, 238, 101, 0));
                break;
            case 3:
                tagIMG.setImageDrawable(getDrawable(R.drawable.black_bar));
                balanceText.setTextColor(Color.argb(255, 0, 0, 0));
                currencyText.setTextColor(Color.argb(255, 0, 0, 0));
                break;
            case 4:
                tagIMG.setImageDrawable(getDrawable(R.drawable.blue_bar));
                balanceText.setTextColor(Color.argb(255, 30, 66, 168));
                currencyText.setTextColor(Color.argb(255, 30,66, 168));
                break;
            case 5:
                tagIMG.setImageDrawable(getDrawable(R.drawable.green_bar));
                balanceText.setTextColor(Color.argb(255, 3, 139, 0));
                currencyText.setTextColor(Color.argb(255, 3, 139, 0));
                break;
            case 6:
                tagIMG.setImageDrawable(getDrawable(R.drawable.purple_bar));
                balanceText.setTextColor(Color.argb(255, 129, 0, 177));
                currencyText.setTextColor(Color.argb(255, 129, 0, 177));
                break;
            case 7:
                tagIMG.setImageDrawable(getDrawable(R.drawable.yellow_bar));
                balanceText.setTextColor(Color.argb(255, 209, 177, 0));
                currencyText.setTextColor(Color.argb(255,209,177,0));
                break;
            case 8:
                tagIMG.setImageDrawable(getDrawable(R.drawable.pink_bar));
                balanceText.setTextColor(Color.argb(255, 230, 0, 162));
                currencyText.setTextColor(Color.argb(255, 230, 0, 162));
                break;
            default:
        }

        //printout stat result
        depositStatText=(TextView)findViewById(R.id.depositStatText);
        withdrawStatText=(TextView)findViewById(R.id.withdrawStatText);
        try {
            depositStatText.setText(getResources().getString(R.string.detailStatDeposit)+statPeriod+getResources().getString(R.string.detailStatUnit)+df.format(stat("Deposit",statPeriod))+" "+currencyDetail);
            withdrawStatText.setText(getResources().getString(R.string.detailStatWithdrawal)+statPeriod+getResources().getString(R.string.detailStatUnit)+df.format(stat("Withdraw",statPeriod))+" "+currencyDetail);
        } catch (Exception e) {
            depositStatText.setText("Stat Error");
            withdrawStatText.setText("Stat Error");
        }

    }//end of onCreate

    public void depositOnClicked (View view){
        final Intent accDetail = new Intent(this,AccDetail.class);
        //SET WIDGET in Layout
        final EditText inputAmount = new EditText(this);
        inputAmount.setInputType(InputType.TYPE_CLASS_NUMBER|InputType.TYPE_NUMBER_FLAG_DECIMAL);
        inputAmount.setMaxEms(18);
        inputAmount.setMinimumWidth(250);

        final EditText inputRemark = new EditText(this);
        inputRemark.setMaxEms(15);
        inputAmount.setMinimumWidth(250);

        final TextView amountText=new TextView(this);
        amountText.setText(getResources().getString(R.string.Amount_Must_Larger_0));
        final TextView remarkText=new TextView(this);
        remarkText.setText(getResources().getString(R.string.Notes_Cannot_Contain));
        //set Layout
        final LinearLayout inputLayout =new LinearLayout(this);
        inputLayout.setOrientation(LinearLayout.VERTICAL);
        inputLayout.setPadding(15, 15, 15, 15);
        inputLayout.addView(amountText);
        inputLayout.addView(inputAmount);
        inputLayout.addView(remarkText);
        inputLayout.addView(inputRemark);
        new AlertDialog.Builder(this)
                .setTitle(getResources().getString(R.string.Deposit))
                .setIcon(android.R.drawable.ic_dialog_info)
                .setView(inputLayout)
                .setPositiveButton(getResources().getString(R.string.Done), new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // After Clicked NEXT
                                try {
                                    if (Double.valueOf(inputAmount.getText().toString()) > 0 && !inputRemark.getText().toString().contains("#") && !inputRemark.getText().toString().contains("*")) {
                                        balanceChange = Float.valueOf(inputAmount.getText().toString());
                                        remark = inputRemark.getText().toString();

                                        //add to sharedPref
                                        balanceDetail = balanceDetail + balanceChange;
                                        SharedPreferences sharedPref = getSharedPreferences(accName, Context.MODE_PRIVATE);
                                        SharedPreferences.Editor editor = sharedPref.edit();
                                        editor.putFloat("accBalance", balanceDetail);
                                        editor.putString("timeModified", getStringDateShort());
                                        java.text.DecimalFormat df = new java.text.DecimalFormat("#.00");
                                        editor.putString("Transactions", transactionRecordA + "\n" + getStringDateShort() + "*Deposit*" + df.format(balanceChange) + "*(" + currencyDetail + ")\nNotes: " + remark + "\nNew Balance*" + df.format(balanceDetail) + "*\n#");
                                        editor.apply();


                                        Toast.makeText(AccDetail.this, getResources().getString(R.string.Success), Toast.LENGTH_SHORT).show();
                                        accDetail.putExtra("accName", accName);
                                        finish();
                                        startActivity(accDetail);


                                    } else {
                                        Toast.makeText(AccDetail.this, getString(R.string.Format_Error), Toast.LENGTH_SHORT).show();
                                    }
                                } catch (Exception e) {
                                    Toast.makeText(AccDetail.this, getString(R.string.Format_Error), Toast.LENGTH_SHORT).show();
                                }

                            }
                        }

                )
                            .

                    setNegativeButton(getString(R.string.Cancel), null)

                    .

                    show();

                }


    public void withdrawOnClicked (View view){
        final Intent accDetail = new Intent(this,AccDetail.class);

        //SET WIDGET in Layout
        final EditText inputAmount = new EditText(this);
        inputAmount.setInputType(InputType.TYPE_CLASS_NUMBER|InputType.TYPE_NUMBER_FLAG_DECIMAL);
        inputAmount.setMaxEms(18);
        inputAmount.setMinimumWidth(250);

        final EditText inputRemark = new EditText(this);
        inputRemark.setMaxEms(15);
        inputAmount.setMinimumWidth(250);

        final TextView amountText=new TextView(this);
        java.text.DecimalFormat df=new java.text.DecimalFormat("#.00");
        amountText.setText(getString(R.string.Amount_Must_Larger_0_Smaller)+df.format(balanceDetail));
        final TextView remarkText=new TextView(this);
        remarkText.setText(getString(R.string.Notes_Cannot_Contain));
        //set Layout
        final LinearLayout inputLayout =new LinearLayout(this);
        inputLayout.setOrientation(LinearLayout.VERTICAL);
        inputLayout.setPadding(15,15,15,15);
        inputLayout.addView(amountText);
        inputLayout.addView(inputAmount);
        inputLayout.addView(remarkText);
        inputLayout.addView(inputRemark);
        new AlertDialog.Builder(this)
                .setTitle(getString(R.string.Withdraw))
                .setIcon(android.R.drawable.ic_dialog_info)
                .setView(inputLayout)
                .setPositiveButton(getString(R.string.Done), new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // After Clicked NEXT
                            try {
                                if (Double.valueOf(inputAmount.getText().toString()) > 0 && !inputRemark.getText().toString().contains("#") && Double.valueOf(inputAmount.getText().toString()) <= balanceDetail && !inputRemark.getText().toString().contains("*")) {
                                    balanceChange = Float.valueOf(inputAmount.getText().toString());
                                    remark = inputRemark.getText().toString();

                                    //add to sharedPref
                                    balanceDetail = balanceDetail - balanceChange;
                                    SharedPreferences sharedPref = getSharedPreferences(accName, Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPref.edit();
                                    editor.putFloat("accBalance", balanceDetail);
                                    editor.putString("timeModified", getStringDateShort());
                                    java.text.DecimalFormat df = new java.text.DecimalFormat("#.00");
                                    editor.putString("Transactions", transactionRecordA + "\n" + getStringDateShort() + "*Withdraw*" + df.format(balanceChange) + "*(" + currencyDetail + ")\nNotes: " + remark + "\nNew Balance*" + df.format(balanceDetail) + "*\n#");
                                    editor.apply();


                                    Toast.makeText(AccDetail.this, getString(R.string.Success), Toast.LENGTH_SHORT).show();
                                    accDetail.putExtra("accName", accName);
                                    finish();
                                    startActivity(accDetail);


                                } else {
                                    Toast.makeText(AccDetail.this, getString(R.string.Format_Error), Toast.LENGTH_SHORT).show();
                                }
                            }catch (Exception e){
                                Toast.makeText(AccDetail.this, getString(R.string.Format_Error), Toast.LENGTH_SHORT).show();
                            }
                            }
                        }

                )
                .

                        setNegativeButton(getString(R.string.Cancel), null)

                .

                        show();

    }

    @Override
    //return button clicked

    public boolean onKeyDown(int keyCode, KeyEvent event) {

        Intent backToMain = new Intent(this,MainActivity.class);
        if(keyCode==KeyEvent.KEYCODE_BACK){

            startActivity(backToMain);
            finish();

        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_acc_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.


        return super.onOptionsItemSelected(item);
    }

    //get current date
    public static String getStringDateShort() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    //statistic
    public float stat(String type,int period) throws Exception{
        float result=0;
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
        String str=getStringDateShort();
        Date dt=sdf.parse(str);
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(dt);
        //rightNow.add(Calendar.YEAR,1);
        //rightNow.add(Calendar.MONTH,1);
        rightNow.add(Calendar.DAY_OF_YEAR,-period);//minus peroid days
        Date dt1=rightNow.getTime();//change calendar to date
        String startDate = sdf.format(dt1); //change date to string
        int startdateInt= Integer.parseInt(startDate);


       SharedPreferences sharedPref = getSharedPreferences(accName, Context.MODE_PRIVATE);
        transactionRecord=sharedPref.getString("Transactions","").split("#\\n");
        //check each transaction
        for(int i=0;i<transactionRecord.length;i++) {
            //split each transactions
            String eachTrans[];
            eachTrans=transactionRecord[i].split("\\*");

            //get date
            int dateInt=Integer.parseInt(eachTrans[0]);
            //check date and transaction type
            if(dateInt>=startdateInt&&eachTrans[1].equals(type)){
                result=result+Float.parseFloat(eachTrans[2]);
            }
            eachTrans=new String[0];
        }
        return result;
    }

    //setPeriod of statistic

    public void setPeriod (View view){

        final Intent accDetail = new Intent(this,AccDetail.class);

        //SET WIDGET in Layout
        final EditText inputPeriod = new EditText(this);
        inputPeriod.setInputType(InputType.TYPE_CLASS_NUMBER);
        inputPeriod.setMaxEms(7);
        inputPeriod.setMinimumWidth(100);

        final TextView periodText=new TextView(this);
        periodText.setText(getString(R.string.enterStatPeriod));

        //set Layout
        final LinearLayout inputLayout =new LinearLayout(this);
        inputLayout.setOrientation(LinearLayout.VERTICAL);
        inputLayout.setPadding(15, 15, 15, 15);
        inputLayout.addView(periodText);
        inputLayout.addView(inputPeriod);

        new AlertDialog.Builder(this)
                .setTitle(getString(R.string.setStatPeriod))
                .setIcon(android.R.drawable.ic_dialog_info)
                .setView(inputLayout)
                .setPositiveButton(getString(R.string.Done), new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // After Clicked NEXT

                                try{
                                    //add to sharedPref
                                    SharedPreferences sharedPref = getSharedPreferences(accName, Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPref.edit();
                                    editor.putInt("statPeriod", Integer.parseInt(inputPeriod.getText().toString()));
                                    editor.apply();
                                    //make toast
                                    Toast.makeText(AccDetail.this, getString(R.string.Success), Toast.LENGTH_SHORT).show();
                                    accDetail.putExtra("accName", accName);
                                    finish();
                                    startActivity(accDetail);


                                } catch (Exception e){
                                    Toast.makeText(AccDetail.this, getString(R.string.Format_Error), Toast.LENGTH_SHORT).show();
                                }

                            }
                        }

                )
                .

                        setNegativeButton(getString(R.string.Cancel), null)

                .

                        show();

    }

}
