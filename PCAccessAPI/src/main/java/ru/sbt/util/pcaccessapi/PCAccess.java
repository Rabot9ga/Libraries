package ru.sbt.util.pcaccessapi;

import ru.sbt.util.pcaccessapi.jsondto.Scenario;

public interface PCAccess extends AutoCloseable{

    Scenario getScenarioById(String domainName, String projectName, int id);

    @Override
    void close() throws Exception;
}
