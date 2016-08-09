package es.agustruiz.hikinggo.system;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

public class MapPreferences {

    public static final String PREF_MAP_NAME = "pref_map";

    public static final String PREF_MAP_LAT = "pref_map_latitude";
    public static final String PREF_MAP_LON = "pref_map_longitude";
    public static final String PREF_MAP_ZOOM = "pref_map_zoom";
    public static final String PREF_MAP_TYPE = "pref_map_type";
    public static final String PREF_MAP_BEARING = "pref_map_bearing";
    public static final String PREF_MAP_TILT = "pref_map_tilt";


    public static final double PREF_MAP_LAT_DEFAULT = 39.8364049;
    public static final double PREF_MAP_LON_DEFAULT = -3.8382183;
    public static final float PREF_MAP_ZOOM_DEFAULT = 5;
    public static final int PREF_MAP_TYPE_DEFAULT = GoogleMap.MAP_TYPE_TERRAIN;
    public static final float PREF_MAP_BEARING_DEFAULT = 0;
    public static final float PREF_MAP_TILT_DEFAULT = 0;

    public static void saveMapState(Context context, GoogleMap map) {
        if (map != null && context != null) {
            SharedPreferences mapPrefs = context.getSharedPreferences(MapPreferences.PREF_MAP_NAME, 0);
            SharedPreferences.Editor editor = mapPrefs.edit();

            CameraPosition camera = map.getCameraPosition();
            LatLng center = camera.target;

            editor.putLong(MapPreferences.PREF_MAP_LAT, Double.doubleToRawLongBits(center.latitude));
            editor.putLong(MapPreferences.PREF_MAP_LON, Double.doubleToRawLongBits(center.longitude));
            editor.putFloat(MapPreferences.PREF_MAP_ZOOM, map.getCameraPosition().zoom);
            editor.putInt(MapPreferences.PREF_MAP_TYPE, map.getMapType());
            editor.putFloat(MapPreferences.PREF_MAP_BEARING, camera.bearing);
            editor.putFloat(MapPreferences.PREF_MAP_TILT, camera.tilt);

            editor.apply();
        }
    }

    public static void restoreMapState(Context context, GoogleMap map) {
        if (map != null && context != null) {
            SharedPreferences mapPrefs = context.getSharedPreferences(MapPreferences.PREF_MAP_NAME, 0);

            Double lat = Double.longBitsToDouble(mapPrefs.getLong(PREF_MAP_LAT,
                    Double.doubleToRawLongBits(PREF_MAP_LAT_DEFAULT)));
            Double lon = Double.longBitsToDouble(mapPrefs.getLong(PREF_MAP_LON,
                    Double.doubleToRawLongBits(PREF_MAP_LON_DEFAULT)));
            Float zoom = mapPrefs.getFloat(PREF_MAP_ZOOM, PREF_MAP_ZOOM_DEFAULT);
            Integer mapType = mapPrefs.getInt(PREF_MAP_TYPE, PREF_MAP_TYPE_DEFAULT);
            Float bearing = mapPrefs.getFloat(PREF_MAP_BEARING, PREF_MAP_BEARING_DEFAULT);
            Float tilt = mapPrefs.getFloat(PREF_MAP_TILT, PREF_MAP_TILT_DEFAULT);

            LatLng startPosition = new LatLng(lat, lon);
            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(startPosition)
                    .zoom(zoom)
                    .bearing(bearing)
                    .tilt(tilt)
                    .build();
            map.setMapType(mapType);
            map.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        }
    }

}
