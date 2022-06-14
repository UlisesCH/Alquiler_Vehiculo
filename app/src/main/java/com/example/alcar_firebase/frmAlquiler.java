package com.example.alcar_firebase;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class frmAlquiler extends AppCompatActivity {

    FirebaseFirestore db;
    public ArrayList<Auto> listaDatos;
    public ListView listaXml;
    public Dialog popEditar;
    public String estado;
    public String tipo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_alquiler);

        listaDatos = new ArrayList<Auto>();
        listaXml = findViewById(R.id.listaAutos);

        // initializing our variable for firebase
        // firestore and getting its instance.
        db = FirebaseFirestore.getInstance();


        listaXml.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Auto modelo = listaDatos.get(i);
                Intent intent = new Intent(frmAlquiler.this, frmAlquilerCliente.class);
                intent.putExtra("idCar", modelo.idAuto);
                intent.putExtra("placa", modelo.placa);
                intent.putExtra("marca", modelo.marca);
                intent.putExtra("modelo", modelo.modelo);
                intent.putExtra("anio", modelo.anio);
                intent.putExtra("dispo", modelo.estado);
                intent.putExtra("tipo", modelo.tipo);
                startActivity(intent);

            }
        });


    }

    protected void onResume() {
        super.onResume();
        loadDatainListview();
    }
    @SuppressLint("Range")
    private void loadDatainListview() {
        // below line is use to get data from Firebase
        // firestore using collection in android.
        listaDatos = new ArrayList<Auto>();

        db.collection("Autos").whereEqualTo("estado", "Disponible")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            Auto p;
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                p= new Auto();
                                p.placa = document.getString("placa");
                                p.marca = document.getString("marca");
                                p.modelo = document.getString("modelo");
                                p.anio = document.getString("anio");
                                p.estado = document.getString("estado");
                                p.tipo = document.getString("tipo");
                                p.idAuto = document.getId();

                                listaDatos.add(p);
                            }
                            adaptadorAuto ad = new adaptadorAuto(listaDatos, getApplicationContext());
                            listaXml.setAdapter(ad);
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });

    }
}