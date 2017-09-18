package com.xunxiaozdh.maintain;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.IdRes;
import android.view.View;
import android.view.Window;
import android.view.animation.Interpolator;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.xunxiaozdh.connectserver.ConnectSql;
import com.xunxiaozdh.connectserver.SQLTableName;
import com.xunxiaozdh.error.MyError;
import com.xunxiaozdh.mydata.CheckBom;
import com.xunxiaozdh.mydata.CheckRecord;
import com.xunxiaozdh.mydata.SqlCmd;
import com.xunxiaozdh.mydata.StaffPermission;
import com.xunxiaozdh.mydata.UartCmd;
import com.xunxiaozdh.myevent.MyEvent;
import com.xunxiaozdh.other.BarCodeScanner;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.xunxiaozdh.error.MyError.ERROR.No_Error;

public class DetailsActivity extends BaseActivity {

    private CheckBom checkBom;
    private TextView machineCodeText;
    private TextView machineNameText;
    private TextView checkItemText;
    private TextView checkParamText;
    private TextView importantLevelText;

    private RadioGroup qualifiedSelect;

    private Button btn_ok;
    private Button btn_cancel;

    UartCmd uartCmd;

    Context context = this;

    private StaffPermission staffPermission;
    private SqlCmd sqlCmd;
    int myLock = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_details);
        //checkBom = (CheckBom)getIntent().getSerializableExtra("checkItem");

        initParams();
        initUI();
    }

    void initParams(){
        sqlCmd = new SqlCmd();
        staffPermission = (StaffPermission) getIntent().getSerializableExtra("staffInfo");

        checkBom = (CheckBom)getIntent().getSerializableExtra("checkItem");

        machineCodeText = (TextView)findViewById(R.id.machine_code_text);
        machineNameText = (TextView)findViewById(R.id.machine_name_text);
        checkItemText = (TextView)findViewById(R.id.check_item_text);
        checkParamText = (TextView)findViewById(R.id.check_params_text);
        importantLevelText = (TextView)findViewById(R.id.important_level_text);

        qualifiedSelect = findViewById(R.id.radio_group);
        qualifiedSelect.setOnCheckedChangeListener(new MyRadioGroupClickListener());

        btn_ok = findViewById(R.id.check_item_button_ok);
        btn_cancel = findViewById(R.id.check_item_button_cancel);

        btn_ok.setOnClickListener(new MyClickListener());
        btn_cancel.setOnClickListener(new MyClickListener());

    }

    private void initUI(){
        if (null == checkBom){return;}

        machineCodeText.setText(checkBom.getEquipment_code());
        machineNameText.setText(checkBom.getEquipment_name());
        checkItemText.setText(checkBom.getCheck_Item());
        checkParamText.setText(checkBom.getCheck_Demand());
        importantLevelText.setText(checkBom.getCheck_Importance());
    }

    class MyRadioGroupClickListener implements RadioGroup.OnCheckedChangeListener{
        @Override
        public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
            try{
                if (R.id.radio_qualified == i){

                }
            }catch (Exception e){
                MyLog.e(MyLog.getTag(),e.getMessage());
            }
        }
    }

    class MyClickListener implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.check_item_button_ok:
                    if (0 >= qualifiedSelect.getCheckedRadioButtonId()){
                        Toast.makeText(MyApplication.getContext(),"请选择合格或不合格！",Toast.LENGTH_SHORT).show();
                    }else {
                        //StartAnimation();
                        SubmitCheckRecord(GenerateRecord(staffPermission));
                    }
                    //StartScanner();
                    break;
                case R.id.check_item_button_cancel:
                    finish();
                    break;
                default:
                    break;
            }
        }
    }

    private void StartScanner(){
        if (null == uartCmd){
            uartCmd = new UartCmd();
        }
        uartCmd.setResult(No_Error);
        uartCmd.setCmdType(BarCodeScanner.CmdType.Uart_Trigger);
        uartCmd.setHandler(handler);

        BarCodeScanner barCodeScanner = BarCodeScanner.newInstance(uartCmd);
        if(null != barCodeScanner){barCodeScanner.start();}
    }

    private void StartAnimation(){
        Intent intent = new Intent();
        intent.setClass(this, ScannerActivity.class);
        this.startActivityForResult(intent, MyEvent.EVENT.RequestCode_StaffCode.getValue());
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (MyEvent.EVENT.valueOf(msg.what)){
                case Insert_To_Sql:
                    if (sqlCmd.getResult() == MyError.ERROR.No_Error){
                        //Toast.makeText(MyApplication.getContext(),"点检记录保存成功！",Toast.LENGTH_SHORT).show();
                        ShowResultDialog("提交点检记录","提交点检记录成功");
                    }else {
                        ShowResultDialog("提交点检记录","提交点检记录失败");
                        //Toast.makeText(MyApplication.getContext(),"点检记录保存失败！",Toast.LENGTH_SHORT).show();
                    }
                    break;
                default:
                    break;
            }
        }
    };
    public Handler getHandler() {
        return handler;
    }

    private void ShowResultDialog(String title, String text){
        new AlertDialog.Builder(this).setTitle(title)
                .setMessage(text)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                        ActivityCollector.getActivityAt(ActivityCollector.getActivities().size()-1).finish();
                    }
                }).show();
    }




    private CheckRecord GenerateRecord(StaffPermission staffPermission){
        int id = qualifiedSelect.getCheckedRadioButtonId();
        if (0 >= id){
            Toast.makeText(this,"请选择合格或者不合格！",Toast.LENGTH_SHORT);
            return null;
        }
        RadioButton radioButton = findViewById(id);

        CheckRecord  checkRecord = new CheckRecord();

        SimpleDateFormat sDateFormat =  new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String str = sDateFormat.format(new Date());
        try {
            checkRecord.setCheck_Date(str.substring(0,"yyyy/MM/dd".length()));// 点检时间，年月日

            checkRecord.setCheck_Time(str.substring("yyyy/MM/dd ".length(),"yyyy/MM/dd HH:mm:ss".length()));// 点检时间 时分秒
            checkRecord.setCheck_Year(Integer.parseInt(str.substring(0,"yyyy".length())));
            checkRecord.setCheck_Month(Integer.parseInt(str.substring("yyyy/".length(),"yyyy/MM".length())));

            checkRecord.setWorksection(checkBom.getWorksection());
            checkRecord.setWorkCenter_Code(checkBom.getWorkCenter_Code());
            checkRecord.setEquipment_name(checkBom.getEquipment_name());
            checkRecord.setEquipment_code(checkBom.getEquipment_code());
            checkRecord.setCheck_Type(checkBom.getCheck_Type());
            checkRecord.setCheck_Item(checkBom.getCheck_Item());
            checkRecord.setCheck_ItemID(checkBom.getCheck_ItemID());
            checkRecord.setCheck_Demand(checkBom.getCheck_Demand());
            checkRecord.setCheck_Importance(checkBom.getCheck_Importance());
            checkRecord.setCheck_Method(checkBom.getCheck_Method());
            checkRecord.setCheck_Result(radioButton.getText().toString());
            checkRecord.setCheck_Person(staffPermission.getPsn_Name());
        }catch (Exception e){
            MyLog.e(MyLog.getTag(),e.getMessage());
        }

        return checkRecord;
    }

    private void SubmitCheckRecord(CheckRecord checkRecord){
        if (null == sqlCmd){sqlCmd = new SqlCmd();}
        List<Object> dataList = new ArrayList<>();
        dataList.add(checkRecord);

        sqlCmd.setDataList(dataList);
        sqlCmd.setDataType(CheckRecord.class);
        sqlCmd.setResult(MyError.ERROR.UnInit);
        sqlCmd.setCmdType(SqlCmd.CmdType.ExcuteInsert);
        sqlCmd.setCmd(SQLTableName.CheckRecord);// 此处只传递一个表名，由后续代码自行生成完整的插入命令
        sqlCmd.setHandler(getHandler());
        sqlCmd.setEvent(MyEvent.EVENT.Insert_To_Sql);

        ConnectSql connectSql = ConnectSql.newInstance("106.14.77.124","sa","X1234567890x",SQLTableName.DataBaseManagement,sqlCmd);
        connectSql.execute();
    }
}
