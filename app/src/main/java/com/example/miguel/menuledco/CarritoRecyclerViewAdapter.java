package com.example.miguel.menuledco;

import android.app.Fragment;
import android.content.Context;
import android.content.ContextWrapper;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.miguel.menuledco.CarritoFragment.OnListFragmentInteractionListener;
import com.example.miguel.menuledco.Model.ProductoVendido;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static com.example.miguel.menuledco.CarritoFragment.actualizarTotal;

public class CarritoRecyclerViewAdapter extends RecyclerView.Adapter<CarritoRecyclerViewAdapter.ViewHolder> {
    private final ContextWrapper cw;
    private final OnListFragmentInteractionListener mListener;

    public CarritoRecyclerViewAdapter(Context context, OnListFragmentInteractionListener listener) {
        mListener = listener;
        cw = new ContextWrapper(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_carrito, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final int index = position;
        holder.obj_producto = Menu.carrito.get(position);
        holder.tv_nom.setText(Menu.carrito.get(position).nombre);
        holder.tv_desc.setText(Menu.carrito.get(position).descripcion);
        holder.tv_precio.setText(String.valueOf(Menu.carrito.get(position).precio_unitario));
        holder.tv_dispon.setText(String.valueOf(Menu.carrito.get(position).inventario_temporal));
        holder.et_cantidad.setText(String.valueOf(Menu.carrito.get(position).cantidad));
        holder.et_cantidad.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i0, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                int cantidad = 0;
                try {
                    cantidad = Integer.parseInt(holder.et_cantidad.getText().toString());
                    if (cantidad >= 0) {
                        if (cantidad <= holder.obj_producto.inventario_temporal) {
                            holder.tv_subtotal.setText(String.valueOf(cantidad * Menu.carrito.get(index).precio_unitario));
                        }else{
                            holder.et_cantidad.setText(String.valueOf(holder.obj_producto.inventario_temporal));
                            Toast.makeText(cw, "No hay suficientes productos en inventario", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        holder.et_cantidad.setText("0");
                        holder.et_cantidad.selectAll();
                    }
                } catch (Exception e) {
                    holder.et_cantidad.setText("0");
                    holder.et_cantidad.selectAll();
                }
                Menu.carrito.get(index).setCantidad(cantidad);
                Menu.carrito.get(index).setCantidad(cantidad);
                Menu.calcularTotal();
                actualizarTotal();
            }
        });
        holder.tv_subtotal.setText(String.valueOf(Menu.carrito.get(position).cantidad * Menu.carrito.get(position).precio_unitario));
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.getId() == holder.btn_info.getId()) {
                    //TODO: show info of the item
                    mListener.onListFragmentInteractionPV(holder.obj_producto, 0);
                    notifyDataSetChanged();
                } else if (view.getId() == holder.btn_remove.getId()) { //OBJETO ELIMINADO
                    mListener.onListFragmentInteractionPV(holder.obj_producto, 1);
                    notifyDataSetChanged();
                    actualizarTotal();
                } else if (view.getId() == holder.img.getId()) {
                    //TODO: enlarge image
                    mListener.onListFragmentInteractionPV(holder.obj_producto, 2);
                }
            }
        };
        holder.btn_info.setOnClickListener(onClickListener);
        holder.btn_remove.setOnClickListener(onClickListener);
        holder.img.setOnClickListener(onClickListener);
        String url = "http://justtryingtolearn.000webhostapp.com/img/Art" + String.valueOf(holder.obj_producto.getId_producto())+".png";
        Picasso.get().load(url).stableKey(String.valueOf("img"+holder.obj_producto.getId_producto())).into(holder.img);

    }

    @Override
    public int getItemCount() {
        return Menu.carrito.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView tv_nom;
        public final TextView tv_desc;
        public final TextView tv_dispon;
        public final TextView tv_precio;
        public final EditText et_cantidad;
        public final TextView tv_subtotal;
        public final ImageView img;
        public final ImageButton btn_info;
        public final ImageButton btn_remove;
        public ProductoVendido obj_producto;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            tv_nom = view.findViewById(R.id.pv_item_name);
            tv_desc = view.findViewById(R.id.pv_item_desc);
            tv_dispon = view.findViewById(R.id.pv_tv_dispon);
            tv_precio = view.findViewById(R.id.pv_tv_precio);
            tv_subtotal = view.findViewById(R.id.pv_tv_subtotal);
            et_cantidad = view.findViewById(R.id.pv_et_cantidad);
            img = view.findViewById(R.id.pv_iv_img);
            btn_remove = view.findViewById(R.id.pv_btn_remove);
            btn_info = view.findViewById(R.id.pv_btn_info);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + tv_desc.getText() + "'";
        }
    }
}
