package com.rebater.cash.well.fun.green;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.rebater.cash.well.fun.bean.PayDbDetail;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "PAY_DB_DETAIL".
*/
public class PayDbDetailDao extends AbstractDao<PayDbDetail, Long> {

    public static final String TABLENAME = "PAY_DB_DETAIL";

    /**
     * Properties of entity PayDbDetail.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Title = new Property(1, String.class, "title", false, "TITLE");
        public final static Property Desc = new Property(2, String.class, "desc", false, "DESC");
        public final static Property Icon = new Property(3, String.class, "icon", false, "ICON");
        public final static Property PayId = new Property(4, int.class, "payId", false, "PAY_ID");
        public final static Property Pay_type = new Property(5, int.class, "pay_type", false, "PAY_TYPE");
        public final static Property Conversion = new Property(6, int.class, "conversion", false, "CONVERSION");
        public final static Property Cash_amount = new Property(7, float.class, "cash_amount", false, "CASH_AMOUNT");
        public final static Property CreateTime = new Property(8, long.class, "createTime", false, "CREATE_TIME");
        public final static Property Date = new Property(9, String.class, "date", false, "DATE");
    }


    public PayDbDetailDao(DaoConfig config) {
        super(config);
    }
    
    public PayDbDetailDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"PAY_DB_DETAIL\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"TITLE\" TEXT," + // 1: title
                "\"DESC\" TEXT," + // 2: desc
                "\"ICON\" TEXT," + // 3: icon
                "\"PAY_ID\" INTEGER NOT NULL ," + // 4: payId
                "\"PAY_TYPE\" INTEGER NOT NULL ," + // 5: pay_type
                "\"CONVERSION\" INTEGER NOT NULL ," + // 6: conversion
                "\"CASH_AMOUNT\" REAL NOT NULL ," + // 7: cash_amount
                "\"CREATE_TIME\" INTEGER NOT NULL ," + // 8: createTime
                "\"DATE\" TEXT);"); // 9: date
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"PAY_DB_DETAIL\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, PayDbDetail entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String title = entity.getTitle();
        if (title != null) {
            stmt.bindString(2, title);
        }
 
        String desc = entity.getDesc();
        if (desc != null) {
            stmt.bindString(3, desc);
        }
 
        String icon = entity.getIcon();
        if (icon != null) {
            stmt.bindString(4, icon);
        }
        stmt.bindLong(5, entity.getPayId());
        stmt.bindLong(6, entity.getPay_type());
        stmt.bindLong(7, entity.getConversion());
        stmt.bindDouble(8, entity.getCash_amount());
        stmt.bindLong(9, entity.getCreateTime());
 
        String date = entity.getDate();
        if (date != null) {
            stmt.bindString(10, date);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, PayDbDetail entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String title = entity.getTitle();
        if (title != null) {
            stmt.bindString(2, title);
        }
 
        String desc = entity.getDesc();
        if (desc != null) {
            stmt.bindString(3, desc);
        }
 
        String icon = entity.getIcon();
        if (icon != null) {
            stmt.bindString(4, icon);
        }
        stmt.bindLong(5, entity.getPayId());
        stmt.bindLong(6, entity.getPay_type());
        stmt.bindLong(7, entity.getConversion());
        stmt.bindDouble(8, entity.getCash_amount());
        stmt.bindLong(9, entity.getCreateTime());
 
        String date = entity.getDate();
        if (date != null) {
            stmt.bindString(10, date);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public PayDbDetail readEntity(Cursor cursor, int offset) {
        PayDbDetail entity = new PayDbDetail( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // title
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // desc
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // icon
            cursor.getInt(offset + 4), // payId
            cursor.getInt(offset + 5), // pay_type
            cursor.getInt(offset + 6), // conversion
            cursor.getFloat(offset + 7), // cash_amount
            cursor.getLong(offset + 8), // createTime
            cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9) // date
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, PayDbDetail entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setTitle(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setDesc(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setIcon(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setPayId(cursor.getInt(offset + 4));
        entity.setPay_type(cursor.getInt(offset + 5));
        entity.setConversion(cursor.getInt(offset + 6));
        entity.setCash_amount(cursor.getFloat(offset + 7));
        entity.setCreateTime(cursor.getLong(offset + 8));
        entity.setDate(cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(PayDbDetail entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(PayDbDetail entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(PayDbDetail entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
