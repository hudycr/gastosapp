package com.uep.followmymoney.dao;

import org.greenrobot.greendao.test.AbstractDaoTestLongPk;

import com.uep.followmymoney.domain.Transaction;

public class TransactionTest extends AbstractDaoTestLongPk<TransactionDao, Transaction> {

    public TransactionTest() {
        super(TransactionDao.class);
    }

    @Override
    protected Transaction createEntity(Long key) {
        Transaction entity = new Transaction();
        entity.setId(key);
        entity.setValue();
        entity.setBlockId();
        entity.setCuentaSenderId();
        entity.setCuentaReciepientId();
        return entity;
    }

}
