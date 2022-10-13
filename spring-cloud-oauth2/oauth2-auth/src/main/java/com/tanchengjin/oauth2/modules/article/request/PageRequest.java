package com.tanchengjin.oauth2.modules.article.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author TanChengjin
 * @Email 18865477815@163.com
 * @Since V1.0.0
 **/
@Data
@ApiModel(description = "查询条件")
public class PageRequest {
    @ApiModelProperty("当前页")
    private Long current = 1L;
    @ApiModelProperty("分页的记录集")
    private Long size = 10L;
}
