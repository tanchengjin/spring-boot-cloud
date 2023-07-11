package com.tanchengjin.generatorcode.core.template.strategy;

import com.tanchengjin.generatorcode.core.config.ApplicationProperties;
import com.tanchengjin.generatorcode.core.config.SystemProperties;
import com.tanchengjin.generatorcode.core.config.TemplateConfig;
import com.tanchengjin.generatorcode.core.entity.TableInfo;

public class StrategyContext {
    private final Process engine;

    public StrategyContext(Process process) {
        this.engine = process;
    }

    public boolean doProcess(ApplicationProperties applicationProperties, SystemProperties systemProperties, TemplateConfig templateConfig, TableInfo tableInfo) {
        return this.engine.process(applicationProperties, systemProperties, templateConfig, tableInfo);
    }
}
