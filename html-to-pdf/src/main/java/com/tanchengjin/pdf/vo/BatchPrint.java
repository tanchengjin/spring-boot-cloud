package com.tanchengjin.pdf.vo;
/**
 * @author TanChengjin
 * @version v1.0.0
 * @email 18865477815@163.com
 */
public class BatchPrint {
    private String html;
    private int physicalNumber;
    private String id;

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public int getPhysicalNumber() {
        return physicalNumber;
    }

    public void setPhysicalNumber(int physicalNumber) {
        this.physicalNumber = physicalNumber;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}