package com.tanchengjin.generatorcode;

import com.tanchengjin.generatorcode.core.config.ApplicationProperties;
import com.tanchengjin.generatorcode.core.config.SystemProperties;
import com.tanchengjin.generatorcode.core.entity.TableInfo;
import com.tanchengjin.generatorcode.core.template.*;
import com.tanchengjin.generatorcode.utils.DBUtil;

public class GeneratorApplication {
    public static void main(String[] args) throws Exception {
        String[] selectTables = getSelectTables();

        TemplateChain controllerChain = getTemplateChain();
//        Configuration configuration = ConfigurationModel.getConfiguration();

        for (String table : selectTables) {
            TableInfo tableInfo = DBUtil.getInstance().getTableInfo(table);

            ApplicationProperties templateObject = new ApplicationProperties();
//            TemplateObject templateObject = new TemplateObject();
            templateObject.setTableInfo(tableInfo);
            templateObject.setPackageName("com.tanchengjin.oauth2.modules.user");
//            templateObject.setPackageName("com.tanchengjin.generatorcode.test");
            controllerChain.process(templateObject, new SystemProperties(), tableInfo);
//            for (TemplateConfig config : configuration.getTemplates()) {
//                //动态文件名前缀
//                String targetFileName = config.getTargetName();
//                if (targetFileName.matches("(.*?)(\\[])(.*?)")) {
//
//                    int i = targetFileName.lastIndexOf(".");
//                    String substring = targetFileName.substring(i + 1, targetFileName.length());
//
//                    if (substring.equals("html")) {
//                        //小写驼峰
//                        targetFileName = targetFileName.replace("[]", CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_UNDERSCORE, tableInfo.getName()));
//                        targetFileName = targetFileName.replace("html", "ftl");
//
//                    } else {
//                        //大写驼峰
//                        targetFileName = targetFileName.replace("[]", CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, tableInfo.getName()));
//                    }
//                }
//                engine.generate(templateObject, config.getTemplate(), targetFileName);
//            }
        }
    }
//    public static void main(String[] args) throws Exception {
//        //模板目录
//        String templateLocation = System.getProperty("user.dir") + "/src/main/resources/blueprint/freemarker";
//        //生成文件存储路径
////        String outputDir=System.getProperty("user.dir")+"/obj";
//        String outputDir = System.getProperty("user.dir");
//        FreemarkerEngine engine = new FreemarkerEngine(outputDir, templateLocation);
//
//        String[] selectTables = getSelectTables();
//
//        Configuration configuration = ConfigurationModel.getConfiguration();
//
//        for (String table : selectTables) {
//            TableInfo tableInfo = DBUtil.getInstance().getTableInfo(table);
//
//            ApplicationProperties templateObject = new ApplicationProperties();
////            TemplateObject templateObject = new TemplateObject();
//            templateObject.setTableInfo(tableInfo);
//            templateObject.setPackageName("org.springblade.chu");
////            templateObject.setPackageName("com.tanchengjin.generatorcode.test");
//
//            for (TemplateConfig config : configuration.getTemplates()) {
//                //动态文件名前缀
//                String targetFileName = config.getTargetName();
//                if (targetFileName.matches("(.*?)(\\[])(.*?)")) {
//
//                    int i = targetFileName.lastIndexOf(".");
//                    String substring = targetFileName.substring(i + 1, targetFileName.length());
//
//                    if (substring.equals("html")) {
//                        //小写驼峰
//                        targetFileName = targetFileName.replace("[]", CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_UNDERSCORE, tableInfo.getName()));
//                        targetFileName = targetFileName.replace("html", "ftl");
//
//                    } else {
//                        //大写驼峰
//                        targetFileName = targetFileName.replace("[]", CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, tableInfo.getName()));
//                    }
//                }
//                engine.generate(templateObject, config.getTemplate(), targetFileName);
//            }
//        }
//    }

    private static TemplateChain getTemplateChain() {
        ControllerChain controllerChain = new ControllerChain();
        ServiceChain serviceChain = new ServiceChain();
        ServiceImplChain serviceImplChain = new ServiceImplChain();
        serviceChain.setNext(serviceImplChain);
        DaoChain daoChain = new DaoChain();
        serviceImplChain.setNext(daoChain);
        MapperChain mapperChain = new MapperChain();
        daoChain.setNext(mapperChain);
        PojoChain pojoChain = new PojoChain();
        mapperChain.setNext(pojoChain);
        LAYListChain layListChain = new LAYListChain();
        pojoChain.setNext(layListChain);
        LAYLAddChain laylAddChain = new LAYLAddChain();
        layListChain.setNext(laylAddChain);
        LAYLEditChain laylEditChain = new LAYLEditChain();
        laylAddChain.setNext(laylEditChain);
        LAYLFormChain laylFormChain = new LAYLFormChain();
        laylEditChain.setNext(laylFormChain);
        controllerChain.setNext(serviceChain);
        return controllerChain;
    }

    private static String packageToPath(String packageName) {
        return "/" + packageName.replaceAll("\\.", "/");
    }


    //模拟输入表
    private static String[] getSelectTables() {
        return new String[]{
                "goods",
        };
    }
}
