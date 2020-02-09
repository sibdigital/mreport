/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.p03.common.test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import ru.p03.makpsb.core.entity.base.Config;
import ru.p03.makpsb.core.entity.base.ConfigBuilder;
import ru.p03.makpsb.core.report.AbstractReport;

/**
 *
 * @author timofeevan
 */
public class SimpleReportTester {

    //private final TestConfig testConfig;
    private final AbstractReport report;
    private final Logger logger;
    private final List<PUConfig> puConfigs = new ArrayList<>();
    private final String tmpdir;

    private boolean skipAll = false;
    private boolean skipMakeReport = false;
    private boolean skipMakeReportFile = false;

    private long minFileSize = 0L;

    private String cause = "";

    private SimpleReportTester(AbstractReport report, String tmpdir, Logger logger, long minFileSize){
        this.report = report;
        this.tmpdir = tmpdir;
        this.logger = logger;
        this.minFileSize = minFileSize;
    }
    
    public SimpleReportTester(AbstractReport report, List<PUConfig> puConfigs, String tmpdir, Logger logger) {
        this(report, tmpdir, logger, 0L);        
        this.puConfigs.addAll(puConfigs);
    }

    public SimpleReportTester(TestConfig testConfig, AbstractReport report, String puName, 
            String tmpdir, Logger logger) {
        this(report, tmpdir, logger, 0L);   
        this.puConfigs.addAll(Arrays.asList(new PUConfig(testConfig, puName)));
    }
    
    public SimpleReportTester(AbstractReport report, List<PUConfig> puConfigs, String tmpdir, 
            Logger logger, long minFileSize) {
        this(report, tmpdir, logger, minFileSize);  
        this.puConfigs.addAll(puConfigs);
    }

    public SimpleReportTester(TestConfig testConfig, AbstractReport report, String puName, 
            String tmpdir, Logger logger, long minFileSize) {
        this(report, tmpdir, logger, minFileSize); 
        this.puConfigs.addAll(Arrays.asList(new PUConfig(testConfig, puName)));
    }

    private Config getConfig() {
        final ConfigBuilder configBuilder = new ConfigBuilder(true);
        puConfigs.forEach((puc) -> {
            TestPersistenceProvider provider = new TestPersistenceProvider(puc.getTestConfig(), puc.getPuName());
            configBuilder.addEntityManagerFactory(puc.getPuName(), provider.getEntityManagerFactory());
        });

        Config config = configBuilder.build();
        return config;
    }

    private void fail(String cause) {
        if (cause.isEmpty()) {
            this.cause = cause;
        } else {
            this.cause += ("\n" + cause);
        }
    }

    private boolean testCreatedFile() {
        boolean result = true;
        String reportFileName = report.reportFileName();
        boolean exists = Files.exists(Paths.get(reportFileName));
        if (!exists) {
            result = false;
            fail("Файл отчета " + reportFileName + " не найден " + report.getClass());
        } else {
            try {
                long size = Files.size(Paths.get(reportFileName));
                if (size <= minFileSize) {
                    result = false;
                    fail("Файл отчета " + reportFileName + " имеет нулевой размер " + report.getClass());
                }
            } catch (IOException ex) {
                logger.log(Level.SEVERE, null, ex);
            }
        }
        return result;
    }

    public boolean test() throws Exception {
        boolean success = false;
        if (skipAll) {
            logger.log(Level.SEVERE, "Формирование всего отменено ");
            return skipAll;
        }
        Config config = getConfig();
        if (config.connectionsAlive()) {

            report.setConfig(config);
            report.setReportPath(tmpdir, report.getClass().getSimpleName() + System.currentTimeMillis() + ".xls");

            if (skipMakeReport) {
                logger.log(Level.SEVERE, "Формирование выборок отменено ");
                return skipMakeReport;
            }
            success = report.make();
            if (success) {
                if (skipMakeReportFile) {
                    logger.log(Level.SEVERE, "Формирование выходного файла отменено ");
                    return skipMakeReportFile;
                }
                success = report.makeReportFile();

                if (success) {
                    success &= testCreatedFile();
                    if (success) {
                        logger.log(Level.SEVERE, "Тест пройден успешно ");
                    }
                } else {
                    fail("Не сформировался выходной файл отчета ");
                }
            } else {
                fail("Не сформировалась выборка из базы данных ");
            }

        } else {
            fail("Отсутствует подключение к одному из источников данных, подробности смотрите в логе" + report.getClass());
        }
        return success;
    }

    public String getFailCause() {
        return report.getClass() + ": " + cause;
    }

    public boolean isSkipAll() {
        return skipAll;
    }

    public boolean isSkipMakeReport() {
        return skipMakeReport;
    }

    public boolean isSkipMakeReportFile() {
        return skipMakeReportFile;
    }

    public void setSkipAll(boolean skipAll) {
        this.skipAll = skipAll;
    }

    public void setSkipMakeReport(boolean skipMakeReport) {
        this.skipMakeReport = skipMakeReport;
    }

    public void setSkipMakeReportFile(boolean skipMakeReportFile) {
        this.skipMakeReportFile = skipMakeReportFile;
    }

    private SimpleReportTester(AbstractReport report, String tmpdir, Logger logger) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
