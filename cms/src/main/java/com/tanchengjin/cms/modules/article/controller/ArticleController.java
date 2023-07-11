package com.tanchengjin.cms.modules.article.controller;

import com.tanchengjin.cms.modules.article.pojo.Article;
import com.tanchengjin.cms.modules.article.service.ArticleService;
import com.tanchengjin.util.ServerResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiOperationSupport;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping("/articles")
@Api(value = "ArticleController", tags = "ArticleController")
public class ArticleController {

    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @RequestMapping(value = "", method = {RequestMethod.GET})
    @ResponseBody
    @ApiOperationSupport(order = 1)
    @ApiOperation(value = "", notes = "")
    public ServerResponse index() {
        List<Article> all = articleService.getAll();
        return ServerResponse.responseWithSuccess(all);
    }

    @RequestMapping(value = "/create", method = {RequestMethod.GET})
    @ApiOperationSupport(order = 2)
    @ApiOperation(value = "", notes = "")
    public String create() {
        return "";
    }

    @RequestMapping(value = "", method = {RequestMethod.POST})
    @ResponseBody
    @ApiOperationSupport(order = 3)
    @ApiOperation(value = "", notes = "")
    public String store() {
        return "";
    }
}
