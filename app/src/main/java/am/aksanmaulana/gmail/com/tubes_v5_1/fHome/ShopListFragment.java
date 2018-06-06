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
import am.aksanmaulana.gmail.com.tubes_v5_1.adapter.AdapterOrderList;
import am.aksanmaulana.gmail.com.tubes_v5_1.api.ApiClient;
import am.aksanmaulana.gmail.com.tubes_v5_1.api.ApiData;
import am.aksanmaulana.gmail.com.tubes_v5_1.api.ApiInterface;
import am.aksanmaulana.gmail.com.tubes_v5_1.model.OrderList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShopListFragment extends Fragment {

    public String PrefName = "PREFS_LOGGED";
    private String TAG = OrderListFragment.class.getSimpleName();
    private int idUserUmkm, idBusiness;

    private Bundle mBundle;
    private FragmentManager mFragmentManager;
    private FragmentTransaction mFragmentTransaction;

    private RecyclerView rvOrderList;
    private AdapterOrderList adapterOrderList;
    private List<OrderList> orderListList;
    private LinearLayoutManager mLayoutManager;

    private SharedPreferences sp;
    private SharedPreferences.Editor ed;

    ApiInterface apiInterface;
    private ApiData<List<OrderList>> apiOrderList;


    public ShopListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_shop_list, container, false);

        sp = getActivity().getSharedPreferences(PrefName, MODE_PRIVATE);
        idUserUmkm = sp.getInt("idUserUmkm", 0);
        idBusiness = getArguments().getInt("idBusiness");
        Log.i("check", "idBuisness = " + idBusiness);

        rvOrderList = (RecyclerView) v.findViewById(R.id.rvShopList);
        orderListList = new ArrayList<OrderList>();

        refresh();
        adapterOrderList = new AdapterOrderList(getActivity(), orderListList, "shopListFragment");

        mLayoutManager = new LinearLayoutManager(getActivity());
        rvOrderList.setLayoutManager(mLayoutManager);
        rvOrderList.setAdapter(adapterOrderList);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rvOrderList.getContext(),
                mLayoutManager.getOrientation());
        rvOrderList.addItemDecoration(dividerItemDecoration);





        return v;
    }

    public void refresh() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = getActivity().getLayoutInflater().inflate(R.layout.progress_bar, null);
        builder.setView(view);
        builder.setCancelable(false);
        final Dialog dialog = builder.create();

        //checking if email and passwords are empty
        dialog.show();
        final Context c = getActivity();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<ApiData<List<OrderList>>> call = apiService.getShopListByIdUserWithJoin(idUserUmkm);
        call.enqueue(new Callback<ApiData<List<OrderList>>>() {
            @Override
            public void onResponse(@NonNull Call<ApiData<List<OrderList>>> call, @NonNull Response<ApiData<List<OrderList>>> response) {
                apiOrderList = response.body();

                if (apiOrderList != null) {
                    if (apiOrderList.getStatus().equals("success")){
                        if(apiOrderList.getData() != null) {
                            dialog.dismiss();
                            orderListList.addAll(apiOrderList.getData());
                            adapterOrderList.notifyDataSetChanged();
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
            public void onFailure(@NonNull Call<ApiData<List<OrderList>>> call, @NonNull Throwable t) {
                dialog.dismiss();
                Log.e("check", "onFailure: ", t);
                Toast.makeText(c, "connection error", Toast.LENGTH_LONG).show();
            }
        });
    }
}
