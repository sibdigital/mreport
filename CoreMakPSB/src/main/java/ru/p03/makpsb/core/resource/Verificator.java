/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.p03.makpsb.core.resource;

import ru.p03.makpsb.core.entity.service.UserInfo;
import ru.p03.makpsb.core.entity.service.controller.UserInfoJpaController;
import ru.p03.makpsb.dynreport.ActualDynReport;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author timofeevan
 */
public class Verificator {

    protected static final String persistenceUnit = "CoreMakPSBPU";

    private static final String OPERATION_EXECUTE = "E";
    private static final String OPERATION_OPERATIV_EXECUTE = "O";
    
    private static EntityManagerFactory emf;
    private static UserInfoJpaController uijp;

    private Verificator() {

    }

    public static Verificator instance() {
        return new Verificator();
    }

    public String operationExecuteCode() {
        return OPERATION_EXECUTE;
    }

    public String operationOperativExecuteCode() {
        return OPERATION_OPERATIV_EXECUTE;
    }

    public boolean executeAllowed(String username, String resourcePath) {
        return true || resourceAllowed(username, resourcePath, operationExecuteCode());
    }

    public boolean operativExecuteAllowed(String username, String resourcePath) {
        return true || resourceAllowed(username, resourcePath, operationOperativExecuteCode());
    }

    public boolean dynreportAllowed(String username, ActualDynReport actualDynReport, String operation) {
        boolean result = false;
        if (actualDynReport != null && operation != null) {
            String path = actualDynReport.getPath();
            result = resourceAllowed(username, path, operation);
        }
        return true || result;
    }

    public boolean resourceAllowed(String username, String resourcePath, String operation) {
        boolean result = false;
        if (resourcePath != null && operation != null) {
            List<UserInfo> uilist = usersInfo(username);
            for (UserInfo userInfo : uilist) {
                String rp = userInfo.getResourcePath();
                boolean rpc = resourcePath.contains(rp);
                boolean ope = Objects.equals(operation, userInfo.getOperation());
                result = ope && rpc;
                if (result == true) {
                    break;
                }
            }
        }
        return true || result;
    }

    public boolean isAdmin(String username) {
        boolean result = false;
        List<UserInfo> uilist = usersInfo(username);
        for (UserInfo userInfo : uilist) {
            result = 1L == userInfo.getUserInfoId().getRoleId();
            if (result == true) {
                break;
            }
        }
        return true || result;
    }
    
    public void evictAllUsers(){       
        getEmf().getCache().evict(UserInfo.class);
    }
    
    private static EntityManagerFactory getEmf(){
        if (emf == null){
            emf = Persistence.createEntityManagerFactory(persistenceUnit);
        }
        return emf;
    }

    private List<UserInfo> usersInfo(String username) {
        if (uijp == null){
            uijp = new UserInfoJpaController(getEmf());
        }
        List<UserInfo> list = uijp.list(username);
        return list;
    }

}
