package ru.sbt.util.pcaccessapi.service;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import ru.sbt.util.pcaccessapi.jsondto.run.Run;
import ru.sbt.util.pcaccessapi.jsondto.scenario.Scenario;
import ru.sbt.util.pcaccessapi.jsondto.script.ScriptMetadata;

import static ru.sbt.util.pcaccessapi.utils.Constants.*;

public interface PerformanceCenterService {

    @GET(URI_TEST)
    @Headers({HEADER_ACCEPT_JSON})
    Call<Scenario> getScenarioById(
            @Path(PATH_DOMAIN_NAME) String domainName,
            @Path(PATH_PROJECT_NAME) String projectName,
            @Path(PATH_TEST_ID) int testId);

    @GET(URI_RUN)
    @Headers({HEADER_ACCEPT_JSON})
    Call<Run> getRunById(
            @Path(PATH_DOMAIN_NAME) String domainName,
            @Path(PATH_PROJECT_NAME) String projectName,
            @Path(PATH_RUN_ID) int runId);

    @GET(URI_SCRIPT)
    @Headers({HEADER_ACCEPT_JSON})
    Call<ScriptMetadata> getScriptMetadataById(
            @Path(PATH_DOMAIN_NAME) String domainName,
            @Path(PATH_PROJECT_NAME) String projectName,
            @Path(PATH_SCRIPT_ID) int runId);

}
