/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.p03.makpsb.core.util;

import java.util.Objects;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import ru.p03.makpsb.core.entity.dict.LeaderInfo;
import ru.p03.makpsb.core.entity.dict.Raion;
import ru.p03.makpsb.core.entity.dict.controller.LeaderInfoJpaController;
import ru.p03.vmvp.utils.ConversionUtils;

/**
 *
 * @author timofeevan
 */
public class LeaderInfoUtil {

    protected final String persistenceUnit = "CoreMakPSBPU";

    protected List<LeaderInfo> adresses = new ArrayList<>();

    private LeaderInfoUtil(){

    }

    public List<LeaderInfo> leaders(){
        if (adresses == null || adresses.isEmpty() == true){
            EntityManagerFactory emf = Persistence.createEntityManagerFactory(persistenceUnit);
            LeaderInfoJpaController controller = new LeaderInfoJpaController(emf);
            adresses = controller.list();
        }
        return adresses; 
    }    

    public LeaderInfo byName (String raionName){
        LeaderInfo r = null;
        Raion raion = RaionUtil.instance().byName(raionName);
        r = byRaion (raion);
        return r;
    }

    public LeaderInfo byNomer (String nomer){
        return byNomer(ConversionUtils.toInteger(nomer));
    }
    
    public String name (LeaderInfo leaderInfo){
        String result = "";
        if (leaderInfo != null){
            List<Raion> raions = RaionUtil.instance().raions();
            for (Raion r : raions) {
                if (Objects.equals(leaderInfo.getIdRaion(), r.getId()) == true){
                    result = r.getName();
                    break;
                }
            }
        }
        return result;
    }
    
    public String name (String nomer){
        return name(byNomer(nomer));
    }
    
    public String name (Integer nomer){
        return name(byNomer(nomer));
    }
    
    public LeaderInfo byNomer (Integer raionNomer){
        LeaderInfo r = null;
        Raion raion = RaionUtil.instance().byNomer(raionNomer);
        r = byRaion (raion);
        return r;
    }
    
    public LeaderInfo byRaion (Raion raion){
        LeaderInfo r = null;
        if (raion != null){
            for (LeaderInfo adr : leaders()) {
                if (Objects.equals(adr.getIdRaion(), raion.getId()) == true){
                    r = adr;
                    break;
                }
            }
        }
        return r;
    }

    private static LeaderInfoUtil INSTANCE = null;

    public static LeaderInfoUtil instance(){
        if (INSTANCE == null){
            INSTANCE = new LeaderInfoUtil();
        }
        return INSTANCE;
    }
}
