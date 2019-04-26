package com.ctech.bartucz.criminalintent;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ctech.bartucz.criminalintent.database.CrimeBaseHelper;
import com.ctech.bartucz.criminalintent.database.CrimeCursorWrapper;
import com.ctech.bartucz.criminalintent.database.CrimeDbSchema.CrimeTable;

import java.io.File;
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

    // get a link to the file location
    public File getPhotoFile(Crime crime) {
        File filesDir = mContext.getFilesDir();
        return new File(filesDir, crime.getPhotoFilename());
    }

    public List<Crime> getCrimes() {

        List<Crime> crimes = new ArrayList<>();

        // in this case we want ALL The crimes, so we don't specify a "where" clause to search for
        CrimeCursorWrapper crimeCursor = queryCrimes(null, null);

        try {
            crimeCursor.moveToFirst(); // make sure we're at the first item in the list
            while (crimeCursor.isAfterLast() != true) {
                Crime thisCrime = crimeCursor.getCrime();   // use the wrapper to get a crime object
                crimes.add(thisCrime);                      // add it to the list
                crimeCursor.moveToNext();
            }
        } finally {
            crimeCursor.close(); // we have to close our connection to the database every time
        }

        return crimes;
    }

    public Crime getCrime(UUID id) {

        // create a string out of the UUID that we can search for
        String[] searchArgs = new String[] {id.toString()};

        // find the Crime where the UUID = our search string
        CrimeCursorWrapper crimeCursor = queryCrimes(
                CrimeTable.Columns.UUID + " = ?", searchArgs);

        try {
            if (crimeCursor.getCount() == 0) {
                return null; // no Crimes in the database!
            } else {
                // there should only ever be 1 result with that id, so we can return the first result
                crimeCursor.moveToFirst();
                return crimeCursor.getCrime();
            }
        } finally {
            crimeCursor.close();
        }
    }

    // convert a Crime object into a ContentValues object which we can store in the database
    private static ContentValues getContentValues(Crime crime) {
        ContentValues myContentValues = new ContentValues();
        myContentValues.put(CrimeTable.Columns.UUID, crime.getId().toString()); // UUID -> string
        myContentValues.put(CrimeTable.Columns.TITLE, crime.getTitle());  // already a string
        myContentValues.put(CrimeTable.Columns.DATE, crime.getDate().getTime()); // store as an int
        myContentValues.put(CrimeTable.Columns.SOLVED, crime.isSolved() ? 1 : 0); // boolean -> 0/1
        myContentValues.put(CrimeTable.Columns.SUSPECT, crime.getSuspect()); // already a string

        return myContentValues;
    }

    private CrimeCursorWrapper queryCrimes(String whereClause, String[] whereArgs) {

        Cursor cursor = mDatabase.query(
                CrimeTable.NAME,
                null, // select ALL the columns
                whereClause,
                whereArgs,
                null,
                null,
                null);

        return new CrimeCursorWrapper(cursor);

    }
}

