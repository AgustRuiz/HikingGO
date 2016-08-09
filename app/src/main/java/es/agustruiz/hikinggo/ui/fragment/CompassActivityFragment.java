package es.agustruiz.hikinggo.ui.fragment;

import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.AppCompatImageView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.agustruiz.hikinggo.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class CompassActivityFragment extends Fragment {

    @BindView(R.id.compass_base)
    AppCompatImageView mCompassBase;

    Context mContext;

    public CompassActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_compass, container, false);
        ButterKnife.bind(this, view);
        mContext = getContext();
        initializeViews();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    //region [Private methods]

    private void initializeViews(){
        //TODO:Where is the shadow?
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ViewCompat.setElevation(mCompassBase, 10);
        }
    }

    //endregion
}
