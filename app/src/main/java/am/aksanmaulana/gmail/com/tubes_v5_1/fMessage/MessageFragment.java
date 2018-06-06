package am.aksanmaulana.gmail.com.tubes_v5_1.fMessage;


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
import am.aksanmaulana.gmail.com.tubes_v5_1.adapter.AdapterMessage;
import am.aksanmaulana.gmail.com.tubes_v5_1.api.ApiClient;
import am.aksanmaulana.gmail.com.tubes_v5_1.api.ApiData;
import am.aksanmaulana.gmail.com.tubes_v5_1.api.ApiInterface;
import am.aksanmaulana.gmail.com.tubes_v5_1.model.MessageKu;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class MessageFragment extends Fragment {

    public String PrefName = "PREFS_LOGGED";
    private String TAG = MessageFragment.class.getSimpleName();
    private String username, businessType;
    private int idUserUmkm;

    private Bundle mBundle;
    private FragmentManager mFragmentManager;
    private FragmentTransaction mFragmentTransaction;

    private RecyclerView rvMessage;
    private AdapterMessage adapterMessage;
    private List<MessageKu> messageKuList;
    private LinearLayoutManager mLayoutManager;

    private SharedPreferences sp;
    private SharedPreferences.Editor ed;

    private TextView tvTitle;
    private String titleMessage;

    ApiInterface apiInterface;
    private ApiData<List<MessageKu>> apiMessageKu;

    public MessageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_message, container, false);

        sp = getActivity().getSharedPreferences(PrefName, MODE_PRIVATE);
        username = sp.getString("username", "");
        idUserUmkm = sp.getInt("idUserUmkm", 0);
        titleMessage = getArguments().getString("titleMessage");

        tvTitle = (TextView) v.findViewById(R.id.tvTitleMessage);
        tvTitle.setText(titleMessage);

        rvMessage = (RecyclerView) v.findViewById(R.id.rvMessage);
        messageKuList = new ArrayList<MessageKu>();

        refresh();
        adapterMessage = new AdapterMessage(getActivity(), messageKuList, titleMessage);

        mLayoutManager = new LinearLayoutManager(getActivity());
        rvMessage.setLayoutManager(mLayoutManager);
        rvMessage.setAdapter(adapterMessage);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rvMessage.getContext(),
                mLayoutManager.getOrientation());
        rvMessage.addItemDecoration(dividerItemDecoration);


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
        Call<ApiData<List<MessageKu>>> call;
        if(titleMessage.equals("Pesan Masuk")) {
            call = apiService.getInboxByIdUserUmkm(String.valueOf(idUserUmkm));
        }else{
            call = apiService.getSentByIdUserUmkm(String.valueOf(idUserUmkm));
        }
        call.enqueue(new Callback<ApiData<List<MessageKu>>>() {
            @Override
            public void onResponse(@NonNull Call<ApiData<List<MessageKu>>> call, @NonNull Response<ApiData<List<MessageKu>>> response) {
                apiMessageKu = response.body();

                if (apiMessageKu != null) {
                    if (apiMessageKu.getStatus().equals("success")){
                        dialog.dismiss();
                        messageKuList.addAll(apiMessageKu.getData());
                        adapterMessage.notifyDataSetChanged();
                    }
                    else{
                        dialog.dismiss();
                        Toast.makeText(c, "Something might be wrong", Toast.LENGTH_LONG).show();
                        return;
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ApiData<List<MessageKu>>> call, @NonNull Throwable t) {
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
        inflater.inflate(R.menu.message_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mInbox:
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

            case R.id.mSent:
                MessageFragment messageFragment1 = new MessageFragment();
                mBundle = new Bundle();
                mBundle.putString("titleMessage", "Pesan Keluar");
                //mBundle.putString("username", username);
                //Log.i("check", "username yang dilempar " + username + " id = " + idUserUmkm);
                messageFragment1.setArguments(mBundle);

                mFragmentManager = getFragmentManager();
                mFragmentTransaction = mFragmentManager
                        .beginTransaction()
                        .replace(R.id.content, messageFragment1, MessageFragment.class.getSimpleName());
                mFragmentTransaction.addToBackStack(null).commit();

                return true;

            default:
                break;
        }

        return false;
    }

}
