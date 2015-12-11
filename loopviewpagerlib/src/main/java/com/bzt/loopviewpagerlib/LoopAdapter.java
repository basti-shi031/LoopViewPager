package com.bzt.loopviewpagerlib;

import android.support.v4.view.PagerAdapter;
import android.view.View;

/**
 * Created by SHIBW-PC on 2015/12/10.
 */
public class LoopAdapter extends PagerAdapter {

    private PagerAdapter mAdapter;

    public LoopAdapter (PagerAdapter adapter){
        mAdapter = adapter;
    }

    @Override
    public int getCount() {
        //原来：012
        //现在：20120
        return mAdapter.getCount()+2;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return false;
    }
}
