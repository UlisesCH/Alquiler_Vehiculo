package com.example.alcar_firebase;

import java.util.Map;

public class Cliente {

    public String idUsuario;
    public String nombre;
    public String documento;
    public String telefono;
    public String email;
    public String fecha_nacimiento;
    public String estado;

    public Cliente(String idUsuario, String nombre, String documento, String telefono, String email, String fecha_nacimiento) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.documento = documento;
        this.telefono = telefono;
        this.email = email;
        this.fecha_nacimiento = fecha_nacimiento;
    }

    public Cliente() {

    }
}
