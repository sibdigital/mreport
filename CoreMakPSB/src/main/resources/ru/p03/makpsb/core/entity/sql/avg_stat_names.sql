/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  altmf
 * Created: 03.03.2017
 */
SELECT COALESCE(RI.VISIBLE_NAME, '') AS VNAME, 
SA.*
FROM(
    SELECT 
    RIGHT(RESOURCE, LENGTH(RESOURCE) - LOCATE_IN_STRING(RESOURCE, '.',-1) ) AS RESOURCE, 
    RES_MONTH, 
    AVG(AVG_DURATION) AS AVG_DURATION 
    FROM MAKPSB.STAT_AVG
    WHERE RES_MONTH IN (12, 1, 2)
    GROUP BY RESOURCE, RES_MONTH
)  AS SA
LEFT JOIN MAKPSB.REPORT_INFO AS RI
ON UPPER(RESOURCE) = UPPER(RI.CLASS_NAME)
ORDER BY RES_MONTH DESC, AVG_DURATION DESC
