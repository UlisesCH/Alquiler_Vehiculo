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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ListaAutos extends AppCompatActivity {
    FirebaseFirestore db;
    public ArrayList<Auto> listaDatos;
    public ListView listaXml;
    public Dialog popEditar;
    public String estado;
    public String tipo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_autos);

        listaDatos = new ArrayList<Auto>();
        listaXml = findViewById(R.id.listaAutos);
        popEditar = new Dialog(this);
        // initializing our variable for firebase
        // firestore and getting its instance.
        db = FirebaseFirestore.getInstance();
        popEditar.setContentView(R.layout.pop_auto);
        // here we are calling a method
        // to load data in our list view.

        Spinner spinner = (Spinner) popEditar.findViewById(R.id.spinner);
        String[] valores = {"Estado: Disponible","Estado: Ocupado","Estado: Mantenimiento"};
        spinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, valores));
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id)
            {

                estado = (String) adapterView.getItemAtPosition(position);

                if(estado =="Estado: Disponible"){
                    estado = "Disponible";
                }else if(estado =="Estado: Ocupado"){
                    estado = "Ocupado";
                }else if (estado =="Estado: Mantenimiento"){
                    estado = "Mantenimiento";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {
                // vacio

            }
        });

        Spinner tipoSpiner = (Spinner) popEditar.findViewById(R.id.tipoSpinerEdit);
        String[] tipoItem = {"Tipo: Coche","Tipo: Microbus","Tipo: Carga", "Tipo Camion"};
        tipoSpiner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, tipoItem));
        tipoSpiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id)
            {

                tipo = (String) adapterView.getItemAtPosition(position);

                if(tipo =="Tipo: Coche"){
                    tipo = "Coche";
                }else if(tipo =="Tipo: Microbus"){
                    tipo = "Microbus";
                }else if (tipo =="Tipo: Carga"){
                    tipo = "Carga";
                }else if (tipo =="Tipo Camion"){
                    tipo = "Camion";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {
                // vacio

            }
        });

        Button btnAddAuto = findViewById(R.id.btnAddAuto);
        btnAddAuto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListaAutos.this, frmAuto.class);
                startActivity(intent);
            }
        });


        listaXml.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Auto modelo = listaDatos.get(i);

                EditText txtID = (EditText) popEditar.findViewById(R.id.txtIdAuto);
                EditText txtPlaca = (EditText) popEditar.findViewById(R.id.txtplaca);
                EditText txtMarca = (EditText) popEditar.findViewById(R.id.txtMarca);
                EditText txtModelo = (EditText) popEditar.findViewById(R.id.txtModelo);
                EditText txtAnio = (EditText) popEditar.findViewById(R.id.txtAnio);

                txtID.setText(String.valueOf(modelo.idAuto));
                txtPlaca.setText(String.valueOf(modelo.placa));
                txtMarca.setText(String.valueOf(modelo.marca));
                txtModelo.setText(String.valueOf(modelo.modelo));
                txtAnio.setText(String.valueOf(modelo.anio));
                estado = String.valueOf(modelo.estado);

                Button btnModificar = (Button) popEditar.findViewById(R.id.btnGuardarAutos);
                btnModificar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String placa = txtPlaca.getText().toString();
                        String marca = txtMarca.getText().toString();
                        String modelo=  txtModelo.getText().toString();
                        String anio=  txtAnio.getText().toString();
                        String dispo=  estado;
                        String tipoAuto = tipo;
                        String id = txtID.getText().toString();
                        Auto AddAuto = new Auto(id,placa, marca,modelo, anio, dispo, tipoAuto);
                        db.collection("Autos")
                                .document(id)
                                .set(AddAuto)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(ListaAutos.this, "REGISTRO ACTUALIZADO CON EXITO", Toast.LENGTH_SHORT).show();
                                        popEditar.dismiss();
                                        loadDatainListview();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(ListaAutos.this, "HUBO UN ERROR EN EL PROCESO", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                });
                Button btnEliminar = popEditar.findViewById(R.id.btnCancelar);
                btnEliminar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        EditText txtID = (EditText) popEditar.findViewById(R.id.txtIdcliente);
                        String id = txtID.getText().toString();
                        db.collection("Clientes").document(id).delete();
                        popEditar.dismiss();
                        loadDatainListview();
                    }
                });
                popEditar.show();
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
        listaDatos = new ArrayList<Auto>();
        db.collection("Autos")
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