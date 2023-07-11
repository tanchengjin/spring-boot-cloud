package com.tanchengjin.cms.modules.test.controller;

import com.tanchengjin.cms.modules.test.vo.TestVO;
import com.tanchengjin.util.ServerResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/TestController")
public class TestController {
    @RequestMapping(value = "", method = {RequestMethod.GET})
    @ResponseBody
    public ServerResponse<TestVO> index() {
        TestVO testVO = new TestVO();
        testVO.setName("123");
        testVO.setVillageId(1L);
        return ServerResponse.responseWithSuccess(testVO);
    }

    public static String getMsgByCode(String code)
    {
        return "getMsgByCode";
    }
}
