package com.example.alcar_firebase;

public class Alquiler {
    public String idCliente;
    public String nombre;
    public String modelo;
    public String placa;
    public String fechaInicio;
    public String FechaEntrega;
    public String TotalPago;

    public Alquiler(String idCliente, String nombre, String modelo, String placa, String fechaInicio, String fechaEntrega,String totalPago) {
        this.idCliente = idCliente;
        this.nombre = nombre;
        this.modelo = modelo;
        this.placa = placa;
        this.fechaInicio = fechaInicio;
        FechaEntrega = fechaEntrega;
        TotalPago = totalPago;
    }

    public Alquiler(){

    }
}
