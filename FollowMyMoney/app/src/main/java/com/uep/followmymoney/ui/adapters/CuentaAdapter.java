package com.uep.followmymoney.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.uep.followmymoney.R;
import com.uep.followmymoney.data.CuentaDataMgr;
import com.uep.followmymoney.domain.Cuenta;

import java.text.NumberFormat;
import java.util.List;

/**
 * Created by hchan on 12/04/2018.
 */

public class CuentaAdapter extends RecyclerView.Adapter<CuentaAdapter.ViewHolder> {

    private List<Cuenta> cuentas;
    private Context context;

    public CuentaAdapter(Context context, List<Cuenta> cuentas) {
        super();
        this.context = context;
        this.cuentas = cuentas;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cuenta_item, parent, false);

        return new CuentaAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Cuenta cuenta = (Cuenta) getItem(position);
        holder.tvNombre.setText(cuenta.getNombre());
        float saldo = CuentaDataMgr.getInstance(this.context).getBalance(cuenta.getId());
        holder.tvSaldo.setText(NumberFormat.getCurrencyInstance().format(saldo));
    }

    private Object getItem(int position) {
        return cuentas.get(position);
    }

    @Override
    public int getItemCount() {
        return cuentas.size();
    }

    public static class ViewHolder extends  RecyclerView.ViewHolder {

        TextView tvNombre;
        TextView tvSaldo;

        public ViewHolder(View v) {
            super(v);

            tvNombre = (TextView) v.findViewById(R.id.cuenta_item_tvNombre);
            tvSaldo = (TextView) v.findViewById(R.id.cuenta_item_tvSaldo);
        }
    }
}
