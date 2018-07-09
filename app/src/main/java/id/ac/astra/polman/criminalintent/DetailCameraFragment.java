package id.ac.astra.polman.criminalintent;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.io.File;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailCameraFragment extends DialogFragment {
    private static final String ARG_PHOTO_FILE = "photoFile";

    private ImageView mPhotoView;
    private File mPhotoFile;

    public static DetailCameraFragment newInstance(File photoFile) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_PHOTO_FILE, photoFile);

        DetailCameraFragment fragment = new DetailCameraFragment();
        fragment.setArguments(args);

        return fragment;
    }

    public DetailCameraFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mPhotoFile = (File) getArguments().getSerializable(ARG_PHOTO_FILE);

        View view = inflater.inflate(R.layout.fragment_detail_camera, container, false);

        mPhotoView = (ImageView) view.findViewById(R.id.photo_view_dialog);

        if (mPhotoFile == null || !mPhotoFile.exists()) {
            mPhotoView.setImageDrawable(null);
        } else {
            Bitmap bitmap = PictureUtils.getScaledBitmap(mPhotoFile.getPath(), getActivity());
            mPhotoView.setImageBitmap(bitmap);
        }

        return view;
    }

}
