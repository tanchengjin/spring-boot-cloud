package com.tanchengjin.generatorcode.core.config;

import lombok.Data;

import java.util.List;

/**
 * 只有系统框架中可读取到的配置(安全级别较高)
 */
@Data
public class SystemProperties {

    private Configuration configuration;

    private List<TemplateConfig> templateConfigList;
    /**
     * 模板所在目录
     */
    private String templateLocation = System.getProperty("user.dir") + "/src/main/resources/blueprint/freemarker";
}
