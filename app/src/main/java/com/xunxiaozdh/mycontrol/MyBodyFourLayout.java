package com.xunxiaozdh.mycontrol;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;

import com.xunxiaozdh.maintain.R;

/**
 * Author:  schullar
 * Company: Xunxiao
 * Date:    2017/8/26 0026 16:22
 * Mail:    schullar@outlook.com
 * Descrip:
 */

public class MyBodyFourLayout extends ConstraintLayout {

    public MyBodyFourLayout(Context context, AttributeSet attrs){
        super(context,attrs);

        LayoutInflater.from(context).inflate(R.layout.fragment_four,this);

    }
}
