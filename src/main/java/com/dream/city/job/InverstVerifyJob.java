package com.dream.city.job;

import com.alibaba.fastjson.JSON;
import com.dream.city.base.Result;
import com.dream.city.base.model.Page;
import com.dream.city.base.model.enu.InvestStatus;
import com.dream.city.base.model.enu.VerifyStatus;
import com.dream.city.base.model.req.InvestOrderReq;
import com.dream.city.base.model.req.VerifyReq;
import com.dream.city.base.model.resp.InvestOrderResp;
import com.dream.city.service.invest.OrderService;
import com.dream.city.service.verify.InvestVerifyHandleService;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 投资预约自动审核
 */
@Slf4j
@Component
public class InverstVerifyJob extends QuartzJobBean {

    private String jobType = "投资预约";

    @Autowired
    private InvestVerifyHandleService investVerifyHandleService;
    @Autowired
    private OrderService orderService;


    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) {
        long time = System.currentTimeMillis();
        log.info(">>>>>>>>>>> "+jobType+"自动审核任务开始执行");
        try {
            executeTask();
        } catch (Exception e) {
            log.error(jobType+"自动审核任务执行Exception",e);
        }
        log.info(">>>>>>>>>>> "+jobType+"自动审核任务执行结束,使用时间(毫秒)：" + (System.currentTimeMillis() - time));
    }

    private void executeTask() {
        log.info(">>>>>>>>>>> 处理"+jobType+"自动审核业务...");

        InvestOrderReq getDataReq = new InvestOrderReq();
        getDataReq.setOrderState(InvestStatus.SUBSCRIBED.name());

        Page pageReq = new Page(0,10000000);
        pageReq.setCondition(getDataReq);

        PageInfo<InvestOrderResp> respPageInfo =  orderService.getInvestOrderList(pageReq);
        List<InvestOrderResp> dataList = null;
        if (respPageInfo != null){
            dataList = respPageInfo.getList();
        }
        if (CollectionUtils.isEmpty(dataList)){
            log.info(jobType+"自动审核结果:没有待审核数据");
        }

        VerifyReq verifyReq = null;
        Result result = null;
        for (InvestOrderResp resp : dataList){
            verifyReq = new VerifyReq();
            verifyReq.setOrderId(resp.getOrderId());
            verifyReq.setVerifyStatus(VerifyStatus.PASS.getCode());
            verifyReq.setVerifyDesc(jobType+"自动审核");
            result = investVerifyHandleService.subscribeOrderVerify(verifyReq);
            log.info(jobType+"自动审核结果:"+JSON.toJSONString(result));
        }

        log.info(">>>>>>>>>>> 处理"+jobType+"自动审核业务结束...");
    }



}
