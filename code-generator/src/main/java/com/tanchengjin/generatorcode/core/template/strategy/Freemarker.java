package com.tanchengjin.generatorcode.core.template.strategy;

import com.google.common.base.CaseFormat;
import com.tanchengjin.generatorcode.core.FreemarkerEngine;
import com.tanchengjin.generatorcode.core.config.ApplicationProperties;
import com.tanchengjin.generatorcode.core.config.SystemProperties;
import com.tanchengjin.generatorcode.core.config.TemplateConfig;
import com.tanchengjin.generatorcode.core.entity.TableInfo;

public class Freemarker implements Process {
    @Override
    public boolean process(ApplicationProperties applicationProperties, SystemProperties systemProperties, TemplateConfig config, TableInfo tableInfo) {
        //生成文件存储路径
//        String outputDir=System.getProperty("user.dir")+"/obj";
        String outputDir = System.getProperty("user.dir");
        FreemarkerEngine engine = new FreemarkerEngine(outputDir, systemProperties.getTemplateLocation());
        applicationProperties.setTableInfo(tableInfo);
        engine.generate(applicationProperties, config.getTemplate(), getTargetFile(config, tableInfo));
        return true;
    }

    private String getTargetFile(TemplateConfig config, TableInfo tableInfo) {
        //动态文件名前缀
        String targetFileName = config.getTargetName();
        if (targetFileName.matches("(.*?)(\\[])(.*?)")) {

            int i = targetFileName.lastIndexOf(".");
            String substring = targetFileName.substring(i + 1, targetFileName.length());

            if (substring.equals("html")) {
                //小写驼峰
                targetFileName = targetFileName.replace("[]", CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_UNDERSCORE, tableInfo.getName()));
                targetFileName = targetFileName.replace("html", "ftl");

            } else {
                //大写驼峰
                targetFileName = targetFileName.replace("[]", CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, tableInfo.getName()));
            }
            return targetFileName;
        }
        return targetFileName;
    }
}
