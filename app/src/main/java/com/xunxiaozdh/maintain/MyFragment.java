package com.xunxiaozdh.maintain;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xunxiaozdh.mycontrol.MyBodyFourLayout;
import com.xunxiaozdh.mycontrol.MyBodyOneLayout;
import com.xunxiaozdh.mycontrol.MyBodyThreeLayout;
import com.xunxiaozdh.mycontrol.MyBodyTwoLayout;

/**
 * Author:  schullar
 * Company: Xunxiao
 * Date:    2017/8/25 0025 16:43
 * Mail:    schullar@outlook.com
 * Descrip:
 */

public class MyFragment extends Fragment {

    private Class<?> cls;// 控件类型
    private int layoutId;// 控件对应布局

    static MyFragment newInstance(Class<?> cls){
        MyFragment myFragment = new MyFragment();
        myFragment.setType(cls);
        //myFragment.setLayoutId(layoutId);

        return myFragment;
    }

    public Class<?> getType() {
        return cls;
    }

    public void setType(Class<?> type) {
        this.cls = type;
    }

    public int getLayoutId() {
        return layoutId;
    }

    public void setLayoutId(int layoutId) {
        this.layoutId = layoutId;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);

        View view = null;
        //view = inflater.inflate(R.layout.my_fragment,container,false);
        if (cls.getName() == MyBodyOneLayout.class.getName()){
            view = new MyBodyOneLayout(getContext(),null);
        }else if (cls.getName() == MyBodyTwoLayout.class.getName()){
            view = new MyBodyTwoLayout(getContext(),null);
        }else if (cls.getName() == MyBodyThreeLayout.class.getName()){
            view = new MyBodyThreeLayout(getContext(),null);
        }else if (cls.getName() == MyBodyFourLayout.class.getName()){
            view = new MyBodyFourLayout(getContext(),null);
        }
       // Toast.makeText(MyApplication.getContext(),cls.getName(),Toast.LENGTH_SHORT).show();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

}
