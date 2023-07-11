package com.tanchengjin.generatorcode.core.template;

import com.tanchengjin.generatorcode.core.config.ApplicationProperties;
import com.tanchengjin.generatorcode.core.config.SystemProperties;
import com.tanchengjin.generatorcode.core.config.TemplateConfig;
import com.tanchengjin.generatorcode.core.entity.TableInfo;

/**
 * 生成服务实现层代码
 */
public class ServiceImplChain extends TemplateChain {
    @Override
    public void process(ApplicationProperties applicationProperties, SystemProperties systemProperties, TableInfo tableInfo) {
        engineStrategy.doProcess(applicationProperties, systemProperties, getTemplateConfig(), tableInfo);
        if (this.next != null) {
            this.next.process(applicationProperties, systemProperties, tableInfo);
        }
    }

    @Override
    public TemplateConfig getTemplateConfig() {
        TemplateConfig templateConfig = new TemplateConfig();
        templateConfig.setTemplate("serviceImpl.java.ftl");
        templateConfig.setTargetName(javaPath + packagePath + "/service/impl/[]ServiceImpl.java");
        return templateConfig;
    }
}
