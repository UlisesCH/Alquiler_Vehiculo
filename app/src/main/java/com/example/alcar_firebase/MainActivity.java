package com.example.alcar_firebase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnClientes = findViewById(R.id.btnClientes);
        btnClientes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ListadoCliente.class);
                startActivity(intent);
            }
        });

        Button btnCars = findViewById(R.id.btnCars);
        btnCars.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ListaAutos.class);
                startActivity(intent);
            }
        });


        Button btnAlquiler = findViewById(R.id.btnAlquiler);
        btnAlquiler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, frmAlquiler.class);
                startActivity(intent);
            }
        });

        Button btnAlquileres = findViewById(R.id.btnList);
        btnAlquileres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ListaAlquileres.class);
                startActivity(intent);
            }
        });

    }
}