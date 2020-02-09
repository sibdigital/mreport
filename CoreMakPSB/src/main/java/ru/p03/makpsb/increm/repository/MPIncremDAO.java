/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.p03.makpsb.increm.repository;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author timofeevan
 */
public class MPIncremDAO {

    private static MPIncremDAO DAO = null;

    private static final long serialVersionUID = 1L;

    protected final String persistenceUnit = "MPIncrem";

    private EntityManagerFactory emf = null;

    public EntityManagerFactory getEntityManagerFactory() {
        if (emf == null) {
            emf = Persistence.createEntityManagerFactory(persistenceUnit);
        }
        return emf;
    }

    public static MPIncremDAO instance() {
        if (DAO == null) {
            DAO = new MPIncremDAO();
        }
        return DAO;
    }
}
