package ru.sbt.util.pcaccessapi.utils;

public interface Constants {
    String JSON = "application/json; charset=utf-8";
    String XML = "application/xml; charset=utf-8";
    String ZIP = "application/zip";
    String HEADER_ACCEPT_JSON = "Accept: " + JSON;
    String HEADER_CONTENT_JSON = "Content-Type: " + JSON;

    String PATH_DOMAIN_NAME = "domainName";
    String PATH_PROJECT_NAME = "projectName";
    String PATH_GROUP_NAME = "groupName";
    String PATH_RUN_ID = "runID";
    String PATH_TEST_ID = "testID";
    String PATH_RUN_RESULT_ID = "resultID";
    String PATH_SCRIPT_ID = "scriptID";

    String COMMON_URI_PREFIX = "LoadTest/rest/domains/{" + PATH_DOMAIN_NAME + "}/projects/{" + PATH_PROJECT_NAME + "}";

    String URI_TEST = COMMON_URI_PREFIX + "/tests/{" + PATH_TEST_ID + "}";
    String URI_RUN = COMMON_URI_PREFIX + "/Runs/{" + PATH_RUN_ID + "}/Extended";
    String URI_LOGIN = "LoadTest/rest/authentication-point/authenticate";
    String URI_LOGOUT = "LoadTest/rest/authentication-point/logout";
}
