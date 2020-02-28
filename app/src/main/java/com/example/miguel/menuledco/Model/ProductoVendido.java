package com.example.miguel.menuledco.Model;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Raul on 01/05/2018.
 */

public class ProductoVendido extends RealmObject {

    public static final List<ProductoVendido> ITEMS = new ArrayList<>();
    public int cantidad, id_producto;
    public int inventario_temporal;
    public String nombre, descripcion ;
    public float precio_unitario, subtotal;
    @PrimaryKey
    String id_venta;

    public ProductoVendido() {
    }

    public static List<ProductoVendido> getITEMS() {
        return ITEMS;
    }

    public String getId_venta() {
        return id_venta;
    }

    public void setId_venta(String id_venta) {
        this.id_venta = id_venta;
    }

    public int getId_producto() {
        return id_producto;
    }

    public void setId_producto(int id_producto) {
        this.id_producto = id_producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public float getPrecio_unitario() {
        return precio_unitario;
    }

    public void setPrecio_unitario(float precio_unitario) {
        this.precio_unitario = precio_unitario;
    }

    public float getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(float subtotal) {
        this.subtotal = subtotal;
    }
}
