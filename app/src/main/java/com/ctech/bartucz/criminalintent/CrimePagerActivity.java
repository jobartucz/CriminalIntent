package com.ctech.bartucz.criminalintent;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.List;
import java.util.UUID;

public class CrimePagerActivity extends AppCompatActivity {

    // This is just a constant string we will use to set and get the crimeId in our Intent
    private static final String EXTRA_CRIME_ID = "com.ctech.bartucz.criminalintent.crime_id";

    private ViewPager mViewPager;
    private List<Crime> mCrimes;

    public static Intent newIntent(Context packageContext, UUID crimeId) {
        Intent myIntent = new Intent(packageContext, CrimePagerActivity.class);
        myIntent.putExtra(EXTRA_CRIME_ID, crimeId);
        return myIntent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crime_pager);

        // Get the crimeId that was passed to us from the CrimeList via the Intent
        UUID crimeId = (UUID) getIntent().getSerializableExtra(EXTRA_CRIME_ID);

        // set the layout for the viewPager to be this view
        mViewPager = findViewById(R.id.crime_view_pager);

        // get the list of Crimes (to scroll through)
        mCrimes = CrimeLab.get(this).getCrimes();

        // Set up the Adapter so that it can create a CrimeFragment for a specific Crime
        FragmentManager myFragmentManager = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(myFragmentManager) {
            @Override
            public Fragment getItem(int position) {
                Crime myCrime = mCrimes.get(position);
                return CrimeFragment.newInstance(myCrime.getId());
            }

            @Override
            public int getCount() {
                return mCrimes.size();
            }
        });

    }
}
