package id.ac.astra.polman.criminalintent;

import android.app.Fragment;

/**
 * Created by Jihad044 on 13/03/2018.
 */

public class CrimeListActivity extends SingleFragmentActivity {
    protected Fragment createFragment()
    {
        return new CrimeListFragment();
    }
}
