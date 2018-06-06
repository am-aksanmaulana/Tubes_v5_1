package am.aksanmaulana.gmail.com.tubes_v5_1.adapter;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import am.aksanmaulana.gmail.com.tubes_v5_1.R;
import am.aksanmaulana.gmail.com.tubes_v5_1.fUserUmkm.ProfileUserUmkmFragment;
import am.aksanmaulana.gmail.com.tubes_v5_1.model.UserUmkm;

public class AdapterUserUmkm extends RecyclerView.Adapter<AdapterUserUmkm.MyViewHolder>{

    private List<UserUmkm> userUmkmList;
    private int position;
    Context context;

    public AdapterUserUmkm(Context context, List<UserUmkm> data) {
        this.context = context;
        this.userUmkmList = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder (ViewGroup parent, int viewType){
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_user_umkm_list, parent, false);
        MyViewHolder mViewHolder = new MyViewHolder(mView);
        return mViewHolder;
    }

    @Override
    public void onBindViewHolder (MyViewHolder holder,final int position){
        String businessUser = "(Belum Mendaftarkan Usaha)";
        String businessType = "-";
        if(userUmkmList.get(position).getNameBusiness() != null){
            businessUser = userUmkmList.get(position).getNameBusiness();
        }
        if(userUmkmList.get(position).getBusinessType() != null){
            businessType = userUmkmList.get(position).getBusinessType();
        }

        holder.tvNameUser.setText("" + userUmkmList.get(position).getNameUser());
        holder.tvBusiness.setText("" + businessUser);
        holder.tvBusinessType.setText("" + businessType);

        if(userUmkmList.get(position).getGenderUser().equals("Laki-laki")){
            holder.ivImageUser.setImageResource(R.drawable.ic_man_profile);
        }else{
            holder.ivImageUser.setImageResource(R.drawable.ic_woman_profile);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle mBundle;
                FragmentManager mFragmentManager;
                FragmentTransaction mFragmentTransaction;

                ProfileUserUmkmFragment profileUserUmkmFragment = new ProfileUserUmkmFragment();
                mBundle = new Bundle();
                mBundle.putInt("idUserUmkm", userUmkmList.get(position).getIdUserUmkm());
                mBundle.putString("username", userUmkmList.get(position).getUsername());
                mBundle.putString("nameUser", userUmkmList.get(position).getNameUser());
                mBundle.putString("genderUser", userUmkmList.get(position).getGenderUser());
                mBundle.putString("addressUser", userUmkmList.get(position).getAddressUser());
                mBundle.putString("phoneUser", userUmkmList.get(position).getPhoneUser());
                mBundle.putString("emailUser", userUmkmList.get(position).getEmailUser());
                mBundle.putString("idBusiness", userUmkmList.get(position).getIdBusiness());
                Log.i("check", "idBusiness = " + userUmkmList.get(position).getIdBusiness());

                if(userUmkmList.get(position).getNameBusiness() == null){
                    mBundle.putString("nameBusiness", "(Belum Mendaftarkan Usaha)");
                }else{
                    mBundle.putString("nameBusiness", userUmkmList.get(position).getNameBusiness());
                }

                if(userUmkmList.get(position).getStartBusiness() == null){
                    mBundle.putString("startBusiness", "0000-00-00");
                }else{
                    mBundle.putString("startBusiness", userUmkmList.get(position).getStartBusiness());
                }

                if(userUmkmList.get(position).getBusinessType() == null){
                    mBundle.putString("businessType", "-");
                }else{
                    mBundle.putString("businessType", userUmkmList.get(position).getBusinessType());
                }

                if(userUmkmList.get(position).getLocationBusiness() == null){
                    mBundle.putString("locationBusiness", "-");
                }else{
                    mBundle.putString("locationBusiness", userUmkmList.get(position).getLocationBusiness());
                }

                profileUserUmkmFragment.setArguments(mBundle);
                mFragmentManager = ((Activity) context).getFragmentManager();
                mFragmentTransaction = mFragmentManager
                        .beginTransaction()
                        .replace(R.id.content, profileUserUmkmFragment, ProfileUserUmkmFragment.class.getSimpleName());
                mFragmentTransaction.addToBackStack(null).commit();
            }
        });
    }


    @Override
    public int getItemCount () {
        return userUmkmList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvNameUser, tvBusiness, tvBusinessType;
        public ImageView ivImageUser;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvNameUser = (TextView) itemView.findViewById(R.id.tvNameUser);
            tvBusiness = (TextView) itemView.findViewById(R.id.tvNameBusiness);
            tvBusinessType = (TextView) itemView.findViewById(R.id.tvBusinessType);
            ivImageUser = (ImageView) itemView.findViewById(R.id.ivImageUser);
        }


    }
}
