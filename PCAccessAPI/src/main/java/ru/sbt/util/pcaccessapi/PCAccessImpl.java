package ru.sbt.util.pcaccessapi;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.OkHttpClient;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.sbt.util.pcaccessapi.dto.LoginData;
import ru.sbt.util.pcaccessapi.jsondto.Action;
import ru.sbt.util.pcaccessapi.jsondto.Test;
import ru.sbt.util.pcaccessapi.json.ActionDeserializer;
import ru.sbt.util.pcaccessapi.service.PerformanceCenterService;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class PCAccessImpl implements PCAccess {

    private PerformanceCenterService service;
    private String url;
    private String login;
    private String password;

    public PCAccessImpl(String url, String login, String password) {
        this.url = url;
        this.login = login;
        this.password = password;

        service = createRetrofit(url);
    }

    @Override
    public Test getTestByID(String domainName, String projectName, int id) {

        Response<Test> testRs;
        try {
            testRs = service.getTest(domainName, projectName, id).execute();
        } catch (IOException e) {
            throw new RuntimeException("Can not get getTestByID!", e);
        }
        return testRs.body();
    }

    @Override
    public void close() throws Exception {

    }

    private PerformanceCenterService createRetrofit(String url) {


        OkHttpClient okhttp = new OkHttpClient.Builder()
//                .cookieJar(new JavaNetCookieJar(cm))
                .addInterceptor(new PCAuthenticationInterceptor(url, login, password, new LoginData()))
//                .addNetworkInterceptor(new PCCookieInterceptor(cm))
//                .addNetworkInterceptor(loi)
                .readTimeout(120, TimeUnit.SECONDS)
                .build();


        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Action.class, new ActionDeserializer())
                .create();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okhttp)
                .build();


        return retrofit.create(PerformanceCenterService.class);
    }
}
