
package com.tanchengjin.oauth2.modules.oauth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tanchengjin.oauth2.modules.oauth.pojo.Oauth;

/**
 * oauth Service
 *
 * @author Tanchengjin
 * @since
 */
public interface OauthService extends IService<Oauth> {
    public int create();
}