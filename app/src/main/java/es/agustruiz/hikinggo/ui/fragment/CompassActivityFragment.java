package es.agustruiz.hikinggo.ui.fragment;

import android.annotation.SuppressLint;
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
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.agustruiz.hikinggo.R;
import es.agustruiz.hikinggo.system.sensor.CompassListener;

/**
 * A placeholder fragment containing a simple view.
 */
public class CompassActivityFragment extends Fragment {

    @BindView(R.id.compass_base)
    AppCompatImageView mCompassBase;

    @BindView(R.id.compass_arrow)
    AppCompatImageView mCompassArrow;

    @BindView(R.id.compass_text)
    TextView mCompassText;

    CompassListener mCompassListener = null;

    Context mContext;

    public CompassActivityFragment() {
    }

    //region [Overriden methods]

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_compass, container, false);
        ButterKnife.bind(this, view);
        mContext = getContext();
        initializeViews();
        initializeCompassListener();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        mCompassListener.start();
    }

    @Override
    public void onPause() {
        super.onPause();
        mCompassListener.stop();
    }

    //endregion

    //region [Private methods]

    private void initializeViews() {
        //TODO:Where is the shadow?
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ViewCompat.setElevation(mCompassBase, 10);
        }
    }

    private void initializeCompassListener() {
        mCompassListener = new CompassListener(mContext);
        mCompassListener.setCompassChangedListener(new CompassListener.OnCompassChangedListener() {
            @Override
            public void onCompassChanged(int degree) {
                setCompassValue(degree);
            }
        });
    }

    @SuppressLint("DefaultLocale")
    private void setCompassValue(float degree) {
        degree = degree % 360;
        mCompassText.setText(String.format("%dº", Math.round(degree)));
        mCompassArrow.setRotation(-1 * degree);
    }

    //endregion
}
