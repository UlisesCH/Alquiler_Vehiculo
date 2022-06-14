package com.example.alcar_firebase;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class adaptadorAuto extends BaseAdapter {

    public ArrayList<Auto> data;
    public Context context;
    public TextView placa, marca, modelo, anio, estado, tipo, idAuto;

    public adaptadorAuto(ArrayList<Auto> lista, Context context){

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
        Auto p = (Auto) getItem(i);
        view = LayoutInflater.from(context).inflate(R.layout.item_autos, null);
        placa = view.findViewById(R.id.txtViewPlaca);
        marca = view.findViewById(R.id.txtViewMarca);
        modelo = view.findViewById(R.id.txtViewModelo);
        anio = view.findViewById(R.id.txtViewAnio);
        estado = view.findViewById(R.id.txtViewDispo);
        tipo = view.findViewById(R.id.txtViewTipo);
        idAuto = view.findViewById(R.id.txtViewPlaca);

        placa.setText(p.placa);
        marca.setText(p.marca);
        modelo.setText(p.modelo);
        anio.setText(p.anio);
        estado.setText(p.estado);
        tipo.setText(p.tipo);
        idAuto.setText(p.idAuto);



        return view;
    }
}
