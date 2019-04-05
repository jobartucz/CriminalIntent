import android.content.Context;

import com.ctech.bartucz.criminalintent.Crime;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

// the CrimeLab singleton class
public class CrimeLab {

    private static CrimeLab sCrimeLab;

    private List<Crime> mCrimes;

    public static CrimeLab get(Context context) {
        if (sCrimeLab == null) {
            sCrimeLab = new CrimeLab(context);
        }
        return sCrimeLab;
    }

    private CrimeLab(Context context) {
        mCrimes = new ArrayList<>();
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

