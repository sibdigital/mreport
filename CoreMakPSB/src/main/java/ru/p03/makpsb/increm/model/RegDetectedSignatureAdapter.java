/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.p03.makpsb.increm.model;

import java.util.ArrayList;
import java.util.List;
import ru.p03.makpsb.core.entity.dict.Raion;
import ru.p03.makpsb.core.util.RaionUtil;

/**
 *
 * @author timofeevan
 */
public class RegDetectedSignatureAdapter extends RegDetectedSignature{

    /**
     * @return the raion
     */
    public Raion getRaion() {
        return raion;
    }

    /**
     * @param raion the raion to set
     */
    public void setRaion(Raion raion) {
        this.raion = raion;
    }
    
    public String getFio(){
        return getFam() + " " + getIm() + " " + getOtc();
    }

    private static final long serialVersionUID = 1L;
    
    private Raion raion;
    
    public RegDetectedSignatureAdapter(RegDetectedSignature regDetectedSignature){
        super(regDetectedSignature.getId(), regDetectedSignature.getIdRaion(), regDetectedSignature.getIsDeleted(), regDetectedSignature.getTimeDetected(), 
                regDetectedSignature.getTimeCreate(), regDetectedSignature.getSnils(), regDetectedSignature.getFam(), 
                regDetectedSignature.getIm(), regDetectedSignature.getOtc(), regDetectedSignature.getDateBorn(),
                regDetectedSignature.getAddMessage(), regDetectedSignature.getIdSignature());
        
        raion = RaionUtil.instance().byId(regDetectedSignature.getIdRaion());
        this.setTimeFix(regDetectedSignature.getTimeFix());
    }
     
    public static RegDetectedSignatureAdapter build(RegDetectedSignature rds){
        return new RegDetectedSignatureAdapter(rds);
    }
    
    public static List<RegDetectedSignatureAdapter> build(List<RegDetectedSignature> list){
        List<RegDetectedSignatureAdapter> result = new ArrayList<>();
        for (RegDetectedSignature rds : list) {
            result.add(build(rds));
        }
        return result;
    }

}
