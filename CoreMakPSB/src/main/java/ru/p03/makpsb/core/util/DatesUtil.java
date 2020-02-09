/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.p03.makpsb.core.util;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Set;
import org.joda.time.DateTime;
import org.joda.time.Days;

/**
 *
 * @author timofeevan
 */
public class DatesUtil {

    private DatesUtil() {

    }

    public static DatesUtil instance() {
        return new DatesUtil();
    }

    public Integer getWorkingDays(Date resh, Date obr, Set<Date> births) {
        int days = 0;
        int minus = 0;
        if (resh != null && obr != null) {
            Date dob = dateWithoutTime(obr);
            while (dob.compareTo(dateWithoutTime(resh)) <= 0) {
                days++;
                if (births.contains(dateWithoutTime(dob)) || isWeekEnd(dob)) {  // тут следует иметь ввиду, что есть еще переносы рабочих дней с понедельника допустим на субботу и субботу надо считать как рабочий день, а понедельника как выходной
                    minus++;
                }
                dob = addDays(dob, 1);
            }

            Date dresh = dateWithoutTime(resh);
            if ((isWeekEnd(resh) || births.contains(dresh))) {
                minus--;
            }
        }
        return days - minus;
    }

    public Date addDays(Date d, int count) {
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        c.add(Calendar.DAY_OF_MONTH, count);
        return c.getTime();
    }

    public Integer getWorkingDays(Object resh, Object obr, Set<Date> births) {
        return getWorkingDays((Date) resh, (Date) obr, births);
    }

    public Date dateWithoutTime(Date dateTime) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateTime);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        Date date = new Date();
        date.setTime(calendar.getTime().getTime());

        return date;
    }

    public boolean isWeekEnd(Date d) {
        Integer dayOfWeek = getDayOfWeek(d);
        return dayOfWeek == Calendar.SUNDAY || dayOfWeek == Calendar.SATURDAY;
    }

    public Integer getDayOfWeek(Date d) {
        GregorianCalendar c = new GregorianCalendar();
        c.setTime(d);
        return c.get(GregorianCalendar.DAY_OF_WEEK);
    }

    @Deprecated
    public Integer getCalendarDays(Date evd, Date obr) {
        int days = 0;
        if (evd != null && obr != null) {
            DateTime end = new DateTime(toBedinDay(evd).getTime());
            DateTime begin = new DateTime(toBedinDay(obr).getTime());
            Days d = Days.daysBetween(begin, end);
            days = d.getDays();
        }
        return days;
    }

    @Deprecated
    private Date toBedinDay(Date d) {
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        c = toBedinDayCalendar(c);
        return c.getTime();
    }

    @Deprecated
    private Calendar toBedinDayCalendar(Calendar c) {
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c;
    }

}
