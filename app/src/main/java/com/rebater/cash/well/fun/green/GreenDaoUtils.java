package com.rebater.cash.well.fun.green;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.rebater.cash.well.fun.util.pre.Constans;

public class GreenDaoUtils {  //greenDao工具类
    private DaoMaster daoMaster; //管理数据库
    private DaoSession daoSession; //创建会话、增删改查
    private DaoMaster.DevOpenHelper devOpenHelper;
    private SQLiteDatabase db;
    private Context mContext;

    private static GreenDaoUtils greenDaoUtils;

    private GreenDaoUtils(Context context){
        mContext=context;
    }

    public static GreenDaoUtils getSingleTon(Context context){
        if (greenDaoUtils==null){
            greenDaoUtils=new GreenDaoUtils(context);
        }
        return greenDaoUtils;
    }

    private void initGreenDao() {
        //1、获取需连接的数据库
        devOpenHelper = new DaoMaster.DevOpenHelper(mContext, Constans.DBNAME, null);
        db = devOpenHelper.getWritableDatabase();
        //2、创建数据库连接
        daoMaster = new DaoMaster(db);
        //3、创建数据库会话
        daoSession = daoMaster.newSession();
    }

    public DaoSession getDaoSession() {
        if (daoMaster==null){
            initGreenDao();
        }
        return daoSession;
    }

    public SQLiteDatabase getDb() {
        if (db==null){
            initGreenDao();
        }
        return db;
    }

}
