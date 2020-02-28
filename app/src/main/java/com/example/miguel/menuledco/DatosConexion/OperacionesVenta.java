package com.example.miguel.menuledco.DatosConexion;

import com.example.miguel.menuledco.Model.Venta;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by Raul on 03/05/2018.
 */

public class OperacionesVenta {
    Realm realm;

    public OperacionesVenta() {
        realm=Realm.getDefaultInstance();

    }

    public boolean eliminarTODOSVentas(int confirm) {
        if (confirm == 1) {
            RealmQuery<Venta> query = realm.where(Venta.class);
            RealmResults<Venta> results = query.findAll();
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

    public boolean insertar_venta(Venta ven){
        realm.beginTransaction();
        try {
            realm.copyToRealm(ven);
        }catch (Exception e){
            realm.commitTransaction();
            return false;
        }
        realm.commitTransaction();
        return true;
    }

    public boolean eliminar_venta(String id){
        RealmQuery<Venta> query;
        query = realm.where(Venta.class).equalTo("id_venta", id);
        RealmResults<Venta> results= query.findAll();
        if (results.size()>0){
            realm.beginTransaction();
            results.deleteAllFromRealm();
            realm.commitTransaction();
        }else {
            return false;
        }
        return true;
    }

    public Venta bucar_venta (String id){
        RealmQuery<Venta> query = realm.where(Venta.class).equalTo("id_venta",id);
        RealmResults<Venta> results = query.findAll();
        if (results.size()>0)
            return results.get(0);
        else return null;
    }
    public ArrayList<Venta> list_venta(){
        RealmQuery<Venta> query = realm.where(Venta.class);
        RealmResults<Venta> results = query.findAll();
        results = results.sort("id_venta", Sort.ASCENDING);
        return new ArrayList<Venta>(results);
    }

    public boolean modificar_venta(Venta v){
        realm.beginTransaction();
        try {
            realm.copyToRealmOrUpdate(v);
        }catch (Exception e){
            realm.commitTransaction();
            return false;
        }
        realm.commitTransaction();
        return true;
    }
}
