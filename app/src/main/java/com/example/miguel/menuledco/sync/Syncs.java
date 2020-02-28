package com.example.miguel.menuledco.sync;

import android.content.Context;
import android.widget.Toast;

import com.example.miguel.menuledco.DatosConexion.DatosConexion;
import com.example.miguel.menuledco.DatosConexion.OperacionesClientes;
import com.example.miguel.menuledco.DatosConexion.OperacionesProducto;
import com.example.miguel.menuledco.DatosConexion.OperacionesProductoVendido;
import com.example.miguel.menuledco.DatosConexion.OperacionesVenta;
import com.example.miguel.menuledco.Model.Cliente;
import com.example.miguel.menuledco.Model.Producto;
import com.example.miguel.menuledco.Model.ProductoVendido;
import com.example.miguel.menuledco.Model.Venta;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.BlackholeHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class Syncs {
    private DatosConexion dc = new DatosConexion();

    public boolean eliminarProductosBD() {
        final OperacionesProducto op = new OperacionesProducto();
        return op.eliminarTODOSProductos(1);
    }

    public boolean eliminarVentasBD() {
        OperacionesVenta ov = new OperacionesVenta();
        return ov.eliminarTODOSVentas(1);
    }

    public boolean eliminarProdVenBD() {
        OperacionesProductoVendido ov = new OperacionesProductoVendido();
        return ov.eliminarTODOSProdVend(1);
    }


    public boolean eliminarDBClientes(final Context context) {
        OperacionesClientes oc = new OperacionesClientes();
        return oc.eliminarTODOSClientes(1);
    }

    public void enviarDBClientes(final Context context) {
        OperacionesClientes operacionesClientes = new OperacionesClientes();
        AsyncHttpClient cliente = new AsyncHttpClient();
        ArrayList<Cliente> list = new ArrayList<>(operacionesClientes.listALLClientes());
        for (final Cliente c : list) {
            if (c.isNuevo()) {
                RequestParams rp = new RequestParams();
                rp.add("id_cliente", String.valueOf(c.getId_cliente()));
                rp.add("nombre_cliente", c.getNombre_cliente());
                rp.add("tipo_cliente", c.getTipo_cliente());
                rp.add("email_cliente", c.getEmail_cliente());
                rp.add("telefono_cliente", c.getTelefono_cliente());
                rp.add("modificado", c.getModificado());
                cliente.post(dc.urlInsertaCliente, rp, new BlackholeHttpResponseHandler());
            } else if (c.isEliminado()) {
                RequestParams rp = new RequestParams();
                rp.add("id_cliente", String.valueOf(c.getId_cliente()));
                cliente.post(dc.urlEliminarCliente, rp, new BlackholeHttpResponseHandler());
            } else if (c.isEditado()) {
                RequestParams rp = new RequestParams();
                rp.add("id_cliente", String.valueOf(c.getId_cliente()));
                rp.add("nombre_cliente", c.getNombre_cliente());
                rp.add("tipo_cliente", c.getTipo_cliente());
                rp.add("email_cliente", c.getEmail_cliente());
                rp.add("telefono_cliente", c.getTelefono_cliente());
                rp.add("modificado", c.getModificado());
                cliente.post(dc.urlActualizaCliente, rp, new BlackholeHttpResponseHandler());
            }
        }
    }


    public void enviarClientesNuevos(final Context context) {
        final OperacionesClientes operacionesClientes = new OperacionesClientes();
        AsyncHttpClient cliente = new AsyncHttpClient();
        ArrayList<Cliente> list = new ArrayList<>(operacionesClientes.listALLClientes());
        RequestParams rp = new RequestParams();
        int cont = 0;
        for (final Cliente c : list) {
            if (c.isNuevo()) {
                rp.add("id_cliente"+cont, String.valueOf(c.getId_cliente()));
                rp.add("nombre_cliente"+cont, c.getNombre_cliente());
                rp.add("tipo_cliente"+cont, c.getTipo_cliente());
                rp.add("email_cliente"+cont, c.getEmail_cliente());
                rp.add("telefono_cliente"+cont, c.getTelefono_cliente());
                rp.add("modificado"+cont, c.getModificado());
                cont++;
            }
        }
        rp.add("cantidad",String.valueOf(cont));
        System.out.println(rp.toString());
        final int finalCont = cont;
        cliente.post(dc.urlInsertaClientesNuevos, rp, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (new String(responseBody).equals("OK")) {
                    //Toast.makeText(context, "clientes nuevos insertados " + finalCont, Toast.LENGTH_SHORT).show();
                    enviarClientesEliminados(context);
                } else {
                    //Toast.makeText(context, "Errore", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
            }
        });
    }

    public void enviarClientesModificados(final Context context) {
        final OperacionesClientes operacionesClientes = new OperacionesClientes();
        AsyncHttpClient cliente = new AsyncHttpClient();
        ArrayList<Cliente> list = new ArrayList<>(operacionesClientes.listALLClientes());
        RequestParams rp = new RequestParams();
        int cont = 0;
        for (final Cliente c : list) {
            if (c.isEditado()) {
                rp.add("id_cliente"+cont, String.valueOf(c.getId_cliente()));
                rp.add("nombre_cliente"+cont, c.getNombre_cliente());
                rp.add("tipo_cliente"+cont, c.getTipo_cliente());
                rp.add("email_cliente"+cont, c.getEmail_cliente());
                rp.add("telefono_cliente"+cont, c.getTelefono_cliente());
                rp.add("modificado"+cont, c.getModificado());
                cont++;
            }
        }
        rp.add("cantidad",String.valueOf(cont));
        System.out.println(rp.toString());
        final int finalCont = cont;
        cliente.post(dc.urlActualizaClientes, rp, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
               /* if (new String(responseBody).equals("OK")) {
                    Toast.makeText(context, "clientes actualizados " + finalCont, Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(context, "Errore", Toast.LENGTH_SHORT).show();
                }*/
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
            }
        });
    }

    public void enviarClientesEliminados(final Context context) {
        final OperacionesClientes operacionesClientes = new OperacionesClientes();
        AsyncHttpClient cliente = new AsyncHttpClient();
        ArrayList<Cliente> list = new ArrayList<>(operacionesClientes.listALLClientes());
        RequestParams rp = new RequestParams();
        int cont = 0;
        for (final Cliente c : list) {
            if (c.isEliminado()) {
                rp.add("id_cliente"+cont, String.valueOf(c.getId_cliente()));
                cont++;
            }
        }
        rp.add("cantidad",String.valueOf(cont));
        System.out.println(rp.toString());
        final int finalCont = cont;
        cliente.post(dc.urlActualizaClientes, rp, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
              /*  if (new String(responseBody).equals("OK")) {
                    Toast.makeText(context, "clientes actualizados " + finalCont, Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(context, "Errore", Toast.LENGTH_SHORT).show();
                }*/
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
            }
        });
    }



    public void enviarDBVentas(final Context context) {
        AsyncHttpClient cliente = new AsyncHttpClient();
        OperacionesVenta ov = new OperacionesVenta();
        ArrayList<Venta> list = new ArrayList<>(ov.list_venta());
        for (final Venta v : list) {
            RequestParams rp = new RequestParams();
            rp.add("id_venta", v.getId_venta());
            rp.add("id_cliente", v.getId_cliente());
            rp.add("total", v.getTotal_venta());
            cliente.post(dc.urlInsertaVenta, rp, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    //Toast.makeText(context, "Insertado " + responseBody, Toast.LENGTH_SHORT).show();
                    //Toast.makeText(context, "Error\n\r" + new String(responseBody), Toast.LENGTH_SHORT).show();

                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    //Toast.makeText(context, "Error\n\r" + new String(responseBody), Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    public void enviarDBProductosVendidos(final Context context) {
        AsyncHttpClient cliente = new AsyncHttpClient();
        Toast.makeText(context, "algo", Toast.LENGTH_SHORT).show();
        OperacionesProductoVendido ov = new OperacionesProductoVendido();
        ArrayList<ProductoVendido> list = new ArrayList<>(ov.listProductoVendido());
        for (final ProductoVendido v : list) {
            RequestParams rp = new RequestParams();
            rp.add("id_venta", String.valueOf(v.getId_venta()));
            rp.add("id_producto", String.valueOf(v.getId_producto()));
            rp.add("cantidad", String.valueOf(v.getCantidad()));
            rp.add("precio_unitario", String.valueOf(v.getPrecio_unitario()));
            rp.add("subtotal", String.valueOf(v.getSubtotal()));
            System.out.println(rp.toString());
            cliente.post(dc.urlInsertaProductoVendido, rp, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    Toast.makeText(context, "Insertado " + new String(responseBody), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    Toast.makeText(context, "Error " + v.getId_venta(), Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    public void cargarClientesBD(final Context context) {
        final OperacionesClientes operacionesClientes = new OperacionesClientes();
        AsyncHttpClient cliente = new AsyncHttpClient();
        cliente.get(dc.urlConsultacliente, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String resp = new String(responseBody);
                    JSONArray jArray = new JSONArray(resp);
                    for (int i = 0; i < jArray.length(); i++) {
                        Cliente cli = new Cliente();
                        cli.setId_cliente(jArray.getJSONObject(i).getString("id_cliente"));
                        cli.setNombre_cliente(jArray.getJSONObject(i).getString("nombre_cliente"));
                        cli.setTipo_cliente(jArray.getJSONObject(i).getString("tipo"));
                        cli.setEmail_cliente(jArray.getJSONObject(i).getString("email_cliente"));
                        cli.setTelefono_cliente(jArray.getJSONObject(i).getString("telefono_cliente"));
                        cli.setModificado(jArray.getJSONObject(i).getString("modificado"));
                        //operaciones
                        operacionesClientes.modificarcliente(cli);
                    }
                } catch (Exception e) {
                    Toast.makeText(context, "Error de al consultar" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(context, "Error de conexion", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void cargarProductosBD(final Context context) {
        final OperacionesProducto op;
        op = new OperacionesProducto();
        AsyncHttpClient producto = new AsyncHttpClient();
        producto.get(dc.urlConsultaproducto, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String resp = new String(responseBody);
                    JSONArray jArray = new JSONArray(resp);
                    for (int i = 0; i < jArray.length(); i++) {
                        Producto pro = new Producto();
                        pro.setId_producto(Integer.parseInt(jArray.getJSONObject(i).getString("id_producto")));
                        pro.setCategoria(Integer.parseInt(jArray.getJSONObject(i).getString("categoria")));
                        pro.setNombre_producto(jArray.getJSONObject(i).getString("nombre_producto"));
                        pro.setInventario(Integer.parseInt(jArray.getJSONObject(i).getString("inventario")));
                        pro.setPrecio_individual(Float.parseFloat(jArray.getJSONObject(i).getString("precio_individual")));
                        pro.setDescripcion_corta(jArray.getJSONObject(i).getString("descripcion_corta"));
                        pro.setModificado(jArray.getJSONObject(i).getString("modificado"));
                        op.modificarProducto(pro);
                    }
                    Toast.makeText(context, "OK", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(context, "ERROR DE DATOS", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(context, "ERROR DE CONEXION", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void test(final Context context) {
        AsyncHttpClient cliente = new AsyncHttpClient();
        final int[] av = {1, 0};
        cliente.get(dc.urlConsultacliente, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String resp = new String(responseBody);
                    JSONArray jArray = new JSONArray(resp);
                    for (int i = 0; i < jArray.length(); i++) {
                        Cliente cli = new Cliente();
                        cli.setId_cliente(jArray.getJSONObject(i).getString("id_cliente"));
                        cli.setNombre_cliente(jArray.getJSONObject(i).getString("nombre_cliente"));

                    }
                } catch (Exception e) {
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
            }
        });
    }
}
