package am.aksanmaulana.gmail.com.tubes_v5_1.fHome;


import android.app.Dialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import am.aksanmaulana.gmail.com.tubes_v5_1.R;
import am.aksanmaulana.gmail.com.tubes_v5_1.adapter.AdapterBuyProduct;
import am.aksanmaulana.gmail.com.tubes_v5_1.adapter.AdapterProduct2;
import am.aksanmaulana.gmail.com.tubes_v5_1.api.ApiClient;
import am.aksanmaulana.gmail.com.tubes_v5_1.api.ApiData;
import am.aksanmaulana.gmail.com.tubes_v5_1.api.ApiInterface;
import am.aksanmaulana.gmail.com.tubes_v5_1.fUserUmkm.ProductUserUmkmFragment;
import am.aksanmaulana.gmail.com.tubes_v5_1.model.Product;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class BuyProductFragment extends Fragment {

    public String PrefName = "PREFS_LOGGED";
    private String TAG = BuyProductFragment.class.getSimpleName();
    private String nameBusiness;
    private int idUserUmkm, idBusiness;

    private Bundle mBundle;
    private FragmentManager mFragmentManager;
    private FragmentTransaction mFragmentTransaction;

    private RecyclerView rvProduct;
    private AdapterBuyProduct adapterBuyProduct;
    private List<Product> productList;
    private LinearLayoutManager mLayoutManager;

    private SharedPreferences sp;
    private SharedPreferences.Editor ed;


    ApiInterface apiInterface;
    private ApiData<List<Product>> apiProduct;

    public BuyProductFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_buy_product, container, false);

        rvProduct = (RecyclerView) v.findViewById(R.id.rvProductUmkm);
        productList = new ArrayList<Product>();

        refresh();
        adapterBuyProduct = new AdapterBuyProduct(getActivity(), productList);

        mLayoutManager = new LinearLayoutManager(getActivity());
        rvProduct.setLayoutManager(mLayoutManager);
        rvProduct.setAdapter(adapterBuyProduct);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rvProduct.getContext(),
                mLayoutManager.getOrientation());
        rvProduct.addItemDecoration(dividerItemDecoration);
        registerForContextMenu(rvProduct);



        return v;
    }

    public void refresh(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = getActivity().getLayoutInflater().inflate(R.layout.progress_bar, null);
        builder.setView(view);
        builder.setCancelable(false);
        final Dialog dialog = builder.create();

        //checking if email and passwords are empty
        //dialog.show();
        final Context c = getActivity();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ApiData<List<Product>>> call = apiService.getAllProduct();
        call.enqueue(new Callback<ApiData<List<Product>>>() {
            @Override
            public void onResponse(@NonNull Call<ApiData<List<Product>>> call, @NonNull Response<ApiData<List<Product>>> response) {
                apiProduct = response.body();

                if (apiProduct != null) {
                    if (apiProduct.getStatus().equals("success")){
                        Log.i("check", "masuk sini");
                        dialog.dismiss();
                        productList.addAll(apiProduct.getData());
                        adapterBuyProduct.notifyDataSetChanged();
                    }
                    else{
                        dialog.dismiss();
                        Toast.makeText(c, "Something might be wrong", Toast.LENGTH_LONG).show();
                        return;
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ApiData<List<Product>>> call, @NonNull Throwable t) {
                dialog.dismiss();
                Log.e("check", "onFailure: ", t);
                Toast.makeText(c, "connection error", Toast.LENGTH_LONG).show();
            }
        });
    }

}
