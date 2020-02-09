/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.p03.makpsb.core.report;

import org.junit.Ignore;
import org.junit.Test;
import ru.p03.common.test.TestConfig;
import ru.p03.common.test.TestConfigBuilder;
import ru.p03.common.test.TestPersistenceProvider;
import ru.p03.makpsb.core.entity.base.Config;
import ru.p03.makpsb.core.entity.base.ConfigBuilder;
import ru.p03.makpsb.core.entity.base.MPDAO;
import ru.p03.makpsb.dynreport.ActualDynReport;
import ru.p03.makpsb.dynreport.ActualDynReportJpaController;
import ru.p03.makpsb.dynreport.RaionPeriodDynReport;

/**
 *
 * @author altmf
 */
public class AbstractPeriodicReportTest {

    @Ignore
    @Test
    public void testPerform() {
        TestConfig testConfig = new TestConfigBuilder()
                .postgresDriver()
                .setUser("openprojectsd")
                .setPassword("")
                .setDbUrl("jdbc:postgresql://localhost:5432/opsd_development")
                .build();

        String puName =  MPDAO.CORE_MAK_PSB_PU + "opsd";
        TestPersistenceProvider provider = new TestPersistenceProvider(testConfig, puName);

        Config mpConfig = new ConfigBuilder(false) //
                .addEntityManagerFactory(puName, provider.getEntityManagerFactory())
                .build();

        ActualDynReportJpaController actualDynReportJpaController = new ActualDynReportJpaController();
        ActualDynReport actualDynReport = actualDynReportJpaController.findActualDynReportEntities().get(0);
        RaionPeriodDynReport report = new RaionPeriodDynReport(actualDynReport);
        report.setConfig(mpConfig);
        report.make();
        report.makeReportFile();

        //SimpleReportTester srt = new SimpleReportTester(testConfig, )
    }

    
//    public AbstractPeriodicReportTest() {
//    }
//
//    @BeforeClass
//    public static void setUpClass() {
//    }
//
//    @AfterClass
//    public static void tearDownClass() {
//    }
//
//    @Before
//    public void setUp() {
//    }
//
//    @After
//    public void tearDown() {
//    }
//
//    /**
//     * Test of perform method, of class AbstractPeriodicReport.
//     */
//    @Ignore
//    @Test
//    public void testPerform() {
//        System.out.println("perform");
//        AbstractPeriodicReport instance = new AbstractPeriodicReport();
//
//        ClsStandartFormingPeriod sfp = new ClsStandartFormingPeriod();
//        sfp.setCode(ClsStandartFormingPeriod.CURRENT_MONTH);
//        boolean expResult = false;
//        instance.setStandartFormingPeriod(sfp);
//        instance.setTargetReport(AbstractRaionPeriodReport.getDummyInstance());
//        boolean result = instance.perform();
//        assertEquals(expResult, result);
//
//        sfp.setCode(ClsStandartFormingPeriod.CURRENT_WEEK);
//        expResult = false;
//        result = instance.perform();
//        assertEquals(expResult, result);
//
//        sfp.setCode(ClsStandartFormingPeriod.LAST_MONTH);
//        expResult = false;
//        result = instance.perform();
//        assertEquals(expResult, result);
//
//        sfp.setCode(ClsStandartFormingPeriod.LAST_WEEK);
//        expResult = false;
//        result = instance.perform();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
    
}
