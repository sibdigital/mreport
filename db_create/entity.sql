
CREATE SCHEMA if not exists MAKPSB;
--СПРАВОЧНИКИ ОБЩИЕ
CREATE TABLE if not exists MAKPSB.RAION(
                             ID              BIGINT NOT NULL GENERATED BY DEFAULT AS IDENTITY,
                             IS_DELETED      INT DEFAULT 0,
                             NOMER           INT DEFAULT 0,
                             NAME            TEXT,
                             PRIMARY KEY	 (ID)
);

CREATE TABLE if not exists MAKPSB.JOB_CALENDAR(
                                    ID              BIGINT NOT NULL GENERATED BY DEFAULT AS IDENTITY,
                                    IS_DELETED      INT DEFAULT 0,
                                    NOT_JOB_DATE    TIMESTAMP,
                                    PRIMARY KEY	 (ID)
);

--СИСТЕМА РОЛЕЙ
CREATE TABLE if not exists MAKPSB.ROLE(
                            ID              BIGINT NOT NULL GENERATED BY DEFAULT AS IDENTITY,
                            IS_DELETED      INT DEFAULT 0,
                            NAME            TEXT,
                            CODE            VARCHAR(255),
                            PRIMARY KEY	 (ID)
);

CREATE TABLE if not exists MAKPSB.DEPART(
                              ID              BIGINT NOT NULL GENERATED BY DEFAULT AS IDENTITY,
                              PARENT_ID       BIGINT NOT NULL DEFAULT 0,
                              ID_RAION        BIGINT NOT NULL DEFAULT 0,
                              IS_DELETED      INT DEFAULT 0,
                              NAME            TEXT,
                              CODE            VARCHAR(255),
                              PRIMARY KEY     (ID)
);

CREATE TABLE if not exists MAKPSB.RESOURCE(
                                ID              BIGINT NOT NULL GENERATED BY DEFAULT AS IDENTITY,
                                IS_DELETED      INT DEFAULT 0,
                                NAME            TEXT,
                                PATH            TEXT,
                                CODE            VARCHAR(255),
                                PRIMARY KEY     (ID)
);

CREATE TABLE if not exists MAKPSB.CVITANTION(
                                  ID              BIGINT NOT NULL GENERATED BY DEFAULT AS IDENTITY,
                                  ID_RESOURCE     BIGINT NOT NULL,
                                  ID_ROLE         BIGINT NOT NULL,
                                  IS_DELETED      INT DEFAULT 0,
                                  OPERATION       VARCHAR(4),
                                  PRIMARY KEY     (ID)
);

CREATE TABLE if not exists MAKPSB.USE_ROLE(
                                ID              BIGINT NOT NULL GENERATED BY DEFAULT AS IDENTITY,
                                ID_ROLE         BIGINT NOT NULL,
                                ID_DEPART       BIGINT NOT NULL,
                                IS_DELETED      INT DEFAULT 0,
                                NAME            TEXT,
                                PRIMARY KEY     (ID)
);

-- ALTER TABLE MAKPSB.USE_ROLE
--     ADD CONSTRAINT FK_ROLE FOREIGN KEY (ID_ROLE) REFERENCES MAKPSB.ROLE(ID)
--         ON DELETE  NO ACTION
--         ON UPDATE  RESTRICT;

--ADRESA PODRAZDELENII
CREATE TABLE if not exists MAKPSB.DEPART_ADRESS(
                                     ID              BIGINT NOT NULL GENERATED BY DEFAULT AS IDENTITY,
                                     ID_RAION        BIGINT NOT NULL,
                                     ID_DEPART       BIGINT NOT NULL,
                                     IS_DELETED      INT DEFAULT 0,
                                     ADRESS          TEXT,
                                     PRIMARY KEY     (ID)
);

--DOLJNOSTI
CREATE TABLE if not exists MAKPSB.POSITION(
                                ID              BIGINT NOT NULL GENERATED BY DEFAULT AS IDENTITY,
                                IS_DELETED      INT DEFAULT 0,
                                NAME            TEXT,
                                PRIMARY KEY     (ID)
);

--SOTRUDNIKI
CREATE TABLE if not exists MAKPSB.WORKER(
                              ID              BIGINT NOT NULL GENERATED BY DEFAULT AS IDENTITY,
                              IS_DELETED      INT DEFAULT 0,
                              FAM             TEXT,
                              IM              TEXT,
                              OTC             TEXT,
                              PRIMARY KEY     (ID)
);

--DOLJNOSTI SOTRUDNIKOV
CREATE TABLE if not exists MAKPSB.WORKER_POSITION(
                                       ID              BIGINT NOT NULL GENERATED BY DEFAULT AS IDENTITY,
                                       ID_WORKER       BIGINT NOT NULL,
                                       ID_POSITION     BIGINT NOT NULL,
                                       ID_DEPART       BIGINT NOT NULL,
                                       IS_DELETED      INT DEFAULT 0,
                                       DATE_BEGIN      TIMESTAMP,
                                       DATE_END        TIMESTAMP,
                                       IS_LEADER       INT DEFAULT 0, -- RUKOVODITEL
                                       PRIMARY KEY     (ID)
);

--STATISTIKA VIPOLNENIA OTCHETOV
CREATE TABLE if not exists MAKPSB.STAT(
                            ID              BIGINT NOT NULL GENERATED BY DEFAULT AS IDENTITY,
                            RESOURCE        VARCHAR(255),
                            TIME_BEGIN      TIMESTAMP,
                            TIME_END        TIMESTAMP,
                            RAION           INT DEFAULT 0,
                            DATE_BEGIN      TIMESTAMP,
                            DATE_END        TIMESTAMP,
                            PRIMARY KEY     (ID)
);
--DROP TABLE MAKPSB.STAT;

--DROP VIEW MAKPSB.STAT_AVG;
--TIMESTAMPDIFF:
-- 1  FRACTIONS OF A SECOND
-- 2 SECONDS
-- 4  MINUTES
-- 8 HOURS
-- 16 DAYS
-- 32 WEEKS
-- 64 MONTHS
-- 128 QUARTERS
-- 256 YEARS

CREATE or replace VIEW  MAKPSB.STAT_AVG (ID, RESOURCE, RES_MONTH, RES_DAYWEEK, RES_HOUR, PERIOD, RAION, AVG_DURATION) AS
    SELECT ROW_NUMBER() OVER () AS ID, RESOURCE, RES_MONTH, RES_DAYWEEK, RES_HOUR, PERIOD, RAION, AVG_DURATION
    FROM (
    SELECT RESOURCE, RES_MONTH, RES_DAYWEEK, RES_HOUR, PERIOD, RAION, AVG(DURATION) AS AVG_DURATION
    FROM (
    SELECT
    RESOURCE,
    COALESCE (date_part('month', S.TIME_BEGIN), 0) AS RES_MONTH,
    COALESCE (EXTRACT (DOW FROM S.TIME_BEGIN), 0) AS RES_DAYWEEK,
    COALESCE (date_part('hour', S.TIME_BEGIN), 0) AS RES_HOUR,
    COALESCE (DATE_PART('day', DATE_END - DATE_BEGIN), 0) AS PERIOD,
    COALESCE (RAION, 0) AS RAION,
    COALESCE (DATE_PART('second', TIME_END - TIME_BEGIN), 0) AS DURATION
    FROM MAKPSB.STAT AS S
    ) AS CSTAT
    GROUP BY RESOURCE, RES_MONTH, RES_DAYWEEK, RES_HOUR, PERIOD, RAION
    ) as s
;

--DINAMICHESKIE OTCHETI
--DROP TABLE MAKPSB.DYN_REPORT;
CREATE TABLE if not exists MAKPSB.DYN_REPORT(
                                  ID              BIGINT NOT NULL GENERATED BY DEFAULT AS IDENTITY,
                                  NAME            TEXT,
                                  PATH            TEXT,
                                  CODE            VARCHAR(255),
                                  VERSION         INT DEFAULT 0,
                                  SQL_TEXT        TEXT,
                                  PARAMS          TEXT,
                                  COLUMNS         TEXT,
                                  VIEW_COLUMNS    TEXT,
                                  VIEW_NAME       TEXT,
                                  PERSISTENCEUNIT TEXT,
                                  DATASOURCE      TEXT,
                                  PRIMARY KEY     (ID)
);

--DROP VIEW MAKPSB.ACTUAL_DYN_REPORT;
CREATE or replace  VIEW MAKPSB.ACTUAL_DYN_REPORT (ID, NAME, PATH, CODE, VERSION, SQL_TEXT, PARAMS, COLUMNS, VIEW_COLUMNS, VIEW_NAME, PERSISTENCEUNIT, DATASOURCE) AS(
SELECT ID, NAME, PATH, DN.CODE, DN.VERSION, SQL_TEXT, PARAMS, COLUMNS, VIEW_COLUMNS, VIEW_NAME, PERSISTENCEUNIT, DATASOURCE
FROM (
         SELECT CODE, MAX(VERSION) AS VERSION
         FROM MAKPSB.DYN_REPORT
         GROUP BY CODE
     ) AS MV
         INNER JOIN MAKPSB.DYN_REPORT AS DN
                    ON MV.CODE = DN.CODE AND MV.VERSION = DN.VERSION
    )
;
--dostupnoe vremya
CREATE TABLE if not exists MAKPSB.AVAILABLE_PERIOD(
                                        ID              BIGINT NOT NULL GENERATED BY DEFAULT AS IDENTITY,
                                        ID_RESOURCE     BIGINT NOT NULL DEFAULT 0,
                                        IS_DELETED      INT DEFAULT 0,
                                        DAY_OF_WEEK     INT DEFAULT 0,
                                        HOUR_BEGIN      INT DEFAULT 0,
                                        HOUR_END        INT DEFAULT 0,
                                        MINUTE_BEGIN    INT DEFAULT 0,
                                        MINUTE_END      INT DEFAULT 0,
                                        PRIMARY KEY     (ID)
);