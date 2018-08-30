package com.uep.followmymoney.data;

import android.content.Context;
import android.database.sqlite.SQLiteException;

import com.uep.followmymoney.dao.DaoMaster;
import com.uep.followmymoney.dao.DaoSession;

import org.greenrobot.greendao.database.Database;

/**
 * Created by hchan on 10/04/2018.
 */

public class DataContext {
    private static final String TAG = DataContext.class.getCanonicalName();

    public static final boolean ENCRYPTED = false;

    private static DataContext instance;
    private Context mContext;
    //private DaoMaster.DevOpenHelper mHelper;
    private DatabaseHelper mHelper;
    private Database mDatabase;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;
    private final String SECRET_KEY = "AER19aXww#";
    private final String dbName = "followmymoney";
    private final String dbNameEncrypted = "followmymoney-encrypted";

    public DataContext(final Context context){
        this.mContext = context;
        //mHelper = new DaoMaster.DevOpenHelper(this.mContext,dbName,null);
        mHelper = new DatabaseHelper(this.mContext,ENCRYPTED ? dbNameEncrypted : dbName,null);
    }

    public static DataContext getInstance(Context context){
        if(instance==null)
            instance= new DataContext(context);
        return instance;
    }

    public DaoSession openReadableDb() throws SQLiteException {
        mDatabase = ENCRYPTED ? mHelper.getEncryptedReadableDb(SECRET_KEY) : mHelper.getReadableDb();
        mDaoMaster = new DaoMaster(mDatabase);
        mDaoSession = mDaoMaster.newSession();
        return mDaoSession;
    }

    public DaoSession openWritableDb() throws SQLiteException {
        mDatabase = ENCRYPTED ? mHelper.getEncryptedWritableDb(SECRET_KEY) : mHelper.getWritableDb();
        mDaoMaster = new DaoMaster(mDatabase);
        mDaoSession = mDaoMaster.newSession();
        return mDaoSession;
    }

    public void closeDbConnections() {
        if (mDaoSession != null) {
            mDaoSession.clear();
            mDaoSession = null;
        }
        if (mDatabase != null) {
            mDatabase.close();
        }
        if (mHelper != null) {
            mHelper.close();
            mHelper = null;
        }
        if (instance != null) {
            instance = null;
        }
    }

    public synchronized void dropDatabase() {
        try {
            openWritableDb();
            DaoMaster.dropAllTables(mDatabase, true); // drops all tables
            mHelper.onCreate(mDatabase);              // creates the tables
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Boolean isReadOnlySession(){
        if(mDatabase == null) return null;
        return false;
    }
}
