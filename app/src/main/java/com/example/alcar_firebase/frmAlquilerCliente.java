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
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class frmAlquilerCliente extends AppCompatActivity {

    FirebaseFirestore db;
    public ArrayList<Cliente> listaDatos;
    public ArrayList<Auto> listaDatosAuto;
    public ListView listaXml;
    public String estado;
    public String tipo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_alquiler_cliente);

        listaDatos = new ArrayList<Cliente>();
        listaDatosAuto = new ArrayList<Auto>();
        listaXml = findViewById(R.id.listaClienteAlquiler);
        db = FirebaseFirestore.getInstance();

        TextView id = findViewById(R.id.txtIdAlquilerAlquilar);
        TextView modelo = findViewById(R.id.txtViewModeloAlquiler);
        TextView marca = findViewById(R.id.txtViewMarcaAlquiler);
        TextView anio = findViewById(R.id.txtViewAnioAlquiler);
        TextView estado = findViewById(R.id.txtViewDispoAlquiler);
        TextView tipo = findViewById(R.id.txtViewTipoAlquiler);
        TextView placa = findViewById(R.id.txtViewPlacaAlquiler);


        modelo.setText(getIntent().getStringExtra("modelo"));
        marca.setText(getIntent().getStringExtra("marca"));
        anio.setText(getIntent().getStringExtra("anio"));
        estado.setText(getIntent().getStringExtra("dispo"));
        tipo.setText(getIntent().getStringExtra("tipo"));
        placa.setText(getIntent().getStringExtra("placa"));


        listaXml.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Cliente modeloCliente = listaDatos.get(i);
                Intent intent = new Intent(frmAlquilerCliente.this, frmAlquilerFinal.class);
                intent.putExtra("idUsuario", modeloCliente.idUsuario);
                intent.putExtra("nombre", modeloCliente.nombre);
                intent.putExtra("documento", modeloCliente.documento);
                intent.putExtra("telefono", modeloCliente.telefono);
                intent.putExtra("fecha_nacimiento", modeloCliente.fecha_nacimiento);
                intent.putExtra("email", modeloCliente.email);
                intent.putExtra("estado", modeloCliente.estado);


                intent.putExtra("idCar", id.getText());
                intent.putExtra("placa", placa.getText());
                intent.putExtra("marca", marca.getText());
                intent.putExtra("modelo", modelo.getText());
                intent.putExtra("anio", anio.getText());
                intent.putExtra("dispo", estado.getText());
                intent.putExtra("tipo", tipo.getText());
                startActivity(intent);

            }
        });
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
        listaDatos = new ArrayList<Cliente>();
        db.collection("Clientes")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            Cliente p;
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                p= new Cliente();
                                p.nombre = document.getString("nombre");
                                p.email = document.getString("email");
                                p.idUsuario = document.getId();
                                p.documento = document.getString("documento");
                                p.fecha_nacimiento = document.getString("fecha_nacimiento");
                                p.telefono = document.getString("telefono");
                                listaDatos.add(p);
                            }
                            adaptadorLista ad = new adaptadorLista(listaDatos, getApplicationContext());
                            listaXml.setAdapter(ad);
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });

    }
}