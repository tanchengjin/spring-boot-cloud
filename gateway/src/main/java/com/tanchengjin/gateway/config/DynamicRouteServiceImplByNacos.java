package com.tanchengjin.gateway.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.Executor;

/**
 * 通过nacos下发动态路由配置,监听Nacos中路由配置变更
 */
@Component
//当此类初始化之后在加载此类
@DependsOn({"gatewayConfig"})
public class DynamicRouteServiceImplByNacos {
    private final static Logger logger = LoggerFactory.getLogger(DynamicRouteServiceImplByNacos.class);
    /**
     * Nacos配置服务
     */
    private ConfigService configService;

    @Autowired
    private DynamicRouteServiceImpl dynamicRouteService;

    private ConfigService initConfigService() {
        try {
            Properties properties = new Properties();
            properties.setProperty("serverAddr", "127.0.0.1:8848");
            properties.setProperty("namespace", GatewayConfig.NACOS_NAMESPACE);
            return configService = NacosFactory.createConfigService(properties);
        } catch (Exception e) {
            logger.info("init gateway nacos config error: [{}]", e.getMessage(), e);
            return null;
        }
    }

    /**
     * //@PostConstruct 当前类在容器中构造完成后立刻执行此方法
     */
    @PostConstruct
    public void init() {
        logger.info("gateway route init...");
        try {
            //初始化 Nacos 配置客户端
            configService = initConfigService();
            if (null == configService) {
                logger.error("init config service fail");
                return;
            }

            //通过Nacos Config 并指定路由配置路径去获取路由配置
            String config = configService.getConfig(GatewayConfig.NACOS_ROUTE_DATA_ID, GatewayConfig.NACOS_ROUTE_GROUP, GatewayConfig.DEFAULT_TIMEOUT);

            logger.info("get current gateway config: [{}]", config);
            //发序列号路由配置信息
            List<RouteDefinition> routeDefinitionList = JSON.parseArray(config, RouteDefinition.class);
            //循环添加到路由中
            if (CollectionUtils.isNotEmpty(routeDefinitionList)) {
                for (RouteDefinition routeDefinition : routeDefinitionList) {
                    logger.info("init gateway config: [{}]", routeDefinition);
                    dynamicRouteService.addRouteDefinition(routeDefinition);
                }
            }
        } catch (Exception e) {
            logger.error("gateway route init has some error: [{}]", e.getMessage(), e);
        }

        //设置监听器
        dynamicRouteByNacosListener(GatewayConfig.NACOS_ROUTE_DATA_ID, GatewayConfig.NACOS_ROUTE_GROUP);
    }

    /**
     * 监听Nacos下发的动态路由配置
     *
     * @param dataId
     * @param group
     */
    private void dynamicRouteByNacosListener(String dataId, String group) {
        try {
            //给Nacos Config 客户端添加一个监听器
            configService.addListener(dataId, group, new Listener() {
                /**
                 * 自定义线程池操作
                 * @return
                 */
                @Override
                public Executor getExecutor() {
                    return null;
                }

                /**
                 * 监听器收到配置更新
                 * @param s 最新的配置
                 */
                @Override
                public void receiveConfigInfo(String s) {
                    logger.info("start to update config: [{}]", s);
                    List<RouteDefinition> routeDefinitionList = JSON.parseArray(s, RouteDefinition.class);
                    logger.info("update route: [{}]", routeDefinitionList.toString());
                    dynamicRouteService.updateList(routeDefinitionList);
                }
            });
        } catch (NacosException exception) {
            logger.error("dynamic update gateway config error: [{}]", exception.getMessage(), exception);
        }
    }

}
