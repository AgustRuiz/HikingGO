package es.agustruiz.hikinggo.ui.activity;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
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
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.agustruiz.hikinggo.R;
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

    Context mContext;

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
        initializeToolbar();
        initializeFabs();
        //initializeDrawer();
        //initializeNavigationView();

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

                PolylineOptions polyline = new PolylineOptions();
                for(LatLng point: pointsList){
                    polyline.add(point);
                    mMap.addCircle(new CircleOptions()
                            .clickable(true)
                            .zIndex(200)
                            .center(point)
                            .radius(5)
                            .fillColor(Color.argb(255, 0, 0, 255))
                            .strokeWidth(0));
                }
                polyline
                        .clickable(true)
                        .zIndex(100)
                        .width(5)
                        .color(Color.argb(255, 255, 0, 0));
                mMap.addPolyline(polyline);

                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(38.115129, -3.087572), 16));

                // End testing...

            }
        });
    }

    private void initializeToolbar() {
        //setSupportActionBar(mToolbar);
    }

    private void initializeFabs(){
        if(mFabTerrain!=null){
            mFabTerrain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mMap!=null){
                        mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                        showMessageView("Terrain map mode");
                    }else{
                        showMessageView("Maps is not ready");
                    }
                    mFabMenu.close(true);
                }
            });
        }
        if(mFabNormal !=null){
            mFabNormal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mMap!=null){
                        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                        showMessageView("Normal map mode");
                    }else{
                        showMessageView("Maps is not ready");
                    }
                    mFabMenu.close(true);
                }
            });
        }
        if(mFabSatellite !=null){
            mFabSatellite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mMap!=null){
                        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                        showMessageView("Hybrid map mode");
                    }else{
                        showMessageView("Maps is not ready");
                    }
                    mFabMenu.close(true);
                }
            });
        }
    }

    protected void configureMap(GoogleMap map) {
        map.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        if (Permission.checkLocationPermission(this)) {
            map.setMyLocationEnabled(true);
        }
    }

    protected void showMessageView(String message) {
        showMessageView(null, message);
    }

    protected void showMessageView(View view, String message) {
        if (view == null)
            view = mFabMenu;
        Snackbar.make(view, message, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    //endregion

}
