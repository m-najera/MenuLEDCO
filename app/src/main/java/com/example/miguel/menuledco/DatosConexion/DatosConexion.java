package com.example.miguel.menuledco.DatosConexion;

/**
 * Created by Raul on 01/05/2018.
 */

public class DatosConexion {
    public String ipLocal = "http://localhost";
    public String ip = "http://localhost";
    public String urlConsultacliente=ip+"/ledco/get_cliente.php";
    public String urlInsertaCliente=ip+"/ledco/insert_cliente.php";
    public String urlInsertaClientesNuevos=ip+"/ledco/insert_clientes_nuevos.php";
    public String urlActualizaCliente=ip+"/ledco/update_cliente.php";
    public String urlActualizaClientes=ip+"/ledco/update_clientes.php";
    public String urlEliminarCliente=ip+"/ledco/delete_cliente.php";
    public String urlEliminarClientes=ip+"/ledco/delete_clientes.php";
    public String urlInsertaVenta=ip+"/ledco/insert_venta.php";
    public String urlInsertaProductoVendido=ip+"/ledco/insert_productoV.php";
    public String urlConsultaproducto=ip+"/ledco/get_producto.php";
    public String urlConsultaventa=ip+"/ledco/get_venta.php";
    public String urlConsultaproductoVendido=ip+"/ledco/get_producto_vendido.php";
}
