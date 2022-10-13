package com.tanchengjin.oauth2.modules.article.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tanchengjin.oauth2.modules.article.mapper.ArticleMapper;
import com.tanchengjin.oauth2.modules.article.pojo.Article;
import com.tanchengjin.oauth2.modules.article.service.ArticleService;
import org.springframework.stereotype.Service;

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

}
