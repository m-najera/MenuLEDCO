package com.example.miguel.menuledco;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.miguel.menuledco.Model.Cliente;
import com.example.miguel.menuledco.Model.ProductoVendido;
import com.example.miguel.menuledco.Model.Venta;

import java.util.ArrayList;

public class CarritoFragment extends Fragment {


    static TextView subtotal;
    static double desc = 0;
    static TextView total;
    TextView descuento;
    private OnListFragmentInteractionListener mListener;
    private OnCarritoBarraInteraction mListenerSales;

    public CarritoFragment() {
    }

    public static void actualizarTotal() {
        Menu.calcularTotal();
        subtotal.setText(String.format("Subtotal: $ %.2f", Menu.totalCarrito));
        total.setText(String.format("Total:  $ %.2f", Menu.totalCarrito - (desc * Menu.totalCarrito)));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_carrito_list, container, false);
        RecyclerView recyclerView = v.findViewById(R.id.list);
        final ImageButton btnAddUser = v.findViewById(R.id.pv_btn_add_user);
        final ImageButton confirm = v.findViewById(R.id.pv_confirmar);
        subtotal = v.findViewById(R.id.pv_subtotal);
        total = v.findViewById(R.id.pv_total);
        descuento = v.findViewById(R.id.pv_descuento);
        final Spinner spinner = v.findViewById(R.id.pv_clientes);
        ArrayList<String> lista = new ArrayList<>();
        for (Cliente c : Menu.listaClientes) {
            lista.add(c.getNombre_cliente());
        }
        spinner.setAdapter(new ArrayAdapter<>(v.getContext(), android.R.layout.simple_spinner_item, lista));
        if (Menu.listaClientes.size() > 0) getDescuento(0);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                getDescuento(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.getId() == btnAddUser.getId()) {
                    mListenerSales.onCarritoBarraInteraction(null, 0);
                } else if (view.getId() == confirm.getId()) {
                    Venta v = new Venta();
                    v.setId_cliente(String.valueOf(Menu.listaClientes.get(spinner.getSelectedItemPosition()).getId_cliente()));
                    v.setTotal_venta(String.valueOf(Menu.totalCarrito - (Menu.totalCarrito * desc)));
                    mListenerSales.onCarritoBarraInteraction(v, 1);
                    actualizarTotal();
                }
            }
        };

        btnAddUser.setOnClickListener(onClickListener);
        confirm.setOnClickListener(onClickListener);
        Context context = v.getContext();
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(new CarritoRecyclerViewAdapter(context, mListener));
        actualizarDescuento();
        actualizarTotal();
        return v;
    }

    public void actualizarDescuento() {
        descuento.setText("");
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) activity;
            mListenerSales = (OnCarritoBarraInteraction) activity;
        } else {
            throw new RuntimeException(activity.toString()
                    + " must implement OnListaBuscarFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void getDescuento(int i) {
        String s = "0%";
        switch (Menu.listaClientes.get(i).getTipo_cliente()) {
            case "1":
                s = "5%";
                desc = 0.05;
                break;
            case "2":
                s = "10%";
                desc = 0.1;
                break;
            case "3":
                s = "0%";
                desc = 0;
                break;
        }
        descuento.setText(s);
    }


    public interface OnCarritoBarraInteraction {
        void onCarritoBarraInteraction(Venta v, int action);
    }

    public interface OnListFragmentInteractionListener {
        void onListFragmentInteractionPV(ProductoVendido item, int action);
    }
}
