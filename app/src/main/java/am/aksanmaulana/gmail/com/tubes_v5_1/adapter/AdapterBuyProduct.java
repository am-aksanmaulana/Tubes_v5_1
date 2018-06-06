package am.aksanmaulana.gmail.com.tubes_v5_1.adapter;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import am.aksanmaulana.gmail.com.tubes_v5_1.R;
import am.aksanmaulana.gmail.com.tubes_v5_1.fHome.FormOrderProductFragment;
import am.aksanmaulana.gmail.com.tubes_v5_1.model.Product;

public class AdapterBuyProduct extends RecyclerView.Adapter<AdapterBuyProduct.MyViewHolder>{

    private List<Product> productList;
    Context context;

    private Bundle mBundle;
    private FragmentManager mFragmentManager;
    private FragmentTransaction mFragmentTransaction;

    public AdapterBuyProduct(Context context, List<Product> data) {
        this.context = context;
        this.productList = data;
    }
    public AdapterBuyProduct() {
    }

    @Override
    public MyViewHolder onCreateViewHolder (ViewGroup parent, int viewType){
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_buy_product, parent, false);
        MyViewHolder mViewHolder = new MyViewHolder(mView);
        return mViewHolder;
    }

    @Override
    public void onBindViewHolder (MyViewHolder holder,final int position){

        if(productList.get(position).getStockProduct().equals("Tersedia")){
            holder.tvStockProduct.setTextColor(Color.parseColor("#00DD21"));
        }else{
            holder.tvStockProduct.setTextColor(Color.parseColor("#FF0000"));
        }

        holder.tvNameProduct.setText("" + productList.get(position).getNameProduct());
        holder.tvNameBusiness.setText("" + productList.get(position).getNameBusiness());
        holder.tvStockProduct.setText("" + productList.get(position).getStockProduct());

        if(productList.get(position).getBusinessType().equals("Pertanian")){
            holder.ivImageProduct.setImageResource(R.drawable.ic_product_agriculture);
        }else if(productList.get(position).getBusinessType().equals("Perdagangan")){
            holder.ivImageProduct.setImageResource(R.drawable.ic_product_commerse);
        }else{
            holder.ivImageProduct.setImageResource(R.drawable.ic_product_manufacture);
        }


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FormOrderProductFragment formOrderProductFragment = new FormOrderProductFragment();
                mBundle = new Bundle();
                mBundle.putString("nameProduct", productList.get(position).getNameProduct());
                mBundle.putString("priceProduct", productList.get(position).getPriceProduct());
                mBundle.putString("satuanHarga", productList.get(position).getSatuanHarga());
                mBundle.putString("stockProduct", productList.get(position).getStockProduct());
                mBundle.putString("idProduct", String.valueOf(productList.get(position).getIdProduct()));

                mBundle.putString("businessType", productList.get(position).getBusinessType());
                mBundle.putString("nameBusiness", productList.get(position).getNameBusiness());
                mBundle.putString("nameUser", productList.get(position).getNameUser());
                mBundle.putString("userUmkmPenjual", productList.get(position).getUserUmkm());
                mBundle.putString("locationBusiness", productList.get(position).getLocationBusiness());

                formOrderProductFragment.setArguments(mBundle);

                mFragmentManager = ((Activity) context).getFragmentManager();
                mFragmentTransaction = mFragmentManager
                        .beginTransaction()
                        .replace(R.id.content, formOrderProductFragment, FormOrderProductFragment.class.getSimpleName());
                mFragmentTransaction.addToBackStack(null).commit();
            }
        });

        holder.itemView.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
            @Override
            public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                menu.add("Ubah Data").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        return true;
                    }
                });
                menu.add("Hapus Data").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        return true;
                    }
                });
            }
        });

    }

    @Override
    public int getItemCount () {
        return productList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvNameProduct, tvNameBusiness, tvStockProduct;
        public ImageView ivImageProduct;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvNameProduct = (TextView) itemView.findViewById(R.id.tvNameProduct);
            tvStockProduct = (TextView) itemView.findViewById(R.id.tvStockProduct);
            ivImageProduct = (ImageView) itemView.findViewById(R.id.ivImageProduct);
            tvNameBusiness = (TextView) itemView.findViewById(R.id.tvNameBusiness);
        }
    }
}
