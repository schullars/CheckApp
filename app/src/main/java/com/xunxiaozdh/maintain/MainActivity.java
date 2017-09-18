package com.xunxiaozdh.maintain;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Window;

import com.xunxiaozdh.mycontrol.MyBodyFourLayout;
import com.xunxiaozdh.mycontrol.MyBodyOneLayout;
import com.xunxiaozdh.mycontrol.MyBodyThreeLayout;
import com.xunxiaozdh.mycontrol.MyBodyTwoLayout;
import com.xunxiaozdh.mycontrol.MyBottomMenuLayout;

import java.util.ArrayList;

public class MainActivity extends FragmentActivity {

    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        initViewPager();
    }

    void initViewPager(){

        try {
            viewPager = (ViewPager)findViewById(R.id.my_body);
        }catch (Exception e){
            MyLog.d(MyLog.getTag(),"get viewPager obj failed,do not init viewpager");
            return;
        }

        ArrayList<Fragment> myFragmentList = new ArrayList<>();

        myFragmentList.add(MyFragment.newInstance(MyBodyOneLayout.class));
        myFragmentList.add(MyFragment.newInstance(MyBodyTwoLayout.class));
        myFragmentList.add(MyFragment.newInstance(MyBodyThreeLayout.class));
        myFragmentList.add(MyFragment.newInstance(MyBodyFourLayout.class));

        viewPager.setAdapter(new FragmentAdapter(getSupportFragmentManager(),myFragmentList));// getSupportFragmentManager函数必须在FragmentActivity或AppcompatActivity子类中才能使用
        viewPager.setCurrentItem(0);
        viewPager.addOnPageChangeListener(((MyBottomMenuLayout)findViewById(R.id.my_bottom_menu)).getMyViewPagerChangedListener());// 切换事件放在MyBottomMenuLayout类中，统一管理，减少MainActivity代码量
    }
}
