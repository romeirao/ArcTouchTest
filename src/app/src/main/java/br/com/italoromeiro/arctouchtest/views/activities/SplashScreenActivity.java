package br.com.italoromeiro.arctouchtest.views.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by italo on 10/08/16.
 */
public class SplashScreenActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ListActivity_.intent(this).start();
        finish();
    }
}
