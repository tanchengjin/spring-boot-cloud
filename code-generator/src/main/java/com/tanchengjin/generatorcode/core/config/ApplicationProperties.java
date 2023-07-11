package com.tanchengjin.generatorcode.core.config;

import com.tanchengjin.generatorcode.core.entity.TableInfo;
import lombok.Data;

/**
 * 模板中可读取到的所有变量
 */
@Data
public class ApplicationProperties {
    /**
     * 作者
     */
    public final String author = "Tanchengjin";
    /**
     * 版本
     */
    public final String version = "v1.0.0";

    /**
     * 是否开启类注释
     */
    public final boolean classDesc = true;

    /**
     * 包名
     */
    private String packageName;

    private TableInfo tableInfo;
    /**
     * 输出文件路径
     */
    private String outputDir;
    /**
     * 是否开启swagger
     */
    private boolean swagger = false;

    public String getOutputDir() {
        if (null == this.outputDir || "".equals(outputDir) || outputDir.length() == 0) {
            this.outputDir = System.getProperty("user.dir");
        }
        return outputDir;
    }
}
