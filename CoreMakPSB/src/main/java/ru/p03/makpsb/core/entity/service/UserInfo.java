/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.p03.makpsb.core.entity.service;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.FieldResult;
import javax.persistence.SqlResultSetMapping;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author timofeevan
 */
@Entity
@SqlResultSetMapping(name="UserInfo", 
    entities={ 
        @EntityResult(entityClass = ru.p03.makpsb.core.entity.service.UserInfo.class, fields={
            @FieldResult(name="UserInfoId.roleId", column="role_id"),
            @FieldResult(name="UserInfoId.useRoleId", column="use_role_id"),
            @FieldResult(name="UserInfoId.cvitantionId", column="cvitantion_id"), 
            @FieldResult(name="UserInfoId.resourceId", column="resource_id"),
            @FieldResult(name="idDepart", column="id_depart"), 
            @FieldResult(name="username", column="username"),
            @FieldResult(name="operation", column="operation"), 
            @FieldResult(name="resourcePath", column="resource_path"),
            @FieldResult(name="resourceCode", column="resource_code"), 
            @FieldResult(name="roleName", column="role_name")
        })
    }
)
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class UserInfo implements Serializable {

    /**
     * @return the edvManId
     */
    public UserInfoId getUserInfoId() {
        return userInfoId;
    }

    /**
     * @param userInfoId the userInfoId to set
     */
    public void setUserInfoId(UserInfoId userInfoId) {
        this.userInfoId = userInfoId;
    }

    /**
     * @return the idDepart
     */
    public Integer getIdDepart() {
        return idDepart;
    }

    /**
     * @param idDepart the idDepart to set
     */
    public void setIdDepart(Integer idDepart) {
        this.idDepart = idDepart;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the operation
     */
    public String getOperation() {
        return operation;
    }

    /**
     * @param operation the operation to set
     */
    public void setOperation(String operation) {
        this.operation = operation;
    }

    /**
     * @return the resourcePath
     */
    public String getResourcePath() {
        return resourcePath;
    }

    /**
     * @param resourcePath the resourcePath to set
     */
    public void setResourcePath(String resourcePath) {
        this.resourcePath = resourcePath;
    }

    /**
     * @return the resourceCode
     */
    public String getResourceCode() {
        return resourceCode;
    }

    /**
     * @param resourceCode the resourceCode to set
     */
    public void setResourceCode(String resourceCode) {
        this.resourceCode = resourceCode;
    }
    //ur.id as user_role_id, cv.id as cvitantion_id, re.id as resource_id, 
    //id_depart, ur.name as username, operation, re."PATH" as resource_path, re.CODE as resource_code
    @Embeddable
    public static class UserInfoId implements Serializable{
        @XmlElement
        @Column(name = "role_id")
        protected Long roleId;
        @XmlElement
        @Column(name = "use_role_id")
        protected Long useRoleId;
        @Column(name = "cvitantion_id")
        protected Long cvitantionId;
        @Column(name = "resource_id")
        protected Long resourceId;

        /**
         * @return the roleId
         */
        public Long getRoleId() {
            return roleId;
        }

        /**
         * @param roleId the roleId to set
         */
        public void setRoleId(Long roleId) {
            this.roleId = roleId;
        }

        /**
         * @return the useRoleId
         */
        public Long getUseRoleId() {
            return useRoleId;
        }

        /**
         * @param useRoleId the useRoleId to set
         */
        public void setUseRoleId(Long useRoleId) {
            this.useRoleId = useRoleId;
        }

        /**
         * @return the cvitantionId
         */
        public Long getCvitantionId() {
            return cvitantionId;
        }

        /**
         * @param cvitantionId the cvitantionId to set
         */
        public void setCvitantionId(Long cvitantionId) {
            this.cvitantionId = cvitantionId;
        }

        /**
         * @return the resourceId
         */
        public Long getResourceId() {
            return resourceId;
        }

        /**
         * @param resourceId the resourceId to set
         */
        public void setResourceId(Long resourceId) {
            this.resourceId = resourceId;
        }

    }
    

    protected static long serialVersionUID = 1L;

    /**
     * @return the serialVersionUID
     */
    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    /**
     * @param aSerialVersionUID the serialVersionUID to set
     */
    public static void setSerialVersionUID(long aSerialVersionUID) {
        serialVersionUID = aSerialVersionUID;
    }
    
    //id_depart, ur.name as username, operation, re."PATH" as resource_path, re.CODE as resource_code
    @EmbeddedId
    protected UserInfoId userInfoId;
    
    @Column(name = "id_depart")
    protected Integer idDepart;
    @Column(name = "username")
    protected String username;
    @Column(name = "operation")
    protected String operation;
    @Column(name = "resource_path")
    protected String resourcePath;
    @Column(name = "resource_code")
    protected String resourceCode;
    @Column(name = "role_name")
    private String roleName;

    /**
     * @return the roleName
     */
    public String getRoleName() {
        return roleName;
    }

    /**
     * @param roleName the roleName to set
     */
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
    
}
