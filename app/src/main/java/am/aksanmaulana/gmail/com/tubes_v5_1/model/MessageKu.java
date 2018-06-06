package am.aksanmaulana.gmail.com.tubes_v5_1.model;

import com.google.gson.annotations.SerializedName;

public class MessageKu {

    @SerializedName("idMessageKu")
    public String idMessageKu;
    @SerializedName("fromId")
    public String fromId;
    @SerializedName("toId")
    public String toId;
    @SerializedName("contentMessage")
    public String contentMessage;
    @SerializedName("dateMessage")
    public String dateMessage;
    @SerializedName("statusMessage")
    public String statusMessage;

    @SerializedName("nameUser")
    public String nameUser;
    @SerializedName("genderUser")
    public String genderUser;

    public MessageKu(String idMessageKu, String fromId, String toId, String contentMessage, String dateMessage, String statusMessage, String nameUser) {
        this.idMessageKu = idMessageKu;
        this.fromId = fromId;
        this.toId = toId;
        this.contentMessage = contentMessage;
        this.dateMessage = dateMessage;
        this.statusMessage = statusMessage;
        this.nameUser = nameUser;
    }

    public String getGenderUser() {
        return genderUser;
    }

    public void setGenderUser(String genderUser) {
        this.genderUser = genderUser;
    }

    public String getNameUser() {
        return nameUser;
    }

    public void setNameUser(String nameUser) {
        this.nameUser = nameUser;
    }

    public String getIdMessageKu() {
        return idMessageKu;
    }

    public void setIdMessageKu(String idMessageKu) {
        this.idMessageKu = idMessageKu;
    }

    public String getFromId() {
        return fromId;
    }

    public void setFromId(String fromId) {
        this.fromId = fromId;
    }

    public String getToId() {
        return toId;
    }

    public void setToId(String toId) {
        this.toId = toId;
    }

    public String getContentMessage() {
        return contentMessage;
    }

    public void setContentMessage(String contentMessage) {
        this.contentMessage = contentMessage;
    }

    public String getDateMessage() {
        return dateMessage;
    }

    public void setDateMessage(String dateMessage) {
        this.dateMessage = dateMessage;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }
}
