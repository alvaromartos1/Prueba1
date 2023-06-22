package com.example.backend;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)

public class Producto {

    @JsonProperty("nombre")
    public String  nombre;
    @JsonProperty("categoria")
    public String categoria;
    @JsonProperty("precio")
    public double precio;
    @JsonProperty("EAN13")
    public String EAN13;

    public Producto(String nombre, String categoria, double precio, String EAN13) {
        this.nombre = nombre;
        this.categoria = categoria;
        this.precio = precio;
        this.EAN13 = EAN13;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getEAN13() {
        return EAN13;
    }

    public void setEAN13(String EAN13) {
        this.EAN13 = EAN13;
    }

    public Producto(){

    }


    @Override
    public String toString() {
        return "Producto{" +
                "nombre='" + nombre + '\'' +
                ", categoria='" + categoria + '\'' +
                ", precio=" + precio +
                ", EAN13='" + EAN13 + '\'' +
                '}';
    }
}
