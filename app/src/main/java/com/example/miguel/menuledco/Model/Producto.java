package com.example.miguel.menuledco.Model;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;


public class Producto extends RealmObject {
    @PrimaryKey
    public int id_producto;
    public int categoria;
    public String nombre_producto;
    public int inventario;
    public float precio_individual;
    public String descripcion_corta;
    public String modificado;


    public Producto(int id_producto, int categoria, String nombre_producto, int inventario, float precio_individual, String descripcion_corta, String modificado) {
        this.id_producto = id_producto;
        this.categoria = categoria;
        this.nombre_producto = nombre_producto;
        this.inventario = inventario;
        this.precio_individual = precio_individual;
        this.descripcion_corta = descripcion_corta;
        this.modificado = modificado;
    }

    public Producto(){}

    public int getId_producto() {
        return id_producto;
    }

    public void setId_producto(int id_producto) {
        this.id_producto = id_producto;
    }

    public int getCategoria() {
        return categoria;
    }

    public void setCategoria(int categoria) {
        this.categoria = categoria;
    }

    public String getNombre_producto() {
        return nombre_producto;
    }

    public void setNombre_producto(String nombre_producto) {
        this.nombre_producto = nombre_producto;
    }

    public int getInventario() {
        return inventario;
    }

    public void setInventario(int inventario) {
        this.inventario = inventario;
    }

    public float getPrecio_individual() {
        return precio_individual;
    }

    public void setPrecio_individual(float precio_individual) {
        this.precio_individual = precio_individual;
    }

    public String getDescripcion_corta() {
        return descripcion_corta;
    }

    public void setDescripcion_corta(String descripcion_corta) {
        this.descripcion_corta = descripcion_corta;
    }

    public String getModificado() {
        return modificado;
    }

    public void setModificado(String modificado) {
        this.modificado = modificado;
    }


}
