package am.aksanmaulana.gmail.com.tubes_v5_1.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import am.aksanmaulana.gmail.com.tubes_v5_1.R;
import am.aksanmaulana.gmail.com.tubes_v5_1.api.ApiClient;
import am.aksanmaulana.gmail.com.tubes_v5_1.api.ApiData;
import am.aksanmaulana.gmail.com.tubes_v5_1.api.ApiInterface;
import am.aksanmaulana.gmail.com.tubes_v5_1.fProfile.AddProductFragment;
import am.aksanmaulana.gmail.com.tubes_v5_1.fProfile.ProductProfileFragment;
import am.aksanmaulana.gmail.com.tubes_v5_1.helper.KursRupiahConvert;
import am.aksanmaulana.gmail.com.tubes_v5_1.model.Product;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterProduct extends RecyclerView.Adapter<AdapterProduct.MyViewHolder>{

    private List<Product> productList;
    Context context;

    ApiData<String> apiProduct;

    public AdapterProduct(Context context, List<Product> data) {
        this.context = context;
        this.productList = data;
    }
    public AdapterProduct() {
    }

    @Override
    public MyViewHolder onCreateViewHolder (ViewGroup parent, int viewType){
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_product, parent, false);
        MyViewHolder mViewHolder = new MyViewHolder(mView);
        return mViewHolder;
    }

    @Override
    public void onBindViewHolder (MyViewHolder holder,final int position) {

        KursRupiahConvert kursRupiahConvert = new KursRupiahConvert();
        final String priceProduct = kursRupiahConvert.convertPrice(Double.valueOf(productList.get(position).getPriceProduct()));

        holder.tvNameProduct.setText("" + productList.get(position).getNameProduct());
        holder.tvPriceProduct.setText("" + priceProduct + "/" + productList.get(position).getSatuanHarga());

        if (productList.get(position).getStockProduct().equals("Tidak Tersedia")){
            holder.tvStockProduct.setTextColor(Color.parseColor("#FF0000"));
        }
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

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                PopupMenu popup = new PopupMenu(context, view);
                popup.getMenuInflater()
                        .inflate(R.menu.product_menu, popup.getMenu());

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.mEdit :
                                editProduct(
                                        "" + productList.get(position).getIdProduct(),
                                        "" + productList.get(position).getIdBusiness(),
                                        "" + productList.get(position).getNameBusiness(),
                                        position
                                );


                                //Toast.makeText(context, "edit ", Toast.LENGTH_SHORT).show();
                                return true;
                            case R.id.mDelete :

                                deleteProduct(
                                        "" + productList.get(position).getIdProduct(),
                                        "" + productList.get(position).getIdBusiness(),
                                        "" + productList.get(position).getNameBusiness()
                                );

                                //Toast.makeText(context, "Berhasil Menghapus Produk", Toast.LENGTH_SHORT).show();
                                return true;
                            default:
                                return true;
                        }
                    }

                });
                popup.show(); //showing popup menu
                return true;
            }
        });
    }

    public void editProduct(String idProduct, final String idBusiness, final String nameBusiness, int position){
        AddProductFragment addProductFragment = new AddProductFragment();
        Bundle mBundle = new Bundle();
        mBundle.putInt("idBusiness", Integer.valueOf(idBusiness));
        mBundle.putString("idProduct", idProduct);
        mBundle.putString("nameBusiness", nameBusiness);
        mBundle.putString("nameProduct", productList.get(position).getNameProduct());
        mBundle.putString("priceProduct", productList.get(position).getPriceProduct());
        mBundle.putString("stockProduct", productList.get(position).getStockProduct());
        mBundle.putString("satuanHarga", productList.get(position).getSatuanHarga());
        mBundle.putString("state", "edit");
        addProductFragment.setArguments(mBundle);

        FragmentManager mFragmentManager = ((Activity)context).getFragmentManager();
        FragmentTransaction mFragmentTransaction = mFragmentManager
                .beginTransaction()
                .replace(R.id.content, addProductFragment, AddProductFragment.class.getSimpleName());
        mFragmentTransaction.addToBackStack(null).commit();
    }

    public void deleteProduct(String idProduct, final String idBusiness, final String nameBusiness){
        AlertDialog.Builder builder = new AlertDialog.Builder((Activity) context);
        //View view = getActivity().getLayoutInflater().inflate(R.layout.progress_bar, null);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        View view = inflater.inflate( R.layout.progress_bar, null );
        builder.setView(view);
        builder.setCancelable(false);
        final Dialog dialog = builder.create();

        //checking if email and passwords are empty
        dialog.show();
        final Context c = (Activity) context;
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ApiData<String>> call;
        call = apiService.deleteProduct(idProduct);

        call.enqueue(new Callback<ApiData<String>>() {
            @Override
            public void onResponse(@NonNull Call<ApiData<String>> call, @NonNull Response<ApiData<String>> response) {
                apiProduct = response.body();

                if (apiProduct != null) {
                    if (apiProduct.getStatus().equals("success")){
                        dialog.dismiss();

                        ProductProfileFragment productProfileFragment = new ProductProfileFragment();
                        Bundle mBundle = new Bundle();
                        mBundle.putInt("idBusiness", Integer.valueOf(idBusiness));
                        mBundle.putString("nameBusiness", nameBusiness);
                        productProfileFragment.setArguments(mBundle);

                        FragmentManager mFragmentManager = ((Activity)context).getFragmentManager();
                        FragmentTransaction mFragmentTransaction = mFragmentManager
                                .beginTransaction()
                                .replace(R.id.content, productProfileFragment, ProductProfileFragment.class.getSimpleName());
                        mFragmentTransaction.addToBackStack(null).commit();
                    }
                    else{
                        dialog.dismiss();
                        Toast.makeText(c, "Something might be wrong", Toast.LENGTH_LONG).show();
                        return;
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ApiData<String>> call, @NonNull Throwable t) {
                dialog.dismiss();
                Log.e("check", "onFailure: ", t);
                Toast.makeText(c, "connection error", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public int getItemCount () {
        return productList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvNameProduct, tvPriceProduct, tvStockProduct;
        public ImageView ivImageProduct;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvNameProduct = (TextView) itemView.findViewById(R.id.tvNameProduct);
            tvPriceProduct = (TextView) itemView.findViewById(R.id.tvPriceProduct);
            tvStockProduct = (TextView) itemView.findViewById(R.id.tvStockProduct);
            ivImageProduct = (ImageView) itemView.findViewById(R.id.ivImageProduct);
        }
    }
}
