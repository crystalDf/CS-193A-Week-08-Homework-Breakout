package com.star.breakout;

import android.support.v4.app.Fragment;

public class BreakoutActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return BreakoutFragment.newInstance();
    }

}
