package com.tanchengjin.oauth2.modules.article.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tanchengjin.oauth2.modules.article.pojo.ArticleCategory;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ArticleCategoryMapper extends BaseMapper<ArticleCategory> {

    int deleteByPrimaryKey(Integer id);

    int insert(ArticleCategory record);

    int insertSelective(ArticleCategory record);

    ArticleCategory selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ArticleCategory record);

    int updateByPrimaryKey(ArticleCategory record);

    List<ArticleCategory> getAll();

    int count();

    /**
     * 查询所有顶级分类
     */
    List<ArticleCategory> getAllAndParentIdIsNull();

    /**
     * 查询某一级分类下的所有分类
     *
     * @param id 分类ID
     */
    List<ArticleCategory> selectByParentId(long id);
}