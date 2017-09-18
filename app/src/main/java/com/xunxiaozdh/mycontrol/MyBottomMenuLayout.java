package com.xunxiaozdh.mycontrol;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.xunxiaozdh.maintain.MyLog;
import com.xunxiaozdh.maintain.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Author:  schullar
 * Company: Xunxiao
 * Date:    2017/8/24 0024 16:37
 * Mail:    schullar@outlook.com
 * Descrip:
 */

public class MyBottomMenuLayout extends LinearLayout implements View.OnClickListener{

    private List<MyMenuItemLayout> layoutList = new ArrayList<>();// 底部菜单项列表
    private int[] item_id = {R.id.menu_home,R.id.menu_info,R.id.menu_setting,R.id.menu_admin};
    private int[] item_text_id = {R.string.my_menu_item_text_home,R.string.my_menu_item_text_info,R.string.my_menu_item_text_setting,R.string.my_menu_item_text_admin};
    private int[] item_image_id = {R.mipmap.ic_action_home,R.mipmap.ic_action_search,R.mipmap.ic_action_gear,R.mipmap.ic_action_key};
    private int selectedItemIndex = 0;



    public MyBottomMenuLayout(Context context, AttributeSet attrs){
        super(context,attrs);
        LayoutInflater.from(context).inflate(R.layout.my_bottom_menu,this);

        initMenuItem();
        MyLog.e(MyLog.getTag(),"this is a test.");
    }

    private void initMenuItem(){

        if (null == layoutList){
            MyLog.e(MyLog.getTag(),"layoutList is null.");
            return;
        }

        for (int i=0;i<item_id.length;i++){
            layoutList.add((MyMenuItemLayout)findViewById(item_id[i]));
        }

        for (int i=0;i<layoutList.size();i++){
            layoutList.get(i).getTextView().setText(item_text_id[i]);
        }

        for (int i=0;i<item_image_id.length;i++){
            layoutList.get(i).getImageView().setImageResource(item_image_id[i]);
        }

        for (int i=0;i<layoutList.size();i++){
            layoutList.get(i).setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {

        setSelectedMenuItem(layoutList.indexOf(v));

    }

    // 点击底部按钮之后，高亮显示当前按钮，同时标题栏显示同步到当前按钮
    private void setSelectedMenuItem(int index)
    {
        if (null == layoutList){
            MyLog.e(MyLog.getTag(),"layoutList is null.");
            return;
        }

        try {

            layoutList.get(selectedItemIndex).setBackgroundColor(0x00ffffff);

            selectedItemIndex = (index==-1)?selectedItemIndex:index;
            if (-1 == selectedItemIndex){return;}

            // 设置标题文本
            ((MyTitleLayout)((ViewGroup)getParent()).findViewById(R.id.my_title)).getTextView().setText(
                    layoutList.get(selectedItemIndex).getTextView().getText()
            );

            // 设置底部菜单高亮
            layoutList.get(selectedItemIndex).setBackgroundColor(0x80FFFFFF);

            // 设置中间viewpager的序号
            ((ViewPager)((ViewGroup)getParent()).findViewById(R.id.my_body)).setCurrentItem(selectedItemIndex);


        }catch (Exception e){
            MyLog.e(MyLog.getTag(),e.getMessage()==null?"":e.getMessage());
        }
    }


    // 将viewPager的切换事件放到此处，标题，viewPager以及底部菜单切换动作统一由setSelectedMenuItem函数管理，减少mainActivity文件中的代码量
    private MyViewPagerChangedListener myViewPagerChangedListener = new MyViewPagerChangedListener();
    public MyViewPagerChangedListener getMyViewPagerChangedListener() {
        return myViewPagerChangedListener;
    }

    class MyViewPagerChangedListener implements ViewPager.OnPageChangeListener{

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }

        @Override
        public void onPageSelected(int position) {
            setSelectedMenuItem(position);
        }
    }
}
