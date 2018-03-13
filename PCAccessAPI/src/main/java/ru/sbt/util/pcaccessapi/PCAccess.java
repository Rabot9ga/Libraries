package ru.sbt.util.pcaccessapi;

import ru.sbt.util.pcaccessapi.jsondto.Test;

public interface PCAccess extends AutoCloseable{

    Test getTestByID(String domainName, String projectName, int id);

    @Override
    void close() throws Exception;
}
