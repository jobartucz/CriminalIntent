package com.ctech.bartucz.criminalintent;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.ctech.bartucz.criminalintent.database.CrimeBaseHelper;
import com.ctech.bartucz.criminalintent.database.CrimeDbSchema;
import com.ctech.bartucz.criminalintent.database.CrimeDbSchema.CrimeTable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

// the com.ctech.bartucz.criminalintent.CrimeLab singleton class
public class CrimeLab {

    private static CrimeLab sCrimeLab;

    private Context mContext;
    private SQLiteDatabase mDatabase;


    public static CrimeLab get(Context context) {
        if (sCrimeLab == null) {
            sCrimeLab = new CrimeLab(context);
        }
        return sCrimeLab;
    }

    private CrimeLab(Context context) {
        // the Context is where the database is stored while the app is running
        mContext = context.getApplicationContext();
        mDatabase = new CrimeBaseHelper(mContext).getWritableDatabase();

    }

    public void addCrime(Crime c) {

        // fill a ContentValues object with the data from the Crime and insert it into the database
        ContentValues newValues = getContentValues(c);
        mDatabase.insert(CrimeTable.NAME, null, newValues);

    }

    public void updateCrime(Crime c) {
        String crimeId = c.getId().toString();
        ContentValues newValues = getContentValues(c);

        // we need to send a search string AND the arguments you want it to match
        // in this case, we want to find the row WHERE the UUID = the CrimeId
        String searchString = CrimeTable.Columns.UUID + " = ?";
        String[] searchArgs = new String[] { crimeId };

        mDatabase.update(CrimeTable.NAME, newValues, searchString, searchArgs);

    }

    public List<Crime> getCrimes() {

        List<Crime> crimes = new ArrayList<>();
        return crimes;
    }

    public Crime getCrime(UUID id) {
        return null;
    }

    // convert a Crime object into a ContentValues object which we can store in the database
    private static ContentValues getContentValues(Crime crime) {
        ContentValues myContentValues = new ContentValues();
        myContentValues.put(CrimeTable.Columns.UUID, crime.getId().toString());
        myContentValues.put(CrimeTable.Columns.TITLE, crime.getTitle());
        myContentValues.put(CrimeTable.Columns.DATE, crime.getDate().toString());
        myContentValues.put(CrimeTable.Columns.SOLVED, crime.isSolved() ? 1 : 0);

        return myContentValues;
    }
}

