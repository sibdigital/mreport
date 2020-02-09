/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  altmf
 * Created: 08.02.2017
 */
select ri.VISIBLE_NAME, ri.CLASS_NAME,  raw.* from (
    select resource,
        COALESCE(MONTH(S.TIME_BEGIN), 0) AS RES_MONTH, 
        COALESCE(DAYOFWEEK(S.TIME_BEGIN), 0) AS RES_DAYWEEK,
        COALESCE(TIMESTAMPDIFF(2, CHAR(TIME_END - TIME_BEGIN)), 0) AS DURATION_SECONDS,
        S.TIME_END,
        S.TIME_BEGIN
    from makpsb.STAT AS S
) as raw
left join (select * from makpsb.REPORT_INFO as ri where NOT ri.CLASS_NAME LIKE '%DynReport%' AND NOT ri.CLASS_NAME = '') as ri on raw.resource like CONCAT('%',ri.CLASS_NAME) 
where res_month in (1, 12, 11, 2)
and (
(hour(TIME_BEGIN) >= 9 ) AND (hour(TIME_BEGIN) <= 16 ) 
or (hour(TIME_BEGIN) = 8 ) AND (minute(TIME_BEGIN) >= 29 ) 
or (hour(TIME_BEGIN) = 17 ) AND (minute(TIME_BEGIN) <= 29 )
)
order by resource, res_month
;
select resource, res_month, count(resource) as cnt, avg(DURATION_SECONDS) as AVG_DURATION_SECONDS from (
    select resource,
        COALESCE(MONTH(S.TIME_BEGIN), 0) AS RES_MONTH, 
        COALESCE(DAYOFWEEK(S.TIME_BEGIN), 0) AS RES_DAYWEEK,
        COALESCE(TIMESTAMPDIFF(2, CHAR(TIME_END - TIME_BEGIN)), 0) AS DURATION_SECONDS,
        S.TIME_END,
        S.TIME_BEGIN
    from makpsb.STAT AS S
) as raw
where res_month in (1, 12, 11, 2)
-- and (
-- (hour(TIME_BEGIN) >= 9 ) AND (hour(TIME_BEGIN) <= 16 ) 
-- or (hour(TIME_BEGIN) = 8 ) AND (minute(TIME_BEGIN) >= 29 ) 
-- or (hour(TIME_BEGIN) = 17 ) AND (minute(TIME_BEGIN) <= 29 )
-- )
group by resource, res_month
order by resource, res_month
;
select resource, res_month, hour(TIME_BEGIN) as hour,  count(resource) as cnt, avg(DURATION_SECONDS) as AVG_DURATION_SECONDS from (
    select resource,
        COALESCE(MONTH(S.TIME_BEGIN), 0) AS RES_MONTH, 
        COALESCE(DAYOFWEEK(S.TIME_BEGIN), 0) AS RES_DAYWEEK,
        COALESCE(TIMESTAMPDIFF(2, CHAR(TIME_END - TIME_BEGIN)), 0) AS DURATION_SECONDS,
        S.TIME_END,
        S.TIME_BEGIN
    from makpsb.STAT AS S
) as raw
where res_month in (1, 12, 11, 2)
and (
(hour(TIME_BEGIN) >= 9 ) AND (hour(TIME_BEGIN) <= 16 ) 
or (hour(TIME_BEGIN) = 8 ) AND (minute(TIME_BEGIN) >= 29 ) 
or (hour(TIME_BEGIN) = 17 ) AND (minute(TIME_BEGIN) <= 29 )
)
group by resource, res_month, hour(TIME_BEGIN)
order by resource, res_month, hour(TIME_BEGIN)
; -- nerabochee vremya
select * from (
    select resource,
        COALESCE(MONTH(S.TIME_BEGIN), 0) AS RES_MONTH, 
        COALESCE(DAYOFWEEK(S.TIME_BEGIN), 0) AS RES_DAYWEEK,
        COALESCE(TIMESTAMPDIFF(2, CHAR(TIME_END - TIME_BEGIN)), 0) AS DURATION_SECONDS,
        S.TIME_END,
        S.TIME_BEGIN
    from makpsb.STAT AS S
) as raw
where res_month in (1, 12, 11, 2)
and not(
(hour(TIME_BEGIN) >= 9 ) AND (hour(TIME_BEGIN) <= 16 ) 
or (hour(TIME_BEGIN) = 8 ) AND (minute(TIME_BEGIN) >= 29 ) 
or (hour(TIME_BEGIN) = 17 ) AND (minute(TIME_BEGIN) <= 29 )
)
order by resource, res_month
;