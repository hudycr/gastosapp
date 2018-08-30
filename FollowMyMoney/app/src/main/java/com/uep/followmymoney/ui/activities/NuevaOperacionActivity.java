package com.uep.followmymoney.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.uep.followmymoney.R;
import com.uep.followmymoney.data.CuentaDataMgr;
import com.uep.followmymoney.data.OperacionDataMgr;
import com.uep.followmymoney.domain.Cuenta;
import com.uep.followmymoney.domain.Operacion;
import com.uep.followmymoney.domain.TipoOperacion;
import com.uep.followmymoney.ui.adapters.CuentaSpinAdapter;
import com.uep.followmymoney.ui.fragments.DatePickerFragment;
import com.uep.followmymoney.ui.fragments.TimePickerFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class NuevaOperacionActivity extends AppCompatActivity {

    public static String TIPO_OPERACION_ARG = "NuevaOperacion_TIPOARG";
    public static int OPERACION_DEPOSITO = 1;
    public static int OPERACION_RETIRO = 2;
    public static int OPERACION_GASTO = 3;

    private int tipoOperacion = 0;

    private Spinner spCuentas;
    private EditText etFecha;
    private EditText etHora;
    private EditText etConcepto;
    private EditText etMonto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nueva_operacion);
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
        spCuentas = (Spinner) findViewById(R.id.nueva_operacion_spCuentas);
        etFecha = (EditText) findViewById(R.id.nueva_operacion_etFecha);
        etHora = (EditText) findViewById(R.id.nueva_operacion_etHora);
        etConcepto = (EditText) findViewById(R.id.nueva_operacion_etConcepto);
        etMonto = (EditText) findViewById(R.id.nueva_operacion_etMonto);

        tipoOperacion = getIntent().getIntExtra(TIPO_OPERACION_ARG, 0);
        List<Cuenta> cuentas = new ArrayList<>();
        if (tipoOperacion == OPERACION_RETIRO) {
            setTitle("Nuevo retiro");
            cuentas = CuentaDataMgr.getInstance(this).getAll(false);
        } else if (tipoOperacion == OPERACION_GASTO) {
            setTitle("Nuevo gasto");
            cuentas = CuentaDataMgr.getInstance(this).getAll();
        } else if (tipoOperacion == OPERACION_DEPOSITO) {
            setTitle("Nuevo deposito");
            cuentas = CuentaDataMgr.getInstance(this).getAll();
        }
        String dateFormat = "dd MMMM yyyy"; //In which you need put here
        String timeFormat = "HH:mm";
        SimpleDateFormat sdformat = new SimpleDateFormat(dateFormat, Locale.getDefault());
        etFecha.setText(sdformat.format(Calendar.getInstance().getTime()));
        etHora.setText(new SimpleDateFormat(timeFormat, Locale.getDefault()).format(Calendar.getInstance().getTime()));

        new DatePickerFragment(etFecha, etHora, this);

        new TimePickerFragment(etHora, etConcepto, this, etFecha);

        CuentaSpinAdapter adapter = new CuentaSpinAdapter(this,
                R.layout.cuenta_spinner_item,
                cuentas);
        spCuentas.setAdapter(adapter);
    }

    private void actionSave() {
        try {
            boolean isValid = validateFields();
            if (!isValid)
                return;
            Operacion operacion = viewToData();
            CuentaDataMgr.getInstance(this).generarNuevaOperacion(operacion);
            Toast.makeText(this, "Registro correcto", Toast.LENGTH_LONG).show();
            NavUtils.navigateUpTo(this, new Intent(this, MainActivity.class));
        } catch (Exception ex) {
            ex.printStackTrace();
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private Operacion viewToData(){
        Operacion operacion =  new Operacion();
        operacion.setConcepto(etConcepto.getText().toString());
        operacion.setFecha(Calendar.getInstance().getTime());
        operacion.setMonto(Float.parseFloat(etMonto.getText().toString()));

        if (tipoOperacion == OPERACION_DEPOSITO) {
            operacion.setTipo(TipoOperacion.DEPOSITO);
        } else if (tipoOperacion == OPERACION_GASTO) {
            operacion.setTipo(TipoOperacion.GASTO);
        } else if (tipoOperacion == OPERACION_RETIRO) {
            operacion.setTipo(TipoOperacion.RETIRO);
        } else  {
            operacion.setTipo(TipoOperacion.DEFAULT);
        }
        Cuenta cuenta = (Cuenta) spCuentas.getSelectedItem();

        operacion.setCuenta(cuenta);
        operacion.setCuentaId(cuenta.getId());
        return operacion;
    }

    private boolean validateFields(){
        return true;
    }


    private void hideInputs() {
        InputMethodManager inputManager = (InputMethodManager) NuevaOperacionActivity.this
                .getSystemService(Context.INPUT_METHOD_SERVICE);

        // check if no view has focus:
        View v = NuevaOperacionActivity.this.getCurrentFocus();
        if (v != null)
            inputManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }
}
