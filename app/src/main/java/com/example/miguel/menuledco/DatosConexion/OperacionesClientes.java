package com.example.miguel.menuledco.DatosConexion;

import com.example.miguel.menuledco.Model.Cliente;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by Raul on 03/05/2018.
 */

public class OperacionesClientes {
    Realm realm;

    public OperacionesClientes() {
        realm = Realm.getDefaultInstance();

    }

    public boolean insertarcliente(Cliente cli) {
        realm.beginTransaction();
        try {
            realm.copyToRealm(cli);
        } catch (Exception e) {
            realm.commitTransaction();
            return false;
        }
        realm.commitTransaction();
        return true;
    }

    public boolean eliminarCliente(String id) {
        RealmQuery<Cliente> query = realm.where(Cliente.class).equalTo("id_cliente", id);
        RealmResults<Cliente> results = query.findAll();
        if (results.size() > 0) {
            realm.beginTransaction();
            results.deleteAllFromRealm();
            realm.commitTransaction();
        } else {
            return false;
        }
        return true;
    }

    public void modificarBanderas(Cliente c, boolean nuevo, boolean editado, boolean eliminado) {
        RealmQuery<Cliente> query = realm.where(Cliente.class).equalTo("id_cliente", c.getId_cliente());
        RealmResults<Cliente> results = query.findAll();
        if (results.size() > 0) {
            realm.beginTransaction();
            results.get(0).setEditado(editado);
            results.get(0).setEliminado(eliminado);
            results.get(0).setNuevo(nuevo);
            realm.commitTransaction();
        }
    }

    public boolean eliminarTODOSClientes(int confirm) {
        if (confirm == 1) {
            RealmQuery<Cliente> query = realm.where(Cliente.class);
            RealmResults<Cliente> results = query.findAll();
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

    public Cliente bucarCliente(String nombre) {
        RealmQuery<Cliente> query = realm.where(Cliente.class).equalTo("nombre_cliente", nombre);
        RealmResults<Cliente> results = query.findAll();
        if (results.size() > 0)
            return results.get(0);
        else return null;
    }

    public ArrayList<Cliente> listClientes() {
        RealmQuery<Cliente> query = realm.where(Cliente.class).equalTo("eliminado",false);
        RealmResults<Cliente> results = query.findAll();
        results = results.sort("id_cliente", Sort.ASCENDING);
        return new ArrayList<>(results);
    }

    public ArrayList<Cliente> listALLClientes() {
        RealmQuery<Cliente> query = realm.where(Cliente.class);
        RealmResults<Cliente> results = query.findAll();
        results = results.sort("id_cliente", Sort.ASCENDING);
        return new ArrayList<>(results);
    }

    public boolean modificarcliente(Cliente c) {
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
