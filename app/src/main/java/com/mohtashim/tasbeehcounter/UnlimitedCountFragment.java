package com.mohtashim.tasbeehcounter;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class UnlimitedCountFragment extends Fragment {

    Spinner spinner;
    TextView zikrTxt, countingTxt;

    ConstraintLayout mainLayout;
    static int count = 0;

    AppCompatButton resetBtn;
    EditText onBeepEditTxt;
    MediaPlayer mediaPlayer;
    Vibrator vibrator;

    public UnlimitedCountFragment() {
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
        return inflater.inflate(R.layout.fragment_unlimited_count, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //Calling initialize function to initialize all UI components
        initializeUiComponents(view);


        // Create an ArrayList of strings
        ArrayList<String> tasbeehItems = new ArrayList<>();
        //calling add items function to add items in arraylist
        addItemsToZikrArrayList(tasbeehItems);


        // Create an ArrayAdapter using the custom layout (spinner_item.xml)
       ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), R.layout.spinner_item, tasbeehItems);
        // Set the adapter on the spinner
       spinner.setAdapter(adapter);
       //Set item selected on spinner and set selected item over textView on screen
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Get the selected item
                String selectedItem = parent.getItemAtPosition(position).toString();

                // Set the selected item text in the TextView
                zikrTxt.setText(selectedItem);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // leaving is as it is but you can display anything or do anything when nothing is selected
            }
        });

        //Counter Perform
        mainLayout.setOnClickListener(v->{
            count++;
            countingTxt.setText(String.valueOf(count));
            // Call the function here to check the count each time the user taps
            playVibrateOnReachingBeepTxt(onBeepEditTxt, count);
        });

        //resetBtn
        resetBtn.setOnClickListener(v -> {
            count = 0;
            countingTxt.setText(String.valueOf(count));
            onBeepEditTxt.setText("");
        });

    }


    private void initializeUiComponents(View view){
        //All UI components used, initialized here
        spinner = view.findViewById(R.id.spinner);
        zikrTxt = view.findViewById(R.id.zikrTxt);
        mainLayout = view.findViewById(R.id.LayoutMain);
        countingTxt = view.findViewById(R.id.countingTxt);
        resetBtn = view.findViewById(R.id.resetBtn);
        onBeepEditTxt = view.findViewById(R.id.onBeepEditTxt);
    }
    //Adding Zikr items to ArrayList
    private void addItemsToZikrArrayList(ArrayList<String> arrayList){
        arrayList.add("اللّٰهم صلِّ على مُحمَّدٍ وَ آلِ مُحمَّدٍ ﷺ");
        arrayList.add("استغفرُ اللّٰه رَبّي من كُلِّ ذَنبٍ وَ أَتُوبُ إِلَيْهِ");
        arrayList.add("اللّهُمَّ لعَن قاتِل الحُسَيْن وَأولاد الحُسَيْن وَأصحاب الحُسَيْن وَأنصار الحُسَيْن");
        arrayList.add("لا إلهَ إِلَّا أَنتَ سُبْحَانَكَ إِنّي كُنتُ من الظَّالمينَ");
        arrayList.add("السَّلَامُ عَلَيْكُمْ يَا أَهْلَ الْبَيْتِ النُّبُوَةِ وَمَعْدِنَ الرِّسَالَةِ جَمِيعًا وَرَحْمَةُ اللَّهِ وَبَرَكَاتُهُ");
        arrayList.add("اللَّهُمَّ اجْعَلْنِي عِندَكَ وَجِيهًا بِالْحُسَيْنِ عَلَيْهِ السَّلَامُ فِي الدُّنْيَا وَالآخِرَةِ");
    }

    // Trigger vibration when the count reaches onBeepEditText
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
    //Playing Media
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

    private void playVibrateOnReachingBeepTxt(EditText editText, int count) {
        String beepTxt = editText.getText().toString().trim();
        if (!beepTxt.isEmpty()) {
            int onBeepTxt = Integer.parseInt(beepTxt);

            if (count == onBeepTxt) {
                playMedia();
                triggerVibration();
            }
        }
    }



}