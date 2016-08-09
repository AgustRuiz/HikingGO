package es.agustruiz.hikinggo.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.agustruiz.hikinggo.R;
import es.agustruiz.hikinggo.presenter.MainPresenter;

public class MainActivity extends AppCompatActivity {

    protected static final String LOG_TAG = MainActivity.class.getName() + "[A]";

    //region [Binded views & Variables]

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.fab)
    FloatingActionButton mFab;

    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawer;

    @BindView(R.id.nav_view)
    NavigationView mNavView;

    MainPresenter mPresenter;
    Context mContext;

    //endregion

    //region [Activity methods]
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mContext = getApplicationContext();
        mPresenter = new MainPresenter(this);
        initializeToolbar();
        initializeFab();
        initializeDrawer();
        initializeNavigationView();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_settings:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //endregions

    //region [Public methods]

    public void showMessageView(String message){
        showMessageView(null, message);
    }

    //endregion

    //region [Protected methods]

    protected void initializeToolbar() {
        setSupportActionBar(mToolbar);
    }

    protected void initializeFab() {
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showMessageView(view, "Do action here");
            }
        });
    }

    protected void initializeDrawer() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.addDrawerListener(toggle);
        toggle.syncState();
    }

    protected void initializeNavigationView() {
        mNavView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                Log.d(LOG_TAG, "Here I am");


                int id = item.getItemId();
                switch (id) {
                    case R.id.nav_map:
                        mPresenter.startMapActivity();
                        break;
                    case R.id.nav_compass:
                        mPresenter.startCompassActivity();
                        break;
                    case R.id.nav_home:
                    case R.id.nav_routes:
                    case R.id.nav_settings:
                    case R.id.nav_about_this:
                        showMessageView("No action yet...");
                        break;
                }
                mDrawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }

    protected void showMessageView(View view, String message){
        if(view == null)
            view = mFab;
        Snackbar.make(view, message, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    //endregion

}
