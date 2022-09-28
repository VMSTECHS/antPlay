package com.vms.antplay.fragments;

import static com.facebook.FacebookSdk.getApplicationContext;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.razorpay.Checkout;
import com.vms.antplay.R;
import com.vms.antplay.activity.PaymentPlanActivity;
import com.vms.antplay.activity.ProfileActivity;
import com.vms.antplay.activity.SpeedTestActivity;
import com.vms.antplay.adapter.ImageAdapter;
import com.vms.antplay.dialog.BottomSheetDialog;
import com.vms.antplay.interfaces.PaymentInitiationInterface;
import com.vms.antplay.model.ImageModel;
import com.vms.antplay.utils.Const;

import org.json.JSONObject;

import java.util.ArrayList;


public class HomeFragment extends Fragment implements PaymentInitiationInterface {
    private ArrayList<ImageModel> imageModelArrayList;
    ImageView imagePlay, imgTimer, imgAddTimer;
    CardView profile_card;
    LinearLayoutCompat llTimer;
    TextView txtTimer;

    private static final String TAG = HomeFragment.class.getSimpleName();


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);

        imagePlay = (ImageView) view.findViewById(R.id.img_play);
        profile_card = (CardView) view.findViewById(R.id.card_profile);

        llTimer = (LinearLayoutCompat) view.findViewById(R.id.llTimer);
        txtTimer = view.findViewById(R.id.txtTimer);
        imgTimer = view.findViewById(R.id.img_timer);
        imgAddTimer = view.findViewById(R.id.imgAddTimer);

        /*RazorPay Checkout*/

        Checkout.preload(getApplicationContext());




       /* Checkout checkout = new Checkout();
        checkout.setKeyID(Const.razorPayKeyId);*/


        imagePlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), SpeedTestActivity.class);
                startActivity(i);

            }
        });
        profile_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), ProfileActivity.class);
                startActivity(i);

            }
        });

        imageModelArrayList = new ArrayList<>();
        imageModelArrayList.add(new ImageModel("Alexa", R.drawable.game_one));
        imageModelArrayList.add(new ImageModel("TFS", R.drawable.game_three));
        imageModelArrayList.add(new ImageModel("Cinp", R.drawable.game_two));
        imageModelArrayList.add(new ImageModel("Cinp", R.drawable.loginwithlogo));
        imageModelArrayList.add(new ImageModel("Cinp", R.drawable.game_one));
        imageModelArrayList.add(new ImageModel("Cinp", R.drawable.game_two));
        imageModelArrayList.add(new ImageModel("Cinp", R.drawable.game_two));
        imageModelArrayList.add(new ImageModel("Cinp", R.drawable.game_two));

        ImageAdapter imageAdapter = new ImageAdapter(requireContext(), imageModelArrayList);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.suppressLayout(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(imageAdapter);

        imgTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(HomeFragment.this);
                bottomSheetDialog.show(getActivity().getSupportFragmentManager(), "BackgrountSheet");
            }
        });

        txtTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(HomeFragment.this);
                bottomSheetDialog.show(getActivity().getSupportFragmentManager(), "BackgrountSheet");
            }
        });

        imgAddTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), PaymentPlanActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }


    @Override
    public void onPaymentInitiated(int hours, int amount) {

        startPayment(hours, amount);
    }

    public void startPayment(int hours, int amount) {
        /*
          You need to pass current activity in order to let Razorpay create CheckoutActivity
         */
        int amountInPaise = amount * 100;
        final Activity activity = getActivity();

        final Checkout checkout = new Checkout();


        checkout.setKeyID(Const.razorPayTestKeyId);

        // EditText etCustomOptions = findViewById(R.id.et_custom_options);
       /* if (!TextUtils.isEmpty(etCustomOptions.getText().toString())){
            try{
                JSONObject options = new JSONObject(etCustomOptions.getText().toString());
                checkout.open(activity, options);
            }catch (JSONException e){
                Toast.makeText(activity, "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT)
                        .show();
                e.printStackTrace();
            }
        }else{*/
        try {
            JSONObject options = new JSONObject();
            options.put("name", "Razorpay Corp");
            options.put("description", "Demoing Charges");
            options.put("send_sms_hash", true);
            options.put("allow_rotation", true);
            //You can omit the image option to fetch the image from dashboard
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
            options.put("currency", "INR");
            options.put("amount", String.valueOf(amountInPaise));

            JSONObject preFill = new JSONObject();
            preFill.put("email", "test@razorpay.com");
            preFill.put("contact", "9876543210");

            options.put("prefill", preFill);

            checkout.open(activity, options);
        } catch (Exception e) {
            Toast.makeText(activity, "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT)
                    .show();
            e.printStackTrace();
        }
    }


}