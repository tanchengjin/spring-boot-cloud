package com.tanchengjin.cms.feign;

import com.tanchengjin.cms.modules.article.pojo.Article;
import com.tanchengjin.cms.modules.article.service.ArticleService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author TanChengjin
 * @Email 18865477815@163.com
 * @Since V1.0.0
 **/
@RestController
public class ArticleFeign implements IArticleClient {
    private final ArticleService articleService;

    public ArticleFeign(ArticleService articleService) {
        this.articleService = articleService;
    }

    @Override
    @GetMapping(value = GET_ARTICLE_BY_ID)
    public Object getArticleById(@PathVariable("id") Long id) {
        Article article = articleService.getById(id);
        return null;
    }
}
