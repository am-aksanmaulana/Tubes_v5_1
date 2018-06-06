package am.aksanmaulana.gmail.com.tubes_v5_1.adapter;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import am.aksanmaulana.gmail.com.tubes_v5_1.R;
import am.aksanmaulana.gmail.com.tubes_v5_1.api.ApiClient;
import am.aksanmaulana.gmail.com.tubes_v5_1.api.ApiData;
import am.aksanmaulana.gmail.com.tubes_v5_1.api.ApiInterface;
import am.aksanmaulana.gmail.com.tubes_v5_1.fMessage.DetailsMessageFragment;
import am.aksanmaulana.gmail.com.tubes_v5_1.model.MessageKu;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterMessage extends RecyclerView.Adapter<AdapterMessage.MyViewHolder>{

    private List<MessageKu> messageKuList;
    Context context;
    String titleMessage;
    private ApiData<String> statusMessage;

    public AdapterMessage(Context context, List<MessageKu> data, String titleMessage) {
        this.context = context;
        this.messageKuList = data;
        this.titleMessage = titleMessage;
    }

    @Override
    public MyViewHolder onCreateViewHolder (ViewGroup parent, int viewType){
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_message, parent, false);
        MyViewHolder mViewHolder = new MyViewHolder(mView);
        return mViewHolder;
    }

    @Override
    public void onBindViewHolder (MyViewHolder holder,final int position){
        String newContent = messageKuList.get(position).getContentMessage();

        holder.tvNameUser.setText("" + messageKuList.get(position).getNameUser());
        if(messageKuList.get(position).getContentMessage().length() > 10){
            newContent = messageKuList.get(position).getContentMessage().substring(0,10) + " ...";
        }
        holder.tvContent.setText("" + newContent);
        holder.tvDate.setText("" + messageKuList.get(position).getDateMessage());

        Log.i("check", "status : " + messageKuList.get(position).getStatusMessage());
        if(messageKuList.get(position).getStatusMessage().equals("Terkirim")){
            if(titleMessage.equals("Pesan Keluar")){
                holder.ivStatus.setImageResource(R.drawable.ic_sent_status);
            }else {
                holder.ivStatus.setImageResource(R.drawable.ic_unread_status);
            }
        }else{
            if(titleMessage.equals("Pesan Keluar")){
                holder.ivStatus.setImageResource(R.drawable.ic_read_status2);
            }else {
                holder.ivStatus.setImageResource(R.drawable.ic_read_status);
            }
        }


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Context c =((Activity) context);
                ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
                Call<ApiData<String>> call=null;
                if(titleMessage.equals("Pesan Masuk")) {
                    call = apiService.updateStatusMessage(String.valueOf(messageKuList.get(position).getIdMessageKu()), "Dibaca");
                    call.enqueue(new Callback<ApiData<String>>() {
                        @Override
                        public void onResponse(@NonNull Call<ApiData<String>> call, @NonNull Response<ApiData<String>> response) {
                            statusMessage = response.body();
                            if (statusMessage.getStatus().equals("success")){
                                //dialog.dismiss();
                            }
                            else{
                                //dialog.dismiss();
                                Toast.makeText(c, "Something might be wrong", Toast.LENGTH_LONG).show();
                                return;
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<ApiData<String>> call, @NonNull Throwable t) {
                            //dialog.dismiss();
                            Log.e("check", "onFailure: ", t);
                            Toast.makeText(c, "connection error", Toast.LENGTH_LONG).show();
                        }
                    });

                }

                DetailsMessageFragment detailsMessageFragment = new DetailsMessageFragment();
                Bundle mBundle = new Bundle();
                mBundle.putString("titleMessage", titleMessage);
                mBundle.putString("fromId", messageKuList.get(position).getFromId());
                mBundle.putString("fromName", messageKuList.get(position).getNameUser());
                mBundle.putString("contentMessage", messageKuList.get(position).getContentMessage());
                mBundle.putString("dateMessage", messageKuList.get(position).getDateMessage());
                if(titleMessage.equals("Pesan Masuk")) {
                    mBundle.putString("statusMessage", "Dibaca");
                }else{
                    mBundle.putString("statusMessage", messageKuList.get(position).getStatusMessage());
                }
                //mBundle.putString("username", username);
                //Log.i("check", "username yang dilempar " + username + " id = " + idUserUmkm);
                detailsMessageFragment.setArguments(mBundle);

                FragmentManager mFragmentManager = ((Activity) context).getFragmentManager();
                FragmentTransaction mFragmentTransaction = mFragmentManager
                        .beginTransaction()
                        .replace(R.id.content, detailsMessageFragment, DetailsMessageFragment.class.getSimpleName());
                mFragmentTransaction.addToBackStack(null).commit();
            }
        });

        holder.itemView.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
            @Override
            public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                menu.add("Ubah Data").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        return true;
                    }
                });
                menu.add("Hapus Data").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        return true;
                    }
                });
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                PopupMenu popup = new PopupMenu(context, view);
                popup.getMenuInflater()
                        .inflate(R.menu.product_menu, popup.getMenu());

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.mEdit :
                                //Intent in = new Intent(mInflater.getContext(), AddMatkulActivity.class);
                                //mInflater.getContext().startActivity(in);
                                //Toast.makeText(LayoutInflater.getContext(), "edit "+holder.kode_mk, Toast.LENGTH_SHORT).show();
                                Toast.makeText(context, "edit ", Toast.LENGTH_SHORT).show();
                                return true;
                            case R.id.mDelete :
                                Toast.makeText(context, "hapus ", Toast.LENGTH_SHORT).show();
                                return true;
                            default:
                                return true;
                        }
                    }

                });
                popup.show(); //showing popup menu
                return true;
            }
        });
    }

    @Override
    public int getItemCount () {
        return messageKuList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvNameUser, tvContent, tvDate;
        public ImageView ivStatus;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvNameUser = (TextView) itemView.findViewById(R.id.tvNameUser);
            tvContent = (TextView) itemView.findViewById(R.id.tvContentMessage);
            tvDate = (TextView) itemView.findViewById(R.id.tvDate);
            ivStatus = (ImageView) itemView.findViewById(R.id.ivStatusMessage);
        }
    }
}