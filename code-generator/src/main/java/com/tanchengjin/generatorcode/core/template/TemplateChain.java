package com.tanchengjin.generatorcode.core.template;

import com.tanchengjin.generatorcode.core.config.ApplicationProperties;
import com.tanchengjin.generatorcode.core.config.SystemProperties;
import com.tanchengjin.generatorcode.core.config.TemplateConfig;
import com.tanchengjin.generatorcode.core.entity.TableInfo;
import com.tanchengjin.generatorcode.core.template.strategy.Freemarker;
import com.tanchengjin.generatorcode.core.template.strategy.StrategyContext;

public abstract class TemplateChain {

    protected static final String javaPath = "/src/main/java";
    /**
     * 资源文件存储路径
     */
    protected static final String resourcePath = "/src/main/resources";

    protected static final String packagePath = "/com/tanchengjin/generatorcode/test";

    public TemplateChain next;

    protected StrategyContext engineStrategy;

    public TemplateChain(StrategyContext processStrategy) {
        this.engineStrategy = processStrategy;
    }

    public TemplateChain() {
        this.engineStrategy = new StrategyContext(new Freemarker());
    }

    public abstract void process(ApplicationProperties applicationProperties, SystemProperties systemProperties, TableInfo tableInfo);

    public TemplateChain getNext() {
        return next;
    }

    public void setNext(TemplateChain next) {
        this.next = next;
    }

    public abstract TemplateConfig getTemplateConfig();
}
