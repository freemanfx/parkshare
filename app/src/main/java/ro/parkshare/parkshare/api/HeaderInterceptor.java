package ro.parkshare.parkshare.api;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import static ro.parkshare.parkshare.BeanProvider.userService;

public class HeaderInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain)
            throws IOException {
        Request request = chain.request();
        String authToken = userService().getAuthToken();
        request = request.newBuilder()
                .addHeader("auth-token", authToken)
                .build();
        return chain.proceed(request);
    }
}
