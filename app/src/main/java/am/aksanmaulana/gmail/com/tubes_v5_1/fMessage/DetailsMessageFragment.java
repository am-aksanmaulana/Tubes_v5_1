package am.aksanmaulana.gmail.com.tubes_v5_1.fMessage;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import am.aksanmaulana.gmail.com.tubes_v5_1.R;
import am.aksanmaulana.gmail.com.tubes_v5_1.fUserUmkm.SendMessageFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailsMessageFragment extends Fragment {

    private TextView tvFrom, tvDate, tvContent, tvStatusMessage;
    private String strFrom, strDate, strContent, titleMessage, strStatusMessage;
    private int idTujuan;
    private Button btnReply;

    public DetailsMessageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_details_message, container, false);

        idTujuan = Integer.valueOf(getArguments().getString("fromId"));
        titleMessage = getArguments().getString("titleMessage");
        strFrom = getArguments().getString("fromName");
        strDate = getArguments().getString("dateMessage");
        strContent = getArguments().getString("contentMessage");
        strStatusMessage = getArguments().getString("statusMessage");

        tvFrom = (TextView) v.findViewById(R.id.tvFrom);
        tvDate = (TextView) v.findViewById(R.id.tvDate);
        tvContent = (TextView) v.findViewById(R.id.tvContentMessage);
        tvStatusMessage = (TextView) v.findViewById(R.id.tvStatusMessage);
        btnReply = (Button) v.findViewById(R.id.btnReply);

        Log.i("check", "strIdTUJUAN : " +idTujuan);
        if(titleMessage.equals("Pesan Masuk")) {
            tvFrom.setText("dari : " + strFrom);
        }else{
            tvFrom.setText("ke : " + strFrom);
            btnReply.setVisibility(View.GONE);
        }
        tvDate.setText("Tanggal : " + strDate);
        tvContent.setText("" + strContent);
        tvStatusMessage.setText("Status : " + strStatusMessage);

        btnReply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendMessageFragment sendMessageFragment = new SendMessageFragment();
                Bundle mBundle = new Bundle();
                mBundle.putString("beforeMessage", strContent);
                mBundle.putString("nameTujuan", strFrom);
                mBundle.putInt("idUserTujuan", idTujuan);

                //mBundle.putString("username", username);
                Log.i("check", "username yang dilempar " + strFrom + " id = " + idTujuan);
                sendMessageFragment.setArguments(mBundle);

                FragmentManager mFragmentManager = getFragmentManager();
                FragmentTransaction mFragmentTransaction = mFragmentManager
                        .beginTransaction()
                        .replace(R.id.content, sendMessageFragment, SendMessageFragment.class.getSimpleName());
                mFragmentTransaction.addToBackStack(null).commit();
            }
        });

        return v;
    }

}
