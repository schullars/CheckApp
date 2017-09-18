package com.xunxiaozdh.mycontrol;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.xunxiaozdh.maintain.R;

/**
 * Author:  schullar
 * Company: Xunxiao
 * Date:    2017/8/31 0031 10:20
 * Mail:    schullar@outlook.com
 * Descrip:
 */

public class MyListViewItemForCheck extends ConstraintLayout {

    private TextView checkItemName;
    private TextView checkItemParam;

    public TextView getCheckItemName() {
        return checkItemName;
    }

    public TextView getCheckItemParam() {
        return checkItemParam;
    }

    public MyListViewItemForCheck(Context context, AttributeSet attrs){
        super(context,attrs);

        LayoutInflater.from(context).inflate(R.layout.my_listview_item_for_check,this);

        initParams();// 优先初始化变量

        applyMyAttrs(context,attrs);// 设置自定义属性

    }

    private void initParams(){
        checkItemName = (TextView)findViewById(R.id.check_listview_item_name);
        checkItemParam = (TextView)findViewById(R.id.check_listview_item_param);
    }

    private void applyMyAttrs(Context context,AttributeSet attrs){
        TypedArray typedArray = context.obtainStyledAttributes(attrs,R.styleable.myTitleLayout);

        if (null == checkItemName || null == typedArray){return;}
        CharSequence text = typedArray.getText(R.styleable.myListViewItemForCheck_listview_item_name);
        if (null != text && 0 != text.length()){
            checkItemName.setText(text);
        }
        if (null == checkItemParam || null == typedArray){return;}
        text = typedArray.getText(R.styleable.myListViewItemForCheck_listview_item_param);
        if (null != text && 0 != text.length()){
            checkItemParam.setText(text);
        }

        if (null != typedArray){
            typedArray.recycle();
        }
    }

    public void setCheckNameText(String text){
        if (null == this.checkItemName){return;}

        this.checkItemName.setText(text);
    }

    public void setCheckParamText(String text){
        if (null == this.checkItemParam){return;}

        this.checkItemParam.setText(text);
    }
}
