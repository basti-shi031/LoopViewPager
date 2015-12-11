package com.bzt.loopviewpager;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by SHIBW-PC on 2015/12/11.
 */
public class MyPageAdapter extends PagerAdapter {

    private List<Integer> mList;
    private Context mctx;

    public MyPageAdapter(List<Integer> List,Context context)
    {
        mList = List;
        mctx = context;
    }


    @Override
    public int getCount() {
        if (mList!= null)
            return mList.size();

        return 0;
    }

    @Override
    public boolean isViewFromObject(View view, Object o) {
        return view == o;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView item = new ImageView(mctx);
        item.setScaleType(ImageView.ScaleType.FIT_XY
        );

        //item.setBackgroundResource(mList.get(position).get);
        if (position!= -1){
            item.setImageResource(mList.get(position));
        }

        container.addView( item);
        return item;

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
