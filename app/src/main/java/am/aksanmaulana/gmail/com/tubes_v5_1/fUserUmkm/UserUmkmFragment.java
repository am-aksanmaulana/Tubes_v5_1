package am.aksanmaulana.gmail.com.tubes_v5_1.fUserUmkm;


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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import am.aksanmaulana.gmail.com.tubes_v5_1.R;
import am.aksanmaulana.gmail.com.tubes_v5_1.adapter.AdapterUserUmkm;
import am.aksanmaulana.gmail.com.tubes_v5_1.api.ApiClient;
import am.aksanmaulana.gmail.com.tubes_v5_1.api.ApiData;
import am.aksanmaulana.gmail.com.tubes_v5_1.api.ApiInterface;
import am.aksanmaulana.gmail.com.tubes_v5_1.model.UserUmkm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserUmkmFragment extends Fragment {

    public String PrefName = "PREFS_LOGGED";
    private String TAG = UserUmkmFragment.class.getSimpleName();
    private String username, businessType;
    private int idUserUmkm;

    private UserUmkmFragment userUmkmFragment;
    private Bundle mBundle;
    private FragmentManager mFragmentManager;
    private FragmentTransaction mFragmentTransaction;

    private RecyclerView rvUserUmkm;
    private AdapterUserUmkm adapterUserUmkm;
    private List<UserUmkm> userUmkmList;
    private LinearLayoutManager mLayoutManager;

    private SharedPreferences sp;
    private SharedPreferences.Editor ed;

    ApiInterface apiInterface;
    private ApiData<List<UserUmkm>> apiUserUmkm;

    private TextView tvBusinessType;

    public UserUmkmFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_user_umkm, container, false);

        sp = getActivity().getSharedPreferences(PrefName, MODE_PRIVATE);
        username = sp.getString("username", "");
        idUserUmkm = sp.getInt("idUserUmkm", 0);
        businessType = getArguments().getString("businessType");

        tvBusinessType = (TextView) v.findViewById(R.id.tvBusinessType);
        if(businessType.equals("")){
            tvBusinessType.setText("Pelaku UMKM > Semua");
        }else{
            tvBusinessType.setText("Pelaku UMKM > " + businessType);
        }
        rvUserUmkm = (RecyclerView) v.findViewById(R.id.rvUserUmkm);
        userUmkmList = new ArrayList<UserUmkm>();

        refresh();
        adapterUserUmkm = new AdapterUserUmkm(getActivity(), userUmkmList);

        mLayoutManager = new LinearLayoutManager(getActivity());
        rvUserUmkm.setLayoutManager(mLayoutManager);
        rvUserUmkm.setAdapter(adapterUserUmkm);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rvUserUmkm.getContext(),
                mLayoutManager.getOrientation());
        rvUserUmkm.addItemDecoration(dividerItemDecoration);

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

        Call<ApiData<List<UserUmkm>>> call = apiService.getAllUserUmkmExcept(String.valueOf(idUserUmkm), businessType);
        call.enqueue(new Callback<ApiData<List<UserUmkm>>>() {
            @Override
            public void onResponse(@NonNull Call<ApiData<List<UserUmkm>>> call, @NonNull Response<ApiData<List<UserUmkm>>> response) {
                apiUserUmkm = response.body();

                if (apiUserUmkm != null) {
                    if (apiUserUmkm.getStatus().equals("success")){
                        dialog.dismiss();
                        userUmkmList.addAll(apiUserUmkm.getData());
                        adapterUserUmkm.notifyDataSetChanged();
                    }
                    else{
                        dialog.dismiss();
                        Toast.makeText(c, "Something might be wrong", Toast.LENGTH_LONG).show();
                        return;
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ApiData<List<UserUmkm>>> call, @NonNull Throwable t) {
                dialog.dismiss();
                Log.e("check", "onFailure: ", t);
                Toast.makeText(c, "connection error", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // TODO Add your menu entries here
        inflater.inflate(R.menu.user_umkm_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.mAllType:
                userUmkmFragment = new UserUmkmFragment();
                mBundle = new Bundle();
                mBundle.putString("businessType", "");
                userUmkmFragment.setArguments(mBundle);

                mFragmentManager = getFragmentManager();
                mFragmentTransaction = mFragmentManager
                        .beginTransaction()
                        .replace(R.id.content, userUmkmFragment, UserUmkmFragment.class.getSimpleName());
                mFragmentTransaction.addToBackStack(null).commit();
                // Do Activity menu item stuff here
                return true;

            case R.id.mPertanian:
                userUmkmFragment = new UserUmkmFragment();
                mBundle = new Bundle();
                mBundle.putString("businessType", "Pertanian");
                userUmkmFragment.setArguments(mBundle);

                mFragmentManager = getFragmentManager();
                mFragmentTransaction = mFragmentManager
                        .beginTransaction()
                        .replace(R.id.content, userUmkmFragment, UserUmkmFragment.class.getSimpleName());
                mFragmentTransaction.addToBackStack(null).commit();

                return true;

            case R.id.mPerdagangan:
                userUmkmFragment = new UserUmkmFragment();
                mBundle = new Bundle();
                mBundle.putString("businessType", "Perdagangan");
                userUmkmFragment.setArguments(mBundle);

                mFragmentManager = getFragmentManager();
                mFragmentTransaction = mFragmentManager
                        .beginTransaction()
                        .replace(R.id.content, userUmkmFragment, UserUmkmFragment.class.getSimpleName());
                mFragmentTransaction.addToBackStack(null).commit();

                return true;

            case R.id.mManufaktur:
                userUmkmFragment = new UserUmkmFragment();
                mBundle = new Bundle();
                mBundle.putString("businessType", "Manufaktur");
                userUmkmFragment.setArguments(mBundle);

                mFragmentManager = getFragmentManager();
                mFragmentTransaction = mFragmentManager
                        .beginTransaction()
                        .replace(R.id.content, userUmkmFragment, UserUmkmFragment.class.getSimpleName());
                mFragmentTransaction.addToBackStack(null).commit();

                return true;

            default:
                break;
        }

        return false;
    }

}
