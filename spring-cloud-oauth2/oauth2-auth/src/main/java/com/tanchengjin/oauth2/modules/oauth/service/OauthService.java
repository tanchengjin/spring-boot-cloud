
package com.tanchengjin.oauth2.modules.oauth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tanchengjin.oauth2.modules.oauth.pojo.Oauth;
import com.tanchengjin.oauth2.modules.oauth.vo.OAuthVO;

import java.util.List;

/**
 * oauth Service
 *
 * @author Tanchengjin
 * @since
 */
public interface OauthService extends IService<Oauth> {
    public int create();

    List<OAuthVO> getOauthBindedListByUserId(String id);
}