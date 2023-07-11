package com.tanchengjin.cms.modules.article.mapper;

import com.tanchengjin.cms.modules.article.pojo.ArticleCategory;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * article_categoryMapper
 *
 * @author  Tanchengjin
 * @since   v1.0.0
 * @version v1.0.0
 */
@Mapper
public interface ArticleCategoryMapper {

    int deleteByPrimaryKey(int id);

    int insert(ArticleCategory record);

    int insertSelective(ArticleCategory record);

    ArticleCategory selectByPrimaryKey(int id);

    int updateByPrimaryKeySelective(ArticleCategory record);

    int updateByPrimaryKey(ArticleCategory record);

    List<ArticleCategory> getAll();

    List<ArticleCategory> getAllByCondition(ArticleCategory condition);

    int count();

    int batchDeleteByPrimaryKey(int[] ids);

}
