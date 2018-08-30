package com.uep.followmymoney.domain;

import org.greenrobot.greendao.converter.PropertyConverter;

/**
 * Created by hchan on 11/04/2018.
 */

public class TipoOperacionConverter implements PropertyConverter<TipoOperacion, Integer> {
    @Override
    public TipoOperacion convertToEntityProperty(Integer databaseValue) {
        if (databaseValue == null) {
            return null;
        }
        for (TipoOperacion role : TipoOperacion.values()) {
            if (role.id == databaseValue) {
                return role;
            }
        }
        return TipoOperacion.DEFAULT;
    }

    @Override
    public Integer convertToDatabaseValue(TipoOperacion entityProperty) {
        return entityProperty == null ? null : entityProperty.id;
    }
}
