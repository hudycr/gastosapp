package com.uep.followmymoney.ui.activities;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.uep.followmymoney.R;
import com.uep.followmymoney.data.CuentaDataMgr;
import com.uep.followmymoney.domain.Cuenta;
import com.uep.followmymoney.domain.TipoCuenta;

import java.util.Calendar;
import java.util.Date;

public class CrearCuentaActivity extends AppCompatActivity {

    public static String OPERATION_TYPE_ARG =  "CrearCuentaActivityOPERATION_TYPE_ARG";
    public static String CUENTA_ID_ARG =  "CrearCuentaActivityCUENTA_ID_ARG";
    public static int OPERATION_NEW = 0;
    public static int OPERATION_EDIT = 1;

    private TextInputEditText etNombre;
    private TextInputEditText etDescripcion;
    private EditText etSaldo;

    private int tipoOperacion = 0;
    private long cuentaId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_cuenta);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setupView();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_save, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.action_save:
                actionSave();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setupView() {
        //cargar componentes
        etNombre = (TextInputEditText) findViewById(R.id.crear_cuenta_etNombre);
        etDescripcion = (TextInputEditText) findViewById(R.id.crear_cuenta_etDescripcion);
        etSaldo = (EditText) findViewById(R.id.crear_cuenta_etSaldo);

        tipoOperacion = getIntent().getIntExtra(OPERATION_TYPE_ARG, 0);
        if (tipoOperacion == OPERATION_EDIT) {
            //cargar datos
            cuentaId = getIntent().getLongExtra(CUENTA_ID_ARG, 0);
            Cuenta cuenta = CuentaDataMgr.getInstance(this).getById(cuentaId);
            if (cuenta != null) {
                dataToView(cuenta);
            } else {
                Toast.makeText(this, "Registro incorrecto", Toast.LENGTH_LONG).show();
                finish();
            }
        }
    }

    private void actionSave() {
        try {
            boolean isValid = validateFields();
            if (!isValid)
                return;


            if (tipoOperacion == OPERATION_NEW) {
                Cuenta cuenta = viewToData(new Cuenta());
                CuentaDataMgr.getInstance(this).insert(cuenta);
            } else {
                Cuenta cuenta = CuentaDataMgr.getInstance(this).getById(cuentaId);
                cuenta = viewToData(cuenta);
                CuentaDataMgr.getInstance(this).update(cuenta);
            }
            Toast.makeText(this, "Registro correcto", Toast.LENGTH_LONG).show();
            NavUtils.navigateUpTo(this, new Intent(this, MainActivity.class));
        } catch (Exception ex) {
            ex.printStackTrace();
            Toast.makeText(this, "Error", Toast.LENGTH_LONG).show();
        }
    }

    private Cuenta viewToData(Cuenta cuenta){

        cuenta.setNombre(etNombre.getText().toString());
        cuenta.setDescripcion(etDescripcion.getText().toString());

        if (tipoOperacion == OPERATION_NEW) {
            cuenta.setFechaCreacion(Calendar.getInstance().getTime());
            cuenta.setTipo(TipoCuenta.PROPIETARIO);
        }
        return cuenta;
    }

    private void dataToView(Cuenta cuenta){
        etNombre.setText(cuenta.getNombre());
        etDescripcion.setText(cuenta.getDescripcion());
    }
    private boolean validateFields(){
        return true;
    }



}
