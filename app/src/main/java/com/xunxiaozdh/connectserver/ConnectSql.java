package com.xunxiaozdh.connectserver;

import android.os.AsyncTask;
import android.os.Message;

import com.xunxiaozdh.error.MyError;
import com.xunxiaozdh.maintain.MyLog;
import com.xunxiaozdh.mydata.CheckBom;
import com.xunxiaozdh.mydata.SqlCmd;
import com.xunxiaozdh.myevent.MyEvent;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

/**
 * Author:  schullar
 * Company: Xunxiao
 * Date:    2017/8/30 0030 9:40
 * Mail:    schullar@outlook.com
 * Descrip:
 */

public class ConnectSql extends AsyncTask<Integer,Integer,String>{

    public static ConnectSql newInstance(String serverIp,String userName,String password,String serverPort,String dataBase,SqlCmd sqlCmd){
        ConnectSql connectSql = new ConnectSql();
        connectSql.setServerIp(serverIp);
        connectSql.setUserName(userName);
        connectSql.setPassword(password);
        connectSql.setServerPort(serverPort);
        connectSql.setDataBase(dataBase);
        connectSql.setSqlCmd(sqlCmd);
        return connectSql;
    }

    public static ConnectSql newInstance(String serverIp,String userName,String password,String dataBase,SqlCmd sqlCmd){// serverPort使用默认值
        ConnectSql connectSql = new ConnectSql();
        connectSql.setServerIp(serverIp);
        connectSql.setUserName(userName);
        connectSql.setPassword(password);
        connectSql.setDataBase(dataBase);
        connectSql.setSqlCmd(sqlCmd);
        return connectSql;
    }

    private String serverIp;
    private String userName;
    private String password;
    private String serverPort = "1433";
    private String dataBase;
    private String tableName;
    private SqlCmd sqlCmd;

    public String getServerIp() {
        return serverIp;
    }

    public void setServerIp(String serverIp) {
        this.serverIp = serverIp;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getServerPort() {
        return serverPort;
    }

    public void setServerPort(String serverPort) {
        this.serverPort = serverPort;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public SqlCmd getSqlCmd() {
        return sqlCmd;
    }

    public void setSqlCmd(SqlCmd sqlCmd) {
        this.sqlCmd = sqlCmd;
    }

    public String getDataBase() {
        return dataBase;
    }

    public void setDataBase(String dataBase) {
        this.dataBase = dataBase;
    }

    public int getDataFromServer(){
        int ret = 0;
        Connection connection = null;

        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            connection = DriverManager.getConnection(generateConnectString(dataBase),userName,password);

            switch (sqlCmd.getCmdType())
            {
                case ExcuteQuary:// 查询，要求返回
                    Statement statement = connection.createStatement();
                    ResultSet rSet = statement.executeQuery(sqlCmd.getCmd());
                    getDataFromSet(rSet);
                    break;
                case ExcuteInsert:// 不要求返回

                    PreparedStatement preparedStatement = MyPrepareStatement(connection);
                    if (null == preparedStatement){sqlCmd.setResult(MyError.ERROR.Generate_Sql_Cmd_Failed); break;}
                    if (preparedStatement.executeUpdate() != 1){sqlCmd.setResult(MyError.ERROR.Insert_To_Sql_Failed);break;}
                    else{sqlCmd.setResult(MyError.ERROR.No_Error);}
                    break;
                default:
                    break;
            }


        }catch (Exception e)
        {
            MyLog.e(MyLog.getTag(),"connetct server failed, info:"+ e.getMessage());
            ret = -1;
        }finally {
            if (null != connection){
                try {
                    connection.close();

                }catch (Exception e){
                    MyLog.e(MyLog.getTag(),"close sql connection failed, info:"+e.getMessage());
                    ret = -1;
                }
            }
        }
        return ret;
    }

    private void getDataFromSet(ResultSet rSet){

        if (null == rSet){
            if (null == sqlCmd){
                MyLog.e(MyLog.getTag(),"get sql data failed and sqlcmd is null!");
            }else {
                sqlCmd.setResult(MyError.ERROR.Get_Data_From_Sql_Failed);
                MyLog.e(MyLog.getTag(),"get sql data failed");
            }
            return;
        }

        if (null == sqlCmd.getDataList()){
            MyLog.e(MyLog.getTag(),"sqlcmd.dataList is null");
            return;
        }

        try {
            //Method[] methods = sqlcmd.data.getClass().getDeclaredMethods();

            while (rSet.next()){

                Field[] fields = sqlCmd.getDataType().getDeclaredFields();
                Object object = sqlCmd.getDataType().newInstance();

                for (Field field:fields){
                    String name = field.getName();
                    name = name.substring(0,1).toUpperCase() + name.substring(1);
                    String type = field.getGenericType().toString();
                    Method method;
                    try{
                        method = sqlCmd.getDataType().getMethod("set" + name,new Class[]{field.getType()});
                    }catch (Exception e){
                        MyLog.e(MyLog.getTag(),"get method from class failed,info:"+e.getMessage());
                        continue;
                    }
                    if (null == method){continue;}

                    if (type.equals("class java.lang.Integer")){
                        method.invoke(object,rSet.getInt(field.getName()));
                    }
                    else if (type.equals("class java.lang.String")){
                        method.invoke(object,rSet.getString(field.getName()));
                    }
                    else if (type.equals("class java.lang.Short")){
                        method.invoke(object,rSet.getShort(field.getName()));
                    }
                    else if (type.equals("class java.lang.Double")){
                        method.invoke(object,rSet.getDouble(field.getName()));
                    }
                    else if (type.equals("class java.lang.Boolean")){
                        method.invoke(object,rSet.getBoolean(field.getName()));
                    }
                    else if (type.equals("class java.lang.Date")){
                        method.invoke(object,rSet.getDate(field.getName()));
                    }

                }

                if (null != object){ sqlCmd.getDataList().add(object);}
            }

        }catch (Exception e){
            MyLog.e(MyLog.getTag(),"get data from table failed");
            if (null != sqlCmd){sqlCmd.setResult(MyError.ERROR.Get_Data_From_Sql_Failed);}
            return;
        }
        if (null != sqlCmd){sqlCmd.setResult(MyError.ERROR.No_Error);}
        return;
    }

    private String generateConnectString(String tableName){
        return "jdbc:jtds:sqlserver://" + serverIp + ":" + serverPort + "/" + tableName;
    }

    private PreparedStatement MyPrepareStatement(Connection connection){
        if (null == connection){return null;}
        String sqlfields="";
        String sqlvars="";
        PreparedStatement pStatement = null;
        try{
            Field[] fields = sqlCmd.getDataType().getDeclaredFields();

            for (Field field:fields ){
                String name = field.getName();
                name = name.substring(0,1).toUpperCase() + name.substring(1);
                //String type = field.getGenericType().toString();
                Method method;
                try{method = sqlCmd.getDataType().getMethod("get" + name,new Class[]{}); }
                catch (Exception e){
                    MyLog.e(MyLog.getTag(),"get method from class failed,info:"+e.getMessage());
                    continue;
                }
                if (null == method || null == method.invoke(sqlCmd.getDataList().get(0))){continue;}// 字段值为空则不添加

                sqlfields  = sqlfields + "," + field.getName();
                sqlvars = sqlvars + ",?";
            }
            if (0 == sqlfields.length()){return null;}
            if (sqlfields.charAt(0) == ','){sqlfields = sqlfields.substring(1);}
            if (sqlvars.charAt(0) == ','){sqlvars = sqlvars.substring(1);}

            String sql = String.format("insert into %1$s (%2$s) values (%3$s)",sqlCmd.getCmd(),sqlfields,sqlvars);

            pStatement = connection.prepareStatement(sql);

            int paramIndex = 0;
            for ( int i=0;i<fields.length;i++){
                Field field = fields[i];
                String name = field.getName();
                name = name.substring(0,1).toUpperCase() + name.substring(1);
                String type = field.getGenericType().toString();
                Method method;
                try{method = sqlCmd.getDataType().getMethod("get" + name,new Class[]{}); }
                catch (Exception e){
                    MyLog.e(MyLog.getTag(),"get method from class failed,info:"+e.getMessage());
                    continue;
                }

                if (null == method || null == method.invoke(sqlCmd.getDataList().get(0))){continue;}

                if (type.equals("class java.lang.Integer")){
                    pStatement.setInt(++paramIndex,(Integer) method.invoke(sqlCmd.getDataList().get(0)));
                }
                else if (type.equals("class java.lang.String")){
                    pStatement.setString(++paramIndex,(String) method.invoke(sqlCmd.getDataList().get(0)));
                }
                else if (type.equals("class java.lang.Short")){
                    pStatement.setShort(++paramIndex,(Short) method.invoke(sqlCmd.getDataList().get(0)));
                }
                else if (type.equals("class java.lang.Double")){
                    pStatement.setDouble(++paramIndex,(Double) method.invoke(sqlCmd.getDataList().get(0)));
                }
                else if (type.equals("class java.lang.Boolean")){
                    pStatement.setBoolean(++paramIndex,(Boolean) method.invoke(sqlCmd.getDataList().get(0)));
                }
                else if (type.equals("class java.lang.Date")){
                    pStatement.setDate(++paramIndex,(Date) method.invoke(sqlCmd.getDataList().get(0)));
                }

            }
        }catch (Exception ex){
            MyLog.e(MyLog.getTag(),ex.getMessage());
            return null;
        }
        return pStatement;
    }


    // 异步处理函数

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(Integer... params) {

        this.getDataFromServer();
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if (null == sqlCmd)
        {
            MyLog.e(MyLog.getTag(),"data is null,do not refresh list view");
            return;
        }
        if (null != sqlCmd.getHandler()){
            Message message = new Message();
            message.what = sqlCmd.getEvent().getValue();
            sqlCmd.getHandler().sendMessage(message);
        }

    }
}
