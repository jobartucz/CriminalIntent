package com.ctech.bartucz.criminalintent;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import java.util.UUID;


public class CrimeActivity extends SingleFragmentActivity {

    // This is just a constant string we will use to set and get the crimeId in our Intent
    private static final String EXTRA_CRIME_ID = "com.ctech.bartucz.criminalintent.crime_id";

    public static Intent newIntent(Context packageContext, UUID crimeId) {
        Intent myIntent = new Intent(packageContext, CrimeActivity.class);
        myIntent.putExtra(EXTRA_CRIME_ID, crimeId);
        return myIntent;
    }

    @Override
    protected Fragment createFragment() {

        return new CrimeFragment();

        // Get the crimeId that was passed to us from the CrimeList via the Intent
        UUID crimeId = (UUID) getIntent().getSerializableExtra(EXTRA_CRIME_ID);

        // Call the "NewInstance" method which will create a new CrimeFragment and store the crimeId inside
        return CrimeFragment.newInstance(crimeId);
    }
}
