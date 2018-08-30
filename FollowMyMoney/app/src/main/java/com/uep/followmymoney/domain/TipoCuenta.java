package com.uep.followmymoney.domain;

public enum  TipoCuenta {
    PROPIETARIO(0), EFECTIVO(1), SALIDAS(2), ENTRADAS(3);

    final int id;

    TipoCuenta(int id) {
        this.id = id;
    }
}
