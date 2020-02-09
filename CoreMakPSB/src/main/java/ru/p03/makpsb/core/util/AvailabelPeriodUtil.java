/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.p03.makpsb.core.util;

import ru.p03.makpsb.core.entity.dict.AvailablePeriod;
import ru.p03.makpsb.core.entity.dict.controller.AvailablePeriodJpaController;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.*;

/**
 *
 * @author timofeevan
 */
public class AvailabelPeriodUtil {

    protected static final String persistenceUnit = "CoreMakPSBPU";

    private static EntityManagerFactory emf;

    private AvailabelPeriodUtil() {

    }

    public static AvailabelPeriodUtil instance() {
        return new AvailabelPeriodUtil();
    }

    private static EntityManagerFactory getEmf() {
        if (emf == null) {
            emf = Persistence.createEntityManagerFactory(persistenceUnit);
        }
        return emf;
    }

    private List<AvailablePeriod> periods(Date d) {
        //EntityManagerFactory emf = Persistence.createEntityManagerFactory(persistenceUnit);
        AvailablePeriodJpaController controller = new AvailablePeriodJpaController(getEmf());
        return controller.getAvailablePeriod(d);
    }

    private List<AvailablePeriod> periods(Integer dayOfWeek) {
        AvailablePeriodJpaController controller = new AvailablePeriodJpaController(getEmf());
        return controller.getAvailablePeriod(dayOfWeek);
    }

    private List<AvailablePeriod> periods() {
        AvailablePeriodJpaController controller = new AvailablePeriodJpaController(getEmf());
        return controller.getAvailablePeriod();
    }

    public List<Integer> availableHours(Date d) {
//        List<AvailablePeriod> periods = periods(d);
        Set<Integer> result = new HashSet<Integer>();
        List<Integer> result1 = new ArrayList<Integer>();
//        for (AvailablePeriod period : periods) {
//            Integer hourBegin = period.getHourBegin();
//            Integer hourEnd = period.getHourEnd();
//            while (hourBegin <= hourEnd) {
//                result.add(hourBegin);
//                ++hourBegin;
//            }
//        }
        Integer hourBegin = 0;
        Integer hourEnd = 23;
        while (hourBegin <= hourEnd) {
            result.add(hourBegin);
            ++hourBegin;
        }
        result1.addAll(result);
        Collections.sort(result1);
        return result1;
    }

    public List<Integer> availableMinutes(Date d) {
        //List<AvailablePeriod> periods = periods(d);
        Set<Integer> result = new HashSet<Integer>();
        List<Integer> result1 = new ArrayList<Integer>();
//        for (AvailablePeriod period : periods) {
//            Integer minuteBegin = period.getMinuteBegin();
//            Integer minuteEnd = period.getMinuteEnd();
//            while (minuteBegin <= minuteEnd) {
//                result.add(minuteBegin);
//                minuteBegin += 3;
//            }
//        }
        Integer minuteBegin = 0;
        Integer minuteEnd = 58;
        while (minuteBegin <= minuteEnd) {
            result.add(minuteBegin);
            minuteBegin += 3;
        }
        result1.addAll(result);
        Collections.sort(result1);
        return result1;
    }

    public List<Integer> availableHours(Integer dayOfWeek) {
        //List<AvailablePeriod> periods = periods(dayOfWeek);
        Set<Integer> result = new HashSet<Integer>();
        List<Integer> result1 = new ArrayList<Integer>();
//        for (AvailablePeriod period : periods) {
//            Integer hourBegin = period.getHourBegin();
//            Integer hourEnd = period.getHourEnd();
//            while (hourBegin <= hourEnd) {
//                result.add(hourBegin);
//                ++hourBegin;
//            }
//        }
        Integer hourBegin = 0;
        Integer hourEnd = 23;
        while (hourBegin <= hourEnd) {
            result.add(hourBegin);
            ++hourBegin;
        }
        result1.addAll(result);
        Collections.sort(result1);
        return result1;
    }

    public List<Integer> availableMinutes(Integer dayOfWeek) {
        //List<AvailablePeriod> periods = periods(dayOfWeek);
        Set<Integer> result = new HashSet<Integer>();
        List<Integer> result1 = new ArrayList<Integer>();
//        for (AvailablePeriod period : periods) {
//            Integer minuteBegin = period.getMinuteBegin();
//            Integer minuteEnd = period.getMinuteEnd();
//            while (minuteBegin <= minuteEnd) {
//                result.add(minuteBegin);
//                minuteBegin += 2;
//            }
//        }
        Integer minuteBegin = 0;
        Integer minuteEnd = 58;
        while (minuteBegin <= minuteEnd) {
            result.add(minuteBegin);
            minuteBegin += 3;
        }
        result1.addAll(result);
        Collections.sort(result1);
        return result1;
    }

    public List<Integer> availableHours() {
        List<AvailablePeriod> periods = periods();
        Set<Integer> result = new HashSet<>();
        List<Integer> result1 = new ArrayList<>();
        Integer maxHourBegin = 0;
        Integer minHourEnd = 24;
        Set<Integer> begins = new HashSet<>();
        Set<Integer> ends = new HashSet<>();
        for (AvailablePeriod period : periods) {
            Integer hourBegin = period.getHourBegin();
            Integer hourEnd = period.getHourEnd();
            begins.add(hourBegin);
            ends.add(hourEnd);
        }
        maxHourBegin = Collections.max(begins);
        minHourEnd = Collections.min(ends);
        for (Integer h : Arrays.asList(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,18,19,20,21,22,23,0)) {
            if (h >= maxHourBegin && h <= minHourEnd){
                result.add(h);
            }
        }
        result1.addAll(result);
        Collections.sort(result1);
        return result1;
    }

    public List<Integer> availableMinutes() {
        List<AvailablePeriod> periods = periods();
        Set<Integer> result = new HashSet<>();
        List<Integer> result1 = new ArrayList<>();
        Integer maxMinBegin = 0;
        Integer minMinEnd = 60;
        Set<Integer> begins = new HashSet<>();
        Set<Integer> ends = new HashSet<>();
        for (AvailablePeriod period : periods) {
            Integer minuteBegin = period.getMinuteBegin();
            Integer minuteEnd = period.getMinuteEnd();
            begins.add(minuteBegin);
            ends.add(minuteEnd);
        }
        maxMinBegin = Collections.max(begins);
        minMinEnd = Collections.min(ends);
        for (Integer h : Arrays.asList(1,3,5,7,9,11,13,15,17,19,21,23,25,27,29,31,33,35,37,39,41,43,45,47,49,51,53,55,57,59)) {
            if (h >= maxMinBegin && h <= minMinEnd){
                result.add(h);
            }
        }
        result1.addAll(result);
        Collections.sort(result1);
        return result1;
    }
}
