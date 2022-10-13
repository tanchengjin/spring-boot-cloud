package com.tanchengjin.oauth2.modules.article.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tanchengjin.oauth2.modules.article.mapper.ArticleCategoryMapper;
import com.tanchengjin.oauth2.modules.article.pojo.ArticleCategory;
import com.tanchengjin.oauth2.modules.article.service.ArticleCategoryService;
import com.tanchengjin.oauth2.modules.article.vo.ArticleCategoryVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ArticleCategoryServiceImpl extends ServiceImpl<ArticleCategoryMapper, ArticleCategory> implements ArticleCategoryService {

    @Autowired
    private ArticleCategoryMapper articleCategoryDao;

    @Override
    public ArticleCategory findOneById(int id) {
        return articleCategoryDao.selectByPrimaryKey(id);
    }

    @Override
    public List<ArticleCategory> getAll() {
        List<ArticleCategory> all = articleCategoryDao.getAll();
//        List<ArticleCategory> tree = new Tree<ArticleCategory>(all).tree();
        return getAllData(null, null);
    }

    private List<ArticleCategory> getAllData(List<ArticleCategory> articleCategoryList, List<ArticleCategory> tmp) {
        if (articleCategoryList == null || articleCategoryList.size() <= 0) {
            articleCategoryList = articleCategoryDao.getAllAndParentIdIsNull();
            tmp = new ArrayList<ArticleCategory>();
        }
        for (ArticleCategory articleCategory : articleCategoryList) {
            tmp.add(articleCategory);
            //获取子分类
            List<ArticleCategory> children = articleCategoryDao.selectByParentId(articleCategory.getId());
            if (children != null && children.size() >= 1) {
                this.getAllData(children, tmp);
            }
        }
        return tmp;
    }

    @Override
    public int delete(int id) {
        return articleCategoryDao.deleteByPrimaryKey(id);
    }

    @Override
    public int update(ArticleCategory articleCategory) {
        return articleCategoryDao.updateByPrimaryKeySelective(articleCategory);
    }

    @Override
    public int batchDelete(int[] id) {
        return 0;
    }

    @Override
    public int create(ArticleCategory articleCategory) {
        int i = 0;
        articleCategory.setId(null);
        Long parentId = articleCategory.getParentId();
        //不为空，设置当前分类path与level
        if (parentId != null) {
            ArticleCategory parentCategory = articleCategoryDao.selectByPrimaryKey(parentId.intValue());
            if (parentCategory == null) throw new RuntimeException("不存在的上及分类");
            articleCategory.setPath(parentCategory.getPath() + parentCategory.getId() + "-");
            articleCategory.setLevel(parentCategory.getLevel() + 1);
        }
        i = articleCategoryDao.insertSelective(articleCategory);
        return i;
    }

    @Override
    public List<ArticleCategoryVO> getTree() {
        return handlerTree(new ArrayList<ArticleCategoryVO>());
    }

    private List<ArticleCategoryVO> handlerTree(List<ArticleCategoryVO> vos) {
        //init
        if (vos == null || vos.size() <= 0) {
            List<ArticleCategory> allAndParentIdIsNull = articleCategoryDao.getAllAndParentIdIsNull();
            vos = buildCategoryVO(allAndParentIdIsNull);
        }

        for (ArticleCategoryVO category : vos) {
            List<ArticleCategory> categoryList = articleCategoryDao.selectByParentId(category.getId());
            //存在子分类
            if (categoryList != null && categoryList.size() >= 1) {
                List<ArticleCategoryVO> vosTmp = buildCategoryVO(categoryList);
                category.setChildren(vosTmp);
                handlerTree(vosTmp);
            }
        }
        return vos;
    }

    /**
     * pojo convert vo
     *
     * @param categoryList pojo
     */
    private List<ArticleCategoryVO> buildCategoryVO(List<ArticleCategory> categoryList) {
        ArrayList<ArticleCategoryVO> articleCategoryVOS = new ArrayList<>();
        for (ArticleCategory category : categoryList) {
            ArticleCategoryVO vo = new ArticleCategoryVO();
            BeanUtils.copyProperties(category, vo);
            articleCategoryVOS.add(vo);
        }
        return articleCategoryVOS;
    }
}
