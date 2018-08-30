package com.uep.followmymoney.dao;

import org.greenrobot.greendao.test.AbstractDaoTestLongPk;

import com.uep.followmymoney.domain.Operacion;

public class OperacionTest extends AbstractDaoTestLongPk<OperacionDao, Operacion> {

    public OperacionTest() {
        super(OperacionDao.class);
    }

    @Override
    protected Operacion createEntity(Long key) {
        Operacion entity = new Operacion();
        entity.setId(key);
        entity.setConcepto();
        entity.setCuentaId();
        return entity;
    }

}
