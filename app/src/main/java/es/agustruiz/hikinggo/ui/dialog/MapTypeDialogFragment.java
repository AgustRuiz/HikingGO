package es.agustruiz.hikinggo.ui.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.LayoutInflater;
import android.view.View;

import com.google.android.gms.maps.GoogleMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.agustruiz.hikinggo.R;

public class MapTypeDialogFragment extends DialogFragment {

    public static final String TAG = "MapTypeDialogFragment";

    //ragion [Views and variables]

    @BindView(R.id.nav_satellite)
    LinearLayoutCompat mSatellite;

    @BindView(R.id.nav_terrain)
    LinearLayoutCompat mTerrain;

    @BindView(R.id.nav_normal)
    LinearLayoutCompat mNormal;

    OnMapTypeChangedListener mOnMapTypeChangedListener;
    int mCurrentMapType = 0;

    //endregion

    //region [Public methods]

    public MapTypeDialogFragment(int currentType, OnMapTypeChangedListener onMapTypeChangedListener) {
        mCurrentMapType = currentType;
        mOnMapTypeChangedListener = onMapTypeChangedListener;
    }

    //endregion

    //region [DialogFragment methods]

    @SuppressWarnings("NullableProblems")
    @SuppressLint("InflateParams")
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_map_type, null);
        ButterKnife.bind(this, view);
        builder.setView(view)
                .setNegativeButton(R.string.action_cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
        Dialog dialog = builder.create();
        configureDialogFragment(dialog);
        return dialog;
    }

    //endregion

    //region [Private methods]

    private void configureDialogFragment(final Dialog dialog) {
        switch (mCurrentMapType){
            case GoogleMap.MAP_TYPE_NORMAL:
                mNormal.setBackgroundColor(getContext().getColor(R.color.grey300));
                break;
            case GoogleMap.MAP_TYPE_TERRAIN:
                mTerrain.setBackgroundColor(getContext().getColor(R.color.grey300));
                break;
            case GoogleMap.MAP_TYPE_HYBRID:
                mSatellite.setBackgroundColor(getContext().getColor(R.color.grey300));
                break;
        }
        mSatellite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnMapTypeChangedListener.onMapTypeChanged(GoogleMap.MAP_TYPE_HYBRID);
                dialog.dismiss();
            }
        });
        mTerrain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnMapTypeChangedListener.onMapTypeChanged(GoogleMap.MAP_TYPE_TERRAIN);
                dialog.dismiss();
            }
        });
        mNormal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnMapTypeChangedListener.onMapTypeChanged(GoogleMap.MAP_TYPE_NORMAL);
                dialog.dismiss();
            }
        });
    }

    //endregion

    //region [OnMapTypeChangedListener]

    public interface OnMapTypeChangedListener{
        void onMapTypeChanged(int mapType);
    }

    public void setOnMapTypeChangedListener(OnMapTypeChangedListener listener){
        mOnMapTypeChangedListener = listener;
    }

    //endregion

}
