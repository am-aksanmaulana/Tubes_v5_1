package am.aksanmaulana.gmail.com.tubes_v5_1.fHome;


import android.app.FragmentManager;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import am.aksanmaulana.gmail.com.tubes_v5_1.R;
import am.aksanmaulana.gmail.com.tubes_v5_1.fUserUmkm.UserUmkmFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class TransferDuitKuFragment extends Fragment {


    public TransferDuitKuFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_transfer_duit_ku, container, false);

        Button btnTransfer = (Button) v.findViewById(R.id.btnTransfer);
        btnTransfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    //FragmentManager manager = getFragmentManager();

                    //UserUmkmFragment dialog = new UserUmkmFragment();
                    //dialog.show(manager, "dialog");

            }
        });




        return v;
    }

}
