package es.agustruiz.hikinggo.ui.activity;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.agustruiz.hikinggo.R;
import es.agustruiz.hikinggo.system.MapPreferences;
import es.agustruiz.hikinggo.system.Permission;

public class MapsActivity extends AppCompatActivity {

    @SuppressWarnings("unused")
    protected static final String LOG_TAG = MapsActivity.class.getName() + "[A]";

    //region [Binded views & Variables]

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.fab_menu)
    FloatingActionMenu mFabMenu;

    @BindView(R.id.fab_terrain)
    FloatingActionButton mFabTerrain;

    @BindView(R.id.fab_normal)
    FloatingActionButton mFabNormal;

    @BindView(R.id.fab_satellite)
    FloatingActionButton mFabSatellite;

    @Nullable
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawer;

    @Nullable
    @BindView(R.id.nav_view)
    NavigationView mNavView;

    protected Context mContext;

    protected GoogleMap mMap;


    //endregion

    // region [Overriden methods]

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        ButterKnife.bind(this);
        mContext = getApplicationContext();

        initializeMap();
        initializeFabs();

    }

    @SuppressWarnings("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case Permission.PERMISSIONS_REQUEST_FINE_LOCATION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mMap.setMyLocationEnabled(true);
                } else {
                    showMessageView(getString(R.string.msg_must_grant_location_permission));
                }
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (mFabMenu.isOpened()) {
            mFabMenu.close(true);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        MapPreferences.saveMapState(mContext, mMap);
    }

    //endregion

    //region [Private methods]

    private void initializeMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mMap = googleMap;
                configureMap(mMap);

                //TODO: This is only for testing

                // Points list
                List<LatLng> pointsList = new ArrayList<>();
                pointsList.add(new LatLng(38.113281, -3.093529));
                pointsList.add(new LatLng(38.113326, -3.093385));
                pointsList.add(new LatLng(38.113484, -3.093304));
                pointsList.add(new LatLng(38.113923, -3.093230));
                pointsList.add(new LatLng(38.113933, -3.092821));
                pointsList.add(new LatLng(38.113944, -3.091978));
                pointsList.add(new LatLng(38.113961, -3.091196));
                pointsList.add(new LatLng(38.113412, -3.090090));

                PolylineOptions polyline = new PolylineOptions()
                        .clickable(true)
                        .zIndex(100)
                        .width(10)
                        .color(Color.argb(210, 33, 150, 243));
                for (LatLng point : pointsList) {
                    polyline.add(point);
                }
                mMap.addPolyline(polyline);

                putMarker(mMap, R.drawable.marker_start, pointsList.get(0));
                putMarker(mMap, R.drawable.marker_stop, pointsList.get(pointsList.size() - 1));

                // End testing...

            }
        });
    }

    private void putMarker(GoogleMap map, int drawable, LatLng latLng) {
        Drawable d;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            d = mContext.getDrawable(drawable);
        } else {
            d = mContext.getResources().getDrawable(drawable);
        }
        Canvas canvas = new Canvas();
        Bitmap bitmap = Bitmap.createBitmap(d.getIntrinsicWidth(), d.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        canvas.setBitmap(bitmap);
        d.setBounds(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
        d.draw(canvas);
        BitmapDescriptor bd = BitmapDescriptorFactory.fromBitmap(bitmap);
        MarkerOptions markerOptions = new MarkerOptions().position(latLng)
                .title("Current Location")
                .snippet("Thinking of finding some thing...")
                .icon(bd);
        map.addMarker(markerOptions);
    }

    private void initializeFabs() {
        if (mFabTerrain != null) {
            mFabTerrain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mMap != null) {
                        mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                        showMessageView(getString(R.string.msg_terrain_map_mode));
                    } else {
                        showMessageView(getString(R.string.msg_map_not_ready));
                    }
                    mFabMenu.close(true);
                }
            });
        }
        if (mFabNormal != null) {
            mFabNormal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mMap != null) {
                        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                        showMessageView(getString(R.string.msg_normal_map_mode));
                    } else {
                        showMessageView(getString(R.string.msg_map_not_ready));
                    }
                    mFabMenu.close(true);
                }
            });
        }
        if (mFabSatellite != null) {
            mFabSatellite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mMap != null) {
                        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                        showMessageView(getString(R.string.msg_satellite_map_mode));
                    } else {
                        showMessageView(getString(R.string.msg_map_not_ready));
                    }
                    mFabMenu.close(true);
                }
            });
        }
    }

    private void configureMap(GoogleMap map) {
        MapPreferences.restoreMapState(mContext, map);
        if (Permission.checkLocationPermission(this)) {
            map.setMyLocationEnabled(true);
        }
    }

    private void showMessageView(String message) {
        showMessageView(null, message);
    }

    private void showMessageView(View view, String message) {
        if (view == null)
            view = mFabMenu;
        Snackbar.make(view, message, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    //endregion

}
