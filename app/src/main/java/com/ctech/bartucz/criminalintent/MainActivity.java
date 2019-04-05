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
        Fragment myFragment = fm.findFragmentById(R.id.fragment_container);

        // the first time through, the fragment will be null, so create it
        if (myFragment == null) {
            myFragment = new CrimeFragment();
            fm.beginTransaction()
                    .add(R.id.fragment_container, myFragment)
                    .commit();
        }
    }
}
