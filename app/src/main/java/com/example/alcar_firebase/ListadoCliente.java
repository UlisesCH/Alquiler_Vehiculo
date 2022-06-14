package com.example.alcar_firebase;


import static android.content.ContentValues.TAG;

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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ListadoCliente extends AppCompatActivity {

    FirebaseFirestore db;
    public ArrayList<Cliente> listaDatos;
    public ListView listaXml;
    public Dialog popEditar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_cliente);

        listaDatos = new ArrayList<Cliente>();
        listaXml = findViewById(R.id.listaCliente);
        popEditar = new Dialog(this);
        // initializing our variable for firebase
        // firestore and getting its instance.
        db = FirebaseFirestore.getInstance();

        // here we are calling a method
        // to load data in our list view.



        Button btnAddCliente = findViewById(R.id.btnAddCliente);
        btnAddCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListadoCliente.this, frmCliente.class);
                startActivity(intent);
            }
        });


        listaXml.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Cliente modelo = listaDatos.get(i);
                popEditar.setContentView(R.layout.pop_cliente);
                EditText txtID = (EditText) popEditar.findViewById(R.id.txtIdcliente);
                EditText txtNombre = (EditText) popEditar.findViewById(R.id.txtNombreCliente);
                EditText txtDocumento = (EditText) popEditar.findViewById(R.id.txtDocumentoCliente);
                EditText txtTelefono = (EditText) popEditar.findViewById(R.id.txtTelefonoCliente);
                EditText txtEmail = (EditText) popEditar.findViewById(R.id.txtCorreoCliente);
                EditText txtFecha = (EditText) popEditar.findViewById(R.id.txtFechaNacimientoCliente);

                txtID.setText(String.valueOf(modelo.idUsuario));
                txtNombre.setText(String.valueOf(modelo.nombre));
                txtDocumento.setText(String.valueOf(modelo.documento));
                txtTelefono.setText(String.valueOf(modelo.telefono));
                txtEmail.setText(String.valueOf(modelo.email));
                txtFecha.setText(String.valueOf(modelo.fecha_nacimiento));
                Button btnModificar = (Button) popEditar.findViewById(R.id.btnGuardarCliente);
                btnModificar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        String nomb = txtNombre.getText().toString();
                        String doc = txtDocumento.getText().toString();
                        String tel=  txtTelefono.getText().toString();
                        String mail=  txtEmail.getText().toString();
                        String naci=  txtFecha.getText().toString();
                        String id = txtID.getText().toString();

                        Cliente AddCliente = new Cliente(id, nomb,doc, tel, mail, naci);

                        db.collection("Clientes")
                                .document(id)
                                .set(AddCliente)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(ListadoCliente.this, "REGISTRO ACTUALIZADO CON EXITO", Toast.LENGTH_SHORT).show();
                                        popEditar.dismiss();
                                        loadDatainListview();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(ListadoCliente.this, "HUBO UN ERROR EN EL PROCESO", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                });
                Button btnEliminar = popEditar.findViewById(R.id.btnEliminar);
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