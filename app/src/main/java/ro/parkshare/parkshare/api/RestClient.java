package ro.parkshare.parkshare.api;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import ro.parkshare.parkshare.Constants;

public class RestClient {

    private static RestClient instance;
    private OffersAPI offersAPI;
    private ParkingAPI parkingAPI;

    private RestClient() {
        final Gson gson =
                new GsonBuilder()
                        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                        .setDateFormat(Constants.DateFormats.JAVA_DATE_FORMAT_WITH_TIME_ZONE)
                        .create();

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        final Retrofit retrofit = new Retrofit.Builder().baseUrl(Constants.SERVER_URL)
                .client(client)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        offersAPI = retrofit.create(OffersAPI.class);
        parkingAPI = retrofit.create(ParkingAPI.class);
    }

    public static RestClient getInstance() {
        if (instance == null) {
            instance = new RestClient();
        }
        return instance;
    }

    public OffersAPI offersAPI() {
        return offersAPI;
    }

    public ParkingAPI parkingAPI() {
        return parkingAPI;
    }
}
