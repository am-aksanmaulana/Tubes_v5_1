package am.aksanmaulana.gmail.com.tubes_v5_1.fHome;


import android.app.Dialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import am.aksanmaulana.gmail.com.tubes_v5_1.R;
import am.aksanmaulana.gmail.com.tubes_v5_1.api.ApiClient;
import am.aksanmaulana.gmail.com.tubes_v5_1.api.ApiData;
import am.aksanmaulana.gmail.com.tubes_v5_1.api.ApiInterface;
import am.aksanmaulana.gmail.com.tubes_v5_1.helper.GenerateRandom;
import am.aksanmaulana.gmail.com.tubes_v5_1.helper.KursRupiahConvert;
import am.aksanmaulana.gmail.com.tubes_v5_1.model.DuitKu;
import am.aksanmaulana.gmail.com.tubes_v5_1.model.OrderList;
import am.aksanmaulana.gmail.com.tubes_v5_1.model.UserUmkm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private Button btnActivation, btnTopUp, btnTransfer;
    private Button btnShop, btnOrderList, btnShopList, btnTransaction;
    private TextView tvIdDuitKu, tvBalance;

    public String PrefName = "PREFS_LOGGED";
    private static final String TAG = HomeFragment.class.getSimpleName();

    private Bundle mBundle;
    private FragmentManager mFragmentManager;
    private FragmentTransaction mFragmentTransaction;

    private SharedPreferences sp;
    private SharedPreferences.Editor ed;

    private int idUserUmkm, idBusiness;
    private String idDuitKu;


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        sp = getActivity().getSharedPreferences(PrefName, Context.MODE_PRIVATE);
        idUserUmkm = sp.getInt("idUserUmkm", 0);
        idBusiness = getArguments().getInt("idBusiness");

        tvIdDuitKu = (TextView) v.findViewById(R.id.tvIdDuitKu);
        tvBalance = (TextView) v.findViewById(R.id.tvBalance);


        btnActivation = (Button) v.findViewById(R.id.btnActivation);
        btnTopUp = (Button) v.findViewById(R.id.btnTopUp);

        checkDuitKu();


        btnOrderList = (Button) v.findViewById(R.id.btnOrderList);
        btnOrderList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrderListFragment orderListFragment = new OrderListFragment();
                mBundle = new Bundle();
                //mBundle.putString("idUserUmkm", idUserUmkm);
                //mBundle.putString("username", username);
                mBundle.putInt("idBusiness", idBusiness);
                orderListFragment.setArguments(mBundle);

                mFragmentManager = getFragmentManager();
                mFragmentTransaction = mFragmentManager
                        .beginTransaction()
                        .replace(R.id.content, orderListFragment, OrderListFragment.class.getSimpleName());
                mFragmentTransaction.addToBackStack(null).commit();
            }
        });
        btnShopList = (Button) v.findViewById(R.id.btnShopList);
        btnShopList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShopListFragment shopListFragment = new ShopListFragment();
                mBundle = new Bundle();
                shopListFragment.setArguments(mBundle);

                mFragmentManager = getFragmentManager();
                mFragmentTransaction = mFragmentManager
                        .beginTransaction()
                        .replace(R.id.content, shopListFragment, ShopListFragment.class.getSimpleName());
                mFragmentTransaction.addToBackStack(null).commit();
            }
        });

        btnShop = (Button) v.findViewById(R.id.btnShopProduct);
        btnShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BuyProductFragment buyProductFragment = new BuyProductFragment();
                mBundle = new Bundle();
                buyProductFragment.setArguments(mBundle);

                mFragmentManager = getFragmentManager();
                mFragmentTransaction = mFragmentManager
                        .beginTransaction()
                        .replace(R.id.content, buyProductFragment, BuyProductFragment.class.getSimpleName());
                mFragmentTransaction.addToBackStack(null).commit();
            }
        });

        btnActivation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activationDuitKu();

            }
        });

        btnTopUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TopUpFragment topUpFragment = new TopUpFragment();
                mBundle = new Bundle();
                mBundle.putInt("idUserUmkm", idUserUmkm);
                mBundle.putInt("idBusiness", idBusiness);
                mBundle.putString("idDuitKu", idDuitKu);

                topUpFragment.setArguments(mBundle);
                mFragmentManager = getFragmentManager();
                mFragmentTransaction = mFragmentManager
                        .beginTransaction()
                        .replace(R.id.content, topUpFragment, TopUpFragment.class.getSimpleName());
                mFragmentTransaction.addToBackStack(null).commit();
            }
        });

        return v;
    }

    private ApiData<DuitKu> apiDuitKu;

    public void checkDuitKu(){
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
                Log.i("check", "data : " + apiDuitKu.getData());

                if (apiDuitKu.getStatus().equals("success")) {
                    if (apiDuitKu.getData() != null) {
                        KursRupiahConvert kursRupiahConvert = new KursRupiahConvert();
                        String balance = kursRupiahConvert.convertPrice(Double.valueOf(apiDuitKu.getData().getBalanceDuitKu()));
                        idDuitKu = apiDuitKu.getData().getIdDuitKu();

                        dialog.dismiss();
                        tvIdDuitKu.setText(apiDuitKu.getData().getIdDuitKu());
                        tvBalance.setText(balance);
                        btnActivation.setVisibility(View.GONE);
                        btnTopUp.setVisibility(View.VISIBLE);

                    }else{
                        dialog.dismiss();
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

    public void activationDuitKu(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = getActivity().getLayoutInflater().inflate(R.layout.progress_bar, null);
        builder.setView(view);
        builder.setCancelable(false);
        final Dialog dialog = builder.create();

        dialog.show();

        GenerateRandom generateRandom = new GenerateRandom();
        String codeDuitKu = generateRandom.generateRandomString();

        final Context c = getActivity();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Log.i("check", "data code duitku " + codeDuitKu);
        Log.i("check", "data iduserumkm: " + idUserUmkm);
        Call<ApiData<DuitKu>> call = apiService.insertDuitKu(
            "" + codeDuitKu,
            "0",
            String.valueOf(idUserUmkm)
        );
        call.enqueue(new Callback<ApiData<DuitKu>>() {
            @Override
            public void onResponse(@NonNull Call<ApiData<DuitKu>> call, @NonNull Response<ApiData<DuitKu>> response) {
                apiDuitKu = response.body();

                if (apiDuitKu != null) {
                    if (apiDuitKu.getStatus().equals("success")){
                        dialog.dismiss();
                        Toast.makeText(c, "" + apiDuitKu.getData().getError(), Toast.LENGTH_LONG).show();
                        HomeFragment homeFragment = new HomeFragment();
                        mBundle = new Bundle();
                        //mBundle.putString("idUserUmkm", idUserUmkm);
                        //mBundle.putString("username", username);
                        homeFragment.setArguments(mBundle);

                        mFragmentManager = getFragmentManager();
                        mFragmentTransaction = mFragmentManager
                                .beginTransaction()
                                .replace(R.id.content, homeFragment, HomeFragment.class.getSimpleName());
                        mFragmentTransaction.addToBackStack(null).commit();
                    }
                    else{
                        dialog.dismiss();
                        Log.i("check", ""+apiDuitKu.getData().getError());
                        Toast.makeText(c, "" + apiDuitKu.getData().getError(), Toast.LENGTH_LONG).show();
                        return;
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ApiData<DuitKu>> call, @NonNull Throwable t) {
                //dialog.dismiss();
                Log.e(TAG, "onFailure: ", t);
                Toast.makeText(c, "connection error", Toast.LENGTH_LONG).show();
            }
        });
    }

}
