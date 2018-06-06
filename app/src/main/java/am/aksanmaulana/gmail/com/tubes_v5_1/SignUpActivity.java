package am.aksanmaulana.gmail.com.tubes_v5_1;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import am.aksanmaulana.gmail.com.tubes_v5_1.api.ApiClient;
import am.aksanmaulana.gmail.com.tubes_v5_1.api.ApiData;
import am.aksanmaulana.gmail.com.tubes_v5_1.api.ApiInterface;
import am.aksanmaulana.gmail.com.tubes_v5_1.model.UserUmkm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {

    public String PrefName = "PREFS_LOGGED";
    private static final String TAG = SignUpActivity.class.getSimpleName();

    private SharedPreferences sp;
    private SharedPreferences.Editor ed;

    private EditText etUsername,etPassword, etNameUser, etAddressUser, etPhoneUser, etEmailUser;
    private Button btnRegister;
    private String username, password, nameUser, genderUser, addressUser, phoneUser, emailUser, imageUserna;
    private int imageUser;
    private RadioGroup rgGender;
    private RadioButton radioButton;

    private ApiData<UserUmkm> account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        sp = getSharedPreferences(PrefName, MODE_PRIVATE);

        rgGender = (RadioGroup) findViewById(R.id.rgGender);

        etUsername = (EditText) findViewById(R.id.etUsername);
        etPassword = (EditText) findViewById(R.id.etPassword);
        etNameUser = (EditText) findViewById(R.id.etNameUser);
        etAddressUser = (EditText) findViewById(R.id.etAddressUser);
        etPhoneUser = (EditText) findViewById(R.id.etPhone);
        etEmailUser = (EditText) findViewById(R.id.etEmail);

        btnRegister = (Button) findViewById(R.id.btnSignUp);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });

    }

    private void register(){
        int selectedId = rgGender.getCheckedRadioButtonId();
        // mencari radio button
        radioButton = (RadioButton) findViewById(selectedId);

        username = etUsername.getText().toString();
        password = etPassword.getText().toString();
        nameUser = etNameUser.getText().toString();
        addressUser = etAddressUser.getText().toString();
        phoneUser = etPhoneUser.getText().toString();
        emailUser = etEmailUser.getText().toString();
        genderUser = radioButton.getText().toString();
        if(genderUser.equals("Laki-laki")){
            imageUserna = "R.drawable.ic_man_profile";
        }else{
            imageUserna = "R.drawable.ic_woman_profile";
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.progress_bar, null);
        builder.setView(view);
        builder.setCancelable(false);
        final Dialog dialog = builder.create();

        //checking if email and passwords are empty
        if (TextUtils.isEmpty(username)) {
            Toast.makeText(this, "Masukkan Nama Pengguna!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Masukkan Kata Sandi!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(nameUser)) {
            Toast.makeText(this, "Masukkan Nama Lengkap!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(addressUser)) {
            Toast.makeText(this, "Masukkan Alamat!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(phoneUser)) {
            Toast.makeText(this, "Masukkan Nomor Telepon!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(emailUser)) {
            Toast.makeText(this, "Masukkan Email!", Toast.LENGTH_SHORT).show();
            return;
        }
        dialog.show();

        final Context c = this;
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ApiData<UserUmkm>> call = apiService.insertUser(
                ""+username,
                ""+password,
                ""+nameUser,
                ""+genderUser,
                ""+addressUser,
                ""+phoneUser,
                ""+emailUser,
                "Pelaku UMKM",
                ""+imageUserna
        );
        Log.i("check", "masuk");
        call.enqueue(new Callback<ApiData<UserUmkm>>() {
            @Override
            public void onResponse(@NonNull Call<ApiData<UserUmkm>> call, @NonNull Response<ApiData<UserUmkm>> response) {
                account = response.body();

                Log.i("check", "status + " + account);
                if(account != null) {
                    Log.i("check", "status + ");
                    if (account.getStatus().equals("success")){
                        dialog.dismiss();
                        ed = sp.edit();
                        ed.putString("username", username);
                        ed.putInt("idUserUmkm", account.getData().getIdUserUmkm());
                        ed.putString("logged", "LoggedIn");
                        ed.apply();
                        Toast.makeText(c, "" + account.getData().getError(), Toast.LENGTH_LONG).show();
                        finish();
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    }
                    else{
                        dialog.dismiss();
                        etUsername.requestFocus(0);
                        Toast.makeText(c, "" + account.getData().getError(), Toast.LENGTH_LONG).show();
                        return;
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ApiData<UserUmkm>> call, @NonNull Throwable t) {
                //dialog.dismiss();
                Log.e(TAG, "onFailure: ", t);
                Toast.makeText(c, "Koneksi Error", Toast.LENGTH_LONG).show();
            }
        });
    }
}
