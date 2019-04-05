package com.ctech.bartucz.criminalintent;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fm = getSupportFragmentManager();
        Fragment crimeListFragment = fm.findFragmentById(R.id.fragment_container);

        // the first time through, the fragment will be null, so create it
        if (crimeListFragment == null) {
            crimeListFragment = new CrimeFragment();
            fm.beginTransaction()
                    .add(R.id.fragment_container, crimeListFragment)
                    .commit();
        }
    }
}
