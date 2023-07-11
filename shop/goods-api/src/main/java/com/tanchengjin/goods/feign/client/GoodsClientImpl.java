package com.tanchengjin.goods.feign.client;

import com.tanchengjin.goods.feign.GoodsClient;
import com.tanchengjin.util.ServerResponse;
import org.springframework.stereotype.Component;

/**
 * @Author TanChengjin
 * @Email 18865477815@163.com
 * @Since V1.0.0
 **/
@Component
public class GoodsClientImpl implements GoodsClient {
    @Override
    public ServerResponse getGoodsList() {
        return ServerResponse.responseWithSuccess("13");
    }
}
