package com.tanchengjin.test.controller;

import com.tanchengjin.test.util.throttle.Throttle;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/test")
public class TestController {
    @Throttle(second = 60, count = 2)
    @RequestMapping(value = {"/", ""}, method = RequestMethod.GET)
    public Map first() {
        System.out.println("in");
        HashMap<String, Object> stringObjectHashMap = new HashMap<>();
        stringObjectHashMap.put("msg", "success");
        return stringObjectHashMap;
    }
}
