package ru.sbt.util.pcaccessapi.service;

import retrofit2.Call;
import retrofit2.HttpException;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import ru.sbt.util.pcaccessapi.jsondto.Scenario;

import static ru.sbt.util.pcaccessapi.utils.Constants.*;

public interface PerformanceCenterService {

    @GET("/LoadTest/rest/authentication-point/authenticate")
    Call<Void> login();


    @GET(URI_TEST)
    @Headers({HEADER_ACCEPT_JSON})
    Call<Scenario> getScenarioById(
            @Path(PATH_DOMAIN_NAME) String domainName,
            @Path(PATH_PROJECT_NAME) String projectName,
            @Path(PATH_TEST_ID) int testId)
            throws HttpException;
}
