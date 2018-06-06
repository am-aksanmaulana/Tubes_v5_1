package am.aksanmaulana.gmail.com.tubes_v5_1.api;

public class ApiData<T> {
    // T stands for "Type"
    private String status;
    private T data;

    public ApiData() {
    }

    public ApiData(String status, T data) {
        this.status = status;
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
