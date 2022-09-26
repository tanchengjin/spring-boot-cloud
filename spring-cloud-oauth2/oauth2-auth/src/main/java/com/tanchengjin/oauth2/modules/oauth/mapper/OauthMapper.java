package com.tanchengjin.oauth2.modules.oauth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tanchengjin.oauth2.modules.oauth.pojo.Oauth;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * oauthMapper
 *
 * @author Tanchengjin
 * @version v1.0.0
 * @since v1.0.0
 */
@Mapper
public interface OauthMapper extends BaseMapper<Oauth> {

    int deleteByPrimaryKey(long id);

//    int insert(Oauth record);

    int insertSelective(Oauth record);

    Oauth selectByPrimaryKey(long id);

    int updateByPrimaryKeySelective(Oauth record);

    int updateByPrimaryKey(Oauth record);

    List<Oauth> getAll();

    List<Oauth> getAllByCondition(Oauth condition);

    long count();

    int batchDeleteByPrimaryKey(long[] ids);

    boolean existsByToken(String token);

}
