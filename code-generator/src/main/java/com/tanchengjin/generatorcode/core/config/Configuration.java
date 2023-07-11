package com.tanchengjin.generatorcode.core.config;

import lombok.Data;

import java.util.List;

/**
 * 总配置
 */
@Data
public class Configuration {
    public final String author = "Tanchengjin";

    public final String version = "v1.0.0";

    private List<TemplateConfig> templates;
}