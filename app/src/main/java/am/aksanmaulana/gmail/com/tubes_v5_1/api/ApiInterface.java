package am.aksanmaulana.gmail.com.tubes_v5_1.api;

import java.util.List;

import am.aksanmaulana.gmail.com.tubes_v5_1.model.DuitKu;
import am.aksanmaulana.gmail.com.tubes_v5_1.model.MessageKu;
import am.aksanmaulana.gmail.com.tubes_v5_1.model.OrderList;
import am.aksanmaulana.gmail.com.tubes_v5_1.model.Product;
import am.aksanmaulana.gmail.com.tubes_v5_1.model.UserUmkm;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {

    // Interface User Umkm
    @GET("UserUmkm/getJoinExceptById/{id}/")
    Call<ApiData<List<UserUmkm>>> getAllUserUmkmExcept(@Path("id") String idUserUmkm, @Query("businessType") String businessType);

    @GET("UserUmkm/getByIdWithJoinBusiness/{id}")
    Call<ApiData<UserUmkm>> getByIdWithJoinBusiness(@Path("id") String idUserUmkm);

    @GET("UserUmkm/getById/{id}")
    Call<ApiData<UserUmkm>> getById(@Path("id") String idUserUmkm);

    @GET("UserUmkm/getAuth/{username}/{password}")
    Call<ApiData<UserUmkm>> getAuth(@Path("username") String username, @Path("password") String password);

    @FormUrlEncoded
    @POST("UserUmkm/insertUser")
    Call<ApiData<UserUmkm>> insertUser(
            @Field("username") String username,
            @Field("password") String password,
            @Field("nameUser") String nameUser,
            @Field("genderUser") String genderUser,
            @Field("addressUser") String addressUser,
            @Field("phoneUser") String phoneUser,
            @Field("emailUser") String emailUser,
            @Field("userType") String userType,
            @Field("imageUser") String imageUser
    );

    @FormUrlEncoded
    @PUT("UserUmkm/updateUser/{id}")
    Call<ApiData<UserUmkm>> updateUser(
            @Path("id") String idUserUmkm,
            @Field("nameUser") String nameUser,
            @Field("genderUser") String genderUser,
            @Field("addressUser") String addressUser,
            @Field("phoneUser") String phoneUser,
            @Field("emailUser") String emailUser
    );

    // interface business
    @FormUrlEncoded
    @POST("Business/insertBusiness")
    Call<ApiData<UserUmkm>> insertBusiness(
            @Field("nameBusiness") String nameBusiness,
            @Field("locationBusiness") String locationBusiness,
            @Field("startBusiness") String startBusiness,
            @Field("businessType") String businessType,
            @Field("userUmkm") String userUmkm
    );
    @FormUrlEncoded
    @PUT("Business/updateBusiness/{id}")
    Call<ApiData<UserUmkm>> updateBusiness(
            @Path("id") String idBusiness,
            @Field("nameBusiness") String nameBusiness,
            @Field("locationBusiness") String locationBusiness,
            @Field("startBusiness") String startBusiness,
            @Field("businessType") String businessType,
            @Field("userUmkm") String userUmkm
    );

    // interface product
    @GET("ProductUmkm/getByIdBusinessWithJoin/{id}")
    Call<ApiData<List<Product>>> getProductByIdBusiness(@Path("id") String idBusiness);
    @GET("ProductUmkm/getAllWithJoin/")
    Call<ApiData<List<Product>>> getAllProduct();

    @FormUrlEncoded
    @POST("ProductUmkm/insertProduct")
    Call<ApiData<Product>> insertProduct(
            @Field("nameProduct") String nameProduct,
            @Field("priceProduct") String priceProduct,
            @Field("satuanHarga") String satuanHarga,
            @Field("stockProduct") String stockProduct,
            @Field("idBusiness") String idBusiness
    );

    @FormUrlEncoded
    @PUT("ProductUmkm/updateProduct/{id}")
    Call<ApiData<Product>> updateProduct(
            @Path("id") String idProduct,
            @Field("nameProduct") String nameProduct,
            @Field("priceProduct") String priceProduct,
            @Field("satuanHarga") String satuanHarga,
            @Field("stockProduct") String stockProduct,
            @Field("idBusiness") String idBusiness
    );

    @DELETE("ProductUmkm/deleteProduct/{id}")
    Call<ApiData<String>> deleteProduct(@Path("id") String idProduct);


    // Interface DuitKu
    @GET("DuitKu/getByIdUserUmkm/{id}")
    Call<ApiData<DuitKu>> getDuitKuByIdUserUmkm(@Path("id") String idUserUmkm);

    @FormUrlEncoded
    @POST("DuitKu/insertDuitKu")
    Call<ApiData<DuitKu>> insertDuitKu(
            @Field("idDuitKu") String idDuitKu,
            @Field("balanceDuitKu") String balanceDuitKu,
            @Field("userUmkm") String userUmkm
    );

    @FormUrlEncoded
    @PUT("DuitKu/tranferDuitKu/")
    Call<ApiData<String>> transferDuitKu(
            @Field("idDuitKu") String idDuitKu,
            @Field("idDuitKuTujuan") String idDuitKuTujuan,
            @Field("jumlah") String jumlah
    );

    @FormUrlEncoded
    @PUT("DuitKu/topUpDuitKu/{id}")
    Call<ApiData<DuitKu>> topUpDuitKu(
            @Path("id") String idDuitKu,
            @Field("amountTopUp") String amountTopUp
    );

    // Interface untuk Message
    @GET("MessageKu/getInboxByIdUserUmkm/{id}")
    Call<ApiData<List<MessageKu>>> getInboxByIdUserUmkm(@Path("id") String idUserUmkm);
    @GET("MessageKu/getSentByIdUserUmkm/{id}")
    Call<ApiData<List<MessageKu>>> getSentByIdUserUmkm(@Path("id") String idUserUmkm);

    @FormUrlEncoded
    @PUT("MessageKu/updateStatusMessageKu/{id}")
    Call<ApiData<String>> updateStatusMessage(
            @Path("id") String idMessageKu,
            @Field("statusMessage") String statusMessage
    );

    @FormUrlEncoded
    @POST("MessageKu/insertMessageKu")
    Call<ApiData<MessageKu>> insertMessageKu(
            @Field("fromId") String fromId,
            @Field("toId") String toId,
            @Field("contentMessage") String contentMessage,
            @Field("statusMessage") String statusMessage
    );


    // Interface untuk Order List
    @GET("OrderProduct/getByIdBusinessWithJoin/{id}")
    Call<ApiData<List<OrderList>>> getOrderListByIdBusinessWithJoin(@Path("id") int idBusiness);
    @GET("OrderProduct/getOrderByIdUserWithJoin/{id}")
    Call<ApiData<List<OrderList>>> getShopListByIdUserWithJoin(@Path("id") int idUserUmkm);

    @FormUrlEncoded
    @PUT("OrderProduct/updateStatusOrder/{id}")
    Call<ApiData<String>> updateStatusOrder(
            @Path("id") int idOrderProduct,
            @Field("statusOrder") String statusOrder
    );

    @FormUrlEncoded
    @PUT("OrderProduct/updateStatusPay/{id}")
    Call<ApiData<String>> updateStatusPay(
            @Path("id") int idOrderProduct,
            @Field("statusPay") String statusPay
    );

    @FormUrlEncoded
    @POST("OrderProduct/insertOrderProduct")
    Call<ApiData<OrderList>> insertOrderProduct(
            @Field("idProduct") String idProduct,
            @Field("idUserUmkm") String idUserUmkm,
            @Field("amountOrder") String amountOrder,
            @Field("totalPrice") String totalPrice,
            @Field("payMethode") String payMethode,
            @Field("statusPay") String statusPay,
            @Field("statusOrder") String statusOrder,
            @Field("orderMessage") String orderMessage,
            @Field("addressOrder") String addressOrder
    );
}
