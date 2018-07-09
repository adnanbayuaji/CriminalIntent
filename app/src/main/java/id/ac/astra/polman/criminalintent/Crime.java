package id.ac.astra.polman.criminalintent;

import java.util.Date;
import java.util.UUID;

/**
 * Created by Jihad044 on 07/03/2018.
 */

public class Crime {
    private UUID mId;
    private String mTitle;
    private Date mDate;
    private boolean mSolved;
    private String mSuspect;
    private String mSuspectNum;

    public Crime() {
//        mId = UUID.randomUUID();
//        mDate = new Date();
        this(UUID.randomUUID());
    }

    public Crime(UUID id) {
        mId = id;
        mDate = new Date();
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

    public String getSuspectNum() {
        return mSuspectNum;
    }

    public void setSuspectNum(String suspectNum) {
        mSuspectNum = suspectNum;
    }

    public String getPhotoFilename()
    {
        return "IMG_" +getId().toString()+".jpg";
    }
}
