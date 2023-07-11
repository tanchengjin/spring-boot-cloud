package com.tanchengjin.pdf.strategy;

import org.springframework.stereotype.Component;

/**
 * @author TanChengjin
 * @version v1.0.0
 * @email 18865477815@163.com
 */
@Component
public class ReportStrategyContext {

    private TemplateStrategy reportStrategy;

    public ReportStrategyContext() {
    }

    public ReportStrategyContext(TemplateStrategy reportStrategy) {
        this.reportStrategy = reportStrategy;
    }

    public String action() {
        return this.reportStrategy.getHTMLString();
    }
}
