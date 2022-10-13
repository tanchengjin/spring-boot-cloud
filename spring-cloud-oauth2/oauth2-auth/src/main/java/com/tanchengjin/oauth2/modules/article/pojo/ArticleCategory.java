package com.tanchengjin.oauth2.modules.article.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.tanchengjin.oauth2.modules.article.Tree;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * 文章分类
 */
@Data
@NoArgsConstructor
public class ArticleCategory implements Tree.ITree<ArticleCategory> {

    private Long id;

    private String name;

    private Long parentId;

    private Integer sort;

    private Integer level;

    private Boolean isDirectory;

    private Boolean enable;

    private String path;

    private Date createdAt;

    private Date updatedAt;


    //+----------------------扩展-------------------
    @TableField(exist = false)

    private List<ArticleCategory> children;

    //+----------------------扩展-------------------


    public boolean setChildren(List<ArticleCategory> children) {
        this.children = children;
        return true;
    }
}