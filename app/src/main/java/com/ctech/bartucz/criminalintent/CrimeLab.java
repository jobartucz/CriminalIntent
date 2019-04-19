package com.ctech.bartucz.criminalintent;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.ctech.bartucz.criminalintent.database.CrimeBaseHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

// the com.ctech.bartucz.criminalintent.CrimeLab singleton class
public class CrimeLab {

    private static CrimeLab sCrimeLab;

    private List<Crime> mCrimes;
    private Context mContext;
    private SQLiteDatabase mDatabase;


    public static CrimeLab get(Context context) {
        if (sCrimeLab == null) {
            sCrimeLab = new CrimeLab(context);
        }
        return sCrimeLab;
    }

    private CrimeLab(Context context) {
        mCrimes = new ArrayList<>();

        // the Context is where the database is stored while the app is running
        mContext = context.getApplicationContext();
        mDatabase = new CrimeBaseHelper(mContext).getWritableDatabase();

    }

    public void addCrime(Crime c) {
        mCrimes.add(c);
    }

    public List<Crime> getCrimes() {
        return mCrimes;
    }

    public Crime getCrime(UUID id) {
        for (Crime thisCrime : mCrimes) {
            if (thisCrime.getId().equals(id)) {
                return thisCrime;
            }
        }

        return null;
    }
}

