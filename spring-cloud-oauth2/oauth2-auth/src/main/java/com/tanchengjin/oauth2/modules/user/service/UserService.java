
package com.tanchengjin.oauth2.modules.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tanchengjin.oauth2.modules.oauth.enumeration.OauthTypeEnum;
import com.tanchengjin.oauth2.modules.oauth.request.MiniProgramLoginRequest;
import com.tanchengjin.oauth2.modules.oauth.request.OauthLoginRequest;
import com.tanchengjin.oauth2.modules.user.pojo.User;
import com.tanchengjin.oauth2.modules.user.request.UserRequest;

/**
 * user Service
 *
 * @author Tanchengjin
 * @since
 */
public interface UserService extends IService<User> {
    @Deprecated
    public boolean registerUser(UserRequest userRequest);

    public boolean createUserByUsernameAndPassword(UserRequest userRequest);

    //    boolean createUserByMiniProgram(MiniProgramLoginRequest request);
    User createUserByMiniProgram(OauthLoginRequest request);

    public User getUserByMobile(String mobile);

    User getUserByUsername(String username);
}