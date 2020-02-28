package com.example.miguel.menuledco.Model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Raul on 01/05/2018.
 */

public class Venta extends RealmObject {
    @PrimaryKey
    private String id_venta;
    private String id_cliente, total_venta, fecha;

    public Venta() {
    }

    @Override
    public String toString() {
        return id_venta + ".-" + id_cliente + ".-" + total_venta + ".-" + fecha;
    }

    public String getId_venta() {
        return id_venta;
    }

    public void setId_venta(String id_venta) {
        this.id_venta = id_venta;
    }

    public String getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(String id_cliente) {
        this.id_cliente = id_cliente;
    }

    public String getTotal_venta() {
        return total_venta;
    }

    public void setTotal_venta(String total_venta) {
        this.total_venta = total_venta;
    }

    public String getFecha() {
        return fecha;
    }


    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
