package com.tanchengjin.cms.modules.article.mapper;

import com.tanchengjin.cms.modules.article.pojo.Article;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * articleMapper
 *
 * @author Tanchengjin
 * @version v1.0.0
 * @since v1.0.0
 */
@Mapper
public interface ArticleMapper {

    int deleteByPrimaryKey(long id);

    int insert(Article record);

    int insertSelective(Article record);

    Article selectByPrimaryKey(long id);

    int updateByPrimaryKeySelective(Article record);

    int updateByPrimaryKey(Article record);

    List<Article> getAll();

    List<Article> getAllByCondition(Article condition);

    long count();

    int batchDeleteByPrimaryKey(long[] ids);

    String getText(@Param("tableName") String tableName, @Param("id") String id, @Param("name") String name);

}
