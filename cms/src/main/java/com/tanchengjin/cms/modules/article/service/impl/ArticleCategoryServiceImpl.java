package com.tanchengjin.cms.modules.article.service.impl;

import com.tanchengjin.cms.modules.article.mapper.ArticleCategoryMapper;
import com.tanchengjin.cms.modules.article.pojo.ArticleCategory;
import com.tanchengjin.cms.modules.article.service.ArticleCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * create by  article_category ServiceImpl
 *
 * @author Tanchengjin
 * @since v1.0.0
 * @version v1.0.0
 */
@Service("articleCategoryService")
public class ArticleCategoryServiceImpl implements ArticleCategoryService {

    @Autowired
    private ArticleCategoryMapper articleCategoryMapper;

    public ArticleCategory findOneById(int id)
    {
        ArticleCategory articleCategory = articleCategoryMapper.selectByPrimaryKey(id);
        return articleCategory;
    }

    public int deleteById(int id)
    {
        int i = articleCategoryMapper.deleteByPrimaryKey(id);
        return i;

    }

    public int create(ArticleCategory articleCategory)
    {
        int i = articleCategoryMapper.insertSelective(articleCategory);
        return i;
    }

    public int updateById(ArticleCategory articleCategory, int id)
    {
        articleCategory.setId(id);
        int i = articleCategoryMapper.updateByPrimaryKeySelective(articleCategory);
        return i;
    }

    public List<ArticleCategory> getAll()
    {
        List<ArticleCategory> articleCategoryList = articleCategoryMapper.getAll();
        return articleCategoryList;
    }

    public List<ArticleCategory> getAllByCondition(ArticleCategory articleCategory)
    {
        List<ArticleCategory> articleCategoryList = articleCategoryMapper.getAllByCondition(articleCategory);
        return articleCategoryList;
    }

    public int batchDelete(int[] ids)
    {
        int i = 0;
        for(int id : ids)
        {
            i+= articleCategoryMapper.deleteByPrimaryKey(id);
        }
        return i;
    }

    public int count()
    {
        return articleCategoryMapper.count();
    }
}