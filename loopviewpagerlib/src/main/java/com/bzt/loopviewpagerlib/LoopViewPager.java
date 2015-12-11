package com.bzt.loopviewpagerlib;

import android.content.Context;
import android.database.DataSetObserver;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by SHIBW-PC on 2015/12/10.
 */
public class LoopViewPager extends ViewPager {

    private boolean isScrolling = true;
    private MyPagerAdapter myPagerAdapter;

    public LoopViewPager(Context context) {
        this(context, null);
    }

    public LoopViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOnPageChangeListener(null);
    }

    @Override
    public void setAdapter(PagerAdapter adapter) {
        myPagerAdapter = new MyPagerAdapter(adapter);
        super.setAdapter(myPagerAdapter);
        //直接跳到index = 1的item，这个Item是真正的第0个Item
        setCurrentItem(1);
    }

    public void setIsScroll(boolean isScroll){
        this.isScrolling = isScroll;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        if (!isScrolling)
            return false;

        return super.onTouchEvent(ev);
    }

    @Override
    public void setOnPageChangeListener(OnPageChangeListener listener) {
        super.setOnPageChangeListener(new MyPageChangeListener(listener));
    }

    private class MyPageChangeListener implements OnPageChangeListener {

        private OnPageChangeListener mListener;
        private int mPosition;

        public MyPageChangeListener(OnPageChangeListener listener){
            mListener = listener;
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            if (mListener != null){
                mListener.onPageScrolled(position,positionOffset,positionOffsetPixels);
            }
        }

        @Override
        public void onPageSelected(int position) {
            mPosition = position;
            if (mListener != null){
                mListener.onPageSelected(position);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            if (mListener != null){
                mListener.onPageScrollStateChanged(state);
            }
            //页面不在被拖动
            if (state == ViewPager.SCROLL_STATE_IDLE){
                if (mPosition == myPagerAdapter.getCount()-1){
                     /*
                        *如果此时已经在最后一个Item，则立即切换到第1个Item
                        * 说明：mAdapter中的元素为{Item3,Item1,Item2,Item3,Item1}
                        *
                        * 切换到最后一个Item1时，立即切换到第一个Item1
                        * */
                    setCurrentItem(1,false);
                }else if(mPosition == 0){
                    /*
                    * 和以上同理
                    * */
                    setCurrentItem(myPagerAdapter.getCount()-2,false);
                }
            }
        }
    }

    private class MyPagerAdapter extends PagerAdapter{

        private PagerAdapter mAdapter;

        public MyPagerAdapter(PagerAdapter adapter){
            mAdapter = adapter;

            mAdapter.registerDataSetObserver(new DataSetObserver() {
                @Override
                public void onChanged() {
                    notifyDataSetChanged();
                    Log.i("TAG","onChanged");
                }

                @Override
                public void onInvalidated() {
                    notifyDataSetChanged();
                    Log.i("TAG", "onInvalidated");
                }
            });
        }

        @Override
        public int getCount() {
            return mAdapter.getCount()+2;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return mAdapter.isViewFromObject(view,object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            /*
            * 说明：mAdapter内的元素为{Item1,Item2,Item3}
            * 则此Adapter中的元素为{Item3,Item1,Item2,Item3,Item1}
            *
            * position == 0时，指向Adapter中的Item3,mAdapter.getCount()=3,将position指向mAdapter中的Item3，index = 2;
            * position == 4时，指向Adapter中的Item1,将指向mAdapter中的Item1，index = 0;
            * 其他情况下，position-1，将其指向mAdapter真正应该指向的Item，如position == 2,此时指向Adapter中的Item2,将position-1,指向mAdapter的Item1
            *
            * 完成以上判断后，调用mAdapter.instantiateItem方法，初始化Item
            *
            * */
            if (position == 0){
                position = mAdapter.getCount()-1;
            }else if (position == mAdapter.getCount()+1){
                position = 0;
            }else {
                position -= 1;
            }

            return mAdapter.instantiateItem(container, position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            mAdapter.destroyItem(container,position,object);
        }
    }
}
