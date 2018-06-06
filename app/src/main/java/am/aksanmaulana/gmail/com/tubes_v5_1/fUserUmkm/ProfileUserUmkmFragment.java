package am.aksanmaulana.gmail.com.tubes_v5_1.fUserUmkm;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import am.aksanmaulana.gmail.com.tubes_v5_1.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileUserUmkmFragment extends Fragment {

    private TextView tvUsername, tvName, tvGender, tvAddress, tvPhone, tvEmail;
    private TextView tvNameBusiness, tvStartBusiness, tvLocationBusiness, tvBusinessType;
    private int idUserUmkm;
    private String idBusiness;
    private String strUsername, strName, strGender, strAddress, strPhone, strEmail;
    private String strNameBusiness, strStartBusiness, strBusinessType, strLocationBusiness;
    private ImageView ivImage;
    private ImageButton ibSendMessage, ibShowProduct;

    private Bundle mBundle;
    private FragmentManager mFragmentManager;
    private FragmentTransaction mFragmentTransaction;

    public ProfileUserUmkmFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_profile_user_umkm, container, false);

        tvUsername = (TextView) v.findViewById(R.id.tvUsername);
        tvName = (TextView) v.findViewById(R.id.tvName);
        tvGender = (TextView) v.findViewById(R.id.tvGender);
        tvAddress = (TextView) v.findViewById(R.id.tvAddress);
        tvPhone = (TextView) v.findViewById(R.id.tvPhone);
        tvEmail = (TextView) v.findViewById(R.id.tvEmail);
        ivImage = (ImageView) v.findViewById(R.id.ivImageUser);

        tvNameBusiness = (TextView) v.findViewById(R.id.tvNameBusiness);
        tvStartBusiness = (TextView) v.findViewById(R.id.tvStartBusiness);
        tvLocationBusiness = (TextView) v.findViewById(R.id.tvLocation);
        tvBusinessType = (TextView) v.findViewById(R.id.tvBusinessType);


        idUserUmkm = getArguments().getInt("idUserUmkm");
        strUsername = getArguments().getString("username");
        strName = getArguments().getString("nameUser");
        strGender = getArguments().getString("genderUser");
        strAddress = getArguments().getString("addressUser");
        strPhone = getArguments().getString("phoneUser");
        strEmail = getArguments().getString("emailUser");

        idBusiness = getArguments().getString("idBusiness");
        strNameBusiness = getArguments().getString("nameBusiness");
        strStartBusiness = getArguments().getString("startBusiness");
        strLocationBusiness = getArguments().getString("locationBusiness");
        strBusinessType = getArguments().getString("businessType");

        tvUsername.setText(strUsername);
        tvName.setText(strName);
        tvGender.setText(strGender);
        tvAddress.setText(strAddress);
        tvPhone.setText(strPhone);
        tvEmail.setText(strEmail);

        tvNameBusiness.setText(strNameBusiness);
        tvStartBusiness.setText(strStartBusiness);
        tvLocationBusiness.setText(strLocationBusiness);
        tvBusinessType.setText(strBusinessType);

        Log.i("check", ""+strBusinessType);
        if(strGender.equals("Laki-laki")){
            ivImage.setImageResource(R.drawable.ic_man_profile);
        }else{
            ivImage.setImageResource(R.drawable.ic_woman_profile);
        }

        ibSendMessage = (ImageButton) v.findViewById(R.id.ibSendMessage);
        ibSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendMessageFragment sendMessageFragment = new SendMessageFragment();
                mBundle = new Bundle();
                mBundle.putString("nameTujuan", strName);
                mBundle.putInt("idUserTujuan", idUserUmkm);

                //mBundle.putString("username", username);
                //Log.i("check", "username yang dilempar " + username + " id = " + idUserUmkm);
                sendMessageFragment.setArguments(mBundle);

                mFragmentManager = getFragmentManager();
                mFragmentTransaction = mFragmentManager
                        .beginTransaction()
                        .replace(R.id.content, sendMessageFragment, SendMessageFragment.class.getSimpleName());
                mFragmentTransaction.addToBackStack(null).commit();
            }
        });

        ibShowProduct = (ImageButton) v.findViewById(R.id.ibShowProduct);
        if(idBusiness==null){
            ibShowProduct.setVisibility(View.INVISIBLE);
        }else{
            ibShowProduct.setVisibility(View.VISIBLE);
        }
        ibShowProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProductUserUmkmFragment productUserUmkmFragment = new ProductUserUmkmFragment();
                mBundle = new Bundle();
                mBundle.putInt("idBusiness", Integer.valueOf(idBusiness));
                mBundle.putString("nameBusiness", strNameBusiness);
                productUserUmkmFragment.setArguments(mBundle);

                mFragmentManager = getFragmentManager();
                mFragmentTransaction = mFragmentManager
                        .beginTransaction()
                        .replace(R.id.content, productUserUmkmFragment, ProductUserUmkmFragment.class.getSimpleName());
                mFragmentTransaction.addToBackStack(null).commit();
            }
        });

        return v;
    }

}
