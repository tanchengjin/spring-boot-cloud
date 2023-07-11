package com.tanchengjin.cms.modules.article.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tanchengjin.cms.annotations.dict.Dict;
import com.tanchengjin.cms.annotations.UserLevelEnum;
import com.tanchengjin.cms.annotations.seria.EnumSerialize;
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
public class Article implements Serializable {

    private static final long serialVersionUID = 1791082720232008134L;

    @ApiModelProperty("")
    private Long id;
    /**
     * 分类id
     */
    @ApiModelProperty("分类id")
    @Dict(value = UserLevelEnum.class, method = "getMsgByCode")
    @EnumSerialize(Article.class)
    public Integer categoryId;
    /**
     * 文章标题
     */
    @ApiModelProperty("文章标题")
    private String title;
    /**
     * 文章副标题
     */
    @ApiModelProperty("文章副标题")
    private String subTitle;
    /**
     * 文章描述
     */
    @ApiModelProperty("文章描述")
    private String description;
    /**
     * 文章关键词,以逗号分割可用于实现标签功能
     */
    @ApiModelProperty("文章关键词,以逗号分割可用于实现标签功能")
    private String keywords;
    /**
     * 文章作者
     */
    @ApiModelProperty("文章作者")
    private String author;
    /**
     * 文章内容
     */
    @ApiModelProperty("文章内容")
    private String content;
    /**
     * 文章图片
     */
    @ApiModelProperty("文章图片")
    private String coverImage;
    /**
     * 是否开启评论默认为否
     */
    @ApiModelProperty("是否开启评论默认为否")
    private Integer comment;
    /**
     * 文章状态0为可获取
     */
    @ApiModelProperty("文章状态0为可获取")
    private Integer status;
    /**
     * 点击量
     */
    @ApiModelProperty("点击量")
    private Long click;
    /**
     * 虚拟访问量
     */
    @ApiModelProperty("虚拟访问量")
    private Integer virtualClick;
    /**
     * 排序
     */
    @ApiModelProperty("排序")
    private Integer sort;
    /**
     * 推荐
     */
    @ApiModelProperty("推荐")
    private Integer commend;
    /**
     * 是否置顶
     */
    @ApiModelProperty("是否置顶")
    private Integer top;
    /**
     * 点赞数量
     */
    @ApiModelProperty("点赞数量")
    private Integer likeCount;
    /**
     * 评论数量
     */
    @ApiModelProperty("评论数量")
    private Integer commentCount;
    /**
     * 文章来源
     */
    @ApiModelProperty("文章来源")
    private String source;
    /**
     * 文章来源url
     */
    @ApiModelProperty("文章来源url")
    private String sourceUrl;
    /**
     * 跳转地址
     */
    @ApiModelProperty("跳转地址")
    private String redirectUrl;
    /**
     * 额外字段
     */
    @ApiModelProperty("额外字段")
    private String extra;
    /**
     * 用于软删除
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("用于软删除")
    private Date deletedAt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("")
    private Date createdAt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("")
    private Date updatedAt;


    public Long getId()
    {
        return this.id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Integer getCategoryId()
    {
        return this.categoryId;
    }

    public void setCategoryId(Integer categoryId)
    {
        this.categoryId = categoryId;
    }

    public String getTitle()
    {
        return this.title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getSubTitle()
    {
        return this.subTitle;
    }

    public void setSubTitle(String subTitle)
    {
        this.subTitle = subTitle;
    }

    public String getDescription()
    {
        return this.description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getKeywords()
    {
        return this.keywords;
    }

    public void setKeywords(String keywords)
    {
        this.keywords = keywords;
    }

    public String getAuthor()
    {
        return this.author;
    }

    public void setAuthor(String author)
    {
        this.author = author;
    }

    public String getContent()
    {
        return this.content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    public String getCoverImage()
    {
        return this.coverImage;
    }

    public void setCoverImage(String coverImage)
    {
        this.coverImage = coverImage;
    }

    public Integer getComment()
    {
        return this.comment;
    }

    public void setComment(Integer comment)
    {
        this.comment = comment;
    }

    public Integer getStatus()
    {
        return this.status;
    }

    public void setStatus(Integer status)
    {
        this.status = status;
    }

    public Long getClick()
    {
        return this.click;
    }

    public void setClick(Long click)
    {
        this.click = click;
    }

    public Integer getVirtualClick()
    {
        return this.virtualClick;
    }

    public void setVirtualClick(Integer virtualClick)
    {
        this.virtualClick = virtualClick;
    }

    public Integer getSort()
    {
        return this.sort;
    }

    public void setSort(Integer sort)
    {
        this.sort = sort;
    }

    public Integer getCommend()
    {
        return this.commend;
    }

    public void setCommend(Integer commend)
    {
        this.commend = commend;
    }

    public Integer getTop()
    {
        return this.top;
    }

    public void setTop(Integer top)
    {
        this.top = top;
    }

    public Integer getLikeCount()
    {
        return this.likeCount;
    }

    public void setLikeCount(Integer likeCount)
    {
        this.likeCount = likeCount;
    }

    public Integer getCommentCount()
    {
        return this.commentCount;
    }

    public void setCommentCount(Integer commentCount)
    {
        this.commentCount = commentCount;
    }

    public String getSource()
    {
        return this.source;
    }

    public void setSource(String source)
    {
        this.source = source;
    }

    public String getSourceUrl()
    {
        return this.sourceUrl;
    }

    public void setSourceUrl(String sourceUrl)
    {
        this.sourceUrl = sourceUrl;
    }

    public String getRedirectUrl()
    {
        return this.redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl)
    {
        this.redirectUrl = redirectUrl;
    }

    public String getExtra()
    {
        return this.extra;
    }

    public void setExtra(String extra)
    {
        this.extra = extra;
    }

    public Date getDeletedAt()
    {
        return this.deletedAt;
    }

    public void setDeletedAt(Date deletedAt)
    {
        this.deletedAt = deletedAt;
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

}