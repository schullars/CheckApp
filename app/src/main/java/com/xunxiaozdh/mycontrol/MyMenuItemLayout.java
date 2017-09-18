package com.xunxiaozdh.mycontrol;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xunxiaozdh.maintain.R;

/**
 * Author:  schullar
 * Company: Xunxiao
 * Date:    2017/8/24 0024 16:48
 * Mail:    schullar@outlook.com
 * Descrip:
 */

public class MyMenuItemLayout extends LinearLayout {

    private ImageView imageView;
    private TextView textView;

    public MyMenuItemLayout(Context context, AttributeSet attrs){
        super(context,attrs);
        LayoutInflater.from(context).inflate(R.layout.menu_item,this);

        imageView = (ImageView)findViewById(R.id.my_bottom_menu_image);
        textView = (TextView)findViewById(R.id.my_bottom_menu_text);
    }

    public ImageView getImageView() {
        return imageView;
    }

    public TextView getTextView() {
        return textView;
    }
}
