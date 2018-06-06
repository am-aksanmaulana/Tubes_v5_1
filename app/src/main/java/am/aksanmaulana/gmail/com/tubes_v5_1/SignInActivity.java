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
import android.widget.TextView;
import android.widget.Toast;

import am.aksanmaulana.gmail.com.tubes_v5_1.api.ApiClient;
import am.aksanmaulana.gmail.com.tubes_v5_1.api.ApiData;
import am.aksanmaulana.gmail.com.tubes_v5_1.api.ApiInterface;
import am.aksanmaulana.gmail.com.tubes_v5_1.model.UserUmkm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignInActivity extends AppCompatActivity {

    public String PrefName = "PREFS_LOGGED";
    private static final String TAG = SignInActivity.class.getSimpleName();

    private TextView tvSignUp;
    private EditText etUsername, etPassword;
    private String strUsername, strPassword;
    private Button btnSignIn;

    private SharedPreferences sp;
    private SharedPreferences.Editor ed;

    private ApiData<UserUmkm> account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        etUsername = (EditText) findViewById(R.id.etUsername);
        etPassword = (EditText) findViewById(R.id.etPassword);

        sp = getSharedPreferences(PrefName, MODE_PRIVATE);
        final String logged = sp.getString("logged", "");
        Log.i("check", "logged = " + logged);

        if (logged.equals("LoggedIn")) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }

        tvSignUp = (TextView) findViewById(R.id.tvSignUp);
        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iSignUp = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(iSignUp);
            }
        });

        btnSignIn = (Button) findViewById(R.id.btnSignIn);
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

    }

    private void login(){
        strUsername = etUsername.getText().toString().trim();
        strPassword = etPassword.getText().toString().trim();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.progress_bar, null);
        builder.setView(view);
        builder.setCancelable(false);
        final Dialog dialog = builder.create();

        //checking if email and passwords are empty
        if (TextUtils.isEmpty(strUsername)) {
            Toast.makeText(this, "Masukkan Nama Pengguna!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(strPassword)) {
            Toast.makeText(this, "Masukkan Kata Sandi!", Toast.LENGTH_SHORT).show();
            return;
        }
        //Log.i("check", "username = " + strUsername + " password = " + strPassword);
        dialog.show();
        final Context c = this;
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ApiData<UserUmkm>> call = apiService.getAuth(strUsername, strPassword);
        call.enqueue(new Callback<ApiData<UserUmkm>>() {
            @Override
            public void onResponse(@NonNull Call<ApiData<UserUmkm>> call, @NonNull Response<ApiData<UserUmkm>> response) {
                account = response.body();
                Log.i("check", "data : " + account);

                if (account != null) {
                    if (account.getStatus().equals("success")){
                        dialog.dismiss();

                        ed = sp.edit();
                        ed.putString("username", strUsername);
                        ed.putInt("idUserUmkm", account.getData().getIdUserUmkm());
                        ed.putString("logged", "LoggedIn");
                        ed.apply();
                        Toast.makeText(c, ""+account.getData().getError(), Toast.LENGTH_LONG).show();
                        finish();
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    }
                    else{
                        dialog.dismiss();
                        Log.i("check", ""+account.getData().getError());
                        Toast.makeText(c, "" + account.getData().getError(), Toast.LENGTH_LONG).show();
                        return;
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ApiData<UserUmkm>> call, @NonNull Throwable t) {
                dialog.dismiss();
                Log.e(TAG, "onFailure: ", t);
                Toast.makeText(c, "connection error", Toast.LENGTH_LONG).show();
            }
        });
    }
}
