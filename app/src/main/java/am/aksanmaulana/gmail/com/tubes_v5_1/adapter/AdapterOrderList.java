package am.aksanmaulana.gmail.com.tubes_v5_1.adapter;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import am.aksanmaulana.gmail.com.tubes_v5_1.R;
import am.aksanmaulana.gmail.com.tubes_v5_1.fHome.DetilsOrderListFragment;
import am.aksanmaulana.gmail.com.tubes_v5_1.model.OrderList;

public class AdapterOrderList extends RecyclerView.Adapter<AdapterOrderList.MyViewHolder> {

    private List<OrderList> orderLists;
    private int position;
    Context context;
    private String fragmentName;

    public AdapterOrderList(Context context, List<OrderList> data, String fragmentName) {
        this.context = context;
        this.orderLists = data;
        this.fragmentName = fragmentName;
    }

    @Override
    public MyViewHolder onCreateViewHolder (ViewGroup parent, int viewType){
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_order_list, parent, false);
        MyViewHolder mViewHolder = new MyViewHolder(mView);
        return mViewHolder;
    }

    @Override
    public void onBindViewHolder (MyViewHolder holder,final int position){

        if(orderLists.get(position).getStatusOrder().equals("Selesai")){
            holder.tvStatusOrder.setTextColor(Color.parseColor("#00DD21"));
        }else if(orderLists.get(position).getStatusOrder().equals("Dikirim")){
            holder.tvStatusOrder.setTextColor(Color.parseColor("#0000EE"));
        }else if(orderLists.get(position).getStatusOrder().equals("Menunggu")){
            holder.tvStatusOrder.setTextColor(Color.parseColor("#FFAC00"));
        }else{
            holder.tvStatusOrder.setTextColor(Color.parseColor("#FF0000"));
        }

        if(fragmentName.equals("orderListFragment")) {
            holder.tvNameUser.setText("Pemesan : " + orderLists.get(position).getNameUser());
            holder.tvStatusOrder.setText("status pemesanan : (" + orderLists.get(position).getStatusOrder() + ")");
        }else{
            holder.tvNameUser.setText("Pesan dari : \n" + orderLists.get(position).getNameBusiness());
            holder.tvStatusOrder.setText("status pembelian : (" + orderLists.get(position).getStatusOrder() + ")");
        }
        holder.tvStatusOrder.setText("(" + orderLists.get(position).getStatusOrder() + ")");

        holder.tvNameProduct.setText("" + orderLists.get(position).getNameProduct());
        holder.ivImageProduct.setImageResource(R.drawable.ic_product_agriculture);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle mBundle;
                FragmentManager mFragmentManager;
                FragmentTransaction mFragmentTransaction;

                DetilsOrderListFragment detilsOrderListFragment = new DetilsOrderListFragment();
                mBundle = new Bundle();
                mBundle.putInt("idOrderProduct", orderLists.get(position).getIdOrderProduct());
                mBundle.putString("idUserUmkm", orderLists.get(position).getIdUserUmkm());
                mBundle.putInt("idProduct", orderLists.get(position).getIdProduct());
                mBundle.putString("nameProduct", orderLists.get(position).getNameProduct());
                mBundle.putString("amountOrder", orderLists.get(position).getAmountOrder());
                mBundle.putString("totalPrice", orderLists.get(position).getTotalPrice());
                mBundle.putString("payMethode", orderLists.get(position).getPayMethode());
                mBundle.putString("statusOrder", orderLists.get(position).getStatusOrder());
                mBundle.putString("dateOrder", orderLists.get(position).getDateOrder());
                mBundle.putString("statusPay", orderLists.get(position).getStatusPay());
                mBundle.putString("satuanHarga", orderLists.get(position).getSatuanHarga());
                mBundle.putString("orderMessage", orderLists.get(position).getOrderMessage());
                mBundle.putString("statusOrder", orderLists.get(position).getStatusOrder());
                mBundle.putString("idBusiness", orderLists.get(position).getIdBusiness());

                mBundle.putString("nameUser", orderLists.get(position).getNameUser());
                mBundle.putString("addressOrder", orderLists.get(position).getAddressOrder());
                mBundle.putString("phoneUser", orderLists.get(position).getPhoneUser());

                mBundle.putString("fragmentName", fragmentName);


                detilsOrderListFragment.setArguments(mBundle);
                mFragmentManager = ((Activity) context).getFragmentManager();
                mFragmentTransaction = mFragmentManager
                        .beginTransaction()
                        .replace(R.id.content, detilsOrderListFragment, DetilsOrderListFragment.class.getSimpleName());
                mFragmentTransaction.addToBackStack(null).commit();
            }
        });
    }


    @Override
    public int getItemCount () {
        return orderLists.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvNameUser, tvNameProduct, tvStatusOrder;
        public ImageView ivImageProduct;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvNameUser = (TextView) itemView.findViewById(R.id.tvNameUser);
            tvNameProduct = (TextView) itemView.findViewById(R.id.tvNameProduct);
            tvStatusOrder = (TextView) itemView.findViewById(R.id.tvStatusOrder);
            ivImageProduct = (ImageView) itemView.findViewById(R.id.ivImageProduct);
        }


    }
}
