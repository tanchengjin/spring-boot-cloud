package com.tanchengjin.cms.modules.article.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;
/**
 * create by  pojo
 *
 * @author Tanchengjin
 * @since v1.0.0
 * @version v1.0.0
 */
//@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ArticleCategory implements Serializable {

    private static final long serialVersionUID = 1791082720232008134L;

    /**
     * 主键
     */
    @ApiModelProperty("主键")
    private Integer id;
    @ApiModelProperty("")
    private String name;
    /**
     * 上级id
     */
    @ApiModelProperty("上级id")
    private Integer parentId;
    /**
     * 祖先id
     */
    @ApiModelProperty("祖先id")
    private String path;
    /**
     * 排序
     */
    @ApiModelProperty("排序")
    private Integer sort;
    /**
     * 状态1正常
     */
    @ApiModelProperty("状态1正常")
    private Integer status;
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
     * 删除日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("删除日期")
    private Date deletedAt;
    /**
     * 创建人
     */
    @ApiModelProperty("创建人")
    private Long createdBy;
    /**
     * 更新人
     */
    @ApiModelProperty("更新人")
    private Long updatedBy;
    /**
     * 租户id
     */
    @ApiModelProperty("租户id")
    private Integer tenantId;


    public Integer getId()
    {
        return this.id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public String getName()
    {
        return this.name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Integer getParentId()
    {
        return this.parentId;
    }

    public void setParentId(Integer parentId)
    {
        this.parentId = parentId;
    }

    public String getPath()
    {
        return this.path;
    }

    public void setPath(String path)
    {
        this.path = path;
    }

    public Integer getSort()
    {
        return this.sort;
    }

    public void setSort(Integer sort)
    {
        this.sort = sort;
    }

    public Integer getStatus()
    {
        return this.status;
    }

    public void setStatus(Integer status)
    {
        this.status = status;
    }

    public Date getCreatedAt()
    {
        return this.createdAt;
    }

    public void setCreatedAt(Date createdAt)
    {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt()
    {
        return this.updatedAt;
    }

    public void setUpdatedAt(Date updatedAt)
    {
        this.updatedAt = updatedAt;
    }

    public Date getDeletedAt()
    {
        return this.deletedAt;
    }

    public void setDeletedAt(Date deletedAt)
    {
        this.deletedAt = deletedAt;
    }

    public Long getCreatedBy()
    {
        return this.createdBy;
    }

    public void setCreatedBy(Long createdBy)
    {
        this.createdBy = createdBy;
    }

    public Long getUpdatedBy()
    {
        return this.updatedBy;
    }

    public void setUpdatedBy(Long updatedBy)
    {
        this.updatedBy = updatedBy;
    }

    public Integer getTenantId()
    {
        return this.tenantId;
    }

    public void setTenantId(Integer tenantId)
    {
        this.tenantId = tenantId;
    }

}