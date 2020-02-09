/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.p03.makpsb.dynreport;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 *
 * @author timofeevan
 */
@Entity
@Table(name = "ACTUAL_DYN_REPORT", schema = "MAKPSB")
@XmlRootElement
public class ActualDynReport implements Serializable {

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @Column(name = "ID")
    @Id
    private long id;
    @Lob
    @Column(name = "NAME")
    private String name;
    @Lob
    @Column(name = "PATH")
    private String path;
    @Column(name = "CODE")
    private String code;
    @Column(name = "VERSION")
    private Integer version;
    @Lob
    @Column(name = "SQL_TEXT")
    private String sqlText;
    @Lob
    @Column(name = "PARAMS")
    private String params;
    @Lob
    @Column(name = "COLUMNS")
    private String columns;
    @Lob
    @Column(name = "VIEW_COLUMNS")
    private String viewColumns;
    @Lob
    @Column(name = "VIEW_NAME")
    private String viewName;
    @Lob
    @Column(name = "PERSISTENCEUNIT")
    private String persistenceunit;
    @Lob
    @Column(name = "DATASOURCE")
    private String datasource;

    @Lob
    @Column(name = "DB_URL")
    private String url;
    @Lob
    @Column(name = "DB_PASSWORD")
    private String password;
    @Lob
    @Column(name = "DB_USER")
    private String user;
    @Lob
    @Column(name = "DB_DRIVER")
    private String driver;


    public ActualDynReport() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getSqlText() {
        return sqlText;
    }

    public void setSqlText(String sqlText) {
        this.sqlText = sqlText;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getColumns() {
        return columns;
    }

    public void setColumns(String columns) {
        this.columns = columns;
    }

    public String getViewColumns() {
        return viewColumns;
    }

    public void setViewColumns(String viewColumns) {
        this.viewColumns = viewColumns;
    }

    public String getViewName() {
        return viewName;
    }

    public void setViewName(String viewName) {
        this.viewName = viewName;
    }

    public String getPersistenceunit() {
        return persistenceunit;
    }

    public void setPersistenceunit(String persistenceunit) {
        this.persistenceunit = persistenceunit;
    }

    public String getDatasource() {
        return datasource;
    }

    public void setDatasource(String datasource) {
        this.datasource = datasource;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }
}
