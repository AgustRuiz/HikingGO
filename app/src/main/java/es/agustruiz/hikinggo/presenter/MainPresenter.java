package es.agustruiz.hikinggo.presenter;

import android.content.Context;
import android.content.Intent;

import es.agustruiz.hikinggo.ui.activity.CompassActivity;
import es.agustruiz.hikinggo.ui.activity.MainActivity;
import es.agustruiz.hikinggo.ui.activity.MapsActivity;

public class MainPresenter implements IPresenter {

    //region [Variables]

    MainActivity mActivity;
    Context mContext;

    //endregion

    //region [Public methods]

    public MainPresenter(MainActivity mActivity) {
        this.mActivity = mActivity;
        this.mContext = mActivity.getApplicationContext();
    }

    public void startMapActivity() {
        mActivity.startActivity(new Intent(mContext, MapsActivity.class));
    }

    public void startCompassActivity() {
        mActivity.startActivity(new Intent(mContext, CompassActivity.class));
    }

    //endregion

    //region [Overriden presenter methods]

    @Override
    public void showMessage(String message) {
        this.mActivity.showMessageView(message);
    }

    //endregion
}
