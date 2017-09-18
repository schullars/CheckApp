package com.xunxiaozdh.maintain;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Author:  schullar
 * Company: Xunxiao
 * Date:    2017/8/26 0026 9:34
 * Mail:    schullar@outlook.com
 * Descrip:
 */

public class FragmentAdapter extends FragmentPagerAdapter {

    private ArrayList<Fragment> list;

    public FragmentAdapter(android.support.v4.app.FragmentManager fm){
        super(fm);
    }

    public FragmentAdapter(FragmentManager fm,ArrayList<Fragment> list){
        super(fm);
        this.list = list;
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
        //return null;
    }

    @Override
    public int getCount() {
        return list.size();
        //return 0;
    }
}
