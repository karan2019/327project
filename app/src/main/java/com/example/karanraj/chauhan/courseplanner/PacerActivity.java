package com.example.karanraj.chauhan.courseplanner;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by karanraj
 * Pacer functionality of app gives the user an estimate of their BAC at regular intervals of time
 * based on alcohol consumption history
 */

// TODO: 12/6/16 notify when gender is not selected/set default gender

public class PacerActivity extends AppCompatActivity {

    private final static String TAG = "PacerActivity";

    private int mUserWeight = 0;

    // Spinners for beverage options & info, quantity, time of consumption
    private Spinner mBeverageOptionsSpinner;
    private Spinner mQuantitySpinner;
    private Spinner mTimeSpinner;

    // NumberPickers for hundreds, tens, and ones digits of weight
    private NumberPicker mWeightHundredsNumberPicker;
    private NumberPicker mWeightTensNumberPicker;
    private NumberPicker mWeightOnesNumberPicker;

    // ArrayList that will contain all user inputs about beverages consumption, i.e., name, quantity and time
    private List<BeverageIntake> beverageIntakes = new ArrayList<>(5);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pacer);

        // Find by id and assign NumberPickers
        mWeightHundredsNumberPicker = (NumberPicker) findViewById(R.id.pacer_weight_hundreds);
        mWeightTensNumberPicker = (NumberPicker) findViewById(R.id.pacer_weight_tens);
        mWeightOnesNumberPicker = (NumberPicker) findViewById(R.id.pacer_weight_ones);

        // Specify the maximum and minimum digit for all NumberPickers
        mWeightHundredsNumberPicker.setMaxValue(3);
        mWeightHundredsNumberPicker.setMinValue(0);
        mWeightTensNumberPicker.setMaxValue(9);
        mWeightTensNumberPicker.setMinValue(0);
        mWeightOnesNumberPicker.setMaxValue(9);
        mWeightOnesNumberPicker.setMinValue(0);

        // Set whether the selector wheel wraps on reaching the min/max value.
        mWeightHundredsNumberPicker.setWrapSelectorWheel(true);
        mWeightTensNumberPicker.setWrapSelectorWheel(true);
        mWeightOnesNumberPicker.setWrapSelectorWheel(true);

        // Find by id and assign Spinners
        mBeverageOptionsSpinner = (Spinner) findViewById(R.id.beverage_options_spinner);
        mQuantitySpinner = (Spinner) findViewById(R.id.quantity_spinner);
        mTimeSpinner = (Spinner) findViewById(R.id.time_spinner);

        // Arrays containing options inside spinners that user can select
        String[] BEVERAGE_OPTIONS_ARRAY = {"Regular Beer (5%, 12oz)", "Light Beer (4%, 12oz)",
                "Table Wine (12%, 5oz)", "Wine Cooler (5%, 12oz)", "Vodka (40%, 1.25oz)",
                "Gin (40%, 1.25oz)", "Rum (40%, 1.25oz)", "Tequila (40%, 1.25oz)",
                "Bourbon (40%, 1.25oz)", "Scotch (40%, 1.25oz)"};

        Integer[] QUANTITY_OPTIONS_ARRAY = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};

        String[] TIME_OPTIONS_ARRAY = {"00:00", "00:30", "01:00", "01:30", "02:00", "02:30",
                "03:00", "03:30", "04:00", "04:30", "05:00", "05:30", "06:00", "06:30", "07:00",
                "07:30", "08:00", "08:30", "09:00", "09:30", "10:00", "10:30", "11:00", "11:30",
                "12:00", "12:30", "13:00", "13:30", "14:00", "14:30", "15:00", "15:30", "16:00",
                "16:30", "17:00", "17:30", "18:00", "18:30", "19:00", "19:30", "20:00", "20:30",
                "21:00", "21:30", "22:00", "22:30", "23:00", "23:30"};

        // Creating and setting adapters for spinners
        ArrayAdapter<String> beverageOptionsSpinnerAdapter = new ArrayAdapter<String>(PacerActivity.this,
                R.layout.support_simple_spinner_dropdown_item, BEVERAGE_OPTIONS_ARRAY);
        ArrayAdapter<Integer> quantitySpinnerAdapter = new ArrayAdapter<Integer>(PacerActivity.this,
                R.layout.support_simple_spinner_dropdown_item, QUANTITY_OPTIONS_ARRAY);
        ArrayAdapter<String> timeSpinnerAdapter = new ArrayAdapter<String>(PacerActivity.this,
                R.layout.support_simple_spinner_dropdown_item, TIME_OPTIONS_ARRAY);

        mBeverageOptionsSpinner.setAdapter(beverageOptionsSpinnerAdapter);
        mQuantitySpinner.setAdapter(quantitySpinnerAdapter);
        mTimeSpinner.setAdapter(timeSpinnerAdapter);

        // When add button is clicked, the current selections in Spinners are added to the appropriate AraryLists
        Button addButton = (Button) findViewById(R.id.add_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                beverageIntakes.add(new BeverageIntake(mBeverageOptionsSpinner.getSelectedItem().toString(),
                        (int) mQuantitySpinner.getSelectedItem(), mTimeSpinner.getSelectedItem().toString() ));
                mUserWeight = +100*mWeightHundredsNumberPicker.getValue()+10*mWeightTensNumberPicker.getValue()+mWeightOnesNumberPicker.getValue();
                Log.d(TAG, "onClick: weight is "+mUserWeight);
                BeverageIntake first = beverageIntakes.get(0);
                Log.d(TAG, "onClick: beverage is "+first.getName()+" "+first.getQuantity()+" "+first.getTime());
            }
        });
    }

    public void onRadioButtonClicked(View view) {

        double genderConstant;

        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radio_button_male:
                if (checked)
                    genderConstant = 0.73;  // standard value for males
                break;
            case R.id.radio_button_female:
                if (checked)
                    genderConstant = 0.66;  // standard value for females
                break;
        }
    }


}