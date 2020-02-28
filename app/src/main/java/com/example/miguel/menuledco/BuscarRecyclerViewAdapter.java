package com.example.miguel.menuledco;

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

import com.example.miguel.menuledco.BuscarFragment.OnListaBuscarFragmentInteractionListener;
import com.example.miguel.menuledco.Model.Producto;
import com.squareup.picasso.Picasso;

public class BuscarRecyclerViewAdapter extends RecyclerView.Adapter<BuscarRecyclerViewAdapter.ViewHolder> {
    private final ContextWrapper cw;
    private final OnListaBuscarFragmentInteractionListener mListener;

    public BuscarRecyclerViewAdapter(Context context, OnListaBuscarFragmentInteractionListener listener) {
        Menu.listaBuscar.clear();
        mListener = listener;
        cw = new ContextWrapper(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final int index = position;
        holder.obj_producto = Menu.listaBuscar.get(position);
        holder.tv_nom.setText(Menu.listaBuscar.get(position).nombre_producto);
        holder.tv_desc.setText(Menu.listaBuscar.get(position).descripcion_corta);
        holder.tv_precio.setText(String.valueOf(Menu.listaBuscar.get(position).precio_individual));
        holder.tv_dispon.setText(String.valueOf(Menu.listaBuscar.get(position).inventario));
        holder.et_cantidad.setText(String.valueOf(1));
        holder.et_cantidad.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                try {
                    int cantidad = Integer.parseInt(holder.et_cantidad.getText().toString());
                    if (cantidad >= 0) {
                        holder.tv_subtotal.setText(String.valueOf(
                                cantidad * Menu.listaBuscar.get(index).precio_individual));
                    } else {
//                        holder.et_cantidad.setText("0");
//                        holder.et_cantidad.selectAll();
                    }
                } catch (Exception e) {
//                    holder.et_cantidad.setText("0");
//                    holder.et_cantidad.selectAll();
                }
            }
        });
        holder.tv_subtotal.setText(String.valueOf(Menu.listaBuscar.get(position).precio_individual));
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.getId() == holder.btn_info.getId()) {
                    //TODO: show info of the item
                    mListener.onListaBuscarFragmentInteraction(holder.obj_producto, 0, 0);
                } else if (view.getId() == holder.btn_add.getId()) {
                    if (!holder.et_cantidad.getText().toString().isEmpty())
                        if ( Integer.parseInt(holder.et_cantidad.getText().toString())>0)
                    mListener.onListaBuscarFragmentInteraction(holder.obj_producto, 1,
                            Integer.parseInt(holder.et_cantidad.getText().toString()));
                } else if (view.getId() == holder.img.getId()) {
                    //TODO: enlarge image
                    mListener.onListaBuscarFragmentInteraction(holder.obj_producto, 2, 0);
                }
            }
        };
        holder.btn_info.setOnClickListener(onClickListener);
        holder.btn_add.setOnClickListener(onClickListener);
        holder.img.setOnClickListener(onClickListener);
        String url = "http://justtryingtolearn.000webhostapp.com/img/Art" + String.valueOf(holder.obj_producto.getId_producto())+".png";
        Picasso.get().load(url).stableKey(String.valueOf("img"+Menu.listaBuscar.get(position).getId_producto())).into(holder.img);

    }

    @Override
    public int getItemCount() {
        return Menu.listaBuscar.size();
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
        public final ImageButton btn_add;
        public Producto obj_producto;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            tv_nom = view.findViewById(R.id.item_name);
            tv_desc = view.findViewById(R.id.item_desc);
            tv_dispon = view.findViewById(R.id.tv_dispon);
            tv_precio = view.findViewById(R.id.tv_precio);
            tv_subtotal = view.findViewById(R.id.tv_subtotal);
            et_cantidad = view.findViewById(R.id.et_cantidad);
            img = view.findViewById(R.id.iv_img);
            btn_add = view.findViewById(R.id.btn_add_cart);
            btn_info = view.findViewById(R.id.btn_info);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + tv_desc.getText() + "'";
        }
    }
}
