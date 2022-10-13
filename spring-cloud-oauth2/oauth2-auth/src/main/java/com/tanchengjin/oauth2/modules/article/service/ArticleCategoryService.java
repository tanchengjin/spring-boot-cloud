package com.tanchengjin.oauth2.modules.article.service;




import com.baomidou.mybatisplus.extension.service.IService;
import com.tanchengjin.oauth2.modules.article.pojo.ArticleCategory;
import com.tanchengjin.oauth2.modules.article.vo.ArticleCategoryVO;

import java.util.List;

public interface ArticleCategoryService extends IService<ArticleCategory> {

    ArticleCategory findOneById(int id);

    List<ArticleCategory> getAll();

    int delete(int id);

    int update(ArticleCategory articleCategory);

    int batchDelete(int[] id);

    int create(ArticleCategory articleCategory);

    /**
     * 获取分类树
     */
    List<ArticleCategoryVO> getTree();
}
