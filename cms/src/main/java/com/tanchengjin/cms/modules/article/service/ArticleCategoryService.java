
package com.tanchengjin.cms.modules.article.service;

import com.tanchengjin.cms.modules.article.pojo.ArticleCategory;

import java.util.List;

/**
 * article_category Service
 *
 * @author Tanchengjin
 * @since
 */
public interface ArticleCategoryService{

    ArticleCategory findOneById(int id);

    int deleteById(int id);

    int create(ArticleCategory record);

    int updateById(ArticleCategory record,int id);

    List<ArticleCategory>getAll();

    List<ArticleCategory>getAllByCondition(ArticleCategory condition);

    int batchDelete(int[] ids);

    int count();


}