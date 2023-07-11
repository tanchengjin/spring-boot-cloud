package com.tanchengjin.pdf.controller;

import com.tanchengjin.pdf.config.App;
import com.tanchengjin.pdf.config.WkhtmltopdfConfig;
import com.tanchengjin.pdf.modules.wkhtmltopdf.FreemarkerUtil;
import com.tanchengjin.pdf.modules.wkhtmltopdf.WkhtmltopdfUtil;
import com.tanchengjin.pdf.strategy.DefaultTemplateStrategy;
import com.tanchengjin.pdf.strategy.ReportStrategyContext;
import com.tanchengjin.pdf.vo.BatchPrint;
import com.tanchengjin.shop.util.ServerResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

/**
 * @Author TanChengjin
 * @Email 18865477815@163.com
 * @Since V1.0.0
 **/
@Controller
public class IndexController {

    private WkhtmltopdfConfig wkhtmltopdfConfig;

    public IndexController(WkhtmltopdfConfig wkhtmltopdfConfig) {
        this.wkhtmltopdfConfig = wkhtmltopdfConfig;
    }

    @GetMapping(value = {"", "/"})
    @ResponseBody
    public String index() {
        return "index controller";
    }

    /**
     * 批量打印
     */
    @RequestMapping("/doBatchPrintLogic")
    public ServerResponse<?> batchPrint() {
        //保存了所有的html页面
        ArrayList<String> htmlList = new ArrayList<>();

        ArrayList<Object> peopleList = new ArrayList<>();

        ExecutorService executorService = Executors.newFixedThreadPool(peopleList.size());

        final CountDownLatch latch = new CountDownLatch(peopleList.size());

        ArrayList<BatchPrint> batchPrints = new ArrayList<>();

        peopleList.forEach(e -> {

            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    //打印简报
                    ReportStrategyContext reportStrategyContext = new ReportStrategyContext(new DefaultTemplateStrategy());
                    String html = reportStrategyContext.action();
                    BatchPrint batchPrint = new BatchPrint();
                    batchPrint.setHtml(html);
                    batchPrints.add(batchPrint);
//                    htmlList.add(html);
                    latch.countDown();
                }
            });
        });

        try {
            latch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        executorService.shutdown();

        List<BatchPrint> batchPrintListSorted = batchPrints.stream().sorted(Comparator.comparing(BatchPrint::getPhysicalNumber)).collect(Collectors.toList());

//        for (String s : dto.getPersonList()) {
//            Optional<BatchPrint> first = batchPrints.stream().filter(f -> s.equals(f.getId())).findFirst();
//            first.ifPresent(batchPrint -> htmlList.add(batchPrint.getHtml()));
//        }

        List<String> collect = batchPrintListSorted.stream().map(BatchPrint::getHtml).collect(Collectors.toList());
        HashMap<String, Object> stringObjectHashMap = new HashMap<>();
        stringObjectHashMap.put("htmlList", collect);
        return ServerResponse.responseWithSuccess(stringObjectHashMap);
    }

    /**
     * 模板渲染
     *
     * @return
     */
    @RequestMapping(value = "/htmlRendering", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<List<String>> templateRendering() {
        HashMap<String, Object> stringObjectHashMap = new HashMap<>();
        stringObjectHashMap.put("var1", "123");
        String vo = FreemarkerUtil.makeHTML("vo", stringObjectHashMap, "E:/java/demo-spring-cloud/html-to-pdf/src/main/resources/templates/index.ftl");
        String s = WkhtmltopdfUtil.getInstance().config(wkhtmltopdfConfig.getExec(),wkhtmltopdfConfig.getCacheDir()).html(vo).toPDF();
        List<String> htmlVO = new ArrayList<>();
        htmlVO.add(vo);
        return ServerResponse.responseWithSuccess(htmlVO);
    }
}
