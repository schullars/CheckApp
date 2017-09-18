package com.xunxiaozdh.maintain;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;

import com.xunxiaozdh.mydata.CheckBom;
import com.xunxiaozdh.mycontrol.HistoryCheckItem;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HistoryActivity extends BaseActivity {

    private ListView historyList;
    private CheckBom checkBom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        WindowManager.LayoutParams attrs = getWindow().getAttributes();

        attrs.dimAmount = 0.1f;//设置窗口之外部分透明
        DisplayMetrics dm = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getRealMetrics(dm);

        attrs.x = 0;
        attrs.y = 0;
        attrs.width = dm.widthPixels*3/4;
        attrs.height = dm.heightPixels*3/4;
        attrs.alpha = 0.8f;
        getWindow().setAttributes(attrs);

        setTitle(R.string.history_check_plan);

        initParams();

        GenerateListView();
    }

    private void initParams(){
        checkBom = (CheckBom)getIntent().getSerializableExtra("checkItem");

        historyList = (ListView)findViewById(R.id.history_check_item_list);

        historyList.setDivider(new ColorDrawable(getResources().getColor(R.color.White)));// 设置分割线颜色及粗细
        historyList.setDividerHeight(1);
    }

    private void GenerateListView(){

        List<Object> controlObjects = new ArrayList<>();
        for (int i=0; i<4;i++){
            HistoryCheckItem historyCheckItem = new HistoryCheckItem(this,null);
            SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            historyCheckItem.setPlanDateText(format.format(new Date(System.currentTimeMillis())));
            historyCheckItem.setJudgeStatus(i%4!=1);
            controlObjects.add(historyCheckItem);
        }

        MyListViewAdapter myListViewAdapter = MyListViewAdapter.newInstance(controlObjects);

        historyList.setAdapter(myListViewAdapter);
        historyList.setOnItemClickListener(new MyItemClickListener(this));

        //setAutoHeight();// 适应列表高度
    }

    class MyItemClickListener implements AdapterView.OnItemClickListener{

        Context context;
        public MyItemClickListener(Context context){
            this.context = context;
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

           Intent intent = new Intent();
            intent.setClass(context,DetailsActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("checkItem", checkBom);
            intent.putExtras(bundle);
            context.startActivity(intent);

            ((Activity)context).finish();// 返回时不再显示此页
        }
    }

}
