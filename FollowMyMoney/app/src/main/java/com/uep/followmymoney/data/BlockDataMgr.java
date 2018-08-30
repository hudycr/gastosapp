package com.uep.followmymoney.data;

import android.content.Context;

import com.uep.followmymoney.dao.BlockDao;
import com.uep.followmymoney.dao.DaoSession;
import com.uep.followmymoney.domain.Block;

import org.greenrobot.greendao.query.QueryBuilder;

public class BlockDataMgr {
    private static final String TAG = OperacionDataMgr.class.getCanonicalName();

    static Context context;

    private static BlockDataMgr instance = new BlockDataMgr();

    public static BlockDataMgr getInstance(Context paramContext) {
        if (instance == null) {
            instance = new BlockDataMgr();
        }
        context = paramContext;
        return instance;
    }

    public void insert(Block entity) {
        DataContext.getInstance(context).openWritableDb().getBlockDao().insert(entity);
    }

    public void update(Block entity){
        DaoSession daoSession = DataContext.getInstance(context).openWritableDb();
        BlockDao dao = daoSession.getBlockDao();
        dao.update(entity);
    }

    public Block getLastBlock(){
        DaoSession daoSession = DataContext.getInstance(context).openReadableDb();
        long count  = daoSession.getBlockDao().count();
        if (count > 0)
            return daoSession.getBlockDao().queryBuilder().orderDesc(BlockDao.Properties.Id).list().get(0);
        return null;
    }
}
