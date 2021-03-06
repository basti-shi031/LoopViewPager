package com.bzt.loopviewpager;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.bzt.loopviewpagerlib.LoopViewPager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private LoopViewPager viewPager;
    private MyPageAdapter myPageAdapter;
    private List<Integer> mlist = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mlist.add(R.mipmap.android1);
        mlist.add(R.mipmap.android2);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(fab,"add an Item",Snackbar.LENGTH_SHORT).show();
                mlist.add(R.mipmap.android3);
                myPageAdapter.notifyDataSetChanged();
            }
        });

        viewPager = (LoopViewPager) findViewById(R.id.viewpager);
        myPageAdapter = new MyPageAdapter(mlist,this);
        viewPager.setAdapter(myPageAdapter);
        //viewPager.start(5000);

        findViewById(R.id.start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //开始自动播放
                viewPager.start(5000);
            }
        });

        findViewById(R.id.stop).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //停止自动播放
                viewPager.stop();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /*
    注意在onDestroy中将消息队列回收
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        viewPager.onDestroy();
    }

}
