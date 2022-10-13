package com.tanchengjin.oauth2.modules.article.vo;

import java.util.List;

/**
 * 文章分类 view/json
 */
public class ArticleCategoryVO {
    private Integer id;

    private String name;

    private Integer parentId;

    private Integer sort;

    private Integer level;

    private Boolean isDirectory;

    private String path;

    private List<ArticleCategoryVO> children;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Boolean getDirectory() {
        return isDirectory;
    }

    public void setDirectory(Boolean directory) {
        isDirectory = directory;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public List<ArticleCategoryVO> getChildren() {
        return children;
    }

    public void setChildren(List<ArticleCategoryVO> children) {
        this.children = children;
    }
}