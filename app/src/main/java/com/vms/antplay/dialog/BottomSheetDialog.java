package com.vms.antplay.dialog;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.vms.antplay.R;

public class BottomSheetDialog extends BottomSheetDialogFragment implements View.OnClickListener {
    SeekBar priceSeekbar;
    TextView txtPrice, txtPurchase,txt2hours,txt8Hours,txt22Hours;
    float discountPctFor8Hours = 0.05f;
    float discountPctFor24Hours = 0.10f;
    int perHourPrice = 32;
    float googleChargesInPct = 0.15f;
    float googleChargesInPctAbove1M = 0.30f;
    int chargesFor2Hours = 64;
    int chargesFor8Hours = 450;
    int chargesFor24Hours = 850;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_sheet_dialog, container, false);

        priceSeekbar = view.findViewById(R.id.seekBar);
        txtPrice = view.findViewById(R.id.txtPrice);
        txtPurchase = view.findViewById(R.id.txtPurchase);
        txt2hours = view.findViewById(R.id.txt2Hours);
        txt8Hours = view.findViewById(R.id.txt8Hours);
        txt22Hours = view.findViewById(R.id.txt22Hours);
        txtPrice.setText("₹ " + priceCalculation(0));
        txtPurchase.setText(getResources().getString(R.string.purchase_2_hours));

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        seekbarProgress();
        setOnClickListener();
    }

    private void setOnClickListener() {
        txtPurchase.setOnClickListener(this);
        txt2hours.setOnClickListener(this);
        txt8Hours.setOnClickListener(this);
        txt22Hours.setOnClickListener(this);

    }

    private void seekbarProgress() {

        priceSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // txtPrice.setText(priceCalculation(0));
                String totalPrice;
                if (progress <= 5 && progress > 0) {
                    priceSeekbar.setProgress(5);
                    totalPrice = getResources().getString(R.string.rupees) + " " + priceCalculation(6);
                    txtPrice.setText(totalPrice);
                    txtPurchase.setText(getResources().getString(R.string.purchase_8_hours));
                } else if (progress > 5 && progress <= 10) {
                    priceSeekbar.setProgress(10);
                    totalPrice = getResources().getString(R.string.rupees) + " " + priceCalculation(22);
                    txtPrice.setText(totalPrice);
                    txtPurchase.setText(getResources().getString(R.string.purchase_24_hours));
                } else if (progress == 0) {
                    // priceSeekbar.setProgress(0);
                    totalPrice = getResources().getString(R.string.rupees) + " " + priceCalculation(0);
                    txtPrice.setText(totalPrice);
                    txtPurchase.setText(getResources().getString(R.string.purchase_2_hours));
                }

                Log.d("PROGRESS", progress + "");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private String priceCalculation(int hours) {
        float totalPrice;
        String strTotalPrice;
        switch (hours) {
            case 0: {
                // totalPrice =(float) (perHourPrice * 2);
                totalPrice = (float) chargesFor2Hours;
                float priceWithServiceCharges = totalPrice + (totalPrice * googleChargesInPct);
                // totalPrice = totalPrice+(totalPrice*googleChargesInPct);
                int roundedPrice = Math.round(priceWithServiceCharges);
                strTotalPrice = String.valueOf(roundedPrice);
                Log.d("PRICE", "Price: " + totalPrice + ", Discount: No Discount, Service Charges: " + priceWithServiceCharges + " : " + strTotalPrice);
                return strTotalPrice;

            }
            case 6: {
                /*totalPrice =(float) (perHourPrice * 8);
                float discountedPrice = totalPrice - (totalPrice*discountPctFor8Hours);
                totalPrice = totalPrice - (totalPrice*discountPctFor8Hours);*/
                totalPrice = (float) chargesFor8Hours;
                float priceWithServiceCharges = totalPrice + (totalPrice * googleChargesInPct);
                // totalPrice = totalPrice+(totalPrice*googleChargesInPct);
                int roundedPrice = Math.round(priceWithServiceCharges);
                strTotalPrice = String.valueOf(roundedPrice);
                Log.d("PRICE", "Price: " + totalPrice + ", Discount: No Discount, Service Charges: " + priceWithServiceCharges + " : " + strTotalPrice);
                return strTotalPrice;
            }
            case 22: {
               /* totalPrice =(float) (perHourPrice * 24);
                float discountedPrice = totalPrice - (totalPrice*discountPctFor24Hours);
                totalPrice = totalPrice - (totalPrice*discountPctFor24Hours);*/
                totalPrice = (float) chargesFor24Hours;
                float priceWithServiceCharges = totalPrice + (totalPrice * googleChargesInPct);
                // totalPrice = totalPrice+(totalPrice*googleChargesInPct);
                int roundedPrice = Math.round(priceWithServiceCharges);
                strTotalPrice = String.valueOf(roundedPrice);
                Log.d("PRICE", "Price: " + totalPrice + ", Discount: No Discount, Service Charges: " + priceWithServiceCharges + " : " + strTotalPrice);
                return strTotalPrice;
            }
            default: {

            }
        }
        return 64 + "";
    }

    @Override
    public void onClick(View v) {
        String totalPrice;
        switch (v.getId()) {
            case R.id.txt2Hours:
                priceSeekbar.setProgress(0);
                totalPrice = getResources().getString(R.string.rupees) + " " + priceCalculation(0);
                txtPrice.setText(totalPrice);
                txtPurchase.setText(getResources().getString(R.string.purchase_2_hours));
                break;
            case R.id.txt8Hours:
                priceSeekbar.setProgress(5);
                totalPrice = getResources().getString(R.string.rupees) + " " + priceCalculation(6);
                txtPrice.setText(totalPrice);
                txtPurchase.setText(getResources().getString(R.string.purchase_8_hours));
                break;
            case R.id.txt22Hours:
                priceSeekbar.setProgress(10);
                totalPrice = getResources().getString(R.string.rupees) + " " + priceCalculation(22);
                txtPrice.setText(totalPrice);
                txtPurchase.setText(getResources().getString(R.string.purchase_24_hours));

                break;
            case R.id.txtPurchase:
                Toast.makeText(getContext(), "Purchased Successfully", Toast.LENGTH_SHORT).show();
                break;

        }
    }
}
