/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.p03.makpsb.increm.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ru.p03.makpsb.core.entity.quartz.QrtzTriggers;
import ru.p03.makpsb.core.entity.quartz.repository.QrtzTriggersJpaController;
import ru.p03.makpsb.core.report.AbstractReport;
import ru.p03.makpsb.core.servlet.MPServlet;
import ru.p03.makpsb.core.util.SchedulerUtils;
import ru.p03.makpsb.dynreport.ActualDynReport;
import ru.p03.makpsb.dynreport.ActualDynReportJpaController;
import ru.p03.makpsb.dynreport.RaionPeriodIncremReport;
import ru.p03.makpsb.increm.model.ClsDetectPeriod;
import ru.p03.makpsb.increm.model.ClsPeriodType;
import ru.p03.makpsb.increm.model.ClsSignatureType;
import ru.p03.makpsb.increm.model.SignatureTiming;
import ru.p03.makpsb.increm.repository.ClsssifierRepository;
import ru.p03.makpsb.increm.repository.DetectPeriodRepository;
import ru.p03.makpsb.increm.utils.JobIncremBuilder;
import ru.p03.makpsb.main.job.JobAbstractReport;
import ru.p03.vmvp.utils.ConversionUtils;
import ru.p03.vmvp.utils.FormatUtils;
import ru.p03.makpsb.main.utils.MPConversion;
/**
 *
 * @author timofeevan
 */
@WebServlet(name = "SignatureElementServlet", urlPatterns = {"/main/signature_element.do"})
public class SignatureElementServlet extends MPServlet {

    private static final long serialVersionUID = 1L;

    final String PAGE = "/jsp/increm/signature_list.jsp";
    final String ELEM_PAGE = "/jsp/increm/signature.jsp";
    final String LIST = "signList";
    final String ELEMENT = "signatureElement";

    private SignatureTiming signatureTiming;
    private ClsssifierRepository classifierRepo;
    private List<ClsPeriodType> listPeriodType;
    private List<ClsSignatureType> listSignatureType;

    protected String page() {
        return ELEM_PAGE;
    }

    @Override
    public void init() throws ServletException {
        classifierRepo = new ClsssifierRepository();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        listPeriodType = classifierRepo.getClsPeriodType();
        listSignatureType = classifierRepo.getClsSignatureType();
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setAttribute("listPeriodType", listPeriodType);
        request.setAttribute("listSignatureType", listSignatureType);
        
        if ("open".equals(request.getParameter("action")) == true) {
            
            ClsssifierRepository classifierRepo = new ClsssifierRepository();
            String sid = request.getParameter("id");
            long id = ConversionUtils.toInteger(sid);
            signatureTiming = classifierRepo.getSignatureTiming(id);
            fillTriggersInfo(request);
            
            if (signatureTiming != null) {
                request.setAttribute(ELEMENT, signatureTiming);
                RequestDispatcher dispatcher = request.getRequestDispatcher(ELEM_PAGE);
                dispatcher.forward(request, response);
            }
        }
        if ("edit".equals(request.getParameter("submit_action")) == true) {

            Integer dayOfMonth = ConversionUtils.toInteger(request.getParameter("dayOfMonth"));
            Integer hour = ConversionUtils.toInteger(request.getParameter("hour"));
            Integer minute = ConversionUtils.toInteger(request.getParameter("minute"));
            Integer isActive = MPConversion.instance().toBoolean(request.getParameter("isActive")) ? 1 : 0;

            if (signatureTiming != null) {

                if (dayOfMonth != null) {
                    if (dayOfMonth != -1) {
                        signatureTiming.setDayOfMonth(dayOfMonth);
                    } else {
                        signatureTiming.setDayOfMonth(null);
                    }
                }
                if (hour != null) {
                    signatureTiming.setHour(hour);
                }
                if (minute != null) {
                    signatureTiming.setMinute(minute);
                }
                if (isActive != null) {
                    signatureTiming.setIsActive(isActive);
                }

                editSignature(signatureTiming);
                
                response.sendRedirect(request.getRequestURI() + "?action=open&id=" + signatureTiming.getId());
            }
        }
    }

    private void editSignature(SignatureTiming signatureTiming) {
        DetectPeriodRepository dpRepo = new DetectPeriodRepository();
        try {
            dpRepo.editSignature(signatureTiming);
            ClsDetectPeriod cdp = dpRepo.findClsDetectPeriod(signatureTiming);

            if (cdp.getIsActive() == 0) {
                SchedulerUtils.getInstance().deleteJob(cdp.getIdSignature().getCode(),
                        cdp.getIdSignature().getIdSignatureType().getCode());
            } else {

                AbstractReport report = createDynReport(cdp);

                JobIncremBuilder builder = new JobIncremBuilder();
                String dayOfMonth = cdp.getDayOfMonth() == null ? null : cdp.getDayOfMonth().toString();
                String dayOfWeek = cdp.getDayOfWeek() == null ? null : cdp.getDayOfWeek().toString();

                if (ClsPeriodType.DAILY.equals(cdp.getIdPeriodType().getCode())) {
                    dayOfWeek = "1-7";
                    dayOfMonth = "?";
                }

                boolean successfullySheduledJob = builder.setReport(report)
                        .setJobAbstractReport(new JobAbstractReport())
                        .setJobClass(JobAbstractReport.class)
                        .setJobGroup(cdp.getIdSignature().getIdSignatureType().getCode())
                        .setTriggerGroup(cdp.getIdSignature().getIdSignatureType().getCode())
                        .setJobName(cdp.getIdSignature().getCode())
                        .setTriggerName(cdp.getIdSignature().getCode())
                        .setDayOfMonth(dayOfMonth)
                        .setDayOfWeek(dayOfWeek)
                        .setHour(cdp.getHour())
                        .setMinute(cdp.getMinute())
                        .buildAndSchedule();
            }
        } catch (Exception ex) {
            Logger.getLogger(SignatureElementServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private AbstractReport createDynReport(ClsDetectPeriod cdp) throws Exception {
        ActualDynReportJpaController adrjc = new ActualDynReportJpaController();
        ActualDynReport adr = adrjc.findActualDynReport(cdp.getIdSignature().getCodeDynreport());
        if (adr == null) {
            throw new Exception("No dyn report");
        }
        RaionPeriodIncremReport rpdr = new RaionPeriodIncremReport(adr, cdp.getIdSignature());
        return rpdr;
    }
    
    private void fillTriggersInfo(HttpServletRequest request){
        if (signatureTiming != null){
            DetectPeriodRepository dpRepo = new DetectPeriodRepository();
            ClsDetectPeriod cdp = dpRepo.findClsDetectPeriod(signatureTiming);
            List<Map<String, Object>> triggersInfo = SchedulerUtils.getInstance().triggersInfo(cdp.getIdSignature().getCode());
            List<String> list = new ArrayList<>();
            for (Map<String, Object> ti : triggersInfo) {
                list.add("Проверка выполняется " + ti.get("nextDate"));
            }
            request.setAttribute("triggersInfo", list);
            
            QrtzTriggersJpaController triggerRepo = new QrtzTriggersJpaController();
            List<QrtzTriggers> qrtzTriggers = triggerRepo.getQrtzTriggers(cdp.getIdSignature().getCode());
            if (!qrtzTriggers.isEmpty()){
                request.setAttribute("nextFireTime", FormatUtils.formatAsDDMMYYY_HHMMSS(new Date(qrtzTriggers.get(0).getNextFireTime().longValue())));
                request.setAttribute("prevFireTime", FormatUtils.formatAsDDMMYYY_HHMMSS(new Date(qrtzTriggers.get(0).getPrevFireTime().longValue())));
            }
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
