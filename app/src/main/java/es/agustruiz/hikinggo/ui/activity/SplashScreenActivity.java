package es.agustruiz.hikinggo.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashScreenActivity extends AppCompatActivity {

    protected static final String LOG_TAG = SplashScreenActivity.class.getName() + "[A]";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Do here some stuff: e.g.: initialize services...

        startActivity(new Intent(this, MainActivity.class));
        finish();

    }
}
