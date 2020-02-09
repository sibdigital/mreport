/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.p03.makpsb.core.util;

import java.util.Objects;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import ru.p03.makpsb.core.entity.base.Config;
import ru.p03.makpsb.core.entity.base.MPDAO;
import ru.p03.makpsb.core.entity.dict.Raion;
import ru.p03.makpsb.core.entity.dict.controller.RaionJpaController;
import ru.p03.vmvp.utils.ConversionUtils;

/**
 *
 * @author timofeevan
 */
public class RaionUtil {

    protected final String persistenceUnit;

    protected List<Raion> raions = new ArrayList<>();

    private EntityManagerFactory emf;

    private final Config config;

    private RaionUtil() {
        this.config = null;
        persistenceUnit = MPDAO.CORE_MAK_PSB_PU;
    }

    private RaionUtil(Config config) {
        this.config = config;
        Set<String> puns = config.getPersistenceUnitNames();
        
        String pun = MPDAO.CORE_MAK_PSB_PU;
        for (String p : puns) {
            try { // в качестве юнита возьмем юнит с тем именеем, на котром сможет выполниться выборка районов. костыль
                //EntityManagerFactory testEmf = config.getEntityManagerFactory(p);
                RaionJpaController controller = new RaionJpaController(getEmf(p));
                raions = controller.findRaionEntities();
                pun = p;
                break;
            } catch (Exception ex) {

            }
        }
        persistenceUnit = pun;
    }

    private EntityManagerFactory getEmf() {
        return getEmf(persistenceUnit);
    }
    
    private EntityManagerFactory getEmf(String persistenceUnit) {
        if (emf == null) {
            if (config == null) {
                emf = Persistence.createEntityManagerFactory(persistenceUnit);
            } else {
                emf = createEntityManagerFactory(persistenceUnit);
            }
        }
        return emf;
    }

    private EntityManagerFactory createEntityManagerFactory(String persistenceUnitName) {
        boolean configIsNull = config == null;
        EntityManagerFactory _emf;

        if (configIsNull || (config.containsEntityManagerFactoryKey(persistenceUnitName)
                && config.getEntityManagerFactory(persistenceUnitName) == null)) {
            _emf = Persistence.createEntityManagerFactory(persistenceUnitName);
        } else {
            _emf = config.getEntityManagerFactory(persistenceUnitName);
        }

        return _emf;
    }

    public List<Raion> raions() {
        if (raions == null || raions.isEmpty() == true) {
            //EntityManagerFactory emf = Persistence.createEntityManagerFactory(persistenceUnit);
            RaionJpaController controller = new RaionJpaController(getEmf());
            raions = controller.findRaionEntities();
        }
        return raions;
    }

    public Raion byName(String name) {
        Raion r = null;
        if (name != null) {
            for (Raion raion : raions()) {
                if (name.equalsIgnoreCase(raion.getName())) {
                    r = raion;
                    break;
                }
            }
        }
        return r;
    }

    public Raion byNomer(String nomer) {
        if (nomer != null && nomer.isEmpty() == false) {
            return byNomer(ConversionUtils.toInteger(nomer));
        } else {
            return null;
        }
    }

    public String name(Raion raion) {
        String result = "";
        if (raion != null) {
            result = raion.getName();
        }
        return result;
    }

    public String name(String nomer) {
        return name(byNomer(nomer));
    }

    public String name(Integer nomer) {
        return name(byNomer(nomer));
    }

    public Raion byNomer(Integer nomer) {
        Raion r = null;
        if (nomer != null) {
            for (Raion raion : raions()) {
                if (Objects.equals(nomer, raion.getNomer())) {
                    r = raion;
                    break;
                }
            }
        }
        return r;
    }

    public Raion byId(Long id) {
        Raion r = null;
        if (id != null) {
            for (Raion raion : raions()) {
                if (Objects.equals(id, raion.getId())) {
                    r = raion;
                    break;
                }
            }
        }
        return r;
    }

    public List<String> names() {
        List<String> names = new ArrayList<>();
        for (Raion raion : raions()) {
            names.add(raion.getName());
        }
        return names;
    }

    public List<Integer> nomers() {
        List<Integer> names = new ArrayList<>();
        for (Raion raion : raions()) {
            names.add(raion.getNomer());
        }
        return names;
    }

    private static RaionUtil INSTANCE = null;

    public static RaionUtil instance() {
        if (INSTANCE == null) {
            INSTANCE = new RaionUtil();
        }
        return INSTANCE;
    }

    public static RaionUtil instance(Config config) {
        return new RaionUtil(config);
    }
}
