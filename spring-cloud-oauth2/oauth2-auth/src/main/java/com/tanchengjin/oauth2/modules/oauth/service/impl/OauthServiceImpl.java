package com.tanchengjin.oauth2.modules.oauth.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tanchengjin.oauth2.modules.oauth.enumeration.OauthTypeEnum;
import com.tanchengjin.oauth2.modules.oauth.mapper.OauthMapper;
import com.tanchengjin.oauth2.modules.oauth.pojo.Oauth;
import com.tanchengjin.oauth2.modules.oauth.service.OauthService;
import com.tanchengjin.oauth2.modules.oauth.vo.OAuthVO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    @Override
    public List<OAuthVO> getOauthBindedListByUserId(String id) {
        List<Oauth> oauthList = this.baseMapper.selectListByUserId(id);

        ArrayList<OAuthVO> vos = new ArrayList<>();
        for (OauthTypeEnum value : OauthTypeEnum.values()) {
            OAuthVO oAuthVO = new OAuthVO();
            oAuthVO.setName(value.getName());
            vos.add(oAuthVO);
            Optional<Oauth> first = oauthList.stream().filter(f -> value.getType().equals(f.getOauthType())).findFirst();
            if (first.isPresent()) {
                oAuthVO.setBound(1);
                oAuthVO.setBoundDate(first.get().getCreatedAt());
            }
        }
        return vos;
    }
}