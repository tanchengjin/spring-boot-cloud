
package com.tanchengjin.cms.modules.article.service;

import com.tanchengjin.cms.annotations.dict.db.DictOperator;
import com.tanchengjin.cms.modules.article.pojo.Article;

import java.util.List;

/**
 * article Service
 *
 * @author Tanchengjin
 * @since
 */
public interface ArticleService extends DictOperator {

    Article findOneById(long id);

    int deleteById(long id);

    int create(Article record);

    int updateById(Article record, long id);

    List<Article> getAll();

    List<Article> getAllByCondition(Article condition);

    int batchDelete(long[] ids);

    long count();

    Article getById(Long id);
}