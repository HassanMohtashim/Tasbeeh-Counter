package com.mohtashim.tasbeehcounter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    TextView tasbeehEZehraTxt, unlimitedCountTxt;
    ViewPager viewPager;
    MainAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //calling initializing component function
        initializeXmlComponents();
        //DefaultTxt and Fragment Selected
        defaultSelected();

        //initializing adapter
        adapter = new MainAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        //setting adapter over viewPager
        viewPager.setAdapter(adapter);

        //Changing Background and updating fragment on clicked text
        tasbeehEZehraTxt.setOnClickListener(v -> {
            changeBackgroundOfTextOnClick(tasbeehEZehraTxt,unlimitedCountTxt);
            viewPager.setCurrentItem(0);
        });
        ////Changing Background and updating fragment on clicked text
        unlimitedCountTxt.setOnClickListener(v -> {
            changeBackgroundOfTextOnClick(unlimitedCountTxt, tasbeehEZehraTxt);
            viewPager.setCurrentItem(1);
        });
        //Changing background of texts with swiping over viewPager
        changeBackgroundOfTxtWithSwipingOverViewPager();


    }
    //XML Components initialization function
    private void initializeXmlComponents(){
        tasbeehEZehraTxt = findViewById(R.id.TasbeehEZahraTxt);
        unlimitedCountTxt = findViewById(R.id.UnlimitedCountTxt);
        viewPager = findViewById(R.id.ViewPager);
    }

    //background changing of texts on click function
    @SuppressLint("UseCompatLoadingForDrawables")
    private void changeBackgroundOfTextOnClick(TextView selected, TextView other){
        selected.setBackground(getResources().getDrawable(R.drawable.selected_background));
        selected.setTextColor(getResources().getColor(R.color.white));
        other.setBackground(getResources().getDrawable(R.drawable.unselected_background));
        other.setTextColor(getResources().getColor(R.color.black));
    }
    //Default text and Fragment selection function
    @SuppressLint("UseCompatLoadingForDrawables")
    private void defaultSelected(){
        viewPager.setCurrentItem(0);
        tasbeehEZehraTxt.setBackground(getResources().getDrawable(R.drawable.selected_background));
        tasbeehEZehraTxt.setTextColor(getResources().getColor(R.color.white));
    }

    //Function to handle changing of background of texts on swiping fragments accordingly
    private void changeBackgroundOfTxtWithSwipingOverViewPager(){
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                // This method is called when the page is being scrolled
            }

            @Override
            public void onPageSelected(int position) {
                // Change TextView background based on the fragment shown
                if (position == 0) {
                    //Changing when tasbeeh e zehra fragment came
                    changeBackgroundOfTextOnClick(tasbeehEZehraTxt,unlimitedCountTxt);
                } else if (position == 1) {
                    //Changing when unlimited count fragment appear
                    changeBackgroundOfTextOnClick(unlimitedCountTxt, tasbeehEZehraTxt);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                // This method is called when the page scroll state changes
            }
        });
    }

    }


    //inner class for making adapter
     class MainAdapter extends FragmentPagerAdapter {

    //Constructor
        public MainAdapter(@NonNull FragmentManager fm, int behavior) {
            super(fm, behavior);
        }

        //check index and return fragment accordingly
        @NonNull
        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                return new TasbeehEZehraFragment();
            } else {
                return new UnlimitedCountFragment();
            }
        }
        //total count of fragment is 2 so used 2 to return here
        @Override
        public int getCount() {
            return 2;
        }
    }