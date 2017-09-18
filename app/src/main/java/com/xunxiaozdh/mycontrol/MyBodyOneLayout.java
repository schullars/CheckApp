package com.xunxiaozdh.mycontrol;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.xunxiaozdh.connectserver.ConnectSql;
import com.xunxiaozdh.connectserver.SQLTableName;
import com.xunxiaozdh.error.MyError;
import com.xunxiaozdh.maintain.ActivityCollector;
import com.xunxiaozdh.maintain.CheckActivity;
import com.xunxiaozdh.maintain.MainActivity;
import com.xunxiaozdh.maintain.MyApplication;
import com.xunxiaozdh.maintain.MyLog;
import com.xunxiaozdh.maintain.R;
import com.xunxiaozdh.maintain.ScannerActivity;
import com.xunxiaozdh.mydata.ParamsThroughActivity;
import com.xunxiaozdh.mydata.SqlCmd;
import com.xunxiaozdh.mydata.StaffPermission;
import com.xunxiaozdh.myevent.MyEvent;

import java.util.ArrayList;

/**
 * Author:  schullar
 * Company: Xunxiao
 * Date:    2017/8/25 0025 16:17
 * Mail:    schullar@outlook.com
 * Descrip:
 */

public class MyBodyOneLayout extends ConstraintLayout implements View.OnClickListener{

    private Button checkButton;
    private Button maintainButton;
    private ImageView logoImage;
    private TextView logoText;

    //private ParamsThroughActivity paramsThroughActivity;
    SqlCmd sqlCmd;

    public MyBodyOneLayout(Context context, AttributeSet attrs){
        super(context,attrs);

        LayoutInflater.from(context).inflate(R.layout.fragment_one,this);

        initFragmentOne();
    }

    void initFragmentOne(){
        //paramsThroughActivity = new ParamsThroughActivity();
        sqlCmd = new SqlCmd();

        checkButton = (Button)findViewById(R.id.button_check);
        maintainButton = (Button)findViewById(R.id.button_maintain);
        logoImage = (ImageView) findViewById(R.id.logo_image);
        logoText = (TextView)findViewById(R.id.logo_text);

        checkButton.setOnClickListener(this);
        maintainButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.button_check:
                Intent intent = new Intent(getContext(),ScannerActivity.class);
                getContext().startActivity(intent);
                break;
            case R.id.button_maintain:
                break;
            default:
                break;
        }
    }
}
