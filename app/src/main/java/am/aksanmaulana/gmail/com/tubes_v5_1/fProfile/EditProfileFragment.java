package am.aksanmaulana.gmail.com.tubes_v5_1.fProfile;


import android.app.Dialog;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import am.aksanmaulana.gmail.com.tubes_v5_1.R;
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
public class EditProfileFragment extends Fragment {

    public String PrefName = "PREFS_LOGGED";
    private static final String TAG = EditProfileFragment.class.getSimpleName();

    int check=0;
    EditText etNameUser, etAddressUser, etPhoneUser, etEmailUser;
    private String strUsername, strName, strGender, strAddress, strPhone, strEmail;
    Button btnUpdate, btnReset;
    Bundle mBundle;
    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;

    private SharedPreferences sp;
    private SharedPreferences.Editor ed;

    private int idUserUmkm;
    private String username;
    private String nameUser, genderUser, addressUser, phoneUser, emailUser;
    private String nameBusiness, startBusiness, locationBusiness, businessType;

    private RadioGroup rgGender;
    private RadioButton radioButton;

    private ApiData<UserUmkm> account;


    public EditProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_edit_profile, container, false);

        sp = getActivity().getSharedPreferences(PrefName, MODE_PRIVATE);
        username = sp.getString("username", "");
        idUserUmkm = sp.getInt("idUserUmkm", 0);

        etNameUser = (EditText) v.findViewById(R.id.etNameUser);
        etAddressUser = (EditText) v.findViewById(R.id.etAddressUser);
        etPhoneUser = (EditText) v.findViewById(R.id.etPhone);
        etEmailUser = (EditText) v.findViewById(R.id.etEmail);
        rgGender = (RadioGroup) v.findViewById(R.id.rgGender);

        strName = getArguments().getString("nameUser");
        strGender = getArguments().getString("genderUser");
        strAddress = getArguments().getString("addressUser");
        strPhone = getArguments().getString("phoneUser");
        strEmail = getArguments().getString("emailUser");

        etNameUser.setText(strName);
        etAddressUser.setText(strAddress);
        etEmailUser.setText(strEmail);
        etPhoneUser.setText(strPhone);

        btnReset = (Button) v.findViewById(R.id.btnReset);
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etNameUser.setText("");
                etAddressUser.setText("");
                etPhoneUser.setText("");
                etEmailUser.setText("");
            }
        });

        btnUpdate = (Button) v.findViewById(R.id.btnUpdate);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId = rgGender.getCheckedRadioButtonId();
                // mencari radio button
                radioButton = (RadioButton) v.findViewById(selectedId);

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                View view = getActivity().getLayoutInflater().inflate(R.layout.progress_bar, null);
                builder.setView(view);
                builder.setCancelable(false);
                final Dialog dialog = builder.create();

                strName = etNameUser.getText().toString();
                strAddress = etAddressUser.getText().toString();
                strEmail = etEmailUser.getText().toString();
                strPhone = etPhoneUser.getText().toString();
                //strGender = radioButton.getText().toString();

                if (TextUtils.isEmpty(strName)) {
                    Toast.makeText(getActivity(), "Masukkan Nama Lengkap!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(strAddress)) {
                    Toast.makeText(getActivity(), "Masukkan Alamat!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(strPhone)) {
                    Toast.makeText(getActivity(), "Masukkan Nomor Telepon!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(strEmail)) {
                    Toast.makeText(getActivity(), "Masukkan Email!", Toast.LENGTH_SHORT).show();
                    return;
                }


                dialog.show();
                final Context c = getActivity();
                ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
                Call<ApiData<UserUmkm>> call = apiService.updateUser(
                        String.valueOf(idUserUmkm),
                        "" + strName,
                        "" + strGender,
                        "" + strAddress,
                        "" + strPhone,
                        "" + strEmail);

                call.enqueue(new Callback<ApiData<UserUmkm>>() {
                    @Override
                    public void onResponse(@NonNull Call<ApiData<UserUmkm>> call, @NonNull Response<ApiData<UserUmkm>> response) {
                        account = response.body();

                        if(account != null) {
                            //Log.i("check", "gender = " + account.getStatus());
                            if (account.getStatus().equals("success")){
                                dialog.dismiss();

                                nameUser = account.getData().getNameUser();
                                genderUser = account.getData().getGenderUser();
                                addressUser = account.getData().getAddressUser();
                                phoneUser = account.getData().getPhoneUser();
                                emailUser = account.getData().getEmailUser();
                                //Log.i("check", "g = " + genderUser);
                                startBusiness = account.getData().getStartBusiness();
                                locationBusiness = account.getData().getLocationBusiness();
                                nameBusiness = account.getData().getNameBusiness();
                                businessType = account.getData().getBusinessType();
                                Log.i("check", "business = " + nameBusiness);

                                ProfileFragment profileFragment = new ProfileFragment();
                                mBundle = new Bundle();
                                mBundle.putInt("idUserUmkm", idUserUmkm);
                                mBundle.putString("username", username);
                                mBundle.putString("nameUser", nameUser);
                                mBundle.putString("genderUser", genderUser);
                                mBundle.putString("addressUser", addressUser);
                                mBundle.putString("phoneUser", phoneUser);
                                mBundle.putString("emailUser", emailUser);

                                mBundle.putString("nameBusiness", nameBusiness);
                                mBundle.putString("startBusiness", startBusiness);
                                mBundle.putString("businessType", businessType);
                                mBundle.putString("locationBusiness", locationBusiness);

                                profileFragment.setArguments(mBundle);
                                mFragmentManager = getFragmentManager();
                                mFragmentTransaction = mFragmentManager
                                        .beginTransaction()
                                        .replace(R.id.content, profileFragment, ProfileFragment.class.getSimpleName());
                                mFragmentTransaction.addToBackStack(null).commit();

                                Toast.makeText(c, "Informasi berhasil diubah!", Toast.LENGTH_LONG).show();
                            }
                            else{
                                return;
                            }
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<ApiData<UserUmkm>> call, @NonNull Throwable t) {
                        Log.e(TAG, "onFailure: ", t);
                        Toast.makeText(c, "Koneksi Error", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        return v;
    }

}
