package com.uep.followmymoney.dao;

import org.greenrobot.greendao.test.AbstractDaoTestLongPk;

import com.uep.followmymoney.domain.Block;

public class BlockTest extends AbstractDaoTestLongPk<BlockDao, Block> {

    public BlockTest() {
        super(BlockDao.class);
    }

    @Override
    protected Block createEntity(Long key) {
        Block entity = new Block();
        entity.setId(key);
        entity.setHash();
        entity.setPreviousHash();
        entity.setTimeStamp();
        return entity;
    }

}
