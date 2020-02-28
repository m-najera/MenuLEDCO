package com.example.miguel.menuledco;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.miguel.menuledco.DatosConexion.OperacionesClientes;
import com.example.miguel.menuledco.DatosConexion.OperacionesProducto;
import com.example.miguel.menuledco.DatosConexion.OperacionesProductoVendido;
import com.example.miguel.menuledco.DatosConexion.OperacionesVenta;
import com.example.miguel.menuledco.Model.Cliente;
import com.example.miguel.menuledco.Model.Producto;
import com.example.miguel.menuledco.Model.ProductoVendido;
import com.example.miguel.menuledco.Model.Venta;
import com.example.miguel.menuledco.sync.Syncs;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Stack;
import java.util.TimeZone;

import io.realm.Realm;

public class Menu extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        ProductosFragment.OnListFragmentInteractionListener,
        HomeFragment.OnFragmentInteractionListener,
        CarritoFragment.OnListFragmentInteractionListener,
        CarritoFragment.OnCarritoBarraInteraction,
        ClientesFragment.OnFragmentInteractionListener,
        BuscarFragment.OnBuscarProducto,
        BuscarFragment.OnListaBuscarFragmentInteractionListener {

    static public ArrayList<Producto> listaProductos;
    static public ArrayList<Producto> listaBuscar = new ArrayList<>();
    static public ArrayList<Cliente> listaClientes;
    static public ArrayList<ProductoVendido> carrito = new ArrayList<>();
    static public float totalCarrito = 0f;
    final int OPTION_HOME = 0;
    final int OPTION_ELECTRONICA = 1;
    final int OPTION_ROUTERS = 2;
    final int OPTION_KITS = 3;
    final int OPTION_ROBOTICA = 4;
    final int OPTION_IMPRESIONES3D = 5;
    final int OPTION_CARRO = 6;
    final int OPTION_CLIENTES = 7;
    final int OPTION_SYNC = 8;
    final int OPTION_DETALLES = 9;
    final int OPTION_BUSCAR = 10;
    String mTitle;
    boolean iniciado = false;
    long time__ = 0;
    Toast theToast;
    Stack<Integer> options__ = new Stack<>();
    FragmentManager fm = getFragmentManager();
    OperacionesProducto op;
    Toolbar toolbar;
    SimpleDateFormat dateFormat, idFormat;

    public static void calcularTotal() {
        int total = 0;
        for (ProductoVendido p : carrito) {
            total += p.cantidad * p.precio_unitario;
        }
        totalCarrito = total;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        idFormat = new SimpleDateFormat("yyMMddHHmmss", Locale.US);
        idFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

        if (!iniciado) {
            Realm.init(getApplicationContext());
            //Realm.deleteRealm(Realm.getDefaultConfiguration());
            iniciado = true;
            Fragment f = new HomeFragment();
            FragmentTransaction ft = fm.beginTransaction();
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            ft.replace(R.id.FRAGMENT, f);
            ft.commit();
            setTitle(0);
            options__.push(0);
        }
        op = new OperacionesProducto();
        theToast = Toast.makeText(this, " ", Toast.LENGTH_SHORT);
        OperacionesProductoVendido op = new OperacionesProductoVendido();
        OperacionesClientes oc = new OperacionesClientes();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (fm.getBackStackEntryCount() < 1) {
                if (System.currentTimeMillis() - time__ < 2200) {
                    theToast.cancel();
                    super.onBackPressed();
                } else {
                    hacerToast("Presiona atras otra vez para salir");
                    time__ = System.currentTimeMillis();
                }
            } else {
                options__.pop();
                setTitle(options__.peek());
                fm.popBackStackImmediate();
            }

        }
    }

    //    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, (android.view.Menu) menu);
        return true;
    }


    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
//        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.HOME && options__.peek() != OPTION_HOME) {
            changeFragment(OPTION_HOME);
            options__.push(OPTION_HOME);
            setTitle(OPTION_HOME);
            return true;
        } else if (id == R.id.SEARCH && options__.peek() != OPTION_BUSCAR) {
            changeFragment(OPTION_BUSCAR);
            options__.push(OPTION_BUSCAR);
            setTitle(OPTION_BUSCAR);
            return true;
        } else if (id == R.id.CART && options__.peek() != OPTION_CARRO) {
            changeFragment(OPTION_CARRO);
            options__.push(OPTION_CARRO);
            setTitle(OPTION_CARRO);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void setTitle(int i) {
        if (i == OPTION_HOME) mTitle = "LEDCO";
        else if (i == OPTION_ELECTRONICA) mTitle = "ELECTRONICA";
        else if (i == OPTION_ROUTERS) mTitle = "ROUTERS";
        else if (i == OPTION_KITS) mTitle = "KITS";
        else if (i == OPTION_ROBOTICA) mTitle = "ROBOTICA";
        else if (i == OPTION_IMPRESIONES3D) mTitle = "IMPRESIONES 3D";
        else if (i == OPTION_CARRO) mTitle = "CARRO";
        else if (i == OPTION_CLIENTES) mTitle = "CLIENTES";
        else if (i == OPTION_SYNC) mTitle = "SYNC";
        else if (i == OPTION_DETALLES) mTitle = "DETALLES";
        else if (i == OPTION_BUSCAR) mTitle = "BUSCAR";
        Menu.this.getSupportActionBar().setTitle(mTitle);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int option = -1;
        int id = item.getItemId();

        if (id == R.id.nav_electronica && options__.peek() != OPTION_ELECTRONICA) {
            option = OPTION_ELECTRONICA;
        } else if (id == R.id.nav_routers && options__.peek() != OPTION_ROUTERS) {
            option = OPTION_ROUTERS;
        } else if (id == R.id.nav_kits && options__.peek() != OPTION_KITS) {
            option = OPTION_KITS;
        } else if (id == R.id.nav_robotica && options__.peek() != OPTION_ROBOTICA) {
            option = OPTION_ROBOTICA;
        } else if (id == R.id.nav_3d && options__.peek() != OPTION_IMPRESIONES3D) {
            option = OPTION_IMPRESIONES3D;
        } else if (id == R.id.nav_cart && options__.peek() != OPTION_CARRO) {
            option = OPTION_CARRO;
        } else if (id == R.id.nav_clientes && options__.peek() != 7) {
            option = 7;
        } else if (id == R.id.nav_sync && options__.peek() != OPTION_SYNC) {
            option = OPTION_SYNC;
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        if (option == OPTION_SYNC) {
            synchronize();
        } else if (option > -1) {
            options__.push(option);
            changeFragment(option);
            setTitle(option);
        }
        return true;
    }

    private void synchronize() {
        Syncs s = new Syncs();
       // s.eliminarProductosBD();
       // s.cargarProductosBD(getApplicationContext());

//        s.enviarDBClientes(getApplicationContext());
       // s.enviarClientesNuevos(getApplicationContext());
      //  s.eliminarDBClientes(getApplicationContext());
      //  s.cargarClientesBD(getApplicationContext());

        s.enviarDBVentas(getApplicationContext());
        s.enviarDBProductosVendidos(getApplicationContext());
       // s.eliminarVentasBD();
       // s.eliminarProdVenBD();

    }

    public void changeFragment(int option) {
        OperacionesClientes clientes;
        Fragment f = null;
        switch (option) {
            case 0: //HOME
                f = new HomeFragment();
                break;
            case 1: //ELECTRONICOS

                listaProductos = op.listProducto(1);
                f = new ProductosFragment();
                break;
            case OPTION_ROUTERS: //ROUTERS
                listaProductos = op.listProducto(OPTION_ROUTERS);
                f = new ProductosFragment();
                break;
            case OPTION_KITS: //KITS
                listaProductos = op.listProducto(OPTION_KITS);
                f = new ProductosFragment();
                break;
            case OPTION_ROBOTICA: //ROBOTICA
                listaProductos = op.listProducto(OPTION_ROBOTICA);
                f = new ProductosFragment();
                break;
            case OPTION_IMPRESIONES3D: //3D
                listaProductos = op.listProducto(OPTION_IMPRESIONES3D);
                f = new ProductosFragment();
                break;
            case OPTION_CARRO: //CARRO
                clientes = new OperacionesClientes();
                listaClientes = clientes.listClientes();
                f = new CarritoFragment();
                break;
            case OPTION_CLIENTES: //CLIENTES
                clientes = new OperacionesClientes();
                listaClientes = clientes.listClientes();
                f = new ClientesFragment();
                break;
            case OPTION_SYNC: //SYNC TODO: view
                return;
            //f = new ProductosFragment();
            case OPTION_DETALLES: //DETALLES TODO: view
                f = new ProductosFragment();
                break;
            case OPTION_BUSCAR: //BUSCAR NOMBRE
                f = new BuscarFragment();
                break;
            default:
                f = new ProductosFragment();
                break;
        }
        FragmentTransaction ft = fm.beginTransaction();
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.replace(R.id.FRAGMENT, f);
        ft.addToBackStack(null);
        ft.commit();
    }

    @Override
    public void onListProductosFragmentInteraction(Producto productoSeleccionado, int action, int cantidad) {
        if (action == 1) {
            ProductoVendido nuevoProducto = new ProductoVendido();
            nuevoProducto.setId_producto(productoSeleccionado.id_producto);
            for (ProductoVendido productoEnCarro : carrito) {
                if (productoEnCarro.id_producto == nuevoProducto.id_producto) {
                    if (productoEnCarro.cantidad + cantidad <= productoSeleccionado.inventario) {
                        productoEnCarro.cantidad += cantidad;
                        productoEnCarro.subtotal = productoEnCarro.cantidad * productoEnCarro.precio_unitario;
                        calcularTotal();
                        hacerToast("Agregado");
                        return;
                    } else {
                        hacerToast("No hay suficientes productos. Revisa el carrito");
                        return;
                    }
                }
            }
            if (cantidad > productoSeleccionado.inventario) {
                hacerToast("No hay suficientes productos.");
                return;
            }
            nuevoProducto.precio_unitario = productoSeleccionado.precio_individual;
            nuevoProducto.nombre = productoSeleccionado.nombre_producto;
            nuevoProducto.cantidad = cantidad;
            nuevoProducto.subtotal = cantidad * nuevoProducto.precio_unitario;
            nuevoProducto.descripcion = productoSeleccionado.descripcion_corta;
            nuevoProducto.inventario_temporal = productoSeleccionado.inventario;
            nuevoProducto.setId_venta(dateFormat.format(new Date()));
            carrito.add(nuevoProducto);
            totalCarrito += nuevoProducto.subtotal;
            hacerToast("Agregado");
        }
    }


    @Override
    public void onListFragmentInteractionPV(ProductoVendido item, int action) {
        if (action == 1) {
            //DELETE
            carrito.remove(item);
            calcularTotal();
            hacerToast("ELIMINADO");
        }
    }

    @Override
    public void onCarritoBarraInteraction(Venta v, int action) {
        if (action == 1) {
            if (carrito.size() < 1) {
                hacerToast("CARRITO VACIO");
                return;
            }
            Date date = new Date();
            v.setFecha(dateFormat.format(date));
            v.setId_venta(idFormat.format(date));
            OperacionesVenta ov = new OperacionesVenta();
            ov.insertar_venta(v);
            OperacionesProductoVendido opv = new OperacionesProductoVendido();
            Toast.makeText(this, "Venta Realizada", Toast.LENGTH_SHORT).show();
            for (ProductoVendido productoVendido : carrito) {
                productoVendido.setId_venta(v.getId_venta());
                opv.insertarProductoVendido(productoVendido);
                op.actualizarInventarioProducto(productoVendido.id_producto, productoVendido.cantidad);
            }
            carrito.clear();
            changeFragment(0);
            options__.push(0);
            setTitle(0);
        } else if (action == 0) {
            options__.push(OPTION_CLIENTES);
            changeFragment(OPTION_CLIENTES);
            setTitle(OPTION_CLIENTES);
        }
    }

    @Override
    public void onHomeFragmentInteraction(int i) {
        int option = -1;
        if (i == 1 && options__.peek() != 1) {
            option = 1;
        } else if (i == OPTION_ROUTERS && options__.peek() != OPTION_ROUTERS) {
            option = OPTION_ROUTERS;
        } else if (i == OPTION_KITS && options__.peek() != OPTION_KITS) {
            option = OPTION_KITS;
        } else if (i == OPTION_ROBOTICA && options__.peek() != OPTION_ROBOTICA) {
            option = OPTION_ROBOTICA;
        } else if (i == OPTION_IMPRESIONES3D && options__.peek() != OPTION_IMPRESIONES3D) {
            option = OPTION_IMPRESIONES3D;
        }
        if (option > -1) {
            options__.push(option);
            changeFragment(option);
            setTitle(option);
        }
    }

    public void hacerToast(String message) {
        theToast.setText(message);
        theToast.show();
    }

    @Override
    public void onOperacionCliente(Cliente cliente, String a) {
        if (a.equals("agregar")) {
            cliente.setNuevo(true);
            Date date = new Date();
            cliente.setModificado(dateFormat.format(date));
            cliente.setId_cliente(idFormat.format(date));
            listaClientes.add(cliente);
            OperacionesClientes oc = new OperacionesClientes();
            oc.insertarcliente(cliente);
            hacerToast("Cliente agregado");
        } else if (a.equals("eliminar")) {
            OperacionesClientes oc = new OperacionesClientes();
            if (cliente.isNuevo()) {
                listaClientes.remove(cliente);
                oc.eliminarCliente(cliente.getId_cliente());
            } else{
                oc.modificarBanderas(cliente,false,false,true);
                listaClientes.remove(cliente);
            }
            hacerToast("Cliente eliminado");
        } else if(a.equals("modificar")){
            cliente.setEditado(true);
            //TODO: METODOS PARA ACTUALIZAR INFORMACION DE CLIENTE
        }
    }

    @Override
    public void onListaBuscarFragmentInteraction(Producto productoSeleccionado, int action, int cantidad) {
        if (action == 1) {
            ProductoVendido nuevoProducto = new ProductoVendido();
            nuevoProducto.id_producto = productoSeleccionado.id_producto;
            for (ProductoVendido productoEnCarro : carrito) {
                if (productoEnCarro.id_producto == nuevoProducto.id_producto) {
                    if (productoEnCarro.cantidad + cantidad <= productoSeleccionado.inventario) {
                        productoEnCarro.cantidad += cantidad;
                        productoEnCarro.subtotal = productoEnCarro.cantidad * productoEnCarro.precio_unitario;
                        calcularTotal();
                        hacerToast("Agregado");
                        return;
                    } else {
                        hacerToast("No hay suficientes productos. Revisa el carrito");
                        return;
                    }
                }
            }
            if (cantidad > productoSeleccionado.inventario) {
                hacerToast("No hay suficientes productos.");
                return;
            }
            nuevoProducto.precio_unitario = productoSeleccionado.precio_individual;
            nuevoProducto.nombre = productoSeleccionado.nombre_producto;
            nuevoProducto.cantidad = cantidad;
            nuevoProducto.subtotal = cantidad * nuevoProducto.precio_unitario;
            nuevoProducto.descripcion = productoSeleccionado.descripcion_corta;
            nuevoProducto.inventario_temporal = productoSeleccionado.inventario;

            carrito.add(nuevoProducto);
            totalCarrito += nuevoProducto.subtotal;
            hacerToast("Agregado");
        }
    }

    @Override
    public void onBuscarProducto(String s) {

        listaBuscar = op.buscarProductoNombre(s);
    }
}
