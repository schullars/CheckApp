package com.xunxiaozdh.maintain;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.xunxiaozdh.connectserver.ConnectSql;
import com.xunxiaozdh.connectserver.SQLTableName;
import com.xunxiaozdh.mydata.CheckBom;
import com.xunxiaozdh.mydata.SqlCmd;
import com.xunxiaozdh.error.MyError;
import com.xunxiaozdh.mycontrol.MyListViewItemForCheck;
import com.xunxiaozdh.mydata.StaffPermission;
import com.xunxiaozdh.myevent.MyEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class CheckActivity extends BaseActivity {

    private EditText machineCode;
    private EditText machineName;
    private ListView checkItemList;
    private SqlCmd sqlCmd;
    private int myLock = 0;// 一次只能创建一个进程
    private StaffPermission staffPermission;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_check);

        initParams();

        initMember();
    }



    private void initParams(){
        sqlCmd = new SqlCmd();
        staffPermission = (StaffPermission)getIntent().getSerializableExtra("staffInfo");
    }

    private void initMember(){
        checkItemList = (ListView)findViewById(R.id.check_item_list);

        machineName = findViewById(R.id.machine_name_text);
        machineCode = (EditText)findViewById(R.id.machine_code_text);

        machineCode.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(event.getKeyCode() == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_UP &&
                   null != machineCode.getText() && 0 <= machineCode.getText().length()){
                   // machineCode.setText(machineCode.getText().subSequence(0,machineCode.getText().length()-1));
                    ScannerActivity.mediaPlayer.start();// 扫描声
                    sqlCmd.setDataList(new ArrayList<>());
                    sqlCmd.setDataType(CheckBom.class);
                    sqlCmd.setHandler(getHandler());
                    sqlCmd.setResult(MyError.ERROR.No_Error);
                    sqlCmd.setCmdType(SqlCmd.CmdType.ExcuteQuary);
                    sqlCmd.setEvent(MyEvent.EVENT.Update_ListView);

                    try {sqlCmd.setCmd(String.format("select * from %1$s where %2$s='%3$s'", SQLTableName.CheckBom, CheckBom.class.getDeclaredField("equipment_code").getName(),v.getText()));}
                    catch (Exception e){MyLog.e(MyLog.getTag(),e.getMessage());}

                    if (0 ==myLock){
                        myLock = 1;
                        ConnectSql connectSql = ConnectSql.newInstance("106.14.77.124","sa","X1234567890x",SQLTableName.DataBaseManagement,sqlCmd);
                        connectSql.execute();
                    }

                }

                return (event.getKeyCode() == KeyEvent.KEYCODE_ENTER);
                //return false;
            }
        });
    }

    private Handler handler = new Handler(){
        public void handleMessage(Message msg){
            switch(MyEvent.EVENT.valueOf(msg.what)){
                case Update_ListView:
                    if (null != sqlCmd && sqlCmd.getResult() == MyError.ERROR.No_Error){ GenerateListView(sqlCmd.getDataList());}
                     String str = ((CheckBom)sqlCmd.getDataList().get(0)).getEquipment_name();
                    if (null != str){machineName.setText(str);}
                    break;
                //case Show_History:


                default:
                    break;
            }
            myLock = 0;// 收到返回消息时才算完成一次数据获取
        }
    };

    public Handler getHandler() {
        return handler;
    }

    private void GenerateListView(List<Object> objectList){
        if (null == objectList){MyLog.e(MyLog.getTag(),"List is null"); return;}
        MyLog.d(MyLog.getTag(),String.format("List length is %1$d",objectList.size()));
        List<Object> controlObjects = new ArrayList<>();

        for (Object object:objectList){

            MyListViewItemForCheck myListViewItemForCheck = new MyListViewItemForCheck(this,null);

            myListViewItemForCheck.setCheckNameText(((CheckBom)object).getCheck_Item());
            myListViewItemForCheck.setCheckParamText(((CheckBom) object).getCheck_Demand());
            controlObjects.add(myListViewItemForCheck);
        }

        MyListViewAdapter myListViewAdapter = MyListViewAdapter.newInstance(controlObjects);

        checkItemList.setAdapter(myListViewAdapter);
        checkItemList.setOnItemClickListener(new MyItemClickListener(this));
    }

    class MyItemClickListener implements AdapterView.OnItemClickListener{

        Context context;
        public MyItemClickListener(Context context){
            this.context = context;
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            ShowItemCheckDetails(position);
            return;
        }
    }

    // 显示本条记录的点检记录
    private void ShowItemCheckDetails(int index)
    {
        Intent intent = new Intent();
        //intent.setClass(this,HistoryActivity.class);
        intent.setClass(this,DetailsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("checkItem",(CheckBom)sqlCmd.getDataList().get(index));
        bundle.putSerializable("staffInfo",staffPermission);
        intent.putExtras(bundle);
        this.startActivity(intent);
    }

}
