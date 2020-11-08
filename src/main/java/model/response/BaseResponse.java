package model.response;

import com.google.gson.annotations.SerializedName;

public class BaseResponse {

    @SerializedName("error")
    private String error;

    public BaseResponse(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

}
