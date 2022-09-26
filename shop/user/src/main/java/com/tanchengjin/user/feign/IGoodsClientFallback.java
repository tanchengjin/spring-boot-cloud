package com.tanchengjin.user.feign;

import com.tanchengjin.util.ServerResponse;
import org.springframework.stereotype.Component;

@Component
public class IGoodsClientFallback implements IGoodsClient {
    @Override
    public ServerResponse list() {
        return ServerResponse.responseWithFailure();
    }
}
