package com.example.alcar_firebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class frmAlquilerFinal extends AppCompatActivity {
    FirebaseFirestore db;
    public ArrayList<Cliente> listaDatos;
    public ArrayList<Auto> listaDatosAuto;
    public ListView listaXml;
    public String estado;
    public String tipo;

    public String resumen;
    public float pDia;
    public float pComision;
    public float pTotal;

    public Dialog popFinal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_alquiler_final);

        listaDatos = new ArrayList<Cliente>();
        listaDatosAuto = new ArrayList<Auto>();
        db = FirebaseFirestore.getInstance();
        popFinal = new Dialog(this);
        popFinal.setContentView(R.layout.mensaje_final);

        TextView id = findViewById(R.id.txtIdAlquilerAlquilarfinal);
        TextView modelo = findViewById(R.id.txtViewModeloAlquilerfinal);
        TextView marca = findViewById(R.id.txtViewMarcaAlquilerfinal);
        TextView anio = findViewById(R.id.txtViewAnioAlquilerfinal);
        TextView estado = findViewById(R.id.txtViewDispoAlquilerfinal);
        TextView tipo = findViewById(R.id.txtViewTipoAlquilerfinal);
        TextView placa = findViewById(R.id.txtViewPlacaAlquilerfinal);


        modelo.setText(getIntent().getStringExtra("modelo"));
        marca.setText("Marca:   "+getIntent().getStringExtra("marca"));
        anio.setText("Año:  "+getIntent().getStringExtra("anio"));
        estado.setText("Estado:  "+getIntent().getStringExtra("dispo"));
        tipo.setText("Tipo:   "+getIntent().getStringExtra("tipo"));
        placa.setText("Placa:   "+getIntent().getStringExtra("placa"));

        TextView idCliente = findViewById(R.id.txtIdAlquilerAlquilarfinalCliente);
        TextView nombre = findViewById(R.id.txtViewModeloAlquilerfinalCliente);
        TextView documento = findViewById(R.id.txtViewAnioAlquilerfinalCliente);
        TextView email = findViewById(R.id.txtViewDispoAlquilerfinalClientTelefono);
        TextView telefono = findViewById(R.id.txtViewPlacaAlquilerfinalCliente);
        TextView fecha_nacimiento = findViewById(R.id.txtViewDispoAlquilerfinalClienteFecha);

        nombre.setText(getIntent().getStringExtra("nombre"));
        documento.setText("DUI:     "+getIntent().getStringExtra("documento"));
        email.setText("Email:   "+getIntent().getStringExtra("email"));
        telefono.setText("Tel:  "+getIntent().getStringExtra("telefono"));
        fecha_nacimiento.setText("Edad: "+getIntent().getStringExtra("fecha_nacimiento"));
        idCliente.setText(getIntent().getStringExtra("idUsuario"));
        String tipoCarro = getIntent().getStringExtra("tipo");

        resumen = "Costo por servicio de alquiler es de $50 diarios, ";
        pDia = 50f;
        switch (tipoCarro){
            case "Coche":
                resumen += "Se a seleccionado un automovil de tipo '" + tipoCarro +"' Este tiene un costo adicional de $15 por dia";
                pComision = 15f;
                break;
            case  "Microbus":
                resumen += "Se a seleccionado un automovil de tipo '" + tipoCarro +"' Este tiene un costo adicional de $20 por alquiler";
                pComision = 20f;
                break;
            case  "Carga":
                resumen += "Se a seleccionado un automovil de tipo '" + tipoCarro +"' Este tiene un costo adicional de $20 por dia";
                pComision = 20f;
                break;
            case  "Camion":
                resumen += "Se a seleccionado un automovil de tipo '" + tipoCarro +"' Este tiene un costo adicional de $25 por dia";
                pComision = 25f;
                break;

        }


        resumen += "\n\n\nSelecciones fecha de alquiler";
        TextView resumenAlquiler = findViewById(R.id.textViewResumen);
        resumenAlquiler.setText(resumen);


        Button btnSiguiente = findViewById(R.id.btnSiguiente);
        btnSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView txtIDCliente = (TextView) popFinal.findViewById(R.id.txtCodigoCliente);
                TextView txtNombreCliente = (TextView) popFinal.findViewById(R.id.txtNombreCliente);
                TextView txtAutomovil = (TextView) popFinal.findViewById(R.id.txtAuto);
                TextView txtPlaca = (TextView) popFinal.findViewById(R.id.txtPlaca);
                TextView txtFechaInicio = (TextView) popFinal.findViewById(R.id.txtFechaInicio);
                TextView txtFechaFin = (TextView) popFinal.findViewById(R.id.txtFechaEntrega);
                TextView txtDiasT = (TextView) popFinal.findViewById(R.id.txtNunDias);
                TextView txtTotalP = (TextView) popFinal.findViewById(R.id.txtPago);

                EditText txtDiaI = (EditText) findViewById(R.id.editTextDiaAlquiler);
                EditText txtMesI = (EditText) findViewById(R.id.editTextMesAlquiler);
                EditText txtAnioI = (EditText) findViewById(R.id.editTextAnioAlquile);
                EditText txtDiaF = (EditText) findViewById(R.id.editTextDiaEntrega);
                EditText txtMesF = (EditText) findViewById(R.id.editTextMesEntrega);
                EditText txtAnioF = (EditText) findViewById(R.id.editTextAnioEntrega);

                String dateBeforeString = txtAnioI.getText() + "-" + txtMesI.getText() +"-"+ txtDiaI.getText();
                String dateAfterString = txtAnioF.getText() + "-" + txtMesF.getText() +"-"+ txtDiaF.getText();

                LocalDate dateBefore = LocalDate.parse(dateBeforeString);
                LocalDate dateAfter = LocalDate.parse(dateAfterString);

                long noOfDaysBetween = ChronoUnit.DAYS.between(dateBefore, dateAfter);

                txtIDCliente.setText(String.valueOf(getIntent().getStringExtra("idUsuario")));
                txtNombreCliente.setText(String.valueOf(getIntent().getStringExtra("nombre")));

                txtAutomovil.setText(String.valueOf(getIntent().getStringExtra("modelo")));
                txtPlaca.setText(String.valueOf(getIntent().getStringExtra("placa")));
                txtFechaInicio.setText(dateBeforeString);
                txtFechaFin.setText(dateAfterString);
                txtDiasT.setText(String.valueOf(noOfDaysBetween));

                switch (tipoCarro){
                    case "Coche":
                        pTotal = (pDia * noOfDaysBetween) + (15*noOfDaysBetween);
                        break;
                    case  "Microbus":
                        pTotal = (pDia * noOfDaysBetween) + 20;

                        break;
                    case  "Carga":
                        pTotal = (pDia * noOfDaysBetween) + (20*noOfDaysBetween);

                        break;
                    case  "Camion":
                        pTotal = (pDia * noOfDaysBetween) + (25*noOfDaysBetween);

                        break;

                }
                txtTotalP.setText(String.valueOf(pTotal));

                Button btnGuardarAlquiler = (Button) popFinal.findViewById(R.id.btnFinalizar);
                btnGuardarAlquiler.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String idCliente = getIntent().getStringExtra("idUsuario");
                        String Cliente = getIntent().getStringExtra("nombre");
                        String idCarro=  getIntent().getStringExtra("placa");
                        String carro=  getIntent().getStringExtra("modelo");
                        String fechaI=  dateBeforeString;
                        String fechaF = dateAfterString;
                        String totalApgar = String.valueOf(pTotal);
                        Alquiler AddAuto = new Alquiler(idCliente,Cliente, carro,idCarro, fechaI, fechaF, totalApgar);
                        db.collection("Alquiler")
                                .document()
                                .set(AddAuto)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(frmAlquilerFinal.this, "REGISTRO INGRESADO CON EXITO", Toast.LENGTH_SHORT).show();
                                        db.collection("Autos")
                                                .document(idCarro)
                                                .update("estado","Ocupado")
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        Toast.makeText(frmAlquilerFinal.this, "REGISTRO ACTUALIZADO CON EXITO", Toast.LENGTH_SHORT).show();

                                                    }
                                                }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(frmAlquilerFinal.this, "HUBO UN ERROR EN EL PROCESO", Toast.LENGTH_SHORT).show();
                                            }
                                        });

                                        popFinal.dismiss();
                                        Intent intent = new Intent(frmAlquilerFinal.this, MainActivity.class); intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); intent.putExtra("EXIT", true); startActivity(intent);


                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(frmAlquilerFinal.this, "HUBO UN ERROR EN EL PROCESO", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                });
                popFinal.show();
            }
        });



    }
}