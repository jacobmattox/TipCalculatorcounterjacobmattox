package com.introtoandroid.tipcalculator_counter_jacobmattox;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
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
                }
            }
        });
            View.OnKeyListener mKeyListener = new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                switch (v.getId()) {
                    case R.id.amount:
                        tipAmount = Double.parseDouble(amountText.getText().toString());
                        return true;
                    case R.id.people:
                        numOfPeople = Integer.parseInt(peopleText.getText().toString());
                        return true;
                    case R.id.otherTipAmount:
                        tipAmount = Double.parseDouble(otherTipAmount.getText().toString()) * .01;
                        return true;
                }
                return false;
            }

        };

        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }
}
