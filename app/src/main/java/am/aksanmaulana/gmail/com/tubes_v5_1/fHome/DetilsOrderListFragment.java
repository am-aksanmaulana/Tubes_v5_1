package am.aksanmaulana.gmail.com.tubes_v5_1.fHome;


import android.app.Dialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

import am.aksanmaulana.gmail.com.tubes_v5_1.R;
import am.aksanmaulana.gmail.com.tubes_v5_1.api.ApiClient;
import am.aksanmaulana.gmail.com.tubes_v5_1.api.ApiData;
import am.aksanmaulana.gmail.com.tubes_v5_1.api.ApiInterface;
import am.aksanmaulana.gmail.com.tubes_v5_1.model.DuitKu;
import am.aksanmaulana.gmail.com.tubes_v5_1.model.OrderList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetilsOrderListFragment extends Fragment {

    private TextView tvNameProduct, tvAmountOrder, tvDateOrder, tvPayMethode, tvStatusPay, tvOrderMessage;
    private TextView tvNameUser, tvAddressUser, tvPhoneUser, tvDataPemesanan;
    private TextView tvIdOrder, tvStatusOrder;

    private Button btnAccept, btnReject, btnCancel, btnComplete;
    private ImageView ivImageUser;

    private String fragmentName;

    private int idOrderProduct;

    private Bundle mBundle;
    private FragmentManager mFragmentManager;
    private FragmentTransaction mFragmentTransaction;

    ApiData<String> apiUpdateStatusOrder;

    public DetilsOrderListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_detils_order_list, container, false);

        idOrderProduct = getArguments().getInt("idOrderProduct");

        tvNameProduct = (TextView) v.findViewById(R.id.tvNameProduct);
        tvAmountOrder = (TextView) v.findViewById(R.id.tvAmountOrder);
        tvDateOrder = (TextView) v.findViewById(R.id.tvDateOrder);
        tvPayMethode = (TextView) v.findViewById(R.id.tvPayMethode);
        tvStatusPay = (TextView) v.findViewById(R.id.tvStatusPay);
        tvOrderMessage = (TextView) v.findViewById(R.id.tvOrderMessage);

        tvNameUser = (TextView) v.findViewById(R.id.tvNameUser);
        tvAddressUser = (TextView) v.findViewById(R.id.tvAddress);
        tvPhoneUser = (TextView) v.findViewById(R.id.tvPhone);
        tvDataPemesanan = (TextView) v.findViewById(R.id.tvDataPemesanan);
        ivImageUser = (ImageView) v.findViewById(R.id.ivImageUser);

        fragmentName = getArguments().getString("fragmentName");

        tvNameUser.setText(getArguments().getString("nameUser"));
        tvPhoneUser.setText("  " + getArguments().getString("phoneUser"));
        tvAddressUser.setText("Kirim ke : \n" + getArguments().getString("addressOrder"));

        tvNameProduct.setText(getArguments().getString("nameProduct"));
        tvAmountOrder.setText("Jumlah : " + getArguments().getString("amountOrder") + " " + getArguments().getString("satuanHarga"));
        tvPayMethode.setText("Pembayaran : " + getArguments().getString("payMethode"));
        tvOrderMessage.setText("Catatan Pemesanan : \n" + getArguments().getString("orderMessage"));
        tvDateOrder.setText("Tanggal : " + getArguments().getString("dateOrder"));

        if(getArguments().getString("statusPay").equals("Lunas")){
            tvStatusPay.setTextColor(Color.parseColor("#00DD21"));
        }else{
            tvStatusPay.setTextColor(Color.parseColor("#DD0000"));
        }
        tvStatusPay.setText("(status : " + getArguments().getString("statusPay") + ")");

        tvIdOrder = (TextView) v.findViewById(R.id.tvIdOrderProduct);
        tvStatusOrder = (TextView) v.findViewById(R.id.tvStatusOrder);

        tvIdOrder.setText("Id Pemesanan : " + getArguments().getInt("idOrderProduct"));
        Log.i("check", "idOrder = " + getArguments().getInt("idOrderProduct"));
        if(getArguments().getString("statusOrder").equals("Selesai")){
            tvStatusOrder.setTextColor(Color.parseColor("#00DD21"));
        }else if(getArguments().getString("statusOrder").equals("Dikirim")){
            tvStatusOrder.setTextColor(Color.parseColor("#0000EE"));
        }else if(getArguments().getString("statusOrder").equals("Menunggu")){
            tvStatusOrder.setTextColor(Color.parseColor("#FFAC00"));
        }else{
            tvStatusOrder.setTextColor(Color.parseColor("#FF0000"));
        }
        tvStatusOrder.setText(getArguments().getString("statusOrder"));




        btnAccept = (Button) v.findViewById(R.id.btnAccept);
        btnReject = (Button) v.findViewById(R.id.btnReject);
        btnCancel = (Button) v.findViewById(R.id.btnCancel);
        btnComplete = (Button) v.findViewById(R.id.btnComplete);

        if(fragmentName.equals("orderListFragment")){
            btnCancel.setVisibility(View.GONE);
            btnComplete.setVisibility(View.GONE);
        }else{
            btnAccept.setVisibility(View.GONE);
            btnReject.setVisibility(View.GONE);
            tvNameUser.setVisibility(View.GONE);
            tvPhoneUser.setVisibility(View.GONE);
            tvAddressUser.setVisibility(View.GONE);
            ivImageUser.setVisibility(View.GONE);
            tvDataPemesanan.setVisibility(View.GONE);
        }

        if(getArguments().getString("payMethode").equals("DuitKu")){
            btnCancel.setVisibility(View.GONE);
        }

        if(getArguments().getString("statusOrder").equals("Menunggu")){

        }else if(getArguments().getString("statusOrder").equals("Dikirim")){
            btnAccept.setVisibility(View.GONE);
            btnReject.setVisibility(View.GONE);
        }else{
            btnAccept.setVisibility(View.GONE);
            btnReject.setVisibility(View.GONE);
            btnCancel.setVisibility(View.GONE);
            btnComplete.setVisibility(View.GONE);
        }



        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                responseOrder("Dikirim");
            }
        });
        btnReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                responseOrder("Ditolak");
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                responseOrder("Dibatalkan");
            }
        });
        btnComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeStatusPay();
                responseOrder("Selesai");
            }
        });

        return v;
    }

    public void changeStatusPay(){
       AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = getActivity().getLayoutInflater().inflate(R.layout.progress_bar, null);
        builder.setView(view);
        builder.setCancelable(false);
        final Dialog dialog = builder.create();

        dialog.show();
        final Context c = getActivity();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ApiData<String>> call = apiService.updateStatusPay(idOrderProduct, "Lunas");
        call.enqueue(new Callback<ApiData<String>>() {
            @Override
            public void onResponse(@NonNull Call<ApiData<String>> call, @NonNull Response<ApiData<String>> response) {
                apiUpdateStatusOrder = response.body();
                Log.i("check", "api : " + apiUpdateStatusOrder);

                if (apiUpdateStatusOrder != null) {
                    if (apiUpdateStatusOrder.getStatus().equals("success")){
                        Log.i("check", "masuk sini");
                        dialog.dismiss();
                        //Toast.makeText(c, "Pembayaran selesai !", Toast.LENGTH_LONG).show();
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

    public void responseOrder(final String statusOrder){
        Log.i("check", "idproduct : " + idOrderProduct);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = getActivity().getLayoutInflater().inflate(R.layout.progress_bar, null);
        builder.setView(view);
        builder.setCancelable(false);
        final Dialog dialog = builder.create();

        dialog.show();
        final Context c = getActivity();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ApiData<String>> call = apiService.updateStatusOrder(idOrderProduct, statusOrder);
        call.enqueue(new Callback<ApiData<String>>() {
            @Override
            public void onResponse(@NonNull Call<ApiData<String>> call, @NonNull Response<ApiData<String>> response) {
                apiUpdateStatusOrder = response.body();
                Log.i("check", "api : " + apiUpdateStatusOrder);

                if (apiUpdateStatusOrder != null) {
                    if (apiUpdateStatusOrder.getStatus().equals("success")){
                        Log.i("check", "masuk sini");
                        dialog.dismiss();
                        Toast.makeText(c, "Pemesanan " + statusOrder + "!", Toast.LENGTH_LONG).show();

                        if(fragmentName.equals("orderListFragment")) {
                            OrderListFragment orderListFragment = new OrderListFragment();
                            mBundle = new Bundle();
                            //mBundle.putString("idUserUmkm", idUserUmkm);
                            //mBundle.putString("username", username);
                            mBundle.putInt("idBusiness", Integer.valueOf(getArguments().getString("idBusiness")));
                            orderListFragment.setArguments(mBundle);

                            mFragmentManager = getFragmentManager();
                            mFragmentTransaction = mFragmentManager
                                    .beginTransaction()
                                    .replace(R.id.content, orderListFragment, OrderListFragment.class.getSimpleName());
                            mFragmentTransaction.addToBackStack(null).commit();
                        }else{
                            ShopListFragment shopListFragment = new ShopListFragment();
                            mBundle = new Bundle();
                            shopListFragment.setArguments(mBundle);

                            mFragmentManager = getFragmentManager();
                            mFragmentTransaction = mFragmentManager
                                    .beginTransaction()
                                    .replace(R.id.content, shopListFragment, ShopListFragment.class.getSimpleName());
                            mFragmentTransaction.addToBackStack(null).commit();
                        }
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
/*
    ApiData<DuitKu> apiDuitKu;
    public void checkSaldo(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = getActivity().getLayoutInflater().inflate(R.layout.progress_bar, null);
        builder.setView(view);
        builder.setCancelable(false);
        final Dialog dialog = builder.create();

        dialog.show();

        final Context c = getActivity();
        final ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ApiData<DuitKu>> call = apiService.getDuitKuByIdUserUmkm(String.valueOf(idUserUmkm));
        call.enqueue(new Callback<ApiData<DuitKu>>() {
            @Override
            public void onResponse(@NonNull Call<ApiData<DuitKu>> call, @NonNull Response<ApiData<DuitKu>> response) {
                apiDuitKu = response.body();

                if (apiDuitKu.getStatus().equals("success")) {
                    if (apiDuitKu.getData() != null) {
                        balanceDuitKu = Double.valueOf(apiDuitKu.getData().getBalanceDuitKu());

                        // untuk pemakaian si DuitKu
                        myBalanceDuitKu = balanceDuitKu;
                        idDuitku = apiDuitKu.getData().getIdDuitKu();
                        // ============================================

                        KursRupiahConvert kursRupiahConvert = new KursRupiahConvert();
                        String balance = kursRupiahConvert.convertPrice(Double.valueOf(apiDuitKu.getData().getBalanceDuitKu()));

                        dialog.dismiss();

                        tvBalanceDuitKu.setTextColor(Color.parseColor("#0000AA"));
                        tvBalanceDuitKu.setText("Saldo DuitKu : " + balance);

                    }else{
                        dialog.dismiss();
                        radioButton = (RadioButton) getActivity().findViewById(R.id.rbDuitKu);
                        radioButton.setEnabled(false);
                    }

                    //Toast.makeText(c, "" + apiDuitKu.getData().getError(), Toast.LENGTH_LONG).show();

                }else{
                    dialog.dismiss();
                    //Log.i("check", ""+apiDuitKu.getData().getError());
                    //Toast.makeText(c, "" + apiDuitKu.getData().getError(), Toast.LENGTH_LONG).show();
                    //return;
                }
            }

            @Override
            public void onFailure(@NonNull Call<ApiData<DuitKu>> call, @NonNull Throwable t) {
                dialog.dismiss();
                Log.e(TAG, "onFailure: ", t);
                Toast.makeText(c, "connection error", Toast.LENGTH_LONG).show();
            }
        });
    }

    ApiData<DuitKu> apiDuitKuTujuan;
    public void getIdDuitKuTujuan(){

        final Context c = getActivity();
        final ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Log.i("check", "idtujuan = " + userUmkmTujuan);
        Call<ApiData<DuitKu>> call = apiService.getDuitKuByIdUserUmkm(userUmkmTujuan);
        call.enqueue(new Callback<ApiData<DuitKu>>() {
            @Override
            public void onResponse(@NonNull Call<ApiData<DuitKu>> call, @NonNull Response<ApiData<DuitKu>> response) {
                apiDuitKuTujuan = response.body();

                if (apiDuitKuTujuan.getStatus().equals("success")) {
                    if (apiDuitKuTujuan.getData() != null) {
                        //balanceDuitKu = Double.valueOf(apiDuitKu.getData().getBalanceDuitKu());

                        // untuk pemakaian si DuitKu
                        //myBalanceDuitKu = balanceDuitKu;
                        idDuitKuTujuan = apiDuitKuTujuan.getData().getIdDuitKu();
                        // ============================================


                    }else{
                        idDuitKuTujuan = null;
                        radioButton = (RadioButton) getActivity().findViewById(R.id.rbDuitKu);
                        radioButton.setEnabled(false);
                    }

                    //Toast.makeText(c, "" + apiDuitKu.getData().getError(), Toast.LENGTH_LONG).show();

                }else{
                    //Log.i("check", ""+apiDuitKu.getData().getError());
                    //Toast.makeText(c, "" + apiDuitKu.getData().getError(), Toast.LENGTH_LONG).show();
                    //return;
                }
            }

            @Override
            public void onFailure(@NonNull Call<ApiData<DuitKu>> call, @NonNull Throwable t) {
                Log.e(TAG, "onFailure: ", t);
                Toast.makeText(c, "connection error", Toast.LENGTH_LONG).show();
            }
        });
    }
*/
}
