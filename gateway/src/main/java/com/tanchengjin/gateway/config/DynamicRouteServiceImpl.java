package com.tanchengjin.gateway.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.cloud.gateway.route.RouteDefinitionWriter;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * 时间推送Aware 动态更新路由网关service
 */
@Service
public class DynamicRouteServiceImpl implements ApplicationEventPublisherAware {
    private final static Logger logger = LoggerFactory.getLogger(DynamicRouteServiceImpl.class);
    //获取路由定义
    @Autowired
    private RouteDefinitionLocator routeDefinitionLocator;
    //写路由定义
    @Autowired
    private RouteDefinitionWriter routeDefinitionWriter;
    //事件发布
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        //完成事件推送句柄的初始化
        this.applicationEventPublisher = applicationEventPublisher;
    }

    /**
     * 增加路由定义
     *
     * @param definition 路由配置
     * @return
     */
    public String addRouteDefinition(RouteDefinition definition) {
        //保存路由配置并发布
        routeDefinitionWriter.save(Mono.just(definition)).subscribe();

        //发布事件通知给gateway，同步新增的路由定义
        this.applicationEventPublisher.publishEvent(new RefreshRoutesEvent(this));
        return "success";
    }

    /**
     * 根据路由id删除路由配置
     *
     * @param id
     * @return
     */
    private String deleteById(String id) {

        try {
            logger.info("gateway delete route id: [{}]", id);
            this.routeDefinitionWriter.delete(Mono.just(id)).subscribe();
            //发布时间通知给gateway更新路由定义
            this.applicationEventPublisher.publishEvent(new RefreshRoutesEvent(this));
            return "delete success";
        } catch (Exception e) {
            logger.error("gateway delete route fail: [{}]", e.getMessage(), e);
            return "delete fail";
        }
    }

    /**
     * 更新路由
     * 删除+新增=更新路由
     *
     * @param definition
     * @return
     */
    private String updateByRouteDefinition(RouteDefinition definition) {
        //删除
        try {
            logger.info("gateway update route: [{}]", definition);
            this.routeDefinitionWriter.delete(Mono.just(definition.getId()));
        } catch (Exception e) {
            return "update fail,not find route routeId: " + definition.getId();
        }

        //新增
        try {
            routeDefinitionWriter.save(Mono.just(definition)).subscribe();
            this.applicationEventPublisher.publishEvent(new RefreshRoutesEvent(this));
            return "success";
        } catch (Exception e) {
            return "update route fail";
        }
    }

    /**
     * 更新路由
     *
     * @param definitions
     * @return
     */
    public String updateList(List<RouteDefinition> definitions) {
        logger.info("gateway update route : [{}]", definitions);
        //先拿到当前Gateway中存储的路由定义
        List<RouteDefinition> routeDefinitionExists = routeDefinitionLocator.getRouteDefinitions().buffer().blockFirst();
        if (!CollectionUtils.isEmpty(routeDefinitionExists)) {
            //如果当前的路由定义存在
            routeDefinitionExists.forEach(r -> {
                logger.info("delete route definition: [{}]", r);
                deleteById(r.getId());
            });
        }

        //更新新的路由定义到gateway中
        definitions.forEach(definition -> updateByRouteDefinition(definition));
        return "success";
    }

}
