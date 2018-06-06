package am.aksanmaulana.gmail.com.tubes_v5_1.model;

import com.google.gson.annotations.SerializedName;

public class DuitKu {

    @SerializedName("idDuitKu")
    public String idDuitKu;
    @SerializedName("balanceDuitKu")
    public String balanceDuitKu;
    @SerializedName("userUmkm")
    public String userUmkm;

    @SerializedName("error")
    public String error;

    public DuitKu(String idDuitKu, String balanceDuitKu, String userUmkm, String error) {
        this.idDuitKu = idDuitKu;
        this.balanceDuitKu = balanceDuitKu;
        this.userUmkm = userUmkm;
        this.error = error;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getIdDuitKu() {
        return idDuitKu;
    }

    public void setIdDuitKu(String idDuitKu) {
        this.idDuitKu = idDuitKu;
    }

    public String getBalanceDuitKu() {
        return balanceDuitKu;
    }

    public void setBalanceDuitKu(String balanceDuitKu) {
        this.balanceDuitKu = balanceDuitKu;
    }

    public String getUserUmkm() {
        return userUmkm;
    }

    public void setUserUmkm(String userUmkm) {
        this.userUmkm = userUmkm;
    }
}
