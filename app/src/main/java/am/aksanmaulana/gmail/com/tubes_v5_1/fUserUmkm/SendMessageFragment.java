package am.aksanmaulana.gmail.com.tubes_v5_1.fUserUmkm;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import am.aksanmaulana.gmail.com.tubes_v5_1.R;
import am.aksanmaulana.gmail.com.tubes_v5_1.SignInActivity;
import am.aksanmaulana.gmail.com.tubes_v5_1.api.ApiClient;
import am.aksanmaulana.gmail.com.tubes_v5_1.api.ApiData;
import am.aksanmaulana.gmail.com.tubes_v5_1.api.ApiInterface;
import am.aksanmaulana.gmail.com.tubes_v5_1.fMessage.MessageFragment;
import am.aksanmaulana.gmail.com.tubes_v5_1.model.MessageKu;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class SendMessageFragment extends Fragment {

    public String PrefName = "PREFS_LOGGED";
    private static final String TAG = SendMessageFragment.class.getSimpleName();

    private String strNameUser, strContentMessage, strBeforeMessage;
    private int idUserTujuan, idUserUmkm, to;

    private SharedPreferences sp;
    private SharedPreferences.Editor ed;

    private EditText etTo, etContent;
    private Button btnSendMessage;

    private Bundle mBundle;
    private FragmentManager mFragmentManager;
    private FragmentTransaction mFragmentTransaction;

    private ApiData<MessageKu> apiMessageKu;
    private TextView tvMessage, tvIsiPesan, textView14;

    public SendMessageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_send_message, container, false);

        sp = getActivity().getSharedPreferences(PrefName, Context.MODE_PRIVATE);
        idUserUmkm = sp.getInt("idUserUmkm", 0);

        strNameUser = getArguments().getString("nameTujuan");
        strBeforeMessage = getArguments().getString("beforeMessage");
        idUserTujuan = getArguments().getInt("idUserTujuan");

        Log.i("check", "strTujuan = " + strNameUser);
        Log.i("check", "idTujuan = " + idUserTujuan);
        Log.i("check", "idUserUmkm = " + idUserUmkm);

        etTo = (EditText) v.findViewById(R.id.etTo);
        etContent = (EditText) v.findViewById(R.id.etContentMessage);
        btnSendMessage = (Button) v.findViewById(R.id.btnSendMessage);
        tvMessage = (TextView) v.findViewById(R.id.tvMessage);
        tvIsiPesan = (TextView) v.findViewById(R.id.tvIsiPesan);
        textView14 = (TextView) v.findViewById(R.id.textView14);

        if(strBeforeMessage == null){
            tvMessage.setVisibility(View.GONE);
            etTo.setText(strNameUser);
        }else{
            tvMessage.setText(strNameUser + " : \n " + strBeforeMessage);
            etTo.setVisibility(View.GONE);
            tvIsiPesan.setVisibility(View.GONE);
            textView14.setText("Balasan : ");
        }

        btnSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();

            }
        });


        return v;
    }

    public void sendMessage(){
        strContentMessage = etContent.getText().toString();

        //checking if email and passwords are empty
        if (TextUtils.isEmpty(strContentMessage)) {
            Toast.makeText(getActivity(), "Masukkan Isi Pesan!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(strNameUser)) {
            Toast.makeText(getActivity(), "Masukkan Tujuan!", Toast.LENGTH_SHORT).show();
            return;
        }

        final Context c = getActivity();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ApiData<MessageKu>> call = apiService.insertMessageKu(
                "" + String.valueOf(idUserUmkm),
                "" + String.valueOf(idUserTujuan),
                "" + strContentMessage,
                "Terkirim"
        );
        call.enqueue(new Callback<ApiData<MessageKu>>() {
            @Override
            public void onResponse(@NonNull Call<ApiData<MessageKu>> call, @NonNull Response<ApiData<MessageKu>> response) {
                apiMessageKu = response.body();
                Log.i("check", "data : " + apiMessageKu);

                if(apiMessageKu.getStatus().equals("success")){
                    Toast.makeText(c, "Berhasil Mengirim Pesan", Toast.LENGTH_LONG).show();

                    MessageFragment messageFragment = new MessageFragment();
                    mBundle = new Bundle();
                    mBundle.putString("titleMessage", "Pesan Keluar");
                    //mBundle.putString("username", username);
                    //Log.i("check", "username yang dilempar " + username + " id = " + idUserUmkm);
                    messageFragment.setArguments(mBundle);

                    mFragmentManager = getFragmentManager();
                    mFragmentTransaction = mFragmentManager
                            .beginTransaction()
                            .replace(R.id.content, messageFragment, MessageFragment.class.getSimpleName());
                    mFragmentTransaction.addToBackStack(null).commit();
                }

            }

            @Override
            public void onFailure(@NonNull Call<ApiData<MessageKu>> call, @NonNull Throwable t) {
                //dialog.dismiss();
                Log.e(TAG, "onFailure: ", t);
                Toast.makeText(c, "connection error", Toast.LENGTH_LONG).show();
            }
        });
    }

}
