package com.tanchengjin.generatorcode.core.template;

public enum TemplateEnum {
    CONTROLLER("");
    /**
     * 模板文件名
     */
    private String template;


    TemplateEnum(String template) {
        this.template = template;
    }
}
