package am.aksanmaulana.gmail.com.tubes_v5_1.model;

import com.google.gson.annotations.SerializedName;

public class UserUmkm {

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

    public String getIdBusiness() {
        return idBusiness;
    }

    public void setIdBusiness(String idBusiness) {
        this.idBusiness = idBusiness;
    }

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
    @SerializedName("idUserUmkm")
    public int idUserUmkm;

    public int getIdUserUmkm() {
        return idUserUmkm;
    }

    public void setIdUserUmkm(int idUserUmkm) {
        this.idUserUmkm = idUserUmkm;
    }

    public String imageUser;

    public UserUmkm(int idUserUmkm, String username, String nameUser, String businessType, String businessUser, String imageUser){
        this.idUserUmkm = idUserUmkm;
        this.username = username;
        this.nameUser = nameUser;
        this.businessType = businessType;
        this.nameBusiness = businessUser;
        this.imageUser = imageUser;
    }

    public UserUmkm(String username, String nameUser, String genderUser, String addressUser, String phoneUser, String emailUser, String userType, String error, String businessType, String nameBusiness, String startBusiness, String locationBusiness, int idUserUmkm, String imageUser) {
        this.username = username;
        this.nameUser = nameUser;
        this.genderUser = genderUser;
        this.addressUser = addressUser;
        this.phoneUser = phoneUser;
        this.emailUser = emailUser;
        this.userType = userType;
        this.error = error;
        this.businessType = businessType;
        this.nameBusiness = nameBusiness;
        this.startBusiness = startBusiness;
        this.locationBusiness = locationBusiness;
        this.idUserUmkm = idUserUmkm;
        this.imageUser = imageUser;
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

    /*
    public UserUmkm(String nameUser, String genderUser, String addressUser, String phoneUser, String emailUser, String imageUser,
                    String userType) {
        this.nameUser = nameUser;
        this.genderUser = genderUser;
        this.addressUser = addressUser;
        this.phoneUser = phoneUser;
        this.emailUser = emailUser;
        this.imageUser = imageUser;
        this.userType = userType;
    }
    */

    public String getNameUser() {
        return nameUser;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getImageUser() {
        return imageUser;
    }

    public void setImageUser(String imageUser) {
        this.imageUser = imageUser;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}

