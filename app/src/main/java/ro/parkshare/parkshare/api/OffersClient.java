package ro.parkshare.parkshare.api;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class OffersClient {

    private static final String SERVER_BASE_URL = "https://demo5826679.mockable.io/";

    private static OffersClient instance;
    private OffersAPI offersAPI;

    private OffersClient() {
        final Gson gson =
                new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        final Retrofit retrofit = new Retrofit.Builder().baseUrl(SERVER_BASE_URL)
                .client(client)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        offersAPI = retrofit.create(OffersAPI.class);
    }

    public static OffersClient getInstance() {
        if (instance == null) {
            instance = new OffersClient();
        }
        return instance;
    }

    public OffersAPI api() {
        return offersAPI;
    }
}
