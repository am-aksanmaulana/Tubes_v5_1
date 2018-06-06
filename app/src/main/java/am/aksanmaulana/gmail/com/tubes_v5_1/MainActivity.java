package am.aksanmaulana.gmail.com.tubes_v5_1;

import android.app.Dialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import am.aksanmaulana.gmail.com.tubes_v5_1.api.ApiClient;
import am.aksanmaulana.gmail.com.tubes_v5_1.api.ApiData;
import am.aksanmaulana.gmail.com.tubes_v5_1.api.ApiInterface;
import am.aksanmaulana.gmail.com.tubes_v5_1.fDuitKu.DuitKuFragment;
import am.aksanmaulana.gmail.com.tubes_v5_1.fHome.HomeFragment;
import am.aksanmaulana.gmail.com.tubes_v5_1.fMessage.MessageFragment;
import am.aksanmaulana.gmail.com.tubes_v5_1.fProfile.ProfileFragment;
import am.aksanmaulana.gmail.com.tubes_v5_1.fUserUmkm.UserUmkmFragment;
import am.aksanmaulana.gmail.com.tubes_v5_1.helper.BottomNavigationViewHelper;
import am.aksanmaulana.gmail.com.tubes_v5_1.model.UserUmkm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    public String PrefName = "PREFS_LOGGED";
    private static final String TAG = MainActivity.class.getSimpleName();

    private Bundle mBundle;
    private FragmentManager mFragmentManager;
    private FragmentTransaction mFragmentTransaction;

    private SharedPreferences sp;
    private SharedPreferences.Editor ed;

    private int idUserUmkm, idBusiness;
    private String username;
    private String nameUser, genderUser, addressUser, phoneUser, emailUser;
    private String nameBusiness, startBusiness, locationBusiness, businessType;

    private ApiData<UserUmkm> account;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navHome:
                    final Context c = getApplication();
                    ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
                    Call<ApiData<UserUmkm>> call = apiService.getByIdWithJoinBusiness(String.valueOf(idUserUmkm));

                    call.enqueue(new Callback<ApiData<UserUmkm>>() {
                        @Override
                        public void onResponse(@NonNull Call<ApiData<UserUmkm>> call, @NonNull Response<ApiData<UserUmkm>> response) {
                            account = response.body();

                            if(account != null) {
                                Log.i("check", "gender = " + account.getStatus());
                                if (account.getStatus().equals("success")){
                                    //Log.i("check", "g = " + genderUser);
                                    if(account.getData().getIdBusiness() != null) {
                                        idBusiness = Integer.valueOf(account.getData().getIdBusiness());
                                    }else{
                                        idBusiness = 0;
                                    }
                                    Log.i("check", "business = " + idBusiness);

                                    HomeFragment homeFragment = new HomeFragment();
                                    mBundle = new Bundle();
                                    mBundle.putInt("idUserUmkm", idUserUmkm);
                                    mBundle.putInt("idBusiness", idBusiness);

                                    homeFragment.setArguments(mBundle);
                                    mFragmentManager = getFragmentManager();
                                    mFragmentTransaction = mFragmentManager
                                            .beginTransaction()
                                            .replace(R.id.content, homeFragment, HomeFragment.class.getSimpleName());
                                    mFragmentTransaction.addToBackStack(null).commit();

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

                    return true;

                case R.id.navUserUmkm:
                    UserUmkmFragment userUmkmFragment = new UserUmkmFragment();
                    mBundle = new Bundle();
                    //mBundle.putString("idUserUmkm", idUserUmkm);
                    //mBundle.putString("username", username);
                    mBundle.putString("businessType", "");
                    userUmkmFragment.setArguments(mBundle);

                    mFragmentManager = getFragmentManager();
                    mFragmentTransaction = mFragmentManager
                            .beginTransaction()
                            .replace(R.id.content, userUmkmFragment, UserUmkmFragment.class.getSimpleName());
                    mFragmentTransaction.addToBackStack(null).commit();

                    return true;

                case R.id.navMessage:
                    MessageFragment messageFragment = new MessageFragment();
                    mBundle = new Bundle();
                    mBundle.putString("titleMessage", "Pesan Masuk");
                    //mBundle.putString("username", username);
                    //Log.i("check", "username yang dilempar " + username + " id = " + idUserUmkm);
                    messageFragment.setArguments(mBundle);

                    mFragmentManager = getFragmentManager();
                    mFragmentTransaction = mFragmentManager
                            .beginTransaction()
                            .replace(R.id.content, messageFragment, MessageFragment.class.getSimpleName());
                    mFragmentTransaction.addToBackStack(null).commit();

                    return true;
                case R.id.navProfile:
                    final Context c2 = getApplication();
                    ApiInterface apiService2 = ApiClient.getClient().create(ApiInterface.class);
                    Call<ApiData<UserUmkm>> call2 = apiService2.getByIdWithJoinBusiness(String.valueOf(idUserUmkm));

                    call2.enqueue(new Callback<ApiData<UserUmkm>>() {
                        @Override
                        public void onResponse(@NonNull Call<ApiData<UserUmkm>> call, @NonNull Response<ApiData<UserUmkm>> response) {
                            account = response.body();

                            if(account != null) {
                                Log.i("check", "gender = " + account.getStatus());
                                if (account.getStatus().equals("success")){
                                    nameUser = account.getData().getNameUser();
                                    genderUser = account.getData().getGenderUser();
                                    addressUser = account.getData().getAddressUser();
                                    phoneUser = account.getData().getPhoneUser();
                                    emailUser = account.getData().getEmailUser();
                                    //Log.i("check", "g = " + genderUser);
                                    if(account.getData().getIdBusiness() != null) {
                                        idBusiness = Integer.valueOf(account.getData().getIdBusiness());
                                        startBusiness = account.getData().getStartBusiness();
                                        locationBusiness = account.getData().getLocationBusiness();
                                        nameBusiness = account.getData().getNameBusiness();
                                        businessType = account.getData().getBusinessType();
                                    }else{

                                    }
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

                                    mBundle.putInt("idBusiness", idBusiness);
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

                                }
                                else{
                                    return;
                                }
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<ApiData<UserUmkm>> call, @NonNull Throwable t) {
                            Log.e(TAG, "onFailure: ", t);
                            Toast.makeText(c2, "Koneksi Error", Toast.LENGTH_LONG).show();
                        }
                    });

                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sp = getSharedPreferences(PrefName, MODE_PRIVATE);
        username = sp.getString("username", "");
        idUserUmkm = sp.getInt("idUserUmkm", 0);
        //Log.i("check", "username : " + sp.getString("username", ""));
        //Log.i("check", "username : " + sp.getInt("idUserUmkm", 0));

        final Context c = getApplication();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ApiData<UserUmkm>> call = apiService.getByIdWithJoinBusiness(String.valueOf(idUserUmkm));

        call.enqueue(new Callback<ApiData<UserUmkm>>() {
            @Override
            public void onResponse(@NonNull Call<ApiData<UserUmkm>> call, @NonNull Response<ApiData<UserUmkm>> response) {
                account = response.body();

                if(account != null) {
                    if (account.getStatus().equals("success")){
                        //Log.i("check", "g = " + genderUser);
                        if(account.getData().getIdBusiness() != null) {
                            idBusiness = Integer.valueOf(account.getData().getIdBusiness());
                        }else{
                            idBusiness = 0;
                        }
                        HomeFragment homeFragment = new HomeFragment();
                        mBundle = new Bundle();
                        mBundle.putInt("idUserUmkm", idUserUmkm);
                        mBundle.putInt("idBusiness", idBusiness);

                        homeFragment.setArguments(mBundle);
                        mFragmentManager = getFragmentManager();
                        mFragmentTransaction = mFragmentManager
                                .beginTransaction()
                                .replace(R.id.content, homeFragment, HomeFragment.class.getSimpleName());
                        mFragmentTransaction.addToBackStack(null).commit();

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

        //mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        BottomNavigationViewHelper.disableShiftMode(navigation);
    }

}
