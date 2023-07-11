package com.tanchengjin.generatorcode.core.template.strategy;

import com.tanchengjin.generatorcode.core.config.ApplicationProperties;
import com.tanchengjin.generatorcode.core.config.SystemProperties;
import com.tanchengjin.generatorcode.core.config.TemplateConfig;
import com.tanchengjin.generatorcode.core.entity.TableInfo;

public interface Process {
    boolean process(ApplicationProperties applicationProperties, SystemProperties systemProperties,TemplateConfig config, TableInfo tableInfo);
}
