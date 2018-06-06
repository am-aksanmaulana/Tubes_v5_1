package am.aksanmaulana.gmail.com.tubes_v5_1.fHome;


import android.app.Dialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import am.aksanmaulana.gmail.com.tubes_v5_1.R;
import am.aksanmaulana.gmail.com.tubes_v5_1.api.ApiClient;
import am.aksanmaulana.gmail.com.tubes_v5_1.api.ApiData;
import am.aksanmaulana.gmail.com.tubes_v5_1.api.ApiInterface;
import am.aksanmaulana.gmail.com.tubes_v5_1.helper.KursRupiahConvert;
import am.aksanmaulana.gmail.com.tubes_v5_1.model.DuitKu;
import am.aksanmaulana.gmail.com.tubes_v5_1.model.OrderList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static java.lang.Double.parseDouble;

/**
 * A simple {@link Fragment} subclass.
 */
public class FormOrderProductFragment extends Fragment {

    private TextView tvNameProduct, tvPriceProduct, tvStockProduct, tvSatuanHarga, tvTotalPricena, tvBalanceDuitKu;
    private TextView tvNameBusiness, tvNameUser, tvLocationBusiness;
    private ImageView ivImageProduct;

    public String PrefName = "PREFS_LOGGED";
    private static final String TAG = FormOrderProductFragment.class.getSimpleName();

    private EditText etAmountOrder, etLocationOrder, etOrderMessage;
    private RadioGroup rgPayMethode;
    private RadioButton radioButton;
    private Button btnOrderProduct;

    private String nameProduct, priceProduct, stockProduct, satuanHarga;
    private String nameBusiness, nameUser, locationBusiness;

    private SharedPreferences sp;
    private SharedPreferences.Editor ed;

    private int idUserUmkm;
    private Double dblPrice, dblTotalPrice;

    private Bundle mBundle;
    private FragmentManager mFragmentManager;
    private FragmentTransaction mFragmentTransaction;

    ApiData<OrderList> apiOrderList;

    double dblTotalPricena, balanceDuitKu;

    private String idDuitku;
    private double myBalanceDuitKu;
    private String userUmkmTujuan,idDuitKuTujuan;

    private String statusPay="Belum Lunas";

    public FormOrderProductFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_form_order_product, container, false);

        sp = getActivity().getSharedPreferences(PrefName, Context.MODE_PRIVATE);
        idUserUmkm = sp.getInt("idUserUmkm", 0);
        userUmkmTujuan = getArguments().getString("userUmkmPenjual");

        checkSaldo();
        getIdDuitKuTujuan();

        tvNameProduct = (TextView) v.findViewById(R.id.tvNameProduct);
        tvPriceProduct = (TextView) v.findViewById(R.id.tvPriceProduct);
        tvStockProduct = (TextView) v.findViewById(R.id.tvStockProduct);
        rgPayMethode = (RadioGroup) v.findViewById(R.id.rgPayMethode);
        tvTotalPricena = (TextView) v.findViewById(R.id.tvTotalPricena);
        tvBalanceDuitKu = (TextView) v.findViewById(R.id.tvBalanceDuitKu);

        tvNameBusiness = (TextView) v.findViewById(R.id.tvNameBusiness);
        tvNameUser = (TextView) v.findViewById(R.id.tvNameUser);
        tvLocationBusiness = (TextView) v.findViewById(R.id.tvLocationBusiness);

        tvNameProduct.setText(getArguments().getString("nameProduct"));
        tvStockProduct.setText(getArguments().getString("stockProduct"));

        priceProduct = getArguments().getString("priceProduct");
        dblPrice = Double.valueOf(priceProduct);

        final KursRupiahConvert kursRupiahConvert = new KursRupiahConvert();
        priceProduct = kursRupiahConvert.convertPrice(Double.valueOf(priceProduct));
        satuanHarga = getArguments().getString("satuanHarga");
        tvPriceProduct.setText("Harga : " + priceProduct + " / " + satuanHarga);

        tvNameBusiness.setText(getArguments().getString("nameBusiness"));
        tvNameUser.setText(getArguments().getString("nameUser"));
        tvLocationBusiness.setText(getArguments().getString("locationBusiness"));

        ivImageProduct = (ImageView) v.findViewById(R.id.ivImageProduct);
        if(getArguments().getString("businessType").equals("Pertanian")){
            ivImageProduct.setImageResource(R.drawable.ic_product_agriculture);
        }else if(getArguments().getString("businessType").equals("Perdagangan")){
            ivImageProduct.setImageResource(R.drawable.ic_product_commerse);
        }else{
            ivImageProduct.setImageResource(R.drawable.ic_product_manufacture);
        }

        etAmountOrder = (EditText) v.findViewById(R.id.etAmountOrder);
        etLocationOrder = (EditText) v.findViewById(R.id.etAddresOrder);
        etOrderMessage = (EditText) v.findViewById(R.id.etOrderMessage);


        final TextWatcher mTextEditorWatcher = new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if(s.length() > 0){
                    dblTotalPricena = 0.0;
                    dblTotalPricena = parseDouble(s.toString()) * dblPrice;
                    String new_total = kursRupiahConvert.convertPrice(dblTotalPricena);
                    tvTotalPricena.setText(new_total);
                }
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //This sets a textview to the current length
            }

            public void afterTextChanged(Editable s) {
                if(s.length() > 0) {
                    dblTotalPricena = parseDouble(s.toString()) * dblPrice;
                    String new_total = kursRupiahConvert.convertPrice(dblTotalPricena);
                    tvTotalPricena.setText("Total Bayar : " + new_total);
                    if(dblTotalPricena > balanceDuitKu){
                        radioButton = (RadioButton) getActivity().findViewById(R.id.rbDuitKu);
                        radioButton.setEnabled(false);

                        tvBalanceDuitKu.setTextColor(Color.parseColor("#FF0000"));
                        tvBalanceDuitKu.setText("Saldo DuitKu tidak mencukupi");
                    }else{
                        tvTotalPricena.setText("Total Bayar : " + new_total);
                        radioButton = (RadioButton) getActivity().findViewById(R.id.rbDuitKu);
                        if(idDuitKuTujuan==null){
                            radioButton.setEnabled(false);
                        }else {
                            radioButton.setEnabled(true);
                        }

                        String balance = kursRupiahConvert.convertPrice(Double.valueOf(balanceDuitKu));
                        tvBalanceDuitKu.setTextColor(Color.parseColor("#0000AA"));
                        tvBalanceDuitKu.setText("Saldo DuitKu : " + balance);
                    }
                }else{
                    dblTotalPricena = 0.0;
                    String new_total = kursRupiahConvert.convertPrice(dblTotalPricena);
                    tvTotalPricena.setText("Total Bayar : " + new_total);


                    radioButton = (RadioButton) getActivity().findViewById(R.id.rbDuitKu);
                    if(idDuitKuTujuan==null){
                        radioButton.setEnabled(false);
                    }else {
                        radioButton.setEnabled(true);
                    }
                }
            }
        };

        etAmountOrder.addTextChangedListener(mTextEditorWatcher);


        btnOrderProduct = (Button) v.findViewById(R.id.btnProductOrder);
        btnOrderProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orderProduct();
            }
        });

        return v;
    }

    ApiData<String> apiTransfer;
    public void transferDuitKu(){
        Log.i("check", "id = " + idDuitku);
        Log.i("check", "idt = " + idDuitKuTujuan);
        Log.i("check", "jml = " + dblTotalPricena);
        final Context c = getActivity();
        final ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ApiData<String>> call = apiService.transferDuitKu(
                "" + idDuitku,
                "" + idDuitKuTujuan,
                "" + String.valueOf(dblTotalPricena)
        );
        call.enqueue(new Callback<ApiData<String>>() {
            @Override
            public void onResponse(@NonNull Call<ApiData<String>> call, @NonNull Response<ApiData<String>> response) {
                apiTransfer = response.body();
                Log.i("check", "statusna = " + apiTransfer.getData());
                if (apiTransfer.getStatus().equals("success")) {
                    if (apiTransfer.getData().equals("Berhasil Transfer")) {
                        statusPay = "Lunas";
                    }else{

                    }

                    //Toast.makeText(c, "" + apiDuitKu.getData().getError(), Toast.LENGTH_LONG).show();

                }else{
                    //Log.i("check", ""+apiDuitKu.getData().getError());
                    //Toast.makeText(c, "" + apiDuitKu.getData().getError(), Toast.LENGTH_LONG).show();
                    //return;
                }
            }

            @Override
            public void onFailure(@NonNull Call<ApiData<String>> call, @NonNull Throwable t) {
                Log.e(TAG, "onFailure: ", t);
                Toast.makeText(c, "connection error", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void orderProduct(){
        String amount, address, message, payMethode;

        int selectedId = rgPayMethode.getCheckedRadioButtonId();
        // mencari radio button
        radioButton = (RadioButton) getActivity().findViewById(selectedId);

        amount = etAmountOrder.getText().toString();
        address = etLocationOrder.getText().toString();
        message = etOrderMessage.getText().toString();
        payMethode = radioButton.getText().toString();

        if(radioButton.getText().toString().equals("DuitKu")){
            transferDuitKu();
            statusPay = "Lunas";
        }
        Log.i("check", "status pay : " + statusPay);

        dblTotalPrice = dblPrice * Double.valueOf(amount);

        if (TextUtils.isEmpty(amount)) {
            Toast.makeText(getActivity(), "Masukkan Jumlah Pemesanan!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(address)) {
            Toast.makeText(getActivity(), "Masukkan Alamat Pengiriman!", Toast.LENGTH_SHORT).show();
            return;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = getActivity().getLayoutInflater().inflate(R.layout.progress_bar, null);
        builder.setView(view);
        builder.setCancelable(false);
        final Dialog dialog = builder.create();

        //checking if email and passwords are empty
        dialog.show();
        final Context c = getActivity();
        final ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Log.i("check", "usrumkm = " + idUserUmkm);
        Log.i("check", "product = " + getArguments().getString("idProduct"));

        Call<ApiData<OrderList>> call = apiService.insertOrderProduct(
                "" + getArguments().getString("idProduct"),
                "" + String.valueOf(idUserUmkm),
                "" + amount,
                "" + String.valueOf(dblTotalPrice),
                "" + payMethode,
                "" + statusPay,
                "Menunggu",
                "" + message,
                "" + address
        );

        call.enqueue(new Callback<ApiData<OrderList>>() {
            @Override
            public void onResponse(@NonNull Call<ApiData<OrderList>> call, @NonNull Response<ApiData<OrderList>> response) {
                apiOrderList = response.body();
                Log.i("check", "informasi : " + apiOrderList);

                if (apiOrderList != null) {
                    if (apiOrderList.getStatus().equals("success")){
                        if(apiOrderList.getData() != null) {
                            dialog.dismiss();

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
            public void onFailure(@NonNull Call<ApiData<OrderList>> call, @NonNull Throwable t) {
                dialog.dismiss();
                Log.e("check", "onFailure: ", t);
                Toast.makeText(c, "connection error", Toast.LENGTH_LONG).show();
            }
        });

    }

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


}
