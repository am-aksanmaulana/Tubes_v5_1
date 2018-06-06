package am.aksanmaulana.gmail.com.tubes_v5_1.model;

import com.google.gson.annotations.SerializedName;

public class OrderList {

    @SerializedName("idUserUmkm")
    public String idUserUmkm;
    @SerializedName("username")
    public String username;
    @SerializedName("nameUser")
    public String nameUser;
    @SerializedName("genderUser")
    public String genderUser;
    @SerializedName("addressUser")
    public String addressUser;
    @SerializedName("phoneUser")
    public String phoneUser;
    @SerializedName("emailUser")
    public String emailUser;
    @SerializedName("userType")
    public String userType;
    @SerializedName("error")
    public String error;

    @SerializedName("idBusiness")
    public String idBusiness;
    @SerializedName("businessType")
    public String businessType;
    @SerializedName("nameBusiness")
    public String nameBusiness;
    @SerializedName("startBusiness")
    public String startBusiness;
    @SerializedName("locationBusiness")
    public String locationBusiness;

    @SerializedName("idProduct")
    public int idProduct;
    @SerializedName("nameProduct")
    public String nameProduct;
    @SerializedName("priceProduct")
    public String priceProduct;
    @SerializedName("satuanHarga")
    public String satuanHarga;
    @SerializedName("stockProduct")
    public String stockProduct;

    @SerializedName("idOrderProduct")
    public int idOrderProduct;
    @SerializedName("totalPrice")
    public String totalPrice;
    @SerializedName("amountOrder")
    public String amountOrder;
    @SerializedName("payMethode")
    public String payMethode;
    @SerializedName("statusOrder")
    public String statusOrder;
    @SerializedName("dateOrder")
    public String dateOrder;
    @SerializedName("statusPay")
    public String statusPay;
    @SerializedName("orderMessage")
    public String orderMessage;
    @SerializedName("addressOrder")
    public String addressOrder;

    public OrderList(String idUserUmkm, int idProduct, int idOrderProduct, String totalPrice, String amountOrder, String statusOrder) {
        this.idUserUmkm = idUserUmkm;
        this.idProduct = idProduct;
        this.idOrderProduct = idOrderProduct;
        this.totalPrice = totalPrice;
        this.amountOrder = amountOrder;
        this.statusOrder = statusOrder;
    }

    public String getAddressOrder() {
        return addressOrder;
    }

    public void setAddressOrder(String addressOrder) {
        this.addressOrder = addressOrder;
    }

    public String getOrderMessage() {
        return orderMessage;
    }

    public void setOrderMessage(String orderMessage) {
        this.orderMessage = orderMessage;
    }

    public String getStatusPay() {
        return statusPay;
    }

    public void setStatusPay(String statusPay) {
        this.statusPay = statusPay;
    }

    public String getIdUserUmkm() {
        return idUserUmkm;
    }

    public void setIdUserUmkm(String idUserUmkm) {
        this.idUserUmkm = idUserUmkm;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNameUser() {
        return nameUser;
    }

    public void setNameUser(String nameUser) {
        this.nameUser = nameUser;
    }

    public String getGenderUser() {
        return genderUser;
    }

    public void setGenderUser(String genderUser) {
        this.genderUser = genderUser;
    }

    public String getAddressUser() {
        return addressUser;
    }

    public void setAddressUser(String addressUser) {
        this.addressUser = addressUser;
    }

    public String getPhoneUser() {
        return phoneUser;
    }

    public void setPhoneUser(String phoneUser) {
        this.phoneUser = phoneUser;
    }

    public String getEmailUser() {
        return emailUser;
    }

    public void setEmailUser(String emailUser) {
        this.emailUser = emailUser;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getIdBusiness() {
        return idBusiness;
    }

    public void setIdBusiness(String idBusiness) {
        this.idBusiness = idBusiness;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public String getNameBusiness() {
        return nameBusiness;
    }

    public void setNameBusiness(String nameBusiness) {
        this.nameBusiness = nameBusiness;
    }

    public String getStartBusiness() {
        return startBusiness;
    }

    public void setStartBusiness(String startBusiness) {
        this.startBusiness = startBusiness;
    }

    public String getLocationBusiness() {
        return locationBusiness;
    }

    public void setLocationBusiness(String locationBusiness) {
        this.locationBusiness = locationBusiness;
    }

    public int getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }

    public String getNameProduct() {
        return nameProduct;
    }

    public void setNameProduct(String nameProduct) {
        this.nameProduct = nameProduct;
    }

    public String getPriceProduct() {
        return priceProduct;
    }

    public void setPriceProduct(String priceProduct) {
        this.priceProduct = priceProduct;
    }

    public String getSatuanHarga() {
        return satuanHarga;
    }

    public void setSatuanHarga(String satuanHarga) {
        this.satuanHarga = satuanHarga;
    }

    public String getStockProduct() {
        return stockProduct;
    }

    public void setStockProduct(String stockProduct) {
        this.stockProduct = stockProduct;
    }

    public int getIdOrderProduct() {
        return idOrderProduct;
    }

    public void setIdOrderProduct(int idOrderProduct) {
        this.idOrderProduct = idOrderProduct;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getAmountOrder() {
        return amountOrder;
    }

    public void setAmountOrder(String amountOrder) {
        this.amountOrder = amountOrder;
    }

    public String getPayMethode() {
        return payMethode;
    }

    public void setPayMethode(String payMethode) {
        this.payMethode = payMethode;
    }

    public String getStatusOrder() {
        return statusOrder;
    }

    public void setStatusOrder(String statusOrder) {
        this.statusOrder = statusOrder;
    }

    public String getDateOrder() {
        return dateOrder;
    }

    public void setDateOrder(String dateOrder) {
        this.dateOrder = dateOrder;
    }
}
