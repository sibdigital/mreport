/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.p03.makpsb.dynreport;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import ru.p03.makpsb.core.entity.dict.Raion;
import ru.p03.makpsb.core.util.RaionUtil;
import ru.p03.makpsb.increm.model.ClsSignature;
import ru.p03.makpsb.increm.model.RegDetectedSignature;
import ru.p03.makpsb.increm.repository.RegDetectedSignatureRepository;

/**
 *
 * @author timofeevan
 */
public class RaionPeriodIncremReport extends RaionPeriodDynReport {

    private static final long serialVersionUID = 1L;

    private static final String NUMBER = "nn";
    private static final String RAION = "ra";
    private static final String SNILS = "npers";
    private static final String FAM = "fa";
    private static final String IM = "im";
    private static final String OTC = "ot";
    private static final String DATE_BORN = "rdat";
    private static final String MSG = "msg";

    private Map<String, Integer> fields = new HashMap<>();
    private ClsSignature signature;

//    public RaionPeriodIncremReport(ActualDynReport adr) {
//        super(adr);
//        buildFieldMapping();
//    }
    public RaionPeriodIncremReport(ActualDynReport adr, ClsSignature signature) {
        super(adr);
        this.signature = signature;
        buildFieldMapping();
    }

    @Override
    protected boolean createReportFile() {
        return true;
    }

    @Override
    protected boolean beforePerform() throws Exception {
        if (signature == null) {
            throw new Exception("Signature can not be NULL!");
        }
        return true;
    }

    @Override
    protected boolean afterPerform() {

        RegDetectedSignatureRepository regDetectRepo = new RegDetectedSignatureRepository();

        List<Object[]> objectList = getObjectList();
        int count = 0;
        for (Object[] objects : objectList) {
            RegDetectedSignature detected = new RegDetectedSignature(signature, new Date(), new Date()); //nn,ra,npers,fa,im,ot,rdat,cause
            Set<Integer> usedIndex = new HashSet<>();
            
            if (fields.containsKey(RAION)) {
                Integer index = objects.length > fields.get(RAION) ? fields.get(RAION) : null;
                if (index != null && objects[index] != null) {
                    Raion r = RaionUtil.instance().byNomer(objects[index].toString());
                    detected.setIdRaion(r.getId());
                    usedIndex.add(index);
                }
            }
            if (fields.containsKey(SNILS)) {
                Integer index = objects.length > fields.get(SNILS) ? fields.get(SNILS) : null;
                if (index != null && objects[index] != null) {
                    detected.setSnils(objects[index].toString());
                    usedIndex.add(index);
                }
            }
            if (fields.containsKey(FAM)) {
                Integer index = objects.length > fields.get(FAM) ? fields.get(FAM) : null;
                if (index != null && objects[index] != null) {
                    detected.setFam(objects[index].toString());
                    usedIndex.add(index);
                }
            }
            if (fields.containsKey(IM)) {
                Integer index = objects.length > fields.get(IM) ? fields.get(IM) : null;
                if (index != null && objects[index] != null) {
                    detected.setIm(objects[index].toString());
                    usedIndex.add(index);
                }
            }
            if (fields.containsKey(OTC)) {
                Integer index = objects.length > fields.get(OTC) ? fields.get(OTC) : null;
                if (index != null && objects[index] != null) {
                    detected.setOtc(objects[index].toString());
                    usedIndex.add(index);
                }
            }
            if (fields.containsKey(DATE_BORN)) {
                Integer index = objects.length > fields.get(DATE_BORN) ? fields.get(DATE_BORN) : null;
                if (index != null && objects[index] != null) {
                    detected.setDateBorn((Date) objects[index]);
                    usedIndex.add(index);
                }
            }
            if (fields.containsKey(MSG)) {
                Integer index = objects.length > fields.get(MSG) ? fields.get(MSG) : null;
                if (index != null && objects[index] != null) {
                    detected.setOtc(objects[index].toString());
                    usedIndex.add(index);
                }
            }
            if (fields.containsKey(NUMBER)) {
                Integer index = objects.length > fields.get(NUMBER) ? fields.get(NUMBER) : null;
                if (index != null) {
                    usedIndex.add(index);
                }
            }

            regDetectRepo.create(detected);
            count++;
            if (count % 1000 == 0){
                System.out.println(signature.getCode() + "long writing " + count + " from " + objectList.size());
            }
        }
        return true;
    }

    private void buildFieldMapping() {
        String[] columns = split(getActualDynReport().getColumns());
        for (int i = 0; i < columns.length; i++) {
            String column = columns[i];
            if (NUMBER.equalsIgnoreCase(column)) {
                fields.put(NUMBER, i);
            } else if (RAION.equalsIgnoreCase(column)) {
                fields.put(RAION, i);
            } else if (SNILS.equalsIgnoreCase(column)) {
                fields.put(SNILS, i);
            } else if (FAM.equalsIgnoreCase(column)) {
                fields.put(FAM, i);
            } else if (IM.equalsIgnoreCase(column)) {
                fields.put(IM, i);
            } else if (OTC.equalsIgnoreCase(column)) {
                fields.put(OTC, i);
            } else if (DATE_BORN.equalsIgnoreCase(column)) {
                fields.put(DATE_BORN, i);
            } else if (MSG.equalsIgnoreCase(column)) {
                fields.put(MSG, i);
            }
        }
    }

    /**
     * @return the signature
     */
    protected ClsSignature getSignature() {
        return signature;
    }

    /**
     * @param signature the signature to set
     */
    protected void setSignature(ClsSignature signature) {
        this.signature = signature;
    }

}
