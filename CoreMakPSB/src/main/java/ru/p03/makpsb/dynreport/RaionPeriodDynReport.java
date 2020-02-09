/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.p03.makpsb.dynreport;

import ru.p03.makpsb.core.report.AbstractRaionPeriodReport;
import ru.p03.makpsb.core.util.PrintUtil;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author timofeevan
 */
public class RaionPeriodDynReport extends AbstractRaionPeriodReport{

    private static final long serialVersionUID = 1L;

    private final ActualDynReport actualDynReport;
    
    private List<Object[]> objectList = new ArrayList();
    
    public RaionPeriodDynReport(ActualDynReport adr){
        super();
        this.actualDynReport = adr;
    }
    
    @Override
    @SuppressWarnings("unchecked")
    protected boolean perform() {
        boolean result = true;
        SQLDynReportJpaController sdrjc = new SQLDynReportJpaController(getActualDynReport());
        String[] split = split(getActualDynReport().getParams());
        if (split.length == 0){
            objectList = sdrjc.list();
        }else{
            boolean isDateBegin = false;
            boolean isDateEnd = false;
            boolean isRaion = false;
            for (String s : split) {
                isDateBegin |= SQLDynReportJpaController.DATE_BEGIN.equalsIgnoreCase(s);
                isDateEnd |= SQLDynReportJpaController.DATE_END.equalsIgnoreCase(s);
                isRaion |= SQLDynReportJpaController.RAION.equalsIgnoreCase(s);
            }
            if (isDateBegin == true && isDateEnd == true && isRaion == true){
                objectList = sdrjc.list(getDateBegin(), getDateEnd(), getRaion());
            }else if (isDateBegin == true && isDateEnd == true && isRaion == false){
                objectList = sdrjc.list(getDateBegin(), getDateEnd());
            }else if (isDateBegin == true && isDateEnd == false && isRaion == false){
                objectList = sdrjc.list(getDateBegin());
            }else if (isDateBegin == false && isDateEnd == false && isRaion == true){
                objectList = sdrjc.list(getRaion());
            }else{
                result = false;
            }
        }
        return result;
    }

    @Override
    protected boolean beforePerform() throws Exception {
        return true;
    }

    @Override
    protected boolean afterPerform() throws Exception {
        return true;
    }

    @Override
    protected boolean createReportFile() {
        boolean result = true;
        String[] columnNames = split(getActualDynReport().getViewColumns());
        result = PrintUtil.inctance().simple_Print(getObjectList(), columnNames, reportFileName(), getActualDynReport().getViewName()); 
        return result;
    }
    
    protected String[] split(String s){
        if (s == null){
            return new String[0];
        }
        String[] split = s.split(",");
        List<String> rspl = new ArrayList<>();
        for (String string : split) {
            string = string.trim();
            if (string.isEmpty() == false){
                rspl.add(string);
            }
        }
        String[] nsplit = new String[rspl.size()];
        nsplit = rspl.toArray(nsplit);
        return nsplit;
    }
    
    /**
     * @return the actualDynReport
     */
    public ActualDynReport getActualDynReport() {
        return actualDynReport;
    }
    
     /**
     * @return the objectList
     */
    protected List<Object[]> getObjectList() {
        return objectList;
    }


}
