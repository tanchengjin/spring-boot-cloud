package com.tanchengjin.oauth2.modules.article.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tanchengjin.oauth2.modules.article.pojo.Article;
import com.tanchengjin.oauth2.modules.article.request.PageRequest;
import com.tanchengjin.oauth2.modules.article.service.ArticleService;
import com.tanchengjin.util.ServerResponse;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author TanChengjin
 * @Email 18865477815@163.com
 * @Since V1.0.0
 **/
@RestController
@RequestMapping("/articles")
public class ArticleController {
    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @RequestMapping(value = {"/", ""}, method = RequestMethod.GET)
    public ServerResponse index(PageRequest pageRequest) {
        Page<Article> page = articleService.page(new Page<>(pageRequest.getCurrent(), pageRequest.getSize()));
        return ServerResponse.responseWithSuccess(page);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ServerResponse show(@PathVariable String id) {
        Article article = articleService.getById(id);
        return ServerResponse.responseWithSuccess(article);
    }
}
