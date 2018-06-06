package am.aksanmaulana.gmail.com.tubes_v5_1.fProfile;


import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import am.aksanmaulana.gmail.com.tubes_v5_1.MainActivity;
import am.aksanmaulana.gmail.com.tubes_v5_1.R;
import am.aksanmaulana.gmail.com.tubes_v5_1.api.ApiClient;
import am.aksanmaulana.gmail.com.tubes_v5_1.api.ApiData;
import am.aksanmaulana.gmail.com.tubes_v5_1.api.ApiInterface;
import am.aksanmaulana.gmail.com.tubes_v5_1.helper.DatePickerFragment;
import am.aksanmaulana.gmail.com.tubes_v5_1.model.UserUmkm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class BusinessInformationFragment extends Fragment {

    public String PrefName = "PREFS_LOGGED";
    private static final String TAG = BusinessInformationFragment.class.getSimpleName();

    private String state;
    private int idUserUmkm;

    private SharedPreferences sp;
    private SharedPreferences.Editor ed;

    Spinner spinBusinessType;
    private String[] businessType = {
            "Pertanian",
            "Perdagangan",
            "Manufaktur"
    };


    Bundle mBundle;
    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;

    String businessTypeSelected="";
    public static final String PREFS_NAME = "LoginPrefs";

    private EditText etNameBusiness, etStartBusiness, etLocationBusiness;
    private String strNameBusiness, strStartBusiness, strLocationBusiness;
    private Button btnSaveBusiness;

    private int idBusiness;
    private String username;
    private String nameUser, genderUser, addressUser, phoneUser, emailUser;
    private String nameBusiness, startBusiness, locationBusiness, strBusinessType;

    private ApiData<UserUmkm> apiUserUmkm;



    public BusinessInformationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_business_information, container, false);

        sp = getActivity().getSharedPreferences(PrefName, MODE_PRIVATE);
        idUserUmkm = sp.getInt("idUserUmkm", 0);
        username = sp.getString("username", "");
        idBusiness = getArguments().getInt("idBusiness");

        state = getArguments().getString("state");

// Untuk memilih jenis usaha ==============================================================================================
        spinBusinessType = (Spinner) v.findViewById(R.id.spinBusinessType);
        // inisialiasi Array Adapter dengan memasukkan string array di atas
        final ArrayAdapter<String> adapterBusinessType = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_dropdown_item, businessType);
        // mengeset Array Adapter tersebut ke Spinner
        spinBusinessType.setAdapter(adapterBusinessType);
        // mengeset listener untuk mengetahui saat item dipilih
        if(state.equals("edit")) {
            int spinnerPosition = adapterBusinessType.getPosition(getArguments().getString("businessType"));
            //set the default according to value
            spinBusinessType.setSelection(spinnerPosition);
        }

        spinBusinessType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                businessTypeSelected = adapterBusinessType.getItem(i);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                businessTypeSelected = adapterBusinessType.getItem(0);
            }
        });
// Untuk memilih Kalender ==============================================================================================
        Button btnShowDate = (Button) v.findViewById(R.id.btnCalender);
        btnShowDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment picker = new DatePickerFragment();
                picker.show(getFragmentManager(), "datePicker");
            }
        });

// Untuk inputan data yang lain ==============================================================================================
        etNameBusiness = (EditText) v.findViewById(R.id.etNameBusiness);
        etStartBusiness = (EditText) v.findViewById(R.id.etStartBusiness);
        etLocationBusiness = (EditText) v.findViewById(R.id.etLocationBusiness);
        btnSaveBusiness = (Button) v.findViewById(R.id.btnSaveBusiness);

        if(state.equals("edit")){
            etNameBusiness.setText(getArguments().getString("nameBusiness"));
            etStartBusiness.setText(getArguments().getString("startBusiness"));
            etLocationBusiness.setText(getArguments().getString("locationBusiness"));
            btnSaveBusiness.setText("Simpan Perubahan");
        }

        btnSaveBusiness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveBusiness();
            }
        });


        return v;
    }

    public void saveBusiness(){
        strNameBusiness = etNameBusiness.getText().toString();
        strStartBusiness = etStartBusiness.getText().toString();
        strLocationBusiness = etLocationBusiness.getText().toString();

        if (TextUtils.isEmpty(strNameBusiness)) {
            Toast.makeText(getActivity(), "Kolom Nama Usaha Harus Diisi!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(strStartBusiness)) {
            Toast.makeText(getActivity(), "Kolom Mulai Usaha Harus Diisi!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(strLocationBusiness)) {
            Toast.makeText(getActivity(), "Kolom Lokasi Usaha Harus Diisi!", Toast.LENGTH_SHORT).show();
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
        Call<ApiData<UserUmkm>> call = null;
        if(state.equals("register")){
             call = apiService.insertBusiness(
                    "" + strNameBusiness,
                    "" + strLocationBusiness,
                    "" + strStartBusiness,
                    "" + businessTypeSelected,
                    "" + String.valueOf(idUserUmkm)
             );
        }else{
            call = apiService.updateBusiness(
                    "" + String.valueOf(idBusiness),
                    "" + strNameBusiness,
                    "" + strLocationBusiness,
                    "" + strStartBusiness,
                    "" + businessTypeSelected,
                    "" + String.valueOf(idUserUmkm)
            );
        }


        call.enqueue(new Callback<ApiData<UserUmkm>>() {
            @Override
            public void onResponse(@NonNull Call<ApiData<UserUmkm>> call, @NonNull Response<ApiData<UserUmkm>> response) {
                apiUserUmkm = response.body();

                if (apiUserUmkm != null) {
                    if (apiUserUmkm.getStatus().equals("success")){
                        if(apiUserUmkm.getData() != null) {
                            dialog.dismiss();

                            nameUser = apiUserUmkm.getData().getNameUser();
                            genderUser = apiUserUmkm.getData().getGenderUser();
                            addressUser = apiUserUmkm.getData().getAddressUser();
                            phoneUser = apiUserUmkm.getData().getPhoneUser();
                            emailUser = apiUserUmkm.getData().getEmailUser();
                            //Log.i("check", "g = " + genderUser);
                            idBusiness = Integer.valueOf(apiUserUmkm.getData().getIdBusiness());
                            startBusiness = apiUserUmkm.getData().getStartBusiness();
                            locationBusiness = apiUserUmkm.getData().getLocationBusiness();
                            nameBusiness = apiUserUmkm.getData().getNameBusiness();
                            strBusinessType = apiUserUmkm.getData().getBusinessType();

                            ProfileFragment profileFragment = new ProfileFragment();
                            mBundle = new Bundle();
                            mBundle.putInt("idUserUmkm", idUserUmkm);
                            mBundle.putString("username", username);
                            mBundle.putString("nameUser", nameUser);
                            mBundle.putString("genderUser", genderUser);
                            mBundle.putString("addressUser", addressUser);
                            mBundle.putString("phoneUser", phoneUser);
                            mBundle.putString("emailUser", emailUser);

                            mBundle.putInt("idBusiness", idBusiness);
                            mBundle.putString("nameBusiness", nameBusiness);
                            mBundle.putString("startBusiness", startBusiness);
                            mBundle.putString("businessType", strBusinessType);
                            mBundle.putString("locationBusiness", locationBusiness);

                            profileFragment.setArguments(mBundle);
                            mFragmentManager = getFragmentManager();
                            mFragmentTransaction = mFragmentManager
                                    .beginTransaction()
                                    .replace(R.id.content, profileFragment, ProfileFragment.class.getSimpleName());
                            mFragmentTransaction.addToBackStack(null).commit();

                            if(state.equals("register")) {
                                Toast.makeText(c, "Berhasil Mendaftarkan Usaha!", Toast.LENGTH_LONG).show();
                            }else{
                                Toast.makeText(c, "Berhasil Mengubah Data Usaha!", Toast.LENGTH_LONG).show();
                            }
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
            public void onFailure(@NonNull Call<ApiData<UserUmkm>> call, @NonNull Throwable t) {
                dialog.dismiss();
                Log.e("check", "onFailure: ", t);
                Toast.makeText(c, "connection error", Toast.LENGTH_LONG).show();
            }
        });
    }

}
