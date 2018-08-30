package com.uep.followmymoney.ui.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.uep.followmymoney.R;
import com.uep.followmymoney.data.CuentaDataMgr;
import com.uep.followmymoney.domain.Cuenta;

import java.text.NumberFormat;
import java.util.List;

/**
 * Created by hchan on 13/04/2018.
 */

public class CuentaSpinAdapter extends ArrayAdapter<Cuenta> {

    private List<Cuenta> cuentas;
    private Context context;
    public CuentaSpinAdapter(Context context, int resource, List<Cuenta> objects) {
        super(context, resource, objects);
        this.context = context;
        cuentas = objects;
    }

    @Override
    public int getCount() {
        return cuentas.size();
    }

    @Override
    public Cuenta getItem(int position) {
        return cuentas.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Cuenta cuenta = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.cuenta_spinner_item, parent, false);
        }
        TextView tvName = (TextView) convertView.findViewById(R.id.cuenta_spinner_item_tvNombre);
        TextView tvSaldo = (TextView) convertView.findViewById(R.id.cuenta_spinner_item_tvSaldo);
        // Populate the data into the template view using the data object
        tvName.setText(cuenta.getNombre());
        float saldo = CuentaDataMgr.getInstance(this.context).getBalance(cuenta.getId());
        tvSaldo.setText(NumberFormat.getCurrencyInstance().format(saldo));
        return convertView;
    }
    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        Cuenta cuenta = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.cuenta_spinner_item, parent, false);
        }
        TextView tvName = (TextView) convertView.findViewById(R.id.cuenta_spinner_item_tvNombre);
        TextView tvSaldo = (TextView) convertView.findViewById(R.id.cuenta_spinner_item_tvSaldo);
        // Populate the data into the template view using the data object
        tvName.setText(cuenta.getNombre());
        float saldo = CuentaDataMgr.getInstance(this.context).getBalance(cuenta.getId());
        tvSaldo.setText(NumberFormat.getCurrencyInstance().format(saldo));

        return convertView;
    }
}
