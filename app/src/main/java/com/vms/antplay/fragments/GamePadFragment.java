package com.vms.antplay.fragments;

import static com.facebook.FacebookSdk.getApplicationContext;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.vms.antplay.R;
import com.vms.antplay.adapter.Gamepad_RawAdapter;
import com.vms.antplay.model.GamePad_RawModal;

import java.util.ArrayList;

public class GamePadFragment extends Fragment {

    String[] users = {"XInput Controller #0 (045E/028E)", "XInput Controller #0 (045E/028E)", "XInput Controller #0 (045E/028E)", "XInput Controller #0 (045E/028E)"};
    RecyclerView recyclerView;
    private ArrayList<GamePad_RawModal> gamePad_rawModals;
    CheckBox checkBox;
    LinearLayout linearLayout_showrow, linearLayout_hiderow;
    TextView tv_reset;

    public GamePadFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_game_pad, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_gamepad);
        checkBox = (CheckBox) view.findViewById(R.id.checkbox_gamepad);
        linearLayout_hiderow = (LinearLayout) view.findViewById(R.id.linear_hideRow);
        linearLayout_showrow = (LinearLayout) view.findViewById(R.id.linear_showRow);
        tv_reset = (TextView) view.findViewById(R.id.reset);

        tv_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(requireContext(), "Reset Clicked", Toast.LENGTH_SHORT).show();
            }
        });

        Spinner spin = (Spinner) view.findViewById(R.id.spinner1);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_text, users);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(adapter);
        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                Toast.makeText(getApplicationContext(), "Selected User: " + users[position], Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (checkBox.isChecked()) {
                    Log.e("hello", "Checked");
                    linearLayout_hiderow.setVisibility(View.GONE);
                    linearLayout_showrow.setVisibility(View.VISIBLE);

                } else {
                    Log.e("hello", "Not Checked");
                    linearLayout_hiderow.setVisibility(View.VISIBLE);
                    linearLayout_showrow.setVisibility(View.GONE);
                }
                //  Toast.makeText(requireContext(), "Checked", Toast.LENGTH_SHORT).show();
            }
        });

        gamePad_rawModals = new ArrayList<>();
        gamePad_rawModals.add(new GamePad_RawModal("A", "0", "0/1", ""));
        gamePad_rawModals.add(new GamePad_RawModal("A", "0", "0/1", ""));
        gamePad_rawModals.add(new GamePad_RawModal("A", "0", "0/1", ""));
        gamePad_rawModals.add(new GamePad_RawModal("A", "0", "0/1", ""));
        gamePad_rawModals.add(new GamePad_RawModal("A", "0", "0/1", ""));
        gamePad_rawModals.add(new GamePad_RawModal("A", "0", "0/1", ""));
        gamePad_rawModals.add(new GamePad_RawModal("A", "0", "0/1", ""));
        gamePad_rawModals.add(new GamePad_RawModal("A", "0", "0/1", ""));
        gamePad_rawModals.add(new GamePad_RawModal("A", "0", "0/1", ""));

        Gamepad_RawAdapter adapter1 = new Gamepad_RawAdapter(requireContext(), gamePad_rawModals);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 1);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter1);


        return view;
    }
}