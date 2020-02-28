package com.example.miguel.menuledco;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.miguel.menuledco.Model.Cliente;

import java.util.ArrayList;


public class ClientesFragment extends Fragment {
    ListView listView;
    Spinner tipo;

    private OnFragmentInteractionListener mListener;
    private ArrayList<String> tipos_clientes = new ArrayList<>();

    public ClientesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_clientes, container, false);
        tipos_clientes.add("Estudiante");
        tipos_clientes.add("Tienda");
        tipos_clientes.add("Cliente");

        final EditText nom, mail, tel;

        ImageButton add = view.findViewById(R.id.clientes_add);
        listView = view.findViewById(R.id.clientes_listview);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                notificacionOpciones(Menu.listaClientes.get(i));
                return false;
            }
        });
        cargarLista(view.getContext());
        nom = view.findViewById(R.id.clientes_nom);
        mail = view.findViewById(R.id.clientes_mail);
        tel = view.findViewById(R.id.clientes_tel);
        tipo = view.findViewById(R.id.clientes_spiner);
        tipo.setAdapter(new ArrayAdapter<>(view.getContext(),
                android.R.layout.simple_spinner_item, tipos_clientes));
        View.OnClickListener registrarCliente = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!nom.getText().toString().isEmpty()) {
                    Cliente c = new Cliente();
                    c.setNombre_cliente(nom.getText().toString());
                    c.setEmail_cliente(mail.getText().toString());
                    c.setTelefono_cliente(tel.getText().toString());
                    c.setTipo_cliente(String.valueOf(tipo.getSelectedItemPosition() + 1));
                    onCreateNewCliente(c);
                    cargarLista(view.getContext());
                    nom.setText("");
                    mail.setText("");
                    tel.setText("");
                }
            }
        };
        add.setOnClickListener(registrarCliente);

        return view;
    }

    private void notificacionOpciones(final Cliente c) {
        final AlertDialog.Builder a = new AlertDialog.Builder(getActivity());
        a.setTitle(c.getNombre_cliente());
        a.setMessage(String.format("ID: %s\nNombre: %s\nTipo: %s\nTel: %s\nMail: %s\nModificado: %s",c.getId_cliente(),c.getNombre_cliente(),c.getTipo_cliente(),
                c.getTelefono_cliente(),c.getEmail_cliente(),c.getModificado()));
        a.setNegativeButton("Eliminar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mListener.onOperacionCliente(c,"eliminar");
                cargarLista(a.getContext());
            }
        });
        //a.setPositiveButton("Modificar",null);
        a.setNeutralButton("Cancelar",null);
        a.create().show();
    }

    public void cargarLista(Context context) {
        ArrayList<String> nombre = new ArrayList<>();
        for (Cliente c : Menu.listaClientes) {
            nombre.add(String.format("%s -  %s",
                    c.getNombre_cliente(), tipoCliente(c.getTipo_cliente())));
        }
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, nombre);
        listView.setAdapter(adapter);
    }

    public String tipoCliente(String t) {
        switch (t) {
            case "1":
                return "Estudiante";
            case "2":
                return "Tienda";
            case "3":
                return "Cliente";
            default:
                return "-1";
        }
    }

    public void onCreateNewCliente(Cliente cliente) {
        if (mListener != null) {
            mListener.onOperacionCliente(cliente, "agregar");
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) activity;
        } else {
            throw new RuntimeException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        void onOperacionCliente(Cliente cliente, String acction);
    }
}
