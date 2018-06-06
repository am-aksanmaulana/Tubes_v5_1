package am.aksanmaulana.gmail.com.tubes_v5_1.model;

import com.google.gson.annotations.SerializedName;

public class Product {

    @SerializedName("idProduct")
    public int idProduct;
    @SerializedName("nameProduct")
    public String nameProduct;
    @SerializedName("priceProduct")
    public String priceProduct;
    @SerializedName("satuanHarga")
    public String satuanHarga;
    @SerializedName("idBusiness")
    public String idBusiness;
    @SerializedName("stockProduct")
    public String stockProduct;


    @SerializedName("userUmkm")
    public String userUmkm;
    @SerializedName("businessType")
    public String businessType;
    @SerializedName("nameBusiness")
    public String nameBusiness;
    @SerializedName("startBusiness")
    public String startBusiness;
    @SerializedName("locationBusiness")
    public String locationBusiness;

    @SerializedName("nameUser")
    public String nameUser;

    public Product(int idProduct, String nameProduct, String priceProduct, String satuanHarga, String idBusiness, String stockProduct, String businessType, String nameBusiness, String startBusiness, String locationBusiness) {
        this.idProduct = idProduct;
        this.nameProduct = nameProduct;
        this.priceProduct = priceProduct;
        this.satuanHarga = satuanHarga;
        this.idBusiness = idBusiness;
        this.stockProduct = stockProduct;
        this.businessType = businessType;
        this.nameBusiness = nameBusiness;
        this.startBusiness = startBusiness;
        this.locationBusiness = locationBusiness;
    }

    public Product() {

    }

    public String getUserUmkm() {
        return userUmkm;
    }

    public void setUserUmkm(String userUmkm) {
        this.userUmkm = userUmkm;
    }

    public String getNameUser() {
        return nameUser;
    }

    public void setNameUser(String nameUser) {
        this.nameUser = nameUser;
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

    public String getIdBusiness() {
        return idBusiness;
    }

    public void setIdBusiness(String idBusiness) {
        this.idBusiness = idBusiness;
    }

    public String getStockProduct() {
        return stockProduct;
    }

    public void setStockProduct(String stockProduct) {
        this.stockProduct = stockProduct;
    }
}
