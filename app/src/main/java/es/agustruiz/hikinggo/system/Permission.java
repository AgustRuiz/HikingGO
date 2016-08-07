package es.agustruiz.hikinggo.system;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;

public class Permission {

    protected static final String LOG_TAG = Permission.class.getName();

    public static final int PERMISSIONS_REQUEST_FINE_LOCATION = 100;

    //region [Public methods]

    public static boolean checkLocationPermission(Activity activity) {
        Context context = activity.getApplicationContext();
        if (isAccessFineLocationPermissionGranted(context) && isCoarseLocationPermissionGranted(context)) {
            return true;
        } else {
            ActivityCompat.requestPermissions(
                    activity,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_FINE_LOCATION);
            return false;
        }

    }


    //endregion

    //region [Protected methods]

    protected static boolean isAccessFineLocationPermissionGranted(Context context) {
        return ActivityCompat.checkSelfPermission(context,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    protected static boolean isCoarseLocationPermissionGranted(Context context) {
        return ActivityCompat.checkSelfPermission(context,
                Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    //endregion

}
