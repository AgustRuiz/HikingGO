package es.agustruiz.hikinggo.presenter;

import android.content.Context;

import es.agustruiz.hikinggo.ui.activity.MainActivity;

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

    //endregion

    //region [Overriden presenter methods]

    @Override
    public void showMessage(String message) {
        this.mActivity.showMessageView(message);
    }

    //endregion
}
