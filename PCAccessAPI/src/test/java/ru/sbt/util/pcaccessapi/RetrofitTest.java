package ru.sbt.util.pcaccessapi;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import org.testng.annotations.Test;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.sbt.util.pcaccessapi.dto.LoginData;
import ru.sbt.util.pcaccessapi.json.ActionDeserializer;
import ru.sbt.util.pcaccessapi.json.LocalDateDeserializer;
import ru.sbt.util.pcaccessapi.jsondto.scenario.Scheduler;
import ru.sbt.util.pcaccessapi.jsondto.script.ScriptMetadata;
import ru.sbt.util.pcaccessapi.service.PerformanceCenterService;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

public class RetrofitTest {

    @Test(enabled = false)
    public void testRetrofit() throws Exception {

        PerformanceCenterService service = createRetrofit("http://sbt-oaar-0835.vm.mos.cloud.sbrf.ru", "Sbt-ontar-jira", "ES_23032017");

        int scriptId = 329;
        Response<ScriptMetadata> script = service.getScriptMetadataById("PPRB", "PPRB_ONTAR_UIP", scriptId).execute();

        System.out.println("script = " + script);
    }


    private PerformanceCenterService createRetrofit(String url, String login, String password) {

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okhttp = new OkHttpClient.Builder()
                .addInterceptor(new PCAuthenticationInterceptor(url, login, password, new LoginData()))
                .addInterceptor(loggingInterceptor)
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
