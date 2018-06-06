package am.aksanmaulana.gmail.com.tubes_v5_1.fProfile;


import android.app.Dialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import am.aksanmaulana.gmail.com.tubes_v5_1.R;
import am.aksanmaulana.gmail.com.tubes_v5_1.api.ApiClient;
import am.aksanmaulana.gmail.com.tubes_v5_1.api.ApiData;
import am.aksanmaulana.gmail.com.tubes_v5_1.api.ApiInterface;
import am.aksanmaulana.gmail.com.tubes_v5_1.model.Product;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddProductFragment extends Fragment {

    private int idBusiness;
    private String strNameBusiness, idProduct;

    private EditText etNameProduct, etPriceProduct, etSatuanHarga;
    private Button btnInsertProduct;
    private RadioGroup rgStock;
    private RadioButton radioButton;

    private String nameProduct, priceProduct, satuanHarga, stockProduct;
    private String state;

    private Bundle mBundle;
    private FragmentManager mFragmentManager;
    private FragmentTransaction mFragmentTransaction;

    private ApiData<Product> apiProduct;

    public AddProductFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_add_product, container, false);

        idBusiness = getArguments().getInt("idBusiness");
        strNameBusiness = getArguments().getString("nameBusiness");
        state = getArguments().getString("state");
        Log.i("check", "idbusines = " + idBusiness);

        etNameProduct = (EditText) v.findViewById(R.id.etNameProduct);
        etPriceProduct = (EditText) v.findViewById(R.id.etPriceProduct);
        etSatuanHarga = (EditText) v.findViewById(R.id.etSatuanHarga);
        rgStock = (RadioGroup) v.findViewById(R.id.rgStock);

        btnInsertProduct = (Button) v.findViewById(R.id.btnInsertProduct);
        btnInsertProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertProduct();
            }
        });

        if(state.equals("edit")){
            etNameProduct.setText(getArguments().getString("nameProduct"));
            etPriceProduct.setText(getArguments().getString("priceProduct"));
            etSatuanHarga.setText(getArguments().getString("satuanHarga"));
            idProduct = getArguments().getString("idProduct");
        }

        return v;
    }

    public void insertProduct(){
        int selectedId = rgStock.getCheckedRadioButtonId();
        // mencari radio button
        radioButton = (RadioButton) getActivity().findViewById(selectedId);

        nameProduct = etNameProduct.getText().toString();
        priceProduct = etPriceProduct.getText().toString();
        satuanHarga = etSatuanHarga.getText().toString();
        stockProduct = radioButton.getText().toString();

        if (TextUtils.isEmpty(nameProduct)) {
            Toast.makeText(getActivity(), "Masukkan Nama Produk!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(priceProduct)) {
            Toast.makeText(getActivity(), "Masukkan Harga Produk!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(satuanHarga)) {
            Toast.makeText(getActivity(), "Masukkan Satuan Harga Produk!", Toast.LENGTH_SHORT).show();
            return;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = getActivity().getLayoutInflater().inflate(R.layout.progress_bar, null);
        builder.setView(view);
        builder.setCancelable(false);
        final Dialog dialog = builder.create();

        //checking if email and passwords are empty
        dialog.show();
        final Context c = getActivity();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ApiData<Product>> call = null;
        if(state.equals("insert")) {
            call = apiService.insertProduct(
                    "" + nameProduct,
                    "" + priceProduct,
                    "" + satuanHarga,
                    "" + stockProduct,
                    "" + String.valueOf(idBusiness)
            );
        }else{
            call = apiService.updateProduct(
                    "" + idProduct,
                    "" + nameProduct,
                    "" + priceProduct,
                    "" + satuanHarga,
                    "" + stockProduct,
                    "" + String.valueOf(idBusiness)
            );
        }
        call.enqueue(new Callback<ApiData<Product>>() {
            @Override
            public void onResponse(@NonNull Call<ApiData<Product>> call, @NonNull Response<ApiData<Product>> response) {
                apiProduct = response.body();

                if (apiProduct != null) {
                    if (apiProduct.getStatus().equals("success")){
                        dialog.dismiss();

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

                        Toast.makeText(c, "Berhasil Menambahkan Produk", Toast.LENGTH_LONG).show();

                    }else{
                        dialog.dismiss();
                        Toast.makeText(c, "Something might be wrong", Toast.LENGTH_LONG).show();
                        return;
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ApiData<Product>> call, @NonNull Throwable t) {
                dialog.dismiss();
                Log.e("check", "onFailure: ", t);
                Toast.makeText(c, "connection error", Toast.LENGTH_LONG).show();
            }
        });


    }

}
