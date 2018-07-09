package id.ac.astra.polman.criminalintent;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.util.List;

/**
 * Created by Jihad044 on 13/03/2018.
 */

public class CrimeListFragment extends Fragment {
    private static final String TAG = "tag";
    private RecyclerView mCrimeRecyclerView;
    private CrimeAdapter mAdapter;
    private boolean mSubtitleVisible;

    private TextView mAddText;
    private Button mAddButton;

    private static final String SAVED_SUBTITLE_VISIBLE = "subtitle";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crime_list, container, false);
        mCrimeRecyclerView = (RecyclerView) view.findViewById(R.id.crime_recycler_view);
        mCrimeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        if(savedInstanceState!=null)
        {
            mSubtitleVisible = savedInstanceState.getBoolean(SAVED_SUBTITLE_VISIBLE);
        }

        mAddText = (TextView) view.findViewById(R.id.crime_text);
        mAddButton = (Button) view.findViewById(R.id.add_crime_button);
        mAddButton.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Log.d(TAG, "onClick");
              Crime crime = new Crime();
              CrimeLab.get(getActivity()).addCrime(crime);

              Intent intent = CrimePagerActivity.newIntent(getActivity(), crime.getId());
              startActivity(intent);
          }
        });

        CrimeLab crimeLab = CrimeLab.get(getActivity());
        int crimeCount = crimeLab.getCrimes().size();
        if(crimeCount==0)
        {
            mCrimeRecyclerView.setVisibility(View.GONE);
//            mLinearLayout.setVisibility(View.VISIBLE);
            mAddText.setVisibility(View.VISIBLE);
            mAddButton.setVisibility(View.VISIBLE);
        }

        updateUI();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        updateUI();
    }

    private void updateUI()
    {
        CrimeLab crimeLab = CrimeLab.get(getActivity());
        List<Crime> crimes = crimeLab.getCrimes();

        if(mAdapter == null)
        {
            mAdapter = new CrimeAdapter(crimes);
            mCrimeRecyclerView.setAdapter(mAdapter);
        }
        else
        {
            mAdapter.setCrimes(crimes);
            mAdapter.notifyDataSetChanged();
        }

        int crimeCount = crimeLab.getCrimes().size();
        if(crimeCount==0)
        {
            mCrimeRecyclerView.setVisibility(View.GONE);
//            mLinearLayout.setVisibility(View.VISIBLE);
            mAddText.setVisibility(View.VISIBLE);
            mAddButton.setVisibility(View.VISIBLE);
        }
        else
        {
            mCrimeRecyclerView.setVisibility(View.VISIBLE);
//            mLinearLayout.setVisibility(View.GONE);
            mAddText.setVisibility(View.GONE);
            mAddButton.setVisibility(View.GONE);
        }

        updateSubtitle();
    }

    private class CrimeHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView mTitleTextView;
        private TextView mDateTextView;
        private Crime mCrime;
        private ImageView mSolvedCrime;

        public CrimeHolder(LayoutInflater layoutInflater, ViewGroup viewGroup)
        {
            super(layoutInflater.inflate(R.layout.list_item_crime, viewGroup, false));

            itemView.setOnClickListener(this); //jangan sampe lupa agar bisa untuk onclick ketika di click di recycle View

            mTitleTextView = (TextView) itemView.findViewById(R.id.crime_title);
            mDateTextView = (TextView) itemView.findViewById(R.id.crime_date);
            mSolvedCrime = (ImageView) itemView.findViewById(R.id.crime_solved);
        }

        public void bind(Crime crime)
        {
            mCrime = crime;
            mTitleTextView.setText(mCrime.getTitle());
            android.text.format.DateFormat dateFormat = new android.text.format.DateFormat();
            mDateTextView.setText(dateFormat.format("EEEE, MMM d, yyyy" ,mCrime.getDate()));
            mSolvedCrime.setVisibility(crime.isSolved() ? View.VISIBLE : View.GONE);
        }

        @Override
        public void onClick(View view) {
//            Toast.makeText(getActivity(), mCrime.getTitle()+" clicked!", Toast.LENGTH_SHORT).show();
//            Intent intent = new Intent(getActivity(), CrimeActivity.class);

//            Intent intent = CrimeActivity.newIntent(getActivity(), mCrime.getId());
//            startActivity(intent);

            Intent intent = CrimePagerActivity.newIntent(getActivity(), mCrime.getId());
            startActivity(intent);
        }
    }

    private class CrimeAdapter extends RecyclerView.Adapter<CrimeHolder>
    {
        private List<Crime> mCrimes;

        public CrimeAdapter(List<Crime> crimes)
        {
            mCrimes = crimes;
        }

        @Override
        public CrimeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new CrimeHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(CrimeHolder holder, int position) {
            Crime crime = mCrimes.get(position);
            holder.bind(crime);
        }

        @Override
        public int getItemCount() {
            return mCrimes.size();
        }

        public void setCrimes(List<Crime> crimes)
        {
            mCrimes = crimes;
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_crime_list, menu);

        MenuItem subtitleItem = menu.findItem(R.id.show_subtitle);
        if(mSubtitleVisible)
        {
            subtitleItem.setTitle(R.string.hide_subtitle);
        }
        else
        {
            subtitleItem.setTitle(R.string.show_subtitle);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.new_crime:
                Crime crime = new Crime();
                CrimeLab.get(getActivity()).addCrime(crime);
                Intent intent = CrimePagerActivity.newIntent(getActivity(), crime.getId());
                startActivity(intent);
                return true;
            case R.id.delete_crime:
                CrimeLab crimeLab = CrimeLab.get(getActivity());
                int crimeCount = crimeLab.getCrimes().size();
                if(crimeCount!=0)
                {
//                    Crime crime1 = new Crime();
                    CrimeLab.get(getActivity()).removeCrime(crimeCount-1);
                    updateUI();
                }
                else
                {
                    Toast.makeText(getActivity(), "Tidak ada data!", Toast.LENGTH_SHORT).show();
                }
                //getActivity().finish();
//                Intent intent1 = CrimePagerActivity.newIntent(getActivity(), crime1.getId());
//                startActivity(intent1);
                return true;
            case R.id.show_subtitle:
                mSubtitleVisible = !mSubtitleVisible;
                updateSubtitle();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void updateSubtitle()
    {
        CrimeLab crimeLab = CrimeLab.get(getActivity());
        int crimeCount = crimeLab.getCrimes().size();
        String subtitle = getString(R.string.subtitle_format, crimeCount);

        if(!mSubtitleVisible)
        {
            subtitle = null;
        }

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.getSupportActionBar().setSubtitle(subtitle);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(SAVED_SUBTITLE_VISIBLE, mSubtitleVisible);
    }
}
