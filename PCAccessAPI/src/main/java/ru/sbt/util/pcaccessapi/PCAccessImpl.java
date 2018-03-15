package ru.sbt.util.pcaccessapi;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.sbt.util.pcaccessapi.dto.DataRs;
import ru.sbt.util.pcaccessapi.dto.LoginData;
import ru.sbt.util.pcaccessapi.json.ActionDeserializer;
import ru.sbt.util.pcaccessapi.json.LocalDateDeserializer;
import ru.sbt.util.pcaccessapi.jsondto.run.Run;
import ru.sbt.util.pcaccessapi.jsondto.scenario.Scenario;
import ru.sbt.util.pcaccessapi.jsondto.scenario.Scheduler;
import ru.sbt.util.pcaccessapi.jsondto.script.ScriptMetadata;
import ru.sbt.util.pcaccessapi.service.PerformanceCenterService;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

@Slf4j
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
    public DataRs<Scenario> getScenarioById(String domainName, String projectName, int id) {

        Response<Scenario> testRs;
        try {
            testRs = service.getScenarioById(domainName, projectName, id).execute();
            return analyzeResponse(testRs);

        } catch (IOException e) {
            throw new RuntimeException("Can not get getTestByID!", e);
        }
    }

    @Override
    public DataRs<Run> getRunById(String domainName, String projectName, int id) {

        Response<Run> response;
        try {
            response = service.getRunById(domainName, projectName, id).execute();
            return analyzeResponse(response);

        } catch (IOException e) {
            throw new RuntimeException("Can not get getRunByID!", e);
        }
    }

    @Override
    public DataRs<ScriptMetadata> getScriptMetadataById(String domainName, String projectName, int id) {
        Response<ScriptMetadata> response;
        try {
            response = service.getScriptMetadataById(domainName, projectName, id).execute();
            return analyzeResponse(response);

        } catch (IOException e) {
            throw new RuntimeException("Can not get getRunByID!", e);
        }

    }

    private <T> DataRs<T> analyzeResponse(Response<T> response) throws IOException {
        if (response.isSuccessful()) {
            return DataRs.success(response.body());

        } else {
            ResponseBody responseBody = response.errorBody();
            if (responseBody != null) {
                return DataRs.error(responseBody.string());
            } else {
                return DataRs.error("errorBody is null, status code:" + response.code());
            }
        }
    }

    private PerformanceCenterService createRetrofit(String url) {

        OkHttpClient okhttp = new OkHttpClient.Builder()
                .addInterceptor(new PCAuthenticationInterceptor(url, login, password, new LoginData()))
                .readTimeout(120, TimeUnit.SECONDS)
                .build();

        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-mm-dd HH:mm:ss")
                .registerTypeAdapter(Scheduler.Action.class, new ActionDeserializer())
                .registerTypeAdapter(LocalDateTime.class, new LocalDateDeserializer())
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okhttp)
                .build();


        return retrofit.create(PerformanceCenterService.class);
    }
}
