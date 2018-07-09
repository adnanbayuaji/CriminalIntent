package id.ac.astra.polman.criminalintent;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import id.ac.astra.polman.criminalintent.database.CrimeBaseHelper;
import id.ac.astra.polman.criminalintent.database.CrimeCursorWrapper;
import id.ac.astra.polman.criminalintent.database.CrimeDbSchema;
import id.ac.astra.polman.criminalintent.database.CrimeDbSchema.CrimeTable;


/**
 * Created by Jihad044 on 13/03/2018.
 */

public class CrimeLab {
    private static CrimeLab sCrimeLab;

    private List<Crime> mCrimes;

    private Context mContext;
    private SQLiteDatabase mDatabase;

    public static CrimeLab get(Context context)
    {
        if(sCrimeLab == null)
        {
            sCrimeLab = new CrimeLab(context);
        }
        return sCrimeLab;
    }

    private CrimeLab(Context context)
    {
        mContext = context.getApplicationContext();
        mDatabase = new CrimeBaseHelper(mContext).getWritableDatabase();
//        mCrimes = new ArrayList<>();
    }

    ///////////////////////////////
    /////////AMBIL BANYAK BARIS DATA
    /////////////////////////////////
    public List<Crime> getCrimes()
    {
//        return mCrimes;
        List<Crime> crimes = new ArrayList<>();
        CrimeCursorWrapper cursor = queryCrimes(null, null);
        try
        {
            cursor.moveToFirst();
            while(!cursor.isAfterLast())
            {
                crimes.add(cursor.getCrime());
                cursor.moveToNext();
            }
        }
        finally {
            cursor.close();
        }
        return crimes;
    }

    public void addCrime(Crime c)
    {
//        mCrimes.add(c);
        ContentValues values = getContentValues(c);
        mDatabase.insert(CrimeTable.NAME, null, values);
    }

    public void removeCrime(int c)
    {
//        mCrimes.remove(c);
        mDatabase.delete(CrimeTable.NAME, null, null);
    }

    public void removeCrime(Crime crime)
    {
        String uuidString = crime.getId().toString();
        ContentValues values = getContentValues(crime);
        mDatabase.delete(CrimeTable.NAME, CrimeTable.Cols.UUID + " = ?", new String[]{uuidString});
    }

    ///////////////////////////////
    /////////AMBIL SATU BARIS DATA
    /////////////////////////////////
    public Crime getCrime(UUID id)
    {
        CrimeCursorWrapper cursor = queryCrimes(CrimeTable.Cols.UUID+" = ?",
                new String[] {id.toString()
        });
        try
        {
            if(cursor.getCount() == 0)
            {
                return null;
            }
            cursor.moveToFirst();
            return cursor.getCrime();
        }
        finally {
            cursor.close();
        }
    }

    public File getPhotoFile(Crime crime)
    {
        File fileDir = mContext.getFilesDir();
        return new File(fileDir, crime.getPhotoFilename());
    }

    public void updateCrime(Crime crime)
    {
        String uuidString = crime.getId().toString();
        ContentValues values = getContentValues(crime);
        mDatabase.update(CrimeTable.NAME, values, CrimeTable.Cols.UUID + " = ?", new String[]{uuidString});
    }

    public void deleteCrime(Crime crime)
    {
        String uuidString = crime.getId().toString();
        ContentValues values = getContentValues(crime);
        mDatabase.delete(CrimeTable.NAME, CrimeTable.Cols.UUID + " = ?", new String[]{uuidString});
    }

    private static ContentValues getContentValues(Crime crime)
    {
        ContentValues values = new ContentValues();
        values.put(CrimeTable.Cols.UUID, crime.getId().toString());
        values.put(CrimeTable.Cols.TITLE, crime.getTitle());
        values.put(CrimeTable.Cols.DATE, crime.getDate().getTime());
        values.put(CrimeTable.Cols.SOLVED, crime.isSolved() ? 1 : 0);
        values.put(CrimeTable.Cols.SUSPECT, crime.getSuspect());
        values.put(CrimeTable.Cols.SUSPECTNUM, crime.getSuspectNum());
        return values;
    }

    private CrimeCursorWrapper queryCrimes(String whereClause, String[] whereArgs)
    {
        Cursor cursor = mDatabase.query(
                CrimeTable.NAME,
                null, //columns
                whereClause,
                whereArgs,
                null, //qroupBy
                null,   //having
                null    //orderby
        );
//        return cursor;
        return new CrimeCursorWrapper(cursor);
    }
}
