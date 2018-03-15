package ru.sbt.util.pcaccessapi;

import ru.sbt.util.pcaccessapi.dto.DataRs;
import ru.sbt.util.pcaccessapi.jsondto.run.Run;
import ru.sbt.util.pcaccessapi.jsondto.scenario.Scenario;

public interface PCAccess {

    DataRs<Scenario> getScenarioById(String domainName, String projectName, int id);

    DataRs<Run> getRunById(String domainName, String projectName, int id);

}
