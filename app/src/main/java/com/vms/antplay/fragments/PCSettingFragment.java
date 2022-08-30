package com.vms.antplay.fragments;

import static com.facebook.FacebookSdk.getApplicationContext;

import android.os.Bundle;

import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.vms.antplay.R;


public class PCSettingFragment extends Fragment {

    SwitchCompat switchCompat;
    RadioButton low, high, medium, extraHigh;
    RadioGroup radioGroup, radioGroup_fps, radioGroup_touch;

    public PCSettingFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_p_c_setting, container, false);
        radioGroup = (RadioGroup) view.findViewById(R.id.radioGroup);
        radioGroup_fps = (RadioGroup) view.findViewById(R.id.radioGroup_fps);
        radioGroup_touch = (RadioGroup) view.findViewById(R.id.radioGroup_touchscreen);
        switchCompat = (SwitchCompat) view.findViewById(R.id.switchButton);
        low = (RadioButton) view.findViewById(R.id.radio_low);
        high = (RadioButton) view.findViewById(R.id.radio_high);
        medium = (RadioButton) view.findViewById(R.id.radio_medium);
        extraHigh = (RadioButton) view.findViewById(R.id.radio_extraHigh);

        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                Snackbar.make(compoundButton, "Switch state checked " + isChecked, Snackbar.LENGTH_LONG)
                        .setAction("ACTION", null).show();
            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                RadioButton radioButton = (RadioButton) radioGroup.findViewById(checkedId);
                int selectedId = radioGroup.getCheckedRadioButtonId();

                if (selectedId == -1) {
                    Toast.makeText(getApplicationContext(),
                                    "No answer has been selected",
                                    Toast.LENGTH_SHORT)
                            .show();
                }
                else {

                    RadioButton radioButton1 = (RadioButton)radioGroup.findViewById(selectedId);

                    Snackbar.make(radioGroup, "Switch state checked " + radioButton1.getText(), Snackbar.LENGTH_LONG)
                        .setAction("ACTION", null).show();
                }



            }
        });
        radioGroup_fps.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                RadioButton radioButton = (RadioButton) radioGroup.findViewById(checkedId);
                int selectedId = radioGroup.getCheckedRadioButtonId();

                if (selectedId == -1) {
                    Toast.makeText(getApplicationContext(),
                                    "No answer has been selected",
                                    Toast.LENGTH_SHORT)
                            .show();
                }
                else {

                    RadioButton radioButton1 = (RadioButton)radioGroup.findViewById(selectedId);

                    Snackbar.make(radioGroup, "Switch state checked " + radioButton1.getText(), Snackbar.LENGTH_LONG)
                        .setAction("ACTION", null).show();
                }



            }
        });
        radioGroup_touch.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                RadioButton radioButton = (RadioButton) radioGroup.findViewById(checkedId);
                int selectedId = radioGroup.getCheckedRadioButtonId();

                if (selectedId == -1) {
                    Toast.makeText(getApplicationContext(),
                                    "No answer has been selected",
                                    Toast.LENGTH_SHORT)
                            .show();
                }
                else {

                    RadioButton radioButton1 = (RadioButton)radioGroup.findViewById(selectedId);

                    Snackbar.make(radioGroup, "Switch state checked " + radioButton1.getText(), Snackbar.LENGTH_LONG)
                        .setAction("ACTION", null).show();
                }



            }
        });


        return view;
    }


}