package com.xunxiaozdh.mycontrol;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.xunxiaozdh.maintain.R;


/**
 * Author:  schullar
 * Company: Xunxiao
 * Date:    2017/9/1 0001 10:10
 * Mail:    schullar@outlook.com
 * Descrip:
 */

public class HistoryCheckItem  extends ConstraintLayout {

    private TextView checkItemPlanDate;// 显示计划执行日期
    private TextView checkItemRealDate;// 显示实际执行日期
    private TextView checkItemJudge;// 显示是否已执行

    public HistoryCheckItem(Context context, AttributeSet attrs){
        super(context,attrs);

        LayoutInflater.from(context).inflate(R.layout.history_check_item,this);

        initParams();// 优先初始化变量

        //applyMyAttrs(context,attrs);// 设置自定义属性

    }

    private void initParams(){
        checkItemPlanDate = (TextView)findViewById(R.id.check_history_plan_date_text);
        checkItemRealDate = (TextView)findViewById(R.id.check_history_real_date_text);
        checkItemJudge = (TextView)findViewById(R.id.check_history_judge_text);
    }

    public void setPlanDateText(String text){
        checkItemPlanDate.setText(text);
    }

    public void setRealDateText(String text){
        checkItemRealDate.setText(text);
    }

    public void setJudgeStatus(boolean bool){
        if (bool){
            checkItemJudge.setText(R.string.checked);
            checkItemJudge.setTextColor(getResources().getColor( R.color.Green));
        }else {
            checkItemJudge.setText(R.string.uncheck);
            checkItemJudge.setTextColor(getResources().getColor( R.color.Red));
        }
    }

    public void setClickable(boolean bool){

        this.setClickable(bool);

    }
}
