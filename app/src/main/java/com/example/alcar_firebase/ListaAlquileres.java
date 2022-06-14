package com.example.alcar_firebase;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ListaAlquileres extends AppCompatActivity {
    FirebaseFirestore db;
    public ArrayList<Alquiler> listaDatos;
    public ListView listaXml;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_alquileres);

        listaDatos = new ArrayList<Alquiler>();
        listaXml = findViewById(R.id.listaAutosAlquilados);

        db = FirebaseFirestore.getInstance();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadDatainListview();
    }
    @SuppressLint("Range")
    private void loadDatainListview() {
        // below line is use to get data from Firebase
        // firestore using collection in android.
        listaDatos = new ArrayList<Alquiler>();
        db.collection("Alquiler")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            Alquiler p;
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                p= new Alquiler();
                                p.idCliente = document.getString("idCliente");
                                p.nombre = document.getString("nombre");
                                p.modelo = document.getString("modelo");
                                p.placa = document.getString("placa");
                                p.fechaInicio = document.getString("fechaInicio");
                                p.FechaEntrega = document.getString("FechaEntrega");
                                p.TotalPago = document.getString("TotalPago");

                                listaDatos.add(p);
                            }
                            adaptadorAlquiler ad = new adaptadorAlquiler(listaDatos, getApplicationContext());
                            listaXml.setAdapter(ad);
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });

    }
}