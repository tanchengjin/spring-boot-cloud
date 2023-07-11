package com.tanchengjin.pdf.strategy;

import com.tanchengjin.pdf.config.App;
import org.springframework.stereotype.Component;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author TanChengjin
 * @version v1.0.0
 * @email 18865477815@163.com
 */
@Component
public abstract class TemplateStrategy {
    protected App app;

    public TemplateStrategy() {
    }

    public TemplateStrategy(App app) {
        this.app = app;
    }

    /**
     * 通过freemarker渲染模板
     * @return string
     */
    public abstract String getHTMLString();

    /**
     * 获取模板入口文件
     *
     * @return String
     */
    abstract String getTemplate();

    /**
     * 默认模板
     *
     * @return List<String>
     */
    public abstract List<String> getDefaultTemplate();


    public static int getAge(Date birthDay) throws Exception {
        Calendar cal = Calendar.getInstance();
        if (cal.before(birthDay)) { //出生日期晚于当前时间，无法计算
            throw new IllegalArgumentException("The birthDay is before Now.It's unbelievable!");
        }
        int yearNow = cal.get(Calendar.YEAR);  //当前年份
        int monthNow = cal.get(Calendar.MONTH);  //当前月份
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH); //当前日期
        cal.setTime(birthDay);
        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH);
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);
        int age = yearNow - yearBirth;   //计算整岁数
        if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {
                if (dayOfMonthNow < dayOfMonthBirth) age--;//当前日期在生日之前，年龄减一
            } else {
                age--;//当前月份在生日之前，年龄减一

            }
        }
        return age;
    }


    public String getFormatDate(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日");
        String format = simpleDateFormat.format(date);
        return format;
    }

    /**
     * 获取模板所在路径
     *
     * @return 模板的根文件夹绝对路径
     */
    protected String getTemplateDir() {
        return app.getWkhtmltopdf().getTemplateDir();
    }

    /**
     * @param f 文件
     * @return
     */
    protected String autoSuffix(String f) {
        String target = getTemplateDir() + f;
        File file = new File(target + ".html");
        if (!file.exists()) {
            target = target + ".ftl";
        } else {
            target = file.getAbsolutePath();
        }
        return target;
    }
}
