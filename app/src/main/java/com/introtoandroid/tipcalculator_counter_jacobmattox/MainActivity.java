package com.introtoandroid.tipcalculator_counter_jacobmattox;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button reset;
    private Button calculate;
    private RadioGroup radioGroup;
    private EditText otherTipAmount;
    private EditText peopleText;
    private EditText amountText;
    private double tipAmount;
    private int numOfPeople;
    private double amount;
    private TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        reset = (Button) findViewById(R.id.reset);
        calculate = (Button) findViewById(R.id.calculate);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        otherTipAmount = (EditText) findViewById(R.id.otherTipAmount);
        peopleText = (EditText) findViewById(R.id.people);
        amountText = (EditText) findViewById(R.id.amount);
        tipAmount = 0;
        amount = 0;
        textView = (TextView) findViewById(R.id.enter);
        calculate.setEnabled(false);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int id = radioGroup.getCheckedRadioButtonId();

                switch (id){
                    case R.id.fifteen:
                        otherTipAmount.setVisibility(View.INVISIBLE);
                        tipAmount = .15;
                        break;
                    case R.id.twenty:
                        otherTipAmount.setVisibility(View.INVISIBLE);
                        tipAmount = .20;
                        break;
                    case R.id.twentyfive:
                        otherTipAmount.setVisibility(View.INVISIBLE);
                        tipAmount = .25;
                        break;
                    case R.id.other:
                        otherTipAmount.setVisibility(View.VISIBLE);
                        tipAmount = 0;
                }
            }
        });

        peopleText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(keyCode == KeyEvent.KEYCODE_ENTER){
                    try {
                        numOfPeople = Integer.parseInt(peopleText.getText().toString());
                    }
                    catch (Exception e){
                        showErrorAlert(getString(R.string.wowpeople), peopleText.getId());
                    }
                }
                return false;
            }
        });

        amountText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(keyCode == KeyEvent.KEYCODE_ENTER) {
                    try {
                        amount = Double.parseDouble(amountText.getText().toString());
                        try {
                            numOfPeople = Integer.parseInt(peopleText.getText().toString());
                        }
                        catch (Exception e){
                            showErrorAlert(getString(R.string.wowpeople), peopleText.getId());
                        }

                        if (numOfPeople > 0) {
                            calculate.setEnabled(true);
                            if ((otherTipAmount.getVisibility() == View.VISIBLE) && tipAmount == 0) {
                                calculate.setEnabled(false);
                            }
                        }
                    }
                    catch(Exception e){
                        showErrorAlert(getString(R.string.wowamount), amountText.getId());
                    }

                }

                return false;
            }
        });

        otherTipAmount.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(keyCode == KeyEvent.KEYCODE_ENTER){
                    try {
                        tipAmount = Double.parseDouble(otherTipAmount.getText().toString()) * .01;
                    }
                    catch(Exception e){
                        showErrorAlert(getString(R.string.wowtip), otherTipAmount.getId());
                    }
                }
                return false;
            }
        });


        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tipAmount > .1 && numOfPeople > 0 && amount > 0) {
                    textView.setText(getString(R.string.totalPerPerson) + String.format(" %.2f", (amount * (1 + tipAmount) / numOfPeople)) + "\n" + getString(R.string.totalBill) + String.format(" %.2f", (amount * (1 + tipAmount))));
                    Log.v("shouldBeChanged", textView.getText().toString());

                }
                else if (tipAmount < .1){
                    showErrorAlert(getString(R.string.tipTooLow), otherTipAmount.getId());

                }
                else if (numOfPeople < 1){
                    showErrorAlert(getString(R.string.specifyPeople), peopleText.getId());
                }
                else if( amount > 0) {
                    showErrorAlert(getString(R.string.specifyAmount), amountText.getId());
                }

            }
        });
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tipAmount = 0;
                amount = 0;
                textView.setText(getString(R.string.text_view_string));
                calculate.setEnabled(false);
                radioGroup.clearCheck();
                otherTipAmount.setVisibility(View.INVISIBLE);
                peopleText.setText(null);
                amountText.setText(null);
            }
        });

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("textView", textView.getText().toString());
        Log.v("putStringhere", getString(R.string.text_view_string));

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        String putString = savedInstanceState.getString("textView");
        textView.setText(putString);
    }
    private void showErrorAlert(String errorMessage, final int fieldId) {
        new AlertDialog.Builder(this)
                .setTitle("Error")
                .setMessage(errorMessage)
                .setNeutralButton("Close",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                findViewById(fieldId).requestFocus();
                            }
                        }).show();
    }
}
