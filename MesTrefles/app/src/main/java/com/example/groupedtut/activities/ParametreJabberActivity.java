package com.example.groupedtut.activities;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;

/**
 * Created by Gael on 24/02/2018.
 */

public class ParametreJabberActivity extends BasicTrefleActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_param_jabber);
        super.onCreate(savedInstanceState);
    }

    @Override
    public DrawerLayout getMainDrawerLayout() {
        return null;
    }
}
