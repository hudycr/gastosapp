package com.uep.followmymoney.domain;

import org.greenrobot.greendao.converter.PropertyConverter;

public class TipoCuentaConverter  implements PropertyConverter<TipoCuenta, Integer> {
    @Override
    public TipoCuenta convertToEntityProperty(Integer databaseValue) {
        if (databaseValue == null) {
            return null;
        }
        for (TipoCuenta role : TipoCuenta.values()) {
            if (role.id == databaseValue) {
                return role;
            }
        }
        return TipoCuenta.PROPIETARIO;
    }

    @Override
    public Integer convertToDatabaseValue(TipoCuenta entityProperty) {
        return entityProperty == null ? null : entityProperty.id;
    }
}
