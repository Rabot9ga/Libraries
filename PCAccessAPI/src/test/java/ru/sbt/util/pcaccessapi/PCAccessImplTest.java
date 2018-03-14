package ru.sbt.util.pcaccessapi;

import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.sbt.util.pcaccessapi.jsondto.Scenario;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

@Slf4j
public class PCAccessImplTest {
    PCAccess pcAccess;

    @BeforeMethod
    public void setUp() throws Exception {
        pcAccess = PCAccessFactory.create("http://sbt-oaar-0835.vm.mos.cloud.sbrf.ru", "Sbt-ontar-jira", "ES_23032017");
    }

    @Test
    public void testGetTestByID() throws Exception {
//        int testId = 364;
        int testId = 368;

        Scenario scenario = pcAccess.getScenarioById("PPRB", "PPRB_ONTAR_UIP", testId);

        System.out.println("scenario = " + scenario);

        assertEquals((int) scenario.getId(), testId, "id is not the same!");
        assertNotNull(scenario, "scenario is null!");
    }

}