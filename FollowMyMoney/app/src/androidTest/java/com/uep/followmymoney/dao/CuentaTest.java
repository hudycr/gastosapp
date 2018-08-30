package com.uep.followmymoney.dao;

import org.greenrobot.greendao.test.AbstractDaoTestLongPk;

import com.uep.followmymoney.domain.Cuenta;

public class CuentaTest extends AbstractDaoTestLongPk<CuentaDao, Cuenta> {

    public CuentaTest() {
        super(CuentaDao.class);
    }

    @Override
    protected Cuenta createEntity(Long key) {
        Cuenta entity = new Cuenta();
        entity.setId(key);
        entity.setNombre("");
        entity.setSaldo(0);
        return entity;
    }

}
