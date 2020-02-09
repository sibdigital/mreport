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
import ru.p03.makpsb.core.entity.dict.DepartAdress;
import ru.p03.makpsb.core.entity.dict.Raion;
import ru.p03.makpsb.core.entity.dict.controller.DepartAdressJpaController;
import ru.p03.vmvp.utils.ConversionUtils;

/**
 *
 * @author timofeevan
 */
public class DepartAdressUtil {

    protected final String persistenceUnit = "CoreMakPSBPU";

    protected List<DepartAdress> adresses = new ArrayList<>();

    private DepartAdressUtil(){

    }

    public List<DepartAdress> adresses(){
        if (adresses == null || adresses.isEmpty() == true){
            EntityManagerFactory emf = Persistence.createEntityManagerFactory(persistenceUnit);
            DepartAdressJpaController controller = new DepartAdressJpaController(emf);
            adresses = controller.findDepartAdressEntities();
        }
        return adresses;
    }

    public DepartAdress byName (String raionName){
        DepartAdress r = null;
        Raion raion = RaionUtil.instance().byName(raionName);
        r = byRaion (raion);
        return r;
    }

    public DepartAdress byNomer (String nomer){
        return byNomer(ConversionUtils.toInteger(nomer));
    }
    
    public String name (DepartAdress departAdress){
        String result = "";
        if (departAdress != null){
            List<Raion> raions = RaionUtil.instance().raions();
            for (Raion r : raions) {
                if (Objects.equals(departAdress.getIdRaion(), r.getId()) == true){
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
    
    public DepartAdress byNomer (Integer raionNomer){
        DepartAdress r = null;
        Raion raion = RaionUtil.instance().byNomer(raionNomer);
        r = byRaion (raion);
        return r;
    }
    
    public DepartAdress byRaion (Raion raion){
        DepartAdress r = null;
        if (raion != null){
            for (DepartAdress adr : adresses()) {
                if (Objects.equals(adr.getIdRaion(), raion.getId()) == true){
                    r = adr;
                    break;
                }
            }
        }
        return r;
    }

    private static DepartAdressUtil INSTANCE = null;

    public static DepartAdressUtil instance(){
        if (INSTANCE == null){
            INSTANCE = new DepartAdressUtil();
        }
        return INSTANCE;
    }
}
