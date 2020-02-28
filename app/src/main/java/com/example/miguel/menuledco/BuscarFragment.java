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
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.miguel.menuledco.Model.Producto;

public class BuscarFragment extends Fragment {

    private OnListaBuscarFragmentInteractionListener mListener;
    private  OnBuscarProducto mListenerBuscar;
    EditText buscar;
    ImageButton button;
    BuscarRecyclerViewAdapter adapter ;

    public BuscarFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_buscar_list, container, false);
        Context context = view.getContext();
        final RecyclerView recyclerView = view.findViewById(R.id.list_buscar);
        buscar = view.findViewById(R.id.buscar_tx);
        button = view.findViewById(R.id.buscar_btn);
        adapter = new BuscarRecyclerViewAdapter(context, mListener);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListenerBuscar.onBuscarProducto(buscar.getText().toString());
                adapter.notifyDataSetChanged();
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(adapter);
        return view;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof OnListaBuscarFragmentInteractionListener) {
            mListener = (OnListaBuscarFragmentInteractionListener) activity;
            mListenerBuscar = (OnBuscarProducto) activity;
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

    public interface OnListaBuscarFragmentInteractionListener {
        void onListaBuscarFragmentInteraction(Producto item, int action, int quantity);
    }

    public interface OnBuscarProducto{
        void onBuscarProducto(String s);
    }
}
