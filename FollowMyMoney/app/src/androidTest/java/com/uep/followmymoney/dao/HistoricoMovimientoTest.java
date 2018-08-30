package com.uep.followmymoney.dao;

import org.greenrobot.greendao.test.AbstractDaoTestLongPk;

public class HistoricoMovimientoTest extends AbstractDaoTestLongPk<HistoricoMovimientoDao, HistoricoMovimiento> {

    public HistoricoMovimientoTest() {
        super(HistoricoMovimientoDao.class);
    }

    @Override
    protected HistoricoMovimiento createEntity(Long key) {
        HistoricoMovimiento entity = new HistoricoMovimiento();
        entity.setId(key);
        entity.setConcepto("");
        entity.setCuentaId(1);
        return entity;
    }

}
