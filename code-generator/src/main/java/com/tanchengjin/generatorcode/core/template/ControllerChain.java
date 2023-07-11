package com.tanchengjin.generatorcode.core.template;

import com.tanchengjin.generatorcode.core.config.ApplicationProperties;
import com.tanchengjin.generatorcode.core.config.SystemProperties;
import com.tanchengjin.generatorcode.core.config.TemplateConfig;
import com.tanchengjin.generatorcode.core.entity.TableInfo;

/**
 * 生成控制器代码
 */
public class ControllerChain extends TemplateChain {
    @Override
    public void process(ApplicationProperties applicationProperties, SystemProperties systemProperties, TableInfo tableInfo) {
        this.engineStrategy.doProcess(applicationProperties, systemProperties, getTemplateConfig(), tableInfo);
        if (this.next != null) {
            this.next.process(applicationProperties, systemProperties, tableInfo);
        }
    }

    @Override
    public TemplateConfig getTemplateConfig() {
        TemplateConfig templateConfig = new TemplateConfig();
        templateConfig.setTemplate("controller.java.ftl");
        templateConfig.setTargetName(javaPath + packagePath + "/controller/[]Controller.java");
        return templateConfig;
    }
}
