/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.p03.makpsb.core.entity.base;

import ru.p03.makpsb.increm.repository.*;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author timofeevan
 */
public class MPDAO {

    private static MPDAO DAO = null;

    private static final long serialVersionUID = 1L;

    public static final String CORE_MAK_PSB_PU = "CoreMakPSBPU";

    private EntityManagerFactory emf = null;

    public EntityManagerFactory getEntityManagerFactory() {
        if (emf == null) {
            emf = Persistence.createEntityManagerFactory(CORE_MAK_PSB_PU);
        }
        return emf;
    }

    public static MPDAO instance() {
        if (DAO == null) {
            DAO = new MPDAO();
        }
        return DAO;
    }
}
