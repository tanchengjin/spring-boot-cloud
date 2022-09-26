package com.tanchengjin.oauth2.modules.oauth.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tanchengjin.oauth2.modules.oauth.mapper.OauthMapper;
import com.tanchengjin.oauth2.modules.oauth.pojo.Oauth;
import com.tanchengjin.oauth2.modules.oauth.service.OauthService;
import org.springframework.stereotype.Service;

/**
 * create by  oauth ServiceImpl
 *
 * @author Tanchengjin
 * @version v1.0.0
 * @since v1.0.0
 */
@Service("oauthService")
public class OauthServiceImpl extends ServiceImpl<OauthMapper, Oauth> implements OauthService {
    private final OauthMapper oauthMapper;

    public OauthServiceImpl(OauthMapper oauthMapper) {
        this.oauthMapper = oauthMapper;
    }

    @Override
    public int create() {
        return 0;
    }
}