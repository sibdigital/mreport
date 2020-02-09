/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.p03.makpsb.main.servlet;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ru.p03.common.model.DomainUser;
import ru.p03.makpsb.core.file.MonthFileView;
import ru.p03.makpsb.core.report.AbstractReport;
import ru.p03.makpsb.core.resource.Verificator;
import ru.p03.makpsb.core.util.ResourceUtil;
import ru.p03.makpsb.core.util.SchedulerUtils;
import ru.p03.makpsb.main.job.JobAbstractReport;
import ru.p03.makpsb.main.utils.Main;
import ru.p03.makpsb.main.utils.WebConversionUtil;
import ru.p03.makpsb.core.util.Translit;
import ru.p03.vmvp.utils.CommonUtils;
import ru.p03.vmvp.utils.FormatUtils;
import ru.p03.makpsb.core.servlet.MPServlet;
import ru.p03.makpsb.main.utils.ShedulerJobAbstractReport;

/**
 *
 * @author timofeevan
 */
public abstract class MPReportServlet extends MPServlet{

    private static final long serialVersionUID = 1L;

    private boolean enabledJob = true;
    private boolean enabledJobPeriod = true;
    private boolean useDefaultExecution = false;
    private String submitAction = "NO_ACTION";
    private boolean successfullySheduledJob = false;
    private boolean successfullySheduledJobPeriod= false;

    private static final String ENV_STORE_LINK = "java:/comp/env/mpcore/jobReportURL";

    private Class reportClass = null;

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected final void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        boolean result = beforeProcessRequest(request, response);
        if (result == true){
            result = defaultProcessRequest(request, response);
            if (result == true){
                processRequest(request, response);
                afterProcessRequest(request, response);
            }
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected final void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        boolean result = beforeProcessRequest(request, response);
        if (result == true){
            result = defaultProcessRequest(request, response);
            if (result == true){
                processRequest(request, response);
                afterProcessRequest(request, response);
            }
        }
    }

    protected abstract AbstractReport prepareReport(HttpServletRequest request, HttpServletResponse response);

    //protected abstract String page();

    protected String successMessage(){
        return "Операция успешно завершена";
    }

    protected String failMessage(){
        return "Операция не выполнена";
    }

    protected String infoMessage(){
        return "Операция выполняется";
    }

    protected final String infoMessage(HttpServletRequest request, String message){
        request.setAttribute("infoMsg", message);
        return message;
    }

    protected final String successMessage(HttpServletRequest request, String message){
        request.setAttribute("successMsg", message);
        return message;
    }

    protected final String failMessage(HttpServletRequest request, String message){
        Object errorMsg = request.getAttribute("errorMsg");
        String _message = message;
        if (errorMsg != null){
            _message += ("\n" + errorMsg.toString());
        }
        request.setAttribute("errorMsg", _message);
        return _message;
    }

    protected final void message(HttpServletRequest request, HttpServletResponse response, boolean success, boolean fail, boolean info){
        if (success == true){
            successMessage(request, successMessage());
        }
        if (fail == true){
            failMessage(request, infoMessage());
        }
        if (info == true){
            infoMessage(request, infoMessage());
        }
    }

    protected boolean afterProcessRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        boolean result = true;
        if (isDefaultExecution() == true){
            RequestDispatcher dispatcher = request.getRequestDispatcher(page());
            dispatcher.forward(request, response);
        }
        return result;
    }

    protected boolean beforeProcessRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        boolean result = true;
        fillSubmitAction(request);
//        if (request.getSession().getAttribute("user") == null) {
//            //response.sendRedirect(request.getContextPath() + "/login.do");
//            result = false;
//        }
        fillStoreLink(request);
        fillTriggersInfo(request);
        return result;
    }

    private void fillStoreLink(HttpServletRequest request){
        if (getReportClass() != null){
            Object jru = ResourceUtil.inctance().getEnvironment(ENV_STORE_LINK);
            if (jru != null){
                String jrus = jru.toString();
                String sl = jrus + "/" + getReportClass().getSimpleName();
                request.setAttribute("storeLink", sl);
            }
        }
    }

    private void fillTriggersInfo(HttpServletRequest request){
        if (getReportClass() != null){
            List<Map<String, Object>> triggersInfo = SchedulerUtils.getInstance().triggersInfo(getReportClass());
            List<String> list = new ArrayList<>();
            for (Map<String, Object> ti : triggersInfo) {
                list.add("Поставлен в очередь на " + ti.get("nextDate"));
            }
            request.setAttribute("triggersInfo", list);
        }
    }

    protected boolean beforePrepareReport(HttpServletRequest request, HttpServletResponse response){
        boolean result = true;
        return result;
    }

    protected boolean afterPrepareReport(AbstractReport report, HttpServletRequest request, HttpServletResponse response){
        boolean result = true;
        report.setContextPath(getContextPath());
        return result;
    }

    protected final AbstractReport makeReport(HttpServletRequest request, HttpServletResponse response){
        beforePrepareReport(request, response);
        AbstractReport report = prepareReport(request, response);
        afterPrepareReport(report, request, response);
        return report;
    }

    protected boolean defaultProcessRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        boolean result = true;
        if (jobEnable() == true){
            defaultJob(request, response);
        }
        if (jobPeriodEnable() == true){
            defaultPeriodJob(request, response);
        }
        if (isDefaultExecution() == true){
            result = defaultExecute(request, response);
        }
        fillFiles(request, response, null);
        return result;
    }

    private void fillFiles(HttpServletRequest request, HttpServletResponse response, Class clazz){
        if (clazz != null){
            String simpleName = clazz.getSimpleName();
            MonthFileView currentMonthFiles = MonthFileView.getCurrentMonthFiles(simpleName);
            MonthFileView lastMonthFiles = MonthFileView.getLastMonthFiles(simpleName);
            request.setAttribute("currentMonthFiles", currentMonthFiles);
            request.setAttribute("lastMonthFiles", lastMonthFiles);
        }
    }

    private boolean defaultExecute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        boolean result = true;
        if ("create".equals(submitAction())){
            AbstractReport report = executeReport(request, response);
            downloadLink(request, response, report.reportFileName(), true);
            successMessage(request, successMessage());
            result = false;
        }
        selfForward(request, response);
        return result;
    }

    private boolean defaultJob(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        boolean result = true;
        if ("queue".equals(submitAction())){
            job(request, response);
            if (successfullySheduledJob == true){
                infoMessage(request, "Задание(-я) на формирование отчета(-ов) добавлены");
            }else{
                failMessage(request, "При установке задания(-й) в расписание произошла ошибка");
            }
        }
        return result;
    }

    private boolean defaultPeriodJob(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        boolean result = true;
        if ("save_sheduler_period".equals(submitAction())){
            jobPeriod(request, response);
            if (successfullySheduledJobPeriod == true){
                infoMessage(request, "Расписание успешно изменено");
            }else{
                failMessage(request, "При изменении расписания возникли ошибки");
            }
        }
        return result;
    }

    protected void job(HttpServletRequest request, HttpServletResponse response){
        ShedulerJobAbstractReport sjar = new ShedulerJobAbstractReport();
        AbstractReport report = makeReport(request, response);
        String jobGroup = "d_" + FormatUtils.formatAsDDMMYYY(new Date()).replace('.', '_');
        sjar.fillJobTriggerInfo(report, new JobAbstractReport(),  jobGroup, "penstriggernazn", jobGroup);
        Map<String, Object> shedulerParams = WebConversionUtil.instance().getShedulerParams(request);
        sjar.fillSchedulerParameters(shedulerParams);
        successfullySheduledJob = sjar.isCorrectFill();
        if (successfullySheduledJob == true){
            successfullySheduledJob = sjar.createJob();
        }else{
            Logger.getLogger(MPReportServlet.class.getName()).log(Level.SEVERE, "Некорректно заполнены параметры расписания");
            failMessage(request, "Некорректно заполнены параметры расписания");
        }
    }

    protected void jobPeriod(HttpServletRequest request, HttpServletResponse response){

        request.getParameter("hsposob");
        request.getParameter("dayOfMonth");
        request.getParameter("dayOfWeek");
        request.getParameter("hour_period");
        request.getParameter("min_period");
        request.getParameter("periodType");

        ShedulerJobAbstractReport sjar = new ShedulerJobAbstractReport();
        AbstractReport report = makeReport(request, response);
        String jobGroup = "d_" + FormatUtils.formatAsDDMMYYY(new Date()).replace('.', '_');
        sjar.fillJobTriggerInfo(report, new JobAbstractReport(),  jobGroup, "penstriggernazn", jobGroup);
        Map<String, Object> shedulerParams = WebConversionUtil.instance().getShedulerParams(request);
        sjar.fillSchedulerParameters(shedulerParams);
        successfullySheduledJobPeriod = sjar.isCorrectFill();
        if (successfullySheduledJobPeriod == true){
            successfullySheduledJobPeriod = sjar.createJob();
        }else{
            Logger.getLogger(MPReportServlet.class.getName()).log(Level.SEVERE, "Некорректно заполнены параметры расписания");
            failMessage(request, "Некорректно заполнены параметры расписания");
        }
    }

    protected void setSuccessfullySheduledJob (boolean value){
        successfullySheduledJob = value;
    }

    public final boolean jobEnable(){
        return enabledJob;
    }

    public final boolean jobPeriodEnable(){
        return enabledJobPeriod;
    }

    public final void enableJob(boolean state){
        enabledJob = state;
    }

    protected String fillSubmitAction(HttpServletRequest request){
        submitAction = request.getParameter("submit_action");
        return submitAction;
    }

    protected final String submitAction(){
        return submitAction;
    }

    protected final void setDefaultExecution(boolean val){
        useDefaultExecution = val;
    }

    protected final boolean isDefaultExecution(){
        return useDefaultExecution;
    }

    /**
     * Выполнение отчета и создание файла отчета, вызываются report.make(), report.makeReportFile()
     * @param   request       HTTP-запрос
     * @param   response  HTTP-ответ
     * @param   filename  имя файла, предпочтительно указывать report.reportFileName()
     * @param   setRequestAttribute  устанавливать атрибут ответа, предпочтительно true
     * @return ссылка для скачивания
     */
    protected String downloadLink(HttpServletRequest request, HttpServletResponse response, String filename, boolean setRequestAttribute){
        String downloadLink = "/download.do?filename=" + filename;
        if (setRequestAttribute == true){
            request.setAttribute("downloadLink", downloadLink);
        }
        return downloadLink;
    }

    /**
     * Выполнение отчета и создание файла отчета, вызываются report.make(), report.makeReportFile()
     * @param   request       HTTP-запрос
     * @param   response  HTTP-ответ
     * @return отчет после выполнения
     */
    protected AbstractReport executeReport(HttpServletRequest request, HttpServletResponse response){
        String userName = getUserName(request, response);
        String servletPath = getServletPath(request, response);
        AbstractReport report =  null;
        if ( true ||Verificator.instance().operativExecuteAllowed(userName, servletPath)){
            report =  makeReport(request, response);
            boolean success = report.make();   
            request.setAttribute("makeReportStatus", success);
            if (success == true){
                success = report.makeReportFile();
                
                if (success == true){
                    request.setAttribute("simplefile", report.reportFileName());
                }else{
                    failMessage(request, "Невозможно сформировать выходной файл отчета.");
                }
            }else{
                failMessage(request, "Невозможно выполнить выборку данных для отчета.");
            }
        }else{
            report = AbstractReport.getDummyInstance();
            failMessage(request, "Отсутсвют права на формирование отчетов по требованию. Сформируйте отчет с помощью расписания");
        }
        return report;
    }

    /**
     * Установка директории для поиска шаблонов вложэенных отчетов (подотчетов)
     * @param   report       Отчет
     * @param   reportName  Имя файла (без пути к файлу и расширения)
     * @param   ext  расширение
     */
    protected void setReportFilePath(AbstractReport report, String reportName, String ext){
        String tmpdir = ((File)getServletContext().getAttribute("javax.servlet.context.tempdir")).getAbsolutePath();
        String tempFileName = Main.getTempFileName("", Translit.instance().translate(reportName),
                FormatUtils.formatAsDDMMYYY(CommonUtils.now())) + ext;
        report.setReportPath(tmpdir, tempFileName);
    }

    /**
     * Установка директории для поиска шаблонов вложэенных отчетов (подотчетов)
     * @param   report       Отчет
     * @param   directory  Директория
     */
    protected void setReportTemplateDirectory(AbstractReport report, String directory){
        String contextPath = getContextPath();
        report.setTemplateDirectory(contextPath + directory);
    }

    /**
     * Обработка действия на создание отчета submit_action = create
     * @param   request       HTTP-запрос
     * @param   response  HTTP-ответ
     */
    protected void processCreate(HttpServletRequest request, HttpServletResponse response){
        if ("create".equals(submitAction())){
            AbstractReport report = executeReport(request, response);
            Object makeReportStatus = request.getAttribute("makeReportStatus");
            if (makeReportStatus != null && ("true".equalsIgnoreCase(makeReportStatus.toString()))){
                downloadLink(request, response, report.reportFileName(), true);
                successMessage(request, "Отчет сформирован");
            }else{
                failMessage(request, "Ошибка при формировании отчета.");
            }
        }
    }

    protected final String getUserName(HttpServletRequest request, HttpServletResponse response){
        String username =" ";
        DomainUser au = getUser(request, response);
        username = au.getLogin();
        return username;
    }

    protected final String getServletPath(HttpServletRequest request, HttpServletResponse response){
        String servletPath = request.getServletPath();
        return servletPath;
    }

    /**
     * @return the reportClass
     */
    public Class getReportClass() {
        return reportClass;
    }

    /**
     * @param reportClass the reportClass to set
     */
    public void setReportClass(Class reportClass) {
        this.reportClass = reportClass;
    }

    protected final String getContextPath(){
        ServletContext servletContext = getServletContext();
	String contextPath = servletContext.getRealPath(File.separator);
        if (contextPath == null){
            contextPath = servletContext.getRealPath("/");
        }
        return contextPath;
    }

}
