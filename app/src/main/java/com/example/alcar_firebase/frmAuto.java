package com.example.alcar_firebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class frmAuto extends AppCompatActivity  {
    EditText placa, modelo, marca, anio, idAuto;
    public FirebaseFirestore db;

    public String estado;
    public String tipo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_frm_auto);
        placa = findViewById(R.id.txtplaca);
        modelo = findViewById(R.id.txtModelo);
        marca = findViewById(R.id.txtMarca);
        anio = findViewById(R.id.txtAnio);

        db = FirebaseFirestore.getInstance();


        Spinner spinner = (Spinner) findViewById(R.id.spinner);
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


        Spinner tipoSpiner = (Spinner) findViewById(R.id.tipoSpiner);
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


    }

    public void clicInsertar(View v){

        String placaAuto = placa.getText().toString();
        String modeloAuto = modelo.getText().toString();
        String marcaAuto=  marca.getText().toString();
        String anioAuto=  anio.getText().toString();
        String estadoAuto=  estado;
        String tipoAuto=  tipo;

        Auto AddAuto = new Auto(placaAuto,placaAuto, marcaAuto,modeloAuto, anioAuto, estadoAuto, tipoAuto);

        db.collection("Autos")
                .document(placaAuto)
                .set(AddAuto)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(frmAuto.this, "REGISTRO INSERTADO CON EXITO", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(frmAuto.this, "HUBO UN ERROR EN EL PROCESO", Toast.LENGTH_SHORT).show();
            }
        });

        super.onBackPressed();

    }

    public void onclickLimpiar (View view){
        onclickLimpiar();
    }
    private void onclickLimpiar() {
        placa.setText("");
        modelo.setText("");
        marca.setText("");
        anio.setText("");
    }


}