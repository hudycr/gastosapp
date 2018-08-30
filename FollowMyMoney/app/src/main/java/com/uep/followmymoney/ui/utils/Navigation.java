package com.uep.followmymoney.ui.utils;

import android.app.Activity;
import android.content.Intent;

import com.uep.followmymoney.ui.activities.CrearCuentaActivity;
import com.uep.followmymoney.ui.activities.NuevaOperacionActivity;

/**
 * Created by hchan on 11/04/2018.
 */

public class Navigation {

    public static void goToCrearCuenta(Activity context) {
        Intent intent = new Intent(context, CrearCuentaActivity.class);
        intent.putExtra(CrearCuentaActivity.OPERATION_TYPE_ARG, CrearCuentaActivity.OPERATION_NEW);
        context.startActivity(intent);
    }

    public static void goToEditarCuenta(Activity context, long cuentaId) {
        Intent intent = new Intent(context, CrearCuentaActivity.class);
        intent.putExtra(CrearCuentaActivity.OPERATION_TYPE_ARG, CrearCuentaActivity.OPERATION_EDIT);
        intent.putExtra(CrearCuentaActivity.CUENTA_ID_ARG, cuentaId);
        context.startActivity(intent);
    }

    public static void goToNuevoDeposito(Activity context) {
        Intent intent = new Intent(context, NuevaOperacionActivity.class);
        intent.putExtra(NuevaOperacionActivity.TIPO_OPERACION_ARG, NuevaOperacionActivity.OPERACION_DEPOSITO);
        context.startActivity(intent);
    }

    public static void goToNuevoRetiro(Activity context) {
        Intent intent = new Intent(context, NuevaOperacionActivity.class);
        intent.putExtra(NuevaOperacionActivity.TIPO_OPERACION_ARG, NuevaOperacionActivity.OPERACION_RETIRO);
        context.startActivity(intent);
    }

    public static void goToNuevoGasto(Activity context) {
        Intent intent = new Intent(context, NuevaOperacionActivity.class);
        intent.putExtra(NuevaOperacionActivity.TIPO_OPERACION_ARG, NuevaOperacionActivity.OPERACION_GASTO);
        context.startActivity(intent);
    }
}
