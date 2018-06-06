package am.aksanmaulana.gmail.com.tubes_v5_1.fProfile;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import am.aksanmaulana.gmail.com.tubes_v5_1.R;
import am.aksanmaulana.gmail.com.tubes_v5_1.SignInActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    public String PrefName = "PREFS_LOGGED";

    private Bundle mBundle;
    private FragmentManager mFragmentManager;
    private FragmentTransaction mFragmentTransaction;

    private TextView tvUsername, tvName, tvGender, tvAddress, tvPhone, tvEmail;
    private TextView tvNameBusiness, tvStartBusiness, tvLocationBusiness, tvBusinessType;
    private int idUserUmkm, idBusiness;
    private String strUsername, strName, strGender, strAddress, strPhone, strEmail;
    private String strNameBusiness, strStartBusiness, strBusinessType, strLocationBusiness;
    private ImageView ivImage;

    private Button btnShowProduct, btnRegisterBusiness;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_profile, container, false);

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

        idBusiness = getArguments().getInt("idBusiness");
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

        if(strNameBusiness != null) {
            tvNameBusiness.setText(strNameBusiness);
            tvStartBusiness.setText(strStartBusiness);
            tvLocationBusiness.setText(strLocationBusiness);
            tvBusinessType.setText(strBusinessType);
        }else{
            tvNameBusiness.setText("-");
            tvStartBusiness.setText("-");
            tvLocationBusiness.setText("-");
            tvBusinessType.setText("(Belum Mendaftarkan Usaha)");
        }

        if(strGender.equals("Laki-laki")){
            ivImage.setImageResource(R.drawable.ic_man_profile);
        }else{
            ivImage.setImageResource(R.drawable.ic_woman_profile);
        }

        btnShowProduct = (Button) v.findViewById(R.id.btnShowProduct);
        btnRegisterBusiness = (Button) v.findViewById(R.id.btnRegisterBusiness);
        if(strNameBusiness != null){
            btnShowProduct.setVisibility(View.VISIBLE);
            btnRegisterBusiness.setVisibility(View.GONE);
        }

        btnRegisterBusiness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BusinessInformationFragment businessInformationFragment = new BusinessInformationFragment();
                mBundle = new Bundle();
                //mBundle.putInt("idBusiness", idBusiness);
                //mBundle.putInt("idUserUmkm", idUserUmkm);
                mBundle.putString("state", "register");
                businessInformationFragment.setArguments(mBundle);

                mFragmentManager = getFragmentManager();
                mFragmentTransaction = mFragmentManager
                        .beginTransaction()
                        .replace(R.id.content, businessInformationFragment, BusinessInformationFragment.class.getSimpleName());
                mFragmentTransaction.addToBackStack(null).commit();
            }
        });

        btnShowProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProductProfileFragment productProfileFragment = new ProductProfileFragment();
                mBundle = new Bundle();
                mBundle.putInt("idBusiness", idBusiness);
                mBundle.putString("nameBusiness", strNameBusiness);
                productProfileFragment.setArguments(mBundle);

                mFragmentManager = getFragmentManager();
                mFragmentTransaction = mFragmentManager
                        .beginTransaction()
                        .replace(R.id.content, productProfileFragment, ProductProfileFragment.class.getSimpleName());
                mFragmentTransaction.addToBackStack(null).commit();
            }
        });

        return v;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // TODO Add your menu entries here
        inflater.inflate(R.menu.profile_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);

        if(strNameBusiness != null) {
            menu.findItem(R.id.mChangeBusiness)
                    .setEnabled(true);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mChangeProfile:
                // Do Activity menu item stuff here
                EditProfileFragment editProfileFragment = new EditProfileFragment();
                mBundle = new Bundle();
                mBundle.putInt("idUserUmkm", idUserUmkm);
                mBundle.putString("nameUser", strName);
                mBundle.putString("genderUser", strGender);
                mBundle.putString("addressUser", strAddress);
                mBundle.putString("phoneUser", strPhone);
                mBundle.putString("emailUser", strEmail);
                editProfileFragment.setArguments(mBundle);

                mFragmentManager = getFragmentManager();
                mFragmentTransaction = mFragmentManager
                        .beginTransaction()
                        .replace(R.id.content, editProfileFragment, EditProfileFragment.class.getSimpleName());
                mFragmentTransaction.addToBackStack(null).commit();

                return true;

            case R.id.mChangeBusiness:

                BusinessInformationFragment businessInformationFragment = new BusinessInformationFragment();
                mBundle = new Bundle();
                mBundle.putInt("idBusiness", idBusiness);
                mBundle.putString("state", "edit");
                mBundle.putString("nameBusiness", strNameBusiness);
                mBundle.putString("locationBusiness", strLocationBusiness);
                mBundle.putString("startBusiness", strStartBusiness);
                mBundle.putString("businessType", strBusinessType);
                businessInformationFragment.setArguments(mBundle);

                mFragmentManager = getFragmentManager();
                mFragmentTransaction = mFragmentManager
                        .beginTransaction()
                        .replace(R.id.content, businessInformationFragment, BusinessInformationFragment.class.getSimpleName());
                mFragmentTransaction.addToBackStack(null).commit();

                return true;

            case R.id.mSignOut:
                SharedPreferences settings = getActivity().getSharedPreferences(PrefName, 0);
                SharedPreferences.Editor editor = settings.edit();
                editor.remove("logged");
                editor.commit();

                Intent intent = new Intent(getActivity(), SignInActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                getActivity().finish();
                // Do Activity menu item stuff here
                return true;

            default:
                break;
        }

        return false;
    }

}
