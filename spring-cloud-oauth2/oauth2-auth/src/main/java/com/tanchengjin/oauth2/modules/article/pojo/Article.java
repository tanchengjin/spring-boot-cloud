package com.tanchengjin.oauth2.modules.article.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * create by  pojo
 *
 * @author TanChengjin
 * @version v1.0.0
 * @since v1.0.0
 */
//@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Data
public class Article implements Serializable {

    private static final long serialVersionUID = 1791082720232008134L;

    private Long id;
    /**
     * 分类id
     */
    private Integer categoryId;
    /**
     * 文章标题
     */
    private String title;
    /**
     * 副标题标题
     */
    private String subTitle;
    /**
     * 文章描述
     */
    private String description;
    /**
     * 文章关键词,以逗号分割可用于实现标签功能
     */
    private String keywords;
    /**
     * 作者
     */
    private String author;
    /**
     * 内容
     */
    private String content;
    /**
     * 文章封面
     */
    private String coverImage;
    /**
     * 文章状态0为可获取
     */
    private Integer status;
    /**
     * 虚拟访问量
     */
    private Integer virtualClick;
    /**
     * 访问量
     */
    private Integer click;
    /**
     * 文章排序
     */
    private Integer sort;
    /**
     * 是否开启评论默认为否
     */
    private Integer comment;
    /**
     * 是否推荐
     */
    private Integer commend;
    /**
     * 是否置顶
     */
    private Integer top;
    /**
     * 点赞数
     */
    private Integer likeCount;
    /**
     * 评论数
     */
    private Integer commentCount;
    /**
     * 来源
     */
    private String source;
    /**
     * 来源url地址
     */
    private String sourceUrl;
    /**
     * 重定向地址
     */
    private String redirectUrl;
    /**
     * 额外字段
     */
    private String extra;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdAt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updatedAt;
    /**
     * 用于软删除
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date deletedAt;

    //+-------------自定义字段------------
    @TableField(exist = false)
    private String categoryName;
}