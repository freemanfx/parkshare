package ro.parkshare.parkshare.api;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

import java.io.IOException;

public class ApiResponse {
    private int status;
    private String message;


    public static ApiResponse fromBody(String body) {
        Gson gson = new Gson();
        TypeAdapter<ApiResponse> adapter = gson.getAdapter(ApiResponse.class);
        try {
            ApiResponse apiResponse = adapter.fromJson(body);
            return apiResponse;
        } catch (IOException e) {
            ApiResponse apiResponse = new ApiResponse();
            apiResponse.setMessage(body);
            return apiResponse;
        }
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
