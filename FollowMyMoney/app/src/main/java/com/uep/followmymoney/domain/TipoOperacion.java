package com.uep.followmymoney.domain;

/**
 * Created by hchan on 11/04/2018.
 */

public enum TipoOperacion {
    DEFAULT(0), DEPOSITO(1), RETIRO(2), GASTO(3);

    final int id;

    TipoOperacion(int id) {
        this.id = id;
    }
}
