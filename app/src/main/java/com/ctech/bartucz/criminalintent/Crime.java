package com.ctech.bartucz.criminalintent;

import java.util.Date;
import java.util.UUID;

public class Crime {

    private UUID mId;       // the crime's unique ID
    private String mTitle;  // title of the crime
    private Date mDate;     // date of the crime
    private boolean mSolved; // has the crime been solved?
    private String mSuspect; // who did it?

    // The user can now specify their own UUID
    public Crime(UUID id) {
        mId = id;
        mDate = new Date(); // default the crime date to right now
    }

    // automatically create a guaranteed-unique ID if not provided.
    public Crime() {
        this(UUID.randomUUID());
    }

    public UUID getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public boolean isSolved() {
        return mSolved;
    }

    public void setSolved(boolean solved) {
        mSolved = solved;
    }

    public String getSuspect() {
        return mSuspect;
    }

    public void setSuspect(String suspect) {
        mSuspect = suspect;
    }


}
