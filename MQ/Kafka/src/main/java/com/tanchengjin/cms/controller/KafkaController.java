package com.tanchengjin.cms.controller;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.tanchengjin.cms.util.AESUtil.decrypt;

/**
 * @Author TanChengjin
 * @Email 18865477815@163.com
 * @Since V1.0.0
 **/
@RequestMapping("kafka")
@RestController
public class KafkaController {
    private final static Logger logger = LoggerFactory.getLogger(KafkaController.class);

    private final KafkaTemplate<String, String> kafkaTemplate;

    public KafkaController(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    /**
     * 生产者
     *
     * @return
     */
    @GetMapping(value = "producter")
    @ResponseBody
    public String producter() {
        kafkaTemplate.send("test", "test");
        return "app controller";
    }

    /**
     * 消费者
     *
     * @return
     */
    @KafkaListener(topics = "test")
    public void consumer(ConsumerRecord<String, String> consumerRecord) {
        System.out.println("收到消息");
        System.out.println(unicodeToString1(consumerRecord.value()));
        System.out.println(consumerRecord.value());
        System.out.println(consumerRecord.toString());
    }

    private static String unicodeToString1(String str) {
        Pattern pattern = Pattern.compile("(\\\\u(\\w{4}))");
        Matcher matcher = pattern.matcher(str);
        char ch;
        while (matcher.find()) {
            // 本行为核心代码，处理当前的unicode后4位变为16进制，在转换为对应的char中文字符
            ch = (char) Integer.parseInt(matcher.group(2), 16);
            str = str.replace(matcher.group(1), ch + "");
        }
        return str;
    }

    public static void main(String[] args) {
//        String encrypt = encrypt("123rtest");
        String encrypt = decrypt("0d6/NNElozl7+SFsFNEvJA==");
        System.out.println(encrypt);
    }


}
