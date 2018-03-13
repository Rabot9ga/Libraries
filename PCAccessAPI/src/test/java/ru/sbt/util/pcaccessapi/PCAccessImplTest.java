package ru.sbt.util.pcaccessapi;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.assertNotNull;

public class PCAccessImplTest {
    PCAccess pcAccess;

    @BeforeMethod
    public void setUp() throws Exception {
        pcAccess = PCAccessFactory.create("http://sbt-oaar-0835.vm.mos.cloud.sbrf.ru", "Sbt-ontar-jira", "ES_23032017");
    }

    @Test
    public void testGetTestByID() throws Exception {
        ru.sbt.util.pcaccessapi.jsondto.Test testByID = pcAccess.getTestByID("PPRB", "PPRB_ONTAR_UIP", 364);

        assertNotNull(testByID, "testByID is null!");
    }

}