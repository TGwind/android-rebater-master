package com.rebater.cash.well.fun.green;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.rebater.cash.well.fun.bean.LogEvent;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "LOG_EVENT".
*/
public class LogEventDao extends AbstractDao<LogEvent, Long> {

    public static final String TABLENAME = "LOG_EVENT";

    /**
     * Properties of entity LogEvent.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property PackageName = new Property(1, String.class, "packageName", false, "PACKAGE_NAME");
        public final static Property OfferId = new Property(2, int.class, "OfferId", false, "OFFER_ID");
        public final static Property IsUp = new Property(3, boolean.class, "isUp", false, "IS_UP");
        public final static Property CreateTime = new Property(4, long.class, "createTime", false, "CREATE_TIME");
        public final static Property ChangeTime = new Property(5, long.class, "changeTime", false, "CHANGE_TIME");
        public final static Property EventName = new Property(6, String.class, "eventName", false, "EVENT_NAME");
        public final static Property Country = new Property(7, String.class, "country", false, "COUNTRY");
        public final static Property TemplateId = new Property(8, String.class, "templateId", false, "TEMPLATE_ID");
        public final static Property Position = new Property(9, String.class, "position", false, "POSITION");
        public final static Property Type = new Property(10, int.class, "type", false, "TYPE");
        public final static Property Page = new Property(11, int.class, "page", false, "PAGE");
        public final static Property Step = new Property(12, int.class, "step", false, "STEP");
    }


    public LogEventDao(DaoConfig config) {
        super(config);
    }
    
    public LogEventDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"LOG_EVENT\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"PACKAGE_NAME\" TEXT," + // 1: packageName
                "\"OFFER_ID\" INTEGER NOT NULL ," + // 2: OfferId
                "\"IS_UP\" INTEGER NOT NULL ," + // 3: isUp
                "\"CREATE_TIME\" INTEGER NOT NULL ," + // 4: createTime
                "\"CHANGE_TIME\" INTEGER NOT NULL ," + // 5: changeTime
                "\"EVENT_NAME\" TEXT," + // 6: eventName
                "\"COUNTRY\" TEXT," + // 7: country
                "\"TEMPLATE_ID\" TEXT," + // 8: templateId
                "\"POSITION\" TEXT," + // 9: position
                "\"TYPE\" INTEGER NOT NULL ," + // 10: type
                "\"PAGE\" INTEGER NOT NULL ," + // 11: page
                "\"STEP\" INTEGER NOT NULL );"); // 12: step
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"LOG_EVENT\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, LogEvent entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String packageName = entity.getPackageName();
        if (packageName != null) {
            stmt.bindString(2, packageName);
        }
        stmt.bindLong(3, entity.getOfferId());
        stmt.bindLong(4, entity.getIsUp() ? 1L: 0L);
        stmt.bindLong(5, entity.getCreateTime());
        stmt.bindLong(6, entity.getChangeTime());
 
        String eventName = entity.getEventName();
        if (eventName != null) {
            stmt.bindString(7, eventName);
        }
 
        String country = entity.getCountry();
        if (country != null) {
            stmt.bindString(8, country);
        }
 
        String templateId = entity.getTemplateId();
        if (templateId != null) {
            stmt.bindString(9, templateId);
        }
 
        String position = entity.getPosition();
        if (position != null) {
            stmt.bindString(10, position);
        }
        stmt.bindLong(11, entity.getType());
        stmt.bindLong(12, entity.getPage());
        stmt.bindLong(13, entity.getStep());
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, LogEvent entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String packageName = entity.getPackageName();
        if (packageName != null) {
            stmt.bindString(2, packageName);
        }
        stmt.bindLong(3, entity.getOfferId());
        stmt.bindLong(4, entity.getIsUp() ? 1L: 0L);
        stmt.bindLong(5, entity.getCreateTime());
        stmt.bindLong(6, entity.getChangeTime());
 
        String eventName = entity.getEventName();
        if (eventName != null) {
            stmt.bindString(7, eventName);
        }
 
        String country = entity.getCountry();
        if (country != null) {
            stmt.bindString(8, country);
        }
 
        String templateId = entity.getTemplateId();
        if (templateId != null) {
            stmt.bindString(9, templateId);
        }
 
        String position = entity.getPosition();
        if (position != null) {
            stmt.bindString(10, position);
        }
        stmt.bindLong(11, entity.getType());
        stmt.bindLong(12, entity.getPage());
        stmt.bindLong(13, entity.getStep());
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public LogEvent readEntity(Cursor cursor, int offset) {
        LogEvent entity = new LogEvent( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // packageName
            cursor.getInt(offset + 2), // OfferId
            cursor.getShort(offset + 3) != 0, // isUp
            cursor.getLong(offset + 4), // createTime
            cursor.getLong(offset + 5), // changeTime
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // eventName
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // country
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // templateId
            cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9), // position
            cursor.getInt(offset + 10), // type
            cursor.getInt(offset + 11), // page
            cursor.getInt(offset + 12) // step
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, LogEvent entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setPackageName(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setOfferId(cursor.getInt(offset + 2));
        entity.setIsUp(cursor.getShort(offset + 3) != 0);
        entity.setCreateTime(cursor.getLong(offset + 4));
        entity.setChangeTime(cursor.getLong(offset + 5));
        entity.setEventName(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setCountry(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setTemplateId(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setPosition(cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9));
        entity.setType(cursor.getInt(offset + 10));
        entity.setPage(cursor.getInt(offset + 11));
        entity.setStep(cursor.getInt(offset + 12));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(LogEvent entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(LogEvent entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(LogEvent entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
