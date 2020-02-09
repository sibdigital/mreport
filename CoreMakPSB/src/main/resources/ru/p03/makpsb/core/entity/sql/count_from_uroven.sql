/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  altmf
 * Created: 09.03.2017
 */
WITH DAT AS(
SELECT DISTINCT CVT.ID_RESOURCE, CVT.ID_ROLE, CAST(UR."NAME" AS VARCHAR(20)) AS NAME FROM MAKPSB.CVITANTION AS CVT
LEFT JOIN
MAKPSB.USE_ROLE UR
ON CVT.ID_ROLE = UR.ID_ROLE
WHERE NOT NAME IS NULL AND CVT.IS_DELETED = 0 AND UR.IS_DELETED = 0
)
SELECT ISP, COUNT(ISP) 
FROM(
    SELECT ID_RESOURCE,
    CASE
    WHEN OPFR > 0 AND UPFR = 0 AND CUVP = 0 THEN 'OPFR' 
    WHEN OPFR > 0 AND UPFR > 0 AND CUVP = 0 THEN 'OPFR-UPFR' 
    WHEN OPFR > 0 AND UPFR > 0 AND CUVP > 0 THEN 'ALL' 
    WHEN OPFR > 0 AND UPFR = 0 AND CUVP > 0 THEN 'OPFR-CUVP' 
    WHEN OPFR = 0 AND UPFR > 0 AND CUVP = 0 THEN 'UPFR' 
    WHEN OPFR = 0 AND UPFR > 0 AND CUVP > 0 THEN 'UPFR-CUVP' 
    WHEN OPFR = 0 AND UPFR = 0 AND CUVP > 0 THEN 'CUVP' 
    ELSE 'NO' END AS ISP
    FROM(
        SELECT ID_RESOURCE, SUM(OPFR) AS OPFR, SUM(UPFR) AS UPFR, SUM(CUVP) AS  CUVP FROM(
            SELECT ID_RESOURCE, ID_ROLE, 1 AS OPFR, 0 AS UPFR, 0 AS CUVP FROM DAT
            WHERE ID_ROLE IN (41, 42, 43, 44)
            UNION ALL
            SELECT ID_RESOURCE, ID_ROLE, 0 AS OPFR, 1 AS UPFR, 0 AS CUVP FROM DAT
            WHERE ID_ROLE IN (46, 48, 51, 61)
            UNION ALL
            SELECT ID_RESOURCE, ID_ROLE, 0 AS OPFR, 0 AS UPFR, 1 AS CUVP FROM DAT
            WHERE ID_ROLE IN (47, 49, 50)
            UNION ALL
            SELECT ID_RESOURCE, ID_ROLE, 1 AS OPFR, 1 AS UPFR, 1 AS CUVP FROM DAT
            WHERE NOT ID_ROLE IN (41, 42, 43, 44, 47, 49, 50, 46, 48, 51, 61, 1)
        ) AS S
        GROUP BY ID_RESOURCE
    ) AS S
)
GROUP BY ISP
