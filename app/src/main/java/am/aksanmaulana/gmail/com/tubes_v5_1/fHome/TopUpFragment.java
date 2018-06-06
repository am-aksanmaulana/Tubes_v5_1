package am.aksanmaulana.gmail.com.tubes_v5_1.fHome;


import android.app.Dialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import am.aksanmaulana.gmail.com.tubes_v5_1.R;
import am.aksanmaulana.gmail.com.tubes_v5_1.api.ApiClient;
import am.aksanmaulana.gmail.com.tubes_v5_1.api.ApiData;
import am.aksanmaulana.gmail.com.tubes_v5_1.api.ApiInterface;
import am.aksanmaulana.gmail.com.tubes_v5_1.model.DuitKu;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class TopUpFragment extends Fragment {

    private String idDuitKu, balanceDuitKu, amountTopUp;
    private EditText etIdDuitKu, etAmount;
    private Button btnTopUp;

    ApiData<DuitKu> apiDuitKu;

    private int idUserUmkm, idBusiness;


    public TopUpFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_top_up, container, false);

        idDuitKu = getArguments().getString("idDuitKu");
        idUserUmkm = getArguments().getInt("idUserUmkm");
        idBusiness = getArguments().getInt("idBusiness");

        etIdDuitKu = (EditText) v.findViewById(R.id.etIdDuitKu);
        etAmount = (EditText) v.findViewById(R.id.etAmount);
        btnTopUp = (Button) v.findViewById(R.id.btnTopUp);

        btnTopUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                topUpProccess();
            }
        });

        etIdDuitKu.setText(idDuitKu);


        return v;
    }

    public void topUpProccess(){
        idDuitKu = etIdDuitKu.getText().toString();
        amountTopUp = etAmount.getText().toString();

        if (TextUtils.isEmpty(amountTopUp)) {
            Toast.makeText(getActivity(), "Masukkan Jumlah Top Up!", Toast.LENGTH_SHORT).show();
            return;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = getActivity().getLayoutInflater().inflate(R.layout.progress_bar, null);
        builder.setView(view);
        builder.setCancelable(false);
        final Dialog dialog = builder.create();

        //checking if email and passwords are empty
        //dialog.show();
        final Context c = getActivity();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ApiData<DuitKu>> call = apiService.topUpDuitKu(
                "" + idDuitKu,
                "" + amountTopUp
        );
        call.enqueue(new Callback<ApiData<DuitKu>>() {
            @Override
            public void onResponse(@NonNull Call<ApiData<DuitKu>> call, @NonNull Response<ApiData<DuitKu>> response) {
                apiDuitKu = response.body();

                if (apiDuitKu != null) {
                    if (apiDuitKu.getStatus().equals("success")){
                        //Log.i("check", "masuk sini");
                        dialog.dismiss();
                        Toast.makeText(c, "Top Up Berhasil!", Toast.LENGTH_LONG).show();

                        HomeFragment homeFragment = new HomeFragment();
                        Bundle mBundle = new Bundle();
                        mBundle.putInt("idUserUmkm", idUserUmkm);
                        mBundle.putInt("idBusiness", idBusiness);

                        homeFragment.setArguments(mBundle);
                        FragmentManager mFragmentManager = getFragmentManager();
                        FragmentTransaction mFragmentTransaction = mFragmentManager
                                .beginTransaction()
                                .replace(R.id.content, homeFragment, HomeFragment.class.getSimpleName());
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
            public void onFailure(@NonNull Call<ApiData<DuitKu>> call, @NonNull Throwable t) {
                dialog.dismiss();
                Log.e("check", "onFailure: ", t);
                Toast.makeText(c, "connection error", Toast.LENGTH_LONG).show();
            }
        });

    }

}
