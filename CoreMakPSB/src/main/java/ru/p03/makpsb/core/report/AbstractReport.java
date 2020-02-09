/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.p03.makpsb.core.report;

import ru.p03.makpsb.core.entity.base.Config;
import ru.p03.makpsb.core.entity.service.Stat;
import ru.p03.makpsb.core.util.JobCalendarUtil;
import ru.p03.makpsb.core.util.RaionUtil;
import ru.p03.makpsb.core.util.ResourceUtil;
import ru.p03.makpsb.core.util.StatUtil;
import ru.p03.makpsb.main.job.JobAbstractReport;
import ru.p03.makpsb.main.utils.ShedulerJobAbstractReport;
import ru.p03.vmvp.utils.CommonUtils;
import ru.p03.vmvp.utils.FormatUtils;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author timofeevan
 */
public abstract class AbstractReport implements Serializable, IReport {

    private static final long serialVersionUID = 5977352965595197032L;

    private static class DummyAbstractReport extends AbstractReport {

        @Override
        protected boolean perform() {
            return false;
        }

        @Override
        protected boolean beforePerform() {
            return false;
        }

        @Override
        protected boolean afterPerform() {
            return false;
        }

        @Override
        protected boolean createReportFile() {
            return false;
        }
    }

    public static AbstractReport getDummyInstance() {
        return new DummyAbstractReport();
    }

    protected Map<String, Object> properies = new HashMap<>();
//    @Deprecated
//    protected String reportFileName = "";
    @Deprecated
    private String templateDirectory = "";

    private String simpleFileName = "";
    private String directory = "";
    private boolean createPathIfNotExists = true;
    private String contextPath;

    private Config config;
    private RaionUtil raionUtil;
    private JobCalendarUtil jobCalendarUtil;

    @Override
    public void setProperties(Map<String, Object> properies) {
        this.properies = properies;
    }

    @Override
    public Object setProperty(String key, Object value) {
        if (properies == null) {
            properies = new HashMap<>();
        }
        return properies.put(key, value);
    }

    @Override
    public Map<String, Object> getProperties() {
        return properies;
    }

    @Override
    public Object getProperty(String key) {
        if (getProperties() != null) {
            return getProperties().get(key);
        }
        return null;
    }

    @Override
    public String getAsString(String key) {
        Object p = getProperty(key);
        if (p != null) {
            return p.toString();
        }
        return "";
    }

    /**
     * Выполняет отчет
     *
     * @return Истина, если операции выполнены успешно, иначе ложь
     */
    @Override
    public boolean make() {
        boolean result = true;
        try {
            result &= beforeMake();
        } catch (UnsupportedOperationException uoe) {

        }
        Stat stat = StatUtil.instance().beginStat(this);
        try {
            result &= perform();
        } catch (Exception ex) {
            Logger.getLogger(AbstractReport.class.getName()).log(Level.SEVERE, null, ex);
            result = false;
        }
        if (!isTest()) {
            StatUtil.instance().endStat(stat);
        }
        try {
            result &= afterMake();
        } catch (UnsupportedOperationException uoe) {

        }
        return result;
    }

    protected boolean beforeMake() {
        boolean result = true;
        try {
            logTime("Начата обработка данных для отчета");
            if (createPathIfNotExists == true) {
                createDirectoryIfNotExists();
            }
            result &= beforePerform();
        } catch (UnsupportedOperationException uoe) {

        } catch (Exception ex) {
            Logger.getLogger(AbstractReport.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    protected boolean afterMake() {
        boolean result = true;
        try {
            logTime("Закончена обработка данных для отчета");
            result &= afterPerform();
        } catch (UnsupportedOperationException uoe) {

        } catch (Exception ex) {
            Logger.getLogger(AbstractReport.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    /**
     * Формирует и записывает файл отчета
     *
     * @return Истина, если файл сформирован и записан, иначе ложь
     */
    @Override
    public boolean makeReportFile() {
        boolean result = true;
        try {
            result &= beforeCreateReportFile();
        } catch (UnsupportedOperationException uoe) {

        }
        result &= createReportFile();
        try {
            result &= afterCreateReportFile();
        } catch (UnsupportedOperationException uoe) {

        }
        return result;
    }

    /**
     * Функция создающая работу по расписанию для выполненияч Quartz
     *
     * @param params параметры выполнения работы по расписанию, н.п дата, час,
     * минута
     */
    @Override
    public void job(Map<String, Object> params) {
        ShedulerJobAbstractReport sjar = new ShedulerJobAbstractReport();
        String jobGroup = "d_" + FormatUtils.formatAsDDMMYYY(new Date()).replace('.', '_');
        sjar.fillJobTriggerInfo(this, new JobAbstractReport(), jobGroup, "mptrigger", "pens");
        //srdi.fillReportParam(request, getParameters());
        sjar.fillSchedulerParameters(params);
        if (sjar.isCorrectFill() == true) {
            sjar.createJob();
        }
    }

    private void logTime(String message) {
        String msg = this.getClass().getName() + "      "
                + FormatUtils.formatAsDDMMYYY_HHMMSS(CommonUtils.now()) + " : " + message;
        log(this.getClass().getName(), msg);
    }

    private void log(String loggerName, String message) {
        Logger.getLogger(loggerName).log(Level.SEVERE, message);
    }

    /**
     * Вызывается до/перед формирования файл отчета и записи его в файловую
     * систему
     *
     * @return Истина, если операции выполнены успешно, иначе ложь
     */
    protected boolean beforeCreateReportFile() {
        boolean result = true;
        logTime("Начато формирование файла отчета");
        return result;
    }

    /**
     * Вызывается после формирования файл отчета и записи его в файловую систему
     *
     * @return Истина, если операции выполнены успешно, иначе ложь
     */
    protected boolean afterCreateReportFile() {
        boolean result = true;
        logTime("Закончено формирование файла отчета");
        return result;
    }

    /**
     * Обрабатывает данные отчета, производит запрос ко всем необходимым БД
     * Должна быть имплементирована в классе
     *
     * @return Истина, если операции выполнены успешно, иначе ложь
     */
    protected abstract boolean perform();

    /**
     * Вызывается до/перед выборки и обработки данных для отчета Должна быть
     * имплементирована в классе
     *
     * @return Истина, если операции выполнены успешно, иначе ложь
     */
    protected abstract boolean beforePerform() throws Exception;

    /**
     * Вызывается после выборки и обработки данных для отчета Должна быть
     * имплементирована в классе
     *
     * @return Истина, если операции выполнены успешно, иначе ложь
     */
    protected abstract boolean afterPerform() throws Exception;

    /**
     * Формирует файл отчета и записывает его в файловую систему Должна быть
     * имплементирована в классе
     *
     * @return Истина, если файл сформирован, иначе ложь
     */
    protected abstract boolean createReportFile();

    protected void print(String message) {

    }

    /**
     * @return the templateDirectory
     */
    @Deprecated
    @Override
    public String getTemplateDirectory() {
        return templateDirectory;
    }

    /**
     * @param reportDirectory the templateDirectory to set
     */
    @Deprecated
    @Override
    public void setTemplateDirectory(String reportDirectory) {
        this.templateDirectory = reportDirectory;
    }

    @Deprecated
    @Override
    public boolean isSuccess() {
        return true;
    }

    /**
     * @return имя файла отчета без полного пути
     */
    @Override
    public String getSimpleFileName() {
        return simpleFileName;
    }

    /**
     * @return папа, где находится/будет находиться файл отчета
     */
    @Override
    public String getDirectory() {
        return directory;
    }

    /**
     * Устанавливает папку, куда будет сохранен отчет и имя файла в этой папке
     * под которым будет сохранен отчет.
     *
     * @param directory Абсолютный путь к папке, в которой должен сохраняться
     * отчет. Указывается без завершающих слэшей Например:
     * C:\\makpsb_report\\example_report\\2016
     *
     * @param simpleFileName Имя файла, в который будет сохранен отчет
     * Указывается без полного пути Например: result_11_11_2016.xls
     */
    @Override
    public void setReportPath(String directory, String simpleFileName) {
        this.directory = directory;
        this.simpleFileName = simpleFileName;
    }

    /**
     * Возвращает полное имя файла сформированного отчета как directory + \\ +
     * simpleFileName
     *
     * @return Возвращает полное имя файла сформированного отчета
     */
    @Override
    public String reportFileName() {
        return getDirectory() + File.separator + getSimpleFileName();
    }

    // проверяет и создает если необходимо папку для отчета
    private void createDirectoryIfNotExists() {
        Path dir = FileSystems.getDefault().getPath(getDirectory());
        if (Files.exists(dir) == false) {
            try {
                Files.createDirectories(dir);
            } catch (IOException ex) {
                Logger.getLogger(AbstractReport.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public Config getConfig() {
        return config;
    }

    public void setConfig(Config config) {
        this.config = config;
    }

    protected EntityManagerFactory createEntityManagerFactory(String persistenceUnitName) {
        boolean configIsNull = config == null;
        EntityManagerFactory emf;

        if (configIsNull || (config.containsEntityManagerFactoryKey(persistenceUnitName)
                && config.getEntityManagerFactory(persistenceUnitName) == null)) {
            emf = Persistence.createEntityManagerFactory(persistenceUnitName);
        } else {
            emf = config.getEntityManagerFactory(persistenceUnitName);
        }

        return emf;
    }

    private boolean isTest() {
        boolean result = !(config == null);
        if (result) {
            result = config.isTest();
        }
        return result;
    }

    /**
     * Возвращает контекстный путь, полученный от сервлета
     *
     * @return Возвращает контекстный путь, полученный от сервлета
     */
    public String getContextPath() {
        return contextPath;
    }

    /**
     * Устанавливает контекстный путь, полученный от сервлета
     */
    public void setContextPath(String contextPath) {
        this.contextPath = contextPath;
    }

    protected String resourceFilePath(String relativePath) {
        String absolutePath = "";
        if (isTest()) {
            ResourceUtil rutil = ResourceUtil.inctance();
            absolutePath = rutil.resourceFilePath(this.getClass(), relativePath);
        } else {
            String rpath = relativePath.startsWith("/") || relativePath.startsWith("\\")
                    ? relativePath.substring(1) : relativePath;
            absolutePath = getContextPath() + rpath;
        }
        return absolutePath;
    }

    /**
     * @return the raionUtil
     */
    public RaionUtil getRaionUtil() {
        if (raionUtil == null) {
            if (config == null) {
                raionUtil = RaionUtil.instance();
            } else {
                raionUtil = RaionUtil.instance(config);
            }
        }
        return raionUtil;
    }

    /**
     * @param raionUtil the raionUtil to set
     */
    public void setRaionUtil(RaionUtil raionUtil) {
        this.raionUtil = raionUtil;
    }

    public JobCalendarUtil getJobCalendarUtil() {
        if (jobCalendarUtil == null) {
            if (config == null) {
                jobCalendarUtil = JobCalendarUtil.instance();
            } else {
                jobCalendarUtil = JobCalendarUtil.instance(config);
            }
        }
        return jobCalendarUtil;
    }

    /**
     * @param jobCalendarUtil the raionUtil to set
     */
    public void setJobCalendarUtil(JobCalendarUtil jobCalendarUtil) {
        this.jobCalendarUtil = jobCalendarUtil;
    }
}
