package com.example.miguel.menuledco.DatosConexion;

import com.example.miguel.menuledco.Model.ProductoVendido;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by Raul on 03/05/2018.
 */

public class OperacionesProductoVendido {
    Realm realm;

    public OperacionesProductoVendido() {
        realm = Realm.getDefaultInstance();

    }

    public boolean insertarProductoVendido(ProductoVendido proV) {
        realm.beginTransaction();
        try {
            realm.copyToRealm(proV);
        } catch (Exception e) {
            realm.commitTransaction();
            return false;
        }
        realm.commitTransaction();
        return true;
    }

    public boolean eliminarTODOSProdVend(int confirm) {
        if (confirm == 1) {
            RealmQuery<ProductoVendido> query = realm.where(ProductoVendido.class);
            RealmResults<ProductoVendido> results = query.findAll();
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

    public boolean eliminarProductoVendido(String id) {
        RealmQuery<ProductoVendido> query = realm.where(ProductoVendido.class).equalTo("id_producto", id);
        RealmResults<ProductoVendido> results = query.findAll();
        if (results.size() > 0) {
            realm.beginTransaction();
            results.deleteAllFromRealm();
            realm.commitTransaction();
        } else {
            return false;
        }
        return true;
    }

    public ProductoVendido bucarProductoVendido(String id) {
        RealmQuery<ProductoVendido> query = realm.where(ProductoVendido.class).equalTo("id_producto", id);
        RealmResults<ProductoVendido> results = query.findAll();
        if (results.size() > 0)
            return results.get(0);
        else return null;
    }


    public ArrayList<ProductoVendido> listProductoVendido() {
        RealmQuery<ProductoVendido> query = realm.where(ProductoVendido.class);
        RealmResults<ProductoVendido> results = query.findAll();
        results = results.sort("id_venta", Sort.ASCENDING);
        return new ArrayList<ProductoVendido>(results);
    }

    public boolean modificarProductoVendido(ProductoVendido c) {
        realm.beginTransaction();
        try {
            realm.copyToRealmOrUpdate(c);
        } catch (Exception e) {
            realm.commitTransaction();
            return false;
        }
        realm.commitTransaction();
        return true;
    }
}
