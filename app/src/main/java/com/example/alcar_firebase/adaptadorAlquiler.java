package com.example.alcar_firebase;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class adaptadorAlquiler extends BaseAdapter {
    public ArrayList<Alquiler> data;
    public Context context;
    public TextView modelo, cliente, fechaInicio, fechaEntrega, total,placa;

    public adaptadorAlquiler(ArrayList<Alquiler> lista, Context context){

        this.data = lista;
        this.context = context;

    }
    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Alquiler p = (Alquiler) getItem(i);
        view = LayoutInflater.from(context).inflate(R.layout.item_autos, null);
        cliente = view.findViewById(R.id.txtViewMarca);
        modelo = view.findViewById(R.id.txtViewModelo);
        fechaInicio = view.findViewById(R.id.txtViewAnio);
        fechaEntrega = view.findViewById(R.id.txtViewPlaca);
        total = view.findViewById(R.id.txtViewDispo);
        placa = view.findViewById(R.id.txtViewTipo);

        cliente.setText("Cliente:   "+p.nombre);
        modelo.setText(p.modelo);
        fechaInicio.setText("Fecha Alquiler:   "+p.fechaInicio);
        fechaEntrega.setText("Fecha Entrega:   "+p.FechaEntrega);
        total.setText("Total a Pagar:   "+"$"+p.TotalPago);
        placa.setVisibility(View.GONE);
        return view;
    }
}
