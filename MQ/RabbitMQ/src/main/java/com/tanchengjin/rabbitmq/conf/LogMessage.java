package com.tanchengjin.rabbitmq.conf;

import java.io.Serializable;
import java.util.Date;

/**
 * 消息内容载体，在rabbitmq中，存储的消息可以是任意的java类型的对象。
 * 强制要求，作为消息数据载体的类型，必须是Serializable的。
 * 如果消息数据载体类型未实现Serializable，在收发消息的时候，都会有异常发生。
 */
public class LogMessage implements Serializable {

    private Long id;
    private String msg;
    private String logLevel;
    private String serviceType;
    private Date createTime;
    private Long userId;

    public LogMessage() {
        super();
    }

    public LogMessage(Long id, String msg, String logLevel, String serviceType, Date createTime, Long userId) {
        super();
        this.id = id;
        this.msg = msg;
        this.logLevel = logLevel;
        this.serviceType = serviceType;
        this.createTime = createTime;
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "LogMessage [id=" + id + ", msg=" + msg + ", logLevel=" + logLevel + ", serviceType=" + serviceType
                + ", createTime=" + createTime + ", userId=" + userId + "]";
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getLogLevel() {
        return logLevel;
    }

    public void setLogLevel(String logLevel) {
        this.logLevel = logLevel;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

}
