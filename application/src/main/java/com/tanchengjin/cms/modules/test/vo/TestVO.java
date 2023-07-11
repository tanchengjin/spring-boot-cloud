package com.tanchengjin.cms.modules.test.vo;

import com.tanchengjin.cms.annotations.dict.Dict;
import com.tanchengjin.cms.modules.test.controller.TestController;
import lombok.Data;

/**
 * @Author TanChengjin
 * @Email 18865477815@163.com
 * @Since V1.0.0
 **/
@Data
public class TestVO {
    @Dict(value = TestController.class)
    private String name="";
//    @Dict
    private Long villageId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getVillageId() {
        return villageId;
    }

    public void setVillageId(Long villageId) {
        this.villageId = villageId;
    }
}
