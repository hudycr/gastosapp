package com.uep.followmymoney.data;

import android.content.Context;

public class TransactionDataMgr {
    private static final String TAG = OperacionDataMgr.class.getCanonicalName();

    static Context context;

    private static TransactionDataMgr instance = new TransactionDataMgr();

    public static TransactionDataMgr getInstance(Context paramContext) {
        if (instance == null) {
            instance = new TransactionDataMgr();
        }
        context = paramContext;
        return instance;
    }
}
