//package com.tanchengjin.oauth2.test.controller;
//
//import com.tanchengjin.auth.AuthUtil;
//import com.tanchengjin.auth.Userinfo;
//import com.tanchengjin.cms.feign.IArticleClient;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.servlet.http.HttpServletRequest;
//
//
///**
// * @Author TanChengjin
// * @Email 18865477815@163.com
// * @Since V1.0.0
// **/
//@RestController
//public class IndexController {
//    private final IArticleClient articleClient;
//
//    public IndexController(IArticleClient articleClient) {
//        this.articleClient = articleClient;
//    }
//
//    @RequestMapping(value = "/test", method = RequestMethod.GET)
//    public String index() {
//        return "test method";
//    }
//
//    @RequestMapping(value = "/user", method = RequestMethod.GET)
//    public Object getUserInfo(HttpServletRequest request) {
//        Userinfo user = AuthUtil.getUser(request);
//        return user;
//    }
//
//    @RequestMapping(value = "/articles/{id}", method = RequestMethod.GET)
//    public Object getUserInfo(@PathVariable("id") Long id) {
//        Object article = articleClient.getArticleById(id);
//        return article;
//    }
//}
