package com.mohtashim.tasbeehcounter;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class TasbeehEZehraFragment extends Fragment {

    ConstraintLayout layoutMain;
    TextView countingTxt, zikrTxt;
    AppCompatButton resetBtn;
    static int count = 0;

    MediaPlayer mediaPlayer;

    Vibrator vibrator;

    public TasbeehEZehraFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Storing our mp3 file in Media Player
        mediaPlayer = MediaPlayer.create(getActivity(), R.raw.bell_sound);
        //Initializing vibrator
        vibrator = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tasbeeh_e_zehra, container, false);
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //initialize your view here for use view.findViewById("your view id")
        InitializeComponents(view);

        //increment on click anywhere
        layoutMain.setOnClickListener(v -> incrementAndUpdateZikr());
        //reset btn
        resetBtn.setOnClickListener(v -> {
            count = 0;
            countingTxt.setText(String.valueOf(count));
            zikrTxt.setText("اللهُ اكبَر");
        });
    }

    //Initialize UI components
    private void InitializeComponents(View view){
        layoutMain = view.findViewById(R.id.layoutMain);
        countingTxt = view.findViewById(R.id.countingTxt);
        zikrTxt = view.findViewById(R.id.zikrTxt);
        resetBtn = view.findViewById(R.id.resetBtn);
    }
    //increment zikr
    private void incrementAndUpdateZikr(){
            count++;
            countingTxt.setText(String.valueOf(count));

        // update zikr text as per count of Tasbeeh e zehra (s)
        if(count == 34 && zikrTxt.getText().toString().equals("اللهُ اكبَر")){
            resetCountAndBeepAndVibrate("الحَمد لِلهِ");
        } else if (zikrTxt.getText().toString().equals("الحَمد لِلهِ") && count == 33) {
            resetCountAndBeepAndVibrate("سُبْحَانَ اللهِ");
        } else if (zikrTxt.getText().toString().equals("سُبْحَانَ اللهِ") && count == 33) {
            resetCountAndBeepAndVibrate("اللهُ اكبَر");
        }
    }
    //Method to update Zikr, reset count, play beep and trigger vibration
    private void resetCountAndBeepAndVibrate(String zikr){
        //calling play media method to play ring bell
        playMedia();
        //calling triggerVibration method to execute vibration
        triggerVibration();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                count = 0;
                countingTxt.setText(String.valueOf(count));
                zikrTxt.setText(zikr);
            }
        },500);
    }

    // Trigger vibration when the count reaches 34
    private void triggerVibration() {
        if (vibrator != null) {
            // For devices running Android O (API level 26) or higher
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE)); // Vibrate for 500 milliseconds
            } else {
                vibrator.vibrate(500); // Vibrate for 500 milliseconds for older devices
            }
        }
    }

    // Play media
    private void playMedia() {
        if (mediaPlayer != null) {
            mediaPlayer.start();  // Play the media when count is 34
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        // Release MediaPlayer when the Fragment stops
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}