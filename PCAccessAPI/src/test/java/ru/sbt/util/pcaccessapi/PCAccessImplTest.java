package ru.sbt.util.pcaccessapi;

import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.sbt.util.pcaccessapi.dto.DataRs;
import ru.sbt.util.pcaccessapi.jsondto.run.Run;
import ru.sbt.util.pcaccessapi.jsondto.scenario.Scenario;
import ru.sbt.util.pcaccessapi.jsondto.script.ScriptMetadata;

import static org.testng.Assert.*;

@Slf4j
public class PCAccessImplTest {
    PCAccess pcAccess;

    @BeforeClass
    public void setUp() throws Exception {
        pcAccess = PCAccessFactory.create("http://sbt-oaar-0835.vm.mos.cloud.sbrf.ru", "Sbt-ontar-jira", "ES_23032017");
    }

    @Test
    public void testGetTestByID() throws Exception {
        int testId = 368;

        DataRs<Scenario> dataRs = pcAccess.getScenarioById("PPRB", "PPRB_ONTAR_UIP", testId);

        assertPositive(dataRs);
        assertEquals((int) dataRs.getPayload().getId(), testId, "id is not the same!");
    }


    @Test
    public void testGetRunById() throws Exception {
        int runId = 366;

        DataRs<Run> dataRs = pcAccess.getRunById("PPRB", "PPRB_ONTAR_UIP", runId);

        assertPositive(dataRs);
        assertEquals((int) dataRs.getPayload().getId(), 366, "run id is not equals");


    }

    @Test
    public void testGetUnavailableRunId() throws Exception {
        int runId = 986;

        DataRs<Run> dataRs = pcAccess.getRunById("PPRB", "PPRB_ONTAR_UIP", runId);

        assertNegative(dataRs);
    }

    @Test
    public void testGetScriptMetadata() throws Exception {
        int scriptId = 329;

        DataRs<ScriptMetadata> dataRs = pcAccess.getScriptMetadataById("PPRB", "PPRB_ONTAR_UIP", scriptId);

        assertPositive(dataRs);
    }


    private void assertPositive(DataRs<?> dataRs) {
        assertNotNull(dataRs, "dataRs is null!");
        assertTrue(dataRs.isSuccess(), "response is not success");
        assertNotNull(dataRs.getPayload(), "dataRs payload is null");
    }

    private void assertNegative(DataRs<?> dataRs) {
        assertNotNull(dataRs, "dataRs is null!");
        assertFalse(dataRs.isSuccess(), "response is success");
        assertNull(dataRs.getPayload(), "payload is not null");
    }
}