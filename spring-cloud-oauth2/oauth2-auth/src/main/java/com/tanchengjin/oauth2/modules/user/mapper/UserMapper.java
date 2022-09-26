package com.tanchengjin.oauth2.modules.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tanchengjin.oauth2.modules.user.pojo.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * userMapper
 *
 * @author  Tanchengjin
 * @since   v1.0.0
 * @version v1.0.0
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

    int deleteByPrimaryKey(long id);

//    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(long id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    List<User> getAll();

    List<User> getAllByCondition(User condition);

    long count();

    int batchDeleteByPrimaryKey(long[] ids);

    boolean existsUserByUsername(String username);

    User selectUserByMobile(String mobile);

    boolean existsMobile(String mobile);

    User selectUserByUsername(String username);
}
