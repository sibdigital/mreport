/**
 * Author:  altmf
 * Created: 12.02.2018
 */

CREATE TABLE if not exists MAKPSB.CLS_STANDART_FORMING_PERIOD(
                                                   ID              BIGINT NOT NULL GENERATED BY DEFAULT AS IDENTITY,
                                                   IS_DELETED      INT DEFAULT 0,
                                                   NAME            VARCHAR(255),
                                                   CODE            VARCHAR(63),
                                                   PRIMARY KEY	 (ID)
);
INSERT INTO MAKPSB.CLS_STANDART_FORMING_PERIOD (IS_DELETED, NAME, CODE)
VALUES (0, 'Прошлая неделя', 'LAST_WEEK'),
       (0, 'Текущая неделя', 'CURRENT_WEEK'),
       (0, 'Прошлый месяц', 'LAST_MONTH'),
       (0, 'Текущий месяц', 'CURRENT_MONTH');

CREATE TABLE if not exists  MAKPSB.CLS_REPORT_PERIOD(
                                         ID                          BIGINT NOT NULL GENERATED BY DEFAULT AS IDENTITY,
                                         ID_REPORT                   BIGINT NOT NULL,
                                         ID_PERIOD_TYPE              BIGINT NOT NULL,
                                         ID_STANDART_FORMING_PERIOD  BIGINT NOT NULL,
                                         IS_DELETED      INT DEFAULT 0,
                                         DAY_OF_MONTH    INT DEFAULT 1,
                                         DAY_OF_WEEK     INT DEFAULT 1,
                                         HOUR            INT DEFAULT 23,
                                         MINUTE          INT DEFAULT 1,
                                         IS_ACTIVE       INT DEFAULT 0,
                                         PRIMARY KEY	 (ID),
                                         CONSTRAINT FK_REPORT FOREIGN KEY (ID_REPORT) REFERENCES MAKPSB.REPORT_INFO(ID) ON DELETE  NO ACTION ON UPDATE RESTRICT,
                                         CONSTRAINT FK_PERIOD_TYPE FOREIGN KEY (ID_PERIOD_TYPE) REFERENCES MP_INCREM.CLS_PERIOD_TYPE(ID) ON DELETE  NO ACTION ON UPDATE RESTRICT,
                                         CONSTRAINT FK_STANDART_FORMING_PERIOD FOREIGN KEY (ID_STANDART_FORMING_PERIOD) REFERENCES MAKPSB.CLS_STANDART_FORMING_PERIOD(ID) ON DELETE  NO ACTION ON UPDATE RESTRICT
);

ALTER TABLE MAKPSB.REPORT_INFO ADD COLUMN CODE VARCHAR(63);
ALTER TABLE MAKPSB.REPORT_INFO ADD COLUMN ID_RESOURCE BIGINT;

CREATE TABLE if not exists MAKPSB.CLS_DATASOURCE(
                                      ID              BIGINT NOT NULL GENERATED BY DEFAULT AS IDENTITY,
                                      IS_DELETED      INT DEFAULT 0,
                                      NAME            VARCHAR(255),
                                      CODE            VARCHAR(63),
                                      PRIMARY KEY	 (ID)
);

CREATE TABLE if not exists MAKPSB.CLS_REPORT_DATASOURCE(
                                             ID              BIGINT NOT NULL GENERATED BY DEFAULT AS IDENTITY,
                                             ID_REPORT       BIGINT NOT NULL,
                                             ID_DATASOURCE   BIGINT NOT NULL,
                                             IS_DELETED      INT DEFAULT 0,
                                             PRIMARY KEY	 (ID),
                                             CONSTRAINT FK_REPORT FOREIGN KEY (ID_REPORT) REFERENCES MAKPSB.REPORT_INFO(ID) ON DELETE  NO ACTION ON UPDATE RESTRICT,
                                             CONSTRAINT FK_DATASOURCE FOREIGN KEY (ID_DATASOURCE) REFERENCES MAKPSB.CLS_DATASOURCE(ID) ON DELETE  NO ACTION ON UPDATE RESTRICT
);

