package com.tanchengjin.cms.controller;

import com.tanchengjin.cms.entity.User;
import com.tanchengjin.util.ObjectUtil;
import com.tanchengjin.util.ServerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Map;

/**
 * @Author TanChengjin
 * @Email 18865477815@163.com
 * @Since V1.0.0
 **/
@Controller
public class IndexController {
    @Resource
    private RedisTemplate<String, User> redisTemplate;

    @Autowired
    private RedisTemplate<String, Object> redisTemplateForObject;

//redisTemplate.opsForValue(); // 操作字符串
//redisTemplate.opsForHash(); // 操作hash
//redisTemplate.opsForList(); // 操作list
//redisTemplate.opsForSet(); // 操作set
//redisTemplate.opsForZSet(); // 操作zset

    @GetMapping(value = {"", "/"})
    @ResponseBody
    public ServerResponse index() {
        User user = new User();
        user.setId(1);
        redisTemplate.opsForValue().set(String.valueOf(user.getId()), user);
        return ServerResponse.responseWithSuccess(redisTemplate.opsForValue().get(String.valueOf(user.getId())));
    }

    @GetMapping(value = {"/redis-string"})
    @ResponseBody
    public ServerResponse redisString() {
        User user = new User();
        user.setId(1);
        redisTemplate.opsForValue().set(String.valueOf(user.getId()), user);
        return ServerResponse.responseWithSuccess(redisTemplate.opsForValue().get(String.valueOf(user.getId())));
    }

    @GetMapping(value = {"/hash"})
    @ResponseBody
    public ServerResponse hash() {
        User user = new User();
        user.setId(1);
        try {
            Map<String, Object> stringObjectMap = ObjectUtil.toMap(user);
            redisTemplate.opsForHash().putAll("test",stringObjectMap);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return ServerResponse.responseWithSuccess(redisTemplate.opsForValue().get(String.valueOf(user.getId())));
    }

    @GetMapping(value = {"set"})
    @ResponseBody
    public ServerResponse set() {
        User user = new User();
        user.setId(1);
        redisTemplate.opsForValue().set(String.valueOf(user.getId()), user);
        return ServerResponse.responseWithSuccess(redisTemplate.opsForValue().get(String.valueOf(user.getId())));
    }

    @GetMapping(value = {"zset"})
    @ResponseBody
    public ServerResponse zset() {
        User user = new User();
        user.setId(1);
        redisTemplate.opsForValue().set(String.valueOf(user.getId()), user);
        return ServerResponse.responseWithSuccess(redisTemplate.opsForValue().get(String.valueOf(user.getId())));
    }

    @GetMapping(value = {"list"})
    @ResponseBody
    public ServerResponse list() {
        User user = new User();
        user.setId(1);
        ArrayList<User> userList = new ArrayList<>();
        userList.add(user);
        redisTemplateForObject.opsForList().rightPush("userList:list", "set test");
        return ServerResponse.responseWithSuccess(redisTemplate.opsForList().range("userList", 0, -1));
    }
}
