package com.example.alcar_firebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

public class frmCliente extends AppCompatActivity {

    EditText nombre, documento, telefono, email, fecha_nacimiento;
    public FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_cliente);

        nombre = findViewById(R.id.txtplaca);
        documento = findViewById(R.id.txtMarca);
        telefono = findViewById(R.id.txtModelo);
        email = findViewById(R.id.txtAnio);
        fecha_nacimiento = findViewById(R.id.txtEstado);

        db = FirebaseFirestore.getInstance();
    }

    public void clicInsertar(View v){

        String nomb = nombre.getText().toString();
        String doc = documento.getText().toString();
        String tel=  telefono.getText().toString();
        String mail=  email.getText().toString();
        String naci=  fecha_nacimiento.getText().toString();
        String estado = "sin auto";
        Cliente AddCliente = new Cliente(doc, nomb,doc, tel, mail, naci);

        db.collection("Clientes")
                .document(doc)
                .set(AddCliente)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(frmCliente.this, "REGISTRO INSERTADO CON EXITO", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(frmCliente.this, "HUBO UN ERROR EN EL PROCESO", Toast.LENGTH_SHORT).show();
            }
        });

        super.onBackPressed();

    }

    public void onclickLimpiar (View view){
        onclickLimpiar();
    }

    private void onclickLimpiar() {
        nombre.setText("");
        documento.setText("");
        telefono.setText("");
        email.setText("");
        fecha_nacimiento.setText("");
    }
}