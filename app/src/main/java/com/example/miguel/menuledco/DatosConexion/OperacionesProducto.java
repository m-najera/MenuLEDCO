package com.example.miguel.menuledco.DatosConexion;

import com.example.miguel.menuledco.Model.Producto;

import java.util.ArrayList;

import io.realm.Case;
import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by Raul on 03/05/2018.
 */

public class OperacionesProducto {
    Realm realm;

    public OperacionesProducto() {
        realm = Realm.getDefaultInstance();

    }


    public boolean eliminarTODOSProductos(int confirm) {
        if (confirm == 1) {
            RealmQuery<Producto> query = realm.where(Producto.class);
            RealmResults<Producto> results = query.findAll();
            if (results.size() > 0) {
                realm.beginTransaction();
                results.deleteAllFromRealm();
                realm.commitTransaction();
            } else {
                return false;
            }
            return true;
        } else {
            return false;
        }
    }

    public boolean insertarProducto(Producto pro) {
        realm.beginTransaction();
        try {
            realm.copyToRealm(pro);
        } catch (Exception e) {
            realm.commitTransaction();
            return false;
        }
        realm.commitTransaction();
        return true;
    }

    public boolean eliminarProducto(String id) {
        RealmQuery<Producto> query = realm.where(Producto.class).equalTo("id_producto", id);
        RealmResults<Producto> results = query.findAll();
        if (results.size() > 0) {
            realm.beginTransaction();
            results.deleteAllFromRealm();
            realm.commitTransaction();
        } else {
            return false;
        }
        return true;
    }

    public Producto buscarProducto(int id) {
        RealmQuery<Producto> query = realm.where(Producto.class)
                .equalTo("id_producto", id);
        return   realm.copyFromRealm(query.findFirst());
    }

    public boolean actualizarInventarioProducto(int id, int venta) {
        RealmQuery<Producto> query = realm.where(Producto.class)
                .equalTo("id_producto", id);
        Producto result = query.findFirst();
        if (result == null) return false;
        realm.beginTransaction();
        result.inventario -= venta;
        realm.commitTransaction();
        return true;
    }

    public ArrayList<Producto> buscarProductoNombre(String s) {
        RealmQuery<Producto> query = realm.where(Producto.class)
                .beginGroup()
                .contains("nombre_producto", s, Case.INSENSITIVE)
                .or()
                .contains("descripcion_corta", s, Case.INSENSITIVE)
                .endGroup();
        RealmResults<Producto> results = query.findAll();
        if (results.size() > 0) return new ArrayList<>(results);
        return new ArrayList<>();
    }

    public ArrayList<Producto> listProducto() {
        RealmQuery<Producto> query = realm.where(Producto.class);
        RealmResults<Producto> results = query.findAll();
        results = results.sort("id_producto", Sort.ASCENDING);
        return new ArrayList<>(results);
    }

    public ArrayList<Producto> listProducto(int categoria) {
        RealmQuery<Producto> query = realm.where(Producto.class).equalTo("categoria", categoria);
        RealmResults<Producto> results = query.findAll();
        results = results.sort("id_producto", Sort.ASCENDING);
        return new ArrayList<>(results);
    }

    public boolean modificarProducto(Producto p) {
        realm.beginTransaction();
        try {
            realm.copyToRealmOrUpdate(p);
        } catch (Exception e) {
            realm.commitTransaction();
            return false;
        }
        realm.commitTransaction();
        return true;
    }
}
