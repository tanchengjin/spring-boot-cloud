package com.tanchengjin.pdf.modules.wkhtmltopdf;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

/**
 * @author TanChengjin
 * @version v1.0.0
 * @email 18865477815@163.com
 */
public class FreemarkerUtil {
    /**
     * freemarker 引擎渲染 html
     *
     * @param vo          传入 html 模板的数据
     * @param ftlFilePath html 模板文件相对路径(相对于 resources路径,路径 + 文件名)
     *                    eg: "templates/pdf_export_demo.ftl"
     * @param voName      共享变量名
     * @return
     */
    public static String makeHTML(String voName, Object vo, String ftlFilePath) {
        Configuration configuration;
        configuration = new Configuration(Configuration.VERSION_2_3_28);

        configuration.setDefaultEncoding("UTF-8");
        configuration.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);

        Writer out = new StringWriter();

        try {
            configuration.setDirectoryForTemplateLoading(new File(ResourceFileUtil.getParent(ftlFilePath)));
            configuration.setLogTemplateExceptions(false);
            configuration.setWrapUncheckedExceptions(true);
            configuration.setSharedVariable(voName, vo);
            //直接读取本地路径
//            Template template = configuration.getTemplate(ftlFilePath);
            Template template = configuration.getTemplate(ResourceFileUtil.getFileName(ftlFilePath));
            template.process(vo, out);
            out.flush();
            return out.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
