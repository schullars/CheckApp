package com.xunxiaozdh.maintain;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Author:  schullar
 * Company: Xunxiao
 * Date:    2017/8/31 0031 13:17
 * Mail:    schullar@outlook.com
 * Descrip:
 */

public class MyListViewAdapter extends BaseAdapter {

    private List<Object> objectList;

    public List<Object> getObjectList() {
        return objectList;
    }

    public void setObjectList(List<Object> objectList) {
        this.objectList = objectList;
    }

    public static MyListViewAdapter newInstance(List<Object> objectList){
        MyListViewAdapter  myListViewAdapter = new MyListViewAdapter();
        myListViewAdapter.setObjectList(objectList);

        return myListViewAdapter;
    }

    @Override
    public int getCount() {
        if (null == objectList){return 0;}

        return  objectList.size();
    }

    @Override
    public Object getItem(int position) {
        if (null == objectList){return null;}
        if (position > objectList.size()){MyLog.e(MyLog.getTag(),"index out of range"); return null;}
        return objectList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (null == objectList && position > objectList.size()){
            return convertView;
        }

        convertView = (ViewGroup)objectList.get(position);
        return convertView;
    }
}
