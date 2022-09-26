package com.tanchengjin.oauth2.modules.oauth.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * create by  pojo
 *
 * @author Tanchengjin
 * @version v1.0.0
 * @since v1.0.0
 */
//@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class Oauth implements Serializable {

    private static final long serialVersionUID = 1791082720232008134L;

    @ApiModelProperty("")
    private Long id;
    /**
     * 用户id
     */
    @ApiModelProperty("用户id")
    private Long userId;
    /**
     * oauth类型
     */
    @ApiModelProperty("oauth类型")
    private String oauthType;
    /**
     * token
     */
    @ApiModelProperty("token")
    private String token;
    /**
     * 创建日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("创建日期")
    private Date createdAt;
    /**
     * 修改日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("修改日期")
    private Date updatedAt;
    /**
     * 创建人
     */
    @ApiModelProperty("创建人")
    private transient Long createdBy;
    /**
     * 更新人
     */
    @ApiModelProperty("更新人")
    private transient Long updatedBy;
    /**
     * 租户id
     */
    @ApiModelProperty("租户id")
    private Integer tenantId;


    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return this.userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getOauthType() {
        return this.oauthType;
    }

    public void setOauthType(String oauthType) {
        this.oauthType = oauthType;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return this.updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Long getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Integer getTenantId() {
        return this.tenantId;
    }

    public void setTenantId(Integer tenantId) {
        this.tenantId = tenantId;
    }

}