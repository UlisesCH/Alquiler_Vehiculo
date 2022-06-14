package com.example.alcar_firebase;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class adaptadorLista extends BaseAdapter {
    public ArrayList<Cliente> data;
    public Context context;
    public TextView txt1, txt2, idCliente, telefono, fecha, documento;

    public adaptadorLista(ArrayList<Cliente> lista, Context context){

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

        Cliente p = (Cliente) getItem(i);
        view = LayoutInflater.from(context).inflate(R.layout.item_cliente, null);
        txt1 = view.findViewById(R.id.textView_superior);
        txt2 = view.findViewById(R.id.textView_inferior);
        idCliente = view.findViewById(R.id.idCliente);
        documento = view.findViewById(R.id.txtDui);
        fecha = view.findViewById(R.id.txtFechaNacimiento);
        telefono = view.findViewById(R.id.txtTel);

        txt1.setText(p.nombre);
        txt2.setText(p.email);
        idCliente.setText(p.idUsuario);
        documento.setText(p.documento);
        fecha.setText(p.fecha_nacimiento);
        telefono.setText(p.telefono);



        return view;
    }
}
