package com.uep.followmymoney.dao;

import org.greenrobot.greendao.test.AbstractDaoTestLongPk;

import com.uep.followmymoney.domain.TransactionOutput;

public class TransactionOutputTest extends AbstractDaoTestLongPk<TransactionOutputDao, TransactionOutput> {

    public TransactionOutputTest() {
        super(TransactionOutputDao.class);
    }

    @Override
    protected TransactionOutput createEntity(Long key) {
        TransactionOutput entity = new TransactionOutput();
        entity.setId(key);
        entity.setValue();
        entity.setActive();
        entity.setReciepientId();
        entity.setParentTransactionId();
        return entity;
    }

}
