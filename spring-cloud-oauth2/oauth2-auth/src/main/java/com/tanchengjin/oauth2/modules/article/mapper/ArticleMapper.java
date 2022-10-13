package com.tanchengjin.oauth2.modules.article.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tanchengjin.oauth2.modules.article.pojo.Article;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * articleMapper
 *
 * @author TanChengjin
 * @version v1.0.0
 * @since v1.0.0
 */
@Mapper
public interface ArticleMapper extends BaseMapper<Article> {

    int deleteByPrimaryKey(long id);

//    int insert(Article record);

    int insertSelective(Article record);

    Article selectByPrimaryKey(long id);

    int updateByPrimaryKeySelective(Article record);

    int updateByPrimaryKey(Article record);

    List<Article> getAll();

    List<Article> getAllByCondition(Article condition);

    long count();

    int batchDeleteByPrimaryKey(long[] ids);

    int logicalBatchDeleteByPrimaryKey(long[] ids);

    /**
     * 获取热门文章
     *
     * @return List<Article>
     */
    @Select("select * from article where deleted_at is null and status = 1 order by click limit 10")
    List<Article> getHotArticle();

    /**
     * 获取推荐文章
     *
     * @return List<Article>
     */
    @Select("select * from article where deleted_at is null and status = 1 order by id limit 10")
    List<Article> getCommentArticle();

    List<Article> pagination(int page, int size);

    List<Article> getAllByCategoryId(int cid);

    List<Article> getByTag(String tag);

    List<Article> getBySearch(int page, int size, String q);

}
