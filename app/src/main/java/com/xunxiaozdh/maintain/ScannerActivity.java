package com.xunxiaozdh.maintain;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.xunxiaozdh.connectserver.ConnectSql;
import com.xunxiaozdh.connectserver.SQLTableName;
import com.xunxiaozdh.error.MyError;
import com.xunxiaozdh.mydata.ParamsThroughActivity;
import com.xunxiaozdh.mydata.SqlCmd;
import com.xunxiaozdh.mydata.StaffPermission;
import com.xunxiaozdh.mydata.UartCmd;
import com.xunxiaozdh.myevent.MyEvent;
import com.xunxiaozdh.other.BarCodeScanner;

import java.util.ArrayList;
import java.util.List;

import static com.xunxiaozdh.error.MyError.ERROR.No_Error;

public class ScannerActivity extends BaseActivity {

    private ImageView imageView;
    private EditText barCode;

    private boolean getDataFlag = false;
    private String staffCode;

    UartCmd uartCmd;

    //private ParamsThroughActivity paramsThroughActivity;
    private SqlCmd sqlCmd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_scanner);

        initParams();// 初始化成员

        SetAnimation();// 设置扫描动画

        StartScanner();//触发扫描

        initBeep();
    }

    private void initParams(){
       // paramsThroughActivity = (ParamsThroughActivity) getIntent().getSerializableExtra("checkItem");
        sqlCmd = new SqlCmd();

        imageView = findViewById(R.id.scan_line);
        barCode = findViewById(R.id.bar_code);
        barCode.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER && keyEvent.getAction() == KeyEvent.ACTION_UP)
                {
                    staffCode = textView.getText().toString();
                    textView.setText("");
                    mediaPlayer.start();
                    getDataFlag = true;
                    getHandler().sendEmptyMessage(MyEvent.EVENT.Get_Stuff_Code.getValue());
                }
                return (keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER);
                //return false;
            }
        });
    }

    private void SetAnimation(){
        Animation animation = new TranslateAnimation(0,0,0,180);
        animation.setDuration(2000);// 2s
        animation.setRepeatCount(Integer.MAX_VALUE);
        animation.setRepeatMode(Animation.REVERSE);
        //animation.setInterpolator(this,android.R.anim.cycle_interpolator);// 设置动画插入器
        imageView.startAnimation(animation);
    }

    private void StartScanner(){
        if (null == uartCmd){
            uartCmd = new UartCmd();
        }
        uartCmd.setResult(No_Error);
        uartCmd.setCmdType(BarCodeScanner.CmdType.Hid_Trigger);
        uartCmd.setHandler(handler);

        BarCodeScanner barCodeScanner = BarCodeScanner.newInstance(uartCmd);
        if(null != barCodeScanner){barCodeScanner.start();}
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (MyEvent.EVENT.valueOf(msg.what)){
                case Get_Stuff_Code:
                    if (null == staffCode || 0 == staffCode.length()){
                        //Toast.makeText(MyApplication.getContext(),"员工号为空，请重新扫描！",Toast.LENGTH_SHORT).show();
                        ShowResultDialog("扫描员工号","员工号为空，请重新扫描!");
                        return;
                    }
                    GetStuffInfo(staffCode);
                    break;
                case Check_Stuff_Permisson:
                    StaffPermission staffPermission = CheckStaffPermission();
                    if(null == staffPermission){
                        //Toast.makeText(MyApplication.getContext(),"读取数据库失败！",Toast.LENGTH_SHORT).show();
                        //ShowResultDialog("验证员工权限","读取数据库失败！");
                        break;
                    }
                    //ShowResultDialog("验证员工",String.format("身份验证通过：\r\n姓名：%1$s\r\n",staffPermission.getPsn_Name()));
                    Toast.makeText(MyApplication.getContext(),String.format("身份验证通过：\r\n姓名：%1$s\r\n",staffPermission.getPsn_Name()),Toast.LENGTH_SHORT).show();
                    StartCheckActivity(staffPermission);
                    finish();
                    break;
            }
        }


    };
    public Handler getHandler() {
        return handler;
    }

    private void GetStuffInfo(String staffCode){
        sqlCmd.setDataList(new ArrayList<>());
        sqlCmd.setDataType(StaffPermission.class);
        sqlCmd.setHandler(getHandler());
        sqlCmd.setResult(MyError.ERROR.UnInit);
        sqlCmd.setCmdType(SqlCmd.CmdType.ExcuteQuary);
        sqlCmd.setEvent(MyEvent.EVENT.Check_Stuff_Permisson);

        try {sqlCmd.setCmd(String.format("select * from %1$s where %2$s='%3$s'", SQLTableName.PersonPermission, StaffPermission.class.getDeclaredField("jobNumber").getName(),staffCode));}
        catch (Exception e){
            MyLog.e(MyLog.getTag(),e.getMessage());}

        ConnectSql connectSql = ConnectSql.newInstance("106.14.77.124","sa","X1234567890x","Management_Center",sqlCmd);
        connectSql.execute();

    }

    private void StartCheckActivity(StaffPermission staffInfo){
        Intent intent = new Intent();
        intent.setClass(this,CheckActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("staffInfo",staffInfo);
        intent.putExtras(bundle);
        this.startActivity(intent);
    }

    private StaffPermission CheckStaffPermission(){
        if (sqlCmd.getResult() != MyError.ERROR.No_Error){// 检查是否正确执行命令
            MyLog.e(MyLog.getTag(),"get info from sql failed");
            //Toast.makeText(MyApplication.getContext(),"获取人员权限信息失败！",Toast.LENGTH_SHORT).show();
            ShowResultDialog("验证员工权限","读取数据库失败！");
            return null;
        }

        if(sqlCmd.getDataList().size() == 0){// 检查查询结果是否为空
            MyLog.e(MyLog.getTag()," no this stuff code");
            //Toast.makeText(MyApplication.getContext(),"无此人员信息！",Toast.LENGTH_SHORT).show();
            ShowResultDialog("验证员工权限","无此人员信息！");
            return null;
        }

        StaffPermission staffInfo =  (StaffPermission)sqlCmd.getDataList().get(0);
        if (null == staffInfo.getScreen30() || 1 != staffInfo.getScreen30()){
            return null;
        }
        return staffInfo;
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

    static MediaPlayer mediaPlayer;
    private void initBeep(){
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                if (null != mediaPlayer){mediaPlayer.seekTo(0);}
            }
        });
        AssetFileDescriptor file = getResources().openRawResourceFd(R.raw.beep);
        try{
            mediaPlayer.setDataSource(file.getFileDescriptor(),file.getStartOffset(),file.getLength());
            file.close();
            mediaPlayer.setVolume(1.0f,1.0f);
            mediaPlayer.prepare();
        }catch (Exception e){

        }
    }

    public static MediaPlayer getBeep()
    {
        return mediaPlayer;
    }
}
