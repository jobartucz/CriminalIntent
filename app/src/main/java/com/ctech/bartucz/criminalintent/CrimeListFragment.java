package com.ctech.bartucz.criminalintent;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class CrimeListFragment extends Fragment {

    private RecyclerView mCrimeRecyclerView;
    private CrimeAdapter mAdapter;
    private boolean mSubtitleVisible;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true); // tell the Fragment that it has an options menu
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View myView = inflater.inflate(R.layout.fragment_crime_list, container, false);

        mCrimeRecyclerView = myView.findViewById(R.id.crime_recycler_view);
        mCrimeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        return myView;
    }


    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_crime_list, menu);

        MenuItem subtitleItem = menu.findItem(R.id.show_subtitle);
        if (mSubtitleVisible) {
            subtitleItem.setTitle(R.string.hide_subtitle);
        } else {
            subtitleItem.setTitle(R.string.show_subtitle);
        }
    }

    // this method automatically gets called if the user selects a menu item.
    // its job is to figure out what was selected and deal appropriately
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.new_crime:
                Crime newCrime = new Crime();
                CrimeLab.get(getActivity()).addCrime(newCrime);
                Intent myIntent = CrimePagerActivity.newIntent(getActivity(), newCrime.getId());
                startActivity(myIntent);
                return true;
            case R.id.show_subtitle:

                // toggle the visibility of the subtitle
                if (mSubtitleVisible) {
                    mSubtitleVisible = false;
                } else {
                    mSubtitleVisible = true;
                }
                // invalidate the options menu so that it forces a re-create
                getActivity().invalidateOptionsMenu();
                updateSubtitle();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    // create a subtitle that shows the number of crimes (if it fits)
    private void updateSubtitle() {
        CrimeLab crimeLab = CrimeLab.get(getActivity());
        int crimeCount = crimeLab.getCrimes().size();
        // pass in the crimeCount and format it properly with plurals
        String subtitle = getResources().getQuantityString(R.plurals.subtitle_plural, crimeCount, crimeCount)

        // if it shouldn't be visible, just set it to null
        if (!mSubtitleVisible) {
            subtitle = null;
        }

        AppCompatActivity thisActivity = (AppCompatActivity) getActivity();
        thisActivity.getSupportActionBar().setSubtitle(subtitle);
    }


    private void updateUI() {
        // get the list of Crimes from the CrimeLab
        CrimeLab crimeLab = CrimeLab.get(getActivity());
        List<Crime> crimes = crimeLab.getCrimes();

        // if this is the first time we are updating the UI, create a new adapter, otherwise, just update it
        if (mAdapter == null) {
            mAdapter = new CrimeAdapter(crimes);
            mCrimeRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.notifyDataSetChanged();
        }

        updateSubtitle();
    }


    private class CrimeHolder extends RecyclerView.ViewHolder
        implements View.OnClickListener {

        private TextView mTitleTextView;
        private TextView mDateTextView;
        private ImageView mSolvedImageView;

        private Crime mCrime;
        private static final String EXTRA_CRIME_ID = "com.ctech.bartucz.criminalintent.crime_id";

        public CrimeHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_crime, parent, false));

            mTitleTextView = itemView.findViewById(R.id.crime_title);
            mDateTextView = itemView.findViewById(R.id.crime_date);

            mSolvedImageView = itemView.findViewById(R.id.crime_solved);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            // we used this to test the click
            /* Toast.makeText(getActivity(),
                    mCrime.getTitle() + " clicked!",
                    Toast.LENGTH_SHORT).show(); */

            // create a new intent that automatically stores the crimeId
            Intent myIntent = CrimePagerActivity.newIntent(getActivity(), mCrime.getId());
            startActivity(myIntent);
        }

        public void bind(Crime crime) {
            mCrime = crime;
            mTitleTextView.setText(mCrime.getTitle());
            mDateTextView.setText(mCrime.getDate().toString());

            mSolvedImageView.setVisibility(crime.isSolved() ? View.VISIBLE : View.GONE);
        }


    }

    private class CrimeAdapter extends RecyclerView.Adapter<CrimeHolder> {
        private List<Crime> mCrimes;

        public CrimeAdapter(List<Crime> crimes) {
            mCrimes = crimes;
        }

        @NonNull
        @Override
        public CrimeHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            LayoutInflater myLayoutInflater = LayoutInflater.from(getActivity());
            return new CrimeHolder(myLayoutInflater, viewGroup);
        }

        @Override
        public void onBindViewHolder(@NonNull CrimeHolder crimeHolder, int position) {
            Crime myCrime = mCrimes.get(position);
            crimeHolder.bind(myCrime);
        }

        @Override
        public int getItemCount() {
            return mCrimes.size();
        }
    }
}

