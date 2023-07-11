package com.tanchengjin.cms.modules.article.service.impl;

import com.tanchengjin.cms.modules.article.mapper.ArticleMapper;
import com.tanchengjin.cms.modules.article.pojo.Article;
import com.tanchengjin.cms.modules.article.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * create by  article ServiceImpl
 *
 * @author Tanchengjin
 * @version v1.0.0
 * @since v1.0.0
 */
@Service("articleService")
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;

    public Article findOneById(long id) {
        Article article = articleMapper.selectByPrimaryKey(id);
        return article;
    }

    public int deleteById(long id) {
        int i = articleMapper.deleteByPrimaryKey(id);
        return i;

    }

    public int create(Article article) {
        int i = articleMapper.insertSelective(article);
        return i;
    }

    public int updateById(Article article, long id) {
        article.setId(id);
        int i = articleMapper.updateByPrimaryKeySelective(article);
        return i;
    }

    public List<Article> getAll() {
        List<Article> articleList = articleMapper.getAll();
        return articleList;
    }

    public List<Article> getAllByCondition(Article article) {
        List<Article> articleList = articleMapper.getAllByCondition(article);
        return articleList;
    }

    public int batchDelete(long[] ids) {
        int i = 0;
        for (long id : ids) {
            i += articleMapper.deleteByPrimaryKey(id);
        }
        return i;
    }

    public long count() {
        return articleMapper.count();
    }

    @Override
    public Article getById(Long id) {
        Article article = articleMapper.selectByPrimaryKey(id);
        return article;
    }

    @Override
    public String getDictString(String tableName, String id, String name) {
        return articleMapper.getText(tableName, id, name);
    }
}