package com.xunxiaozdh.mycontrol;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.xunxiaozdh.maintain.R;

/**
 * Author:  schullar
 * Company: Xunxiao
 * Date:    2017/8/24 0024 16:30
 * Mail:    schullar@outlook.com
 * Descrip:
 */

public class MyTitleLayout extends ConstraintLayout {

    private TextView textView;

    public MyTitleLayout(Context context, AttributeSet attrs){
        super(context,attrs);

        LayoutInflater.from(context).inflate(R.layout.my_title,this);

        initTitle();// 优先初始化变量

        applyMyAttrs(context,attrs);

    }

    void initTitle(){
        textView = (TextView)findViewById(R.id.title_text);

        Button titleBack = (Button) findViewById(R.id.title_back);
        titleBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity)getContext()).finish();
            }
        });
    }

    public TextView getTextView() {
        return textView;
    }

    private void applyMyAttrs(Context context,AttributeSet attrs){
        TypedArray typedArray = context.obtainStyledAttributes(attrs,R.styleable.myTitleLayout);

        if (null == textView || null == typedArray){return;}

        CharSequence text = typedArray.getText(R.styleable.myTitleLayout_title_text);

        if (null != text && 0 != text.length()){
            textView.setText(text);
        }

        if (null != typedArray){
            typedArray.recycle();
        }
    }
}
