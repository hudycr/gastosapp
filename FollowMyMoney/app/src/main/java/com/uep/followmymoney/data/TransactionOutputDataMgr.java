package com.uep.followmymoney.data;

import android.content.Context;

import com.uep.followmymoney.dao.DaoSession;
import com.uep.followmymoney.dao.TransactionOutputDao;
import com.uep.followmymoney.domain.TransactionOutput;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

public class TransactionOutputDataMgr {
    private static final String TAG = OperacionDataMgr.class.getCanonicalName();

    static Context context;

    private static TransactionOutputDataMgr instance = new TransactionOutputDataMgr();

    public static TransactionOutputDataMgr getInstance(Context paramContext) {
        if (instance == null) {
            instance = new TransactionOutputDataMgr();
        }
        context = paramContext;
        return instance;
    }

    public void insert(TransactionOutput entity) {
        DataContext.getInstance(context).openWritableDb().getTransactionOutputDao().insert(entity);
    }

    public void update(TransactionOutput entity){
        DaoSession daoSession = DataContext.getInstance(context).openWritableDb();
        TransactionOutputDao dao = daoSession.getTransactionOutputDao();
        dao.update(entity);
    }

    public List<TransactionOutput> getByCuentaId(long cuentaId) {
        DaoSession daoSession = DataContext.getInstance(context).openReadableDb();
        QueryBuilder<TransactionOutput> query = daoSession.getTransactionOutputDao().queryBuilder();
        query.where(TransactionOutputDao.Properties.ReciepientId.eq(cuentaId),
                TransactionOutputDao.Properties.Active.eq(1));

        query.orderAsc(TransactionOutputDao.Properties.Id);
        return query.list();
    }
}
