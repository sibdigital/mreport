/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  altmf
 * Created: 09.03.2017
 */


SELECT * FROM 
(
    SELECT RIGHT(RESOURCE, LENGTH(RESOURCE) - LOCATE_IN_STRING(RESOURCE, '.',-1) ) AS RESOURCE, MONTH(TIME_BEGIN) AS MONTH, COUNT(ID) AS CNT FROM MAKPSB.STAT 
    WHERE YEAR(TIME_BEGIN) = 2018
    GROUP BY RESOURCE, MONTH(TIME_BEGIN)
) AS S
LEFT JOIN 
MAKPSB.REPORT_INFO AS RI
ON UPPER(S.RESOURCE) = UPPER(RI.CLASS_NAME)

