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
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 预约自动审核
 */
@Slf4j
@Component
public class InverstVerifyJob extends QuartzJobBean {


    @Autowired
    private InvestVerifyHandleService investVerifyHandleService;
    @Autowired
    OrderService orderService;



    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        long time = System.currentTimeMillis();
        log.info(">>>>>>>>>>> 预约自动审核任务开始执行");
        try {
            executeTask();
        } catch (SchedulerException e) {
            log.error("预约自动审核任务执行Exception",e);
        }
        log.info(">>>>>>>>>>> 预约自动审核任务执行结束,使用时间(毫秒)：" + (System.currentTimeMillis() - time));
    }

    private void executeTask() throws SchedulerException {
        log.info(">>>>>>>>>>> 处理预约自动审核业务...");

        InvestOrderReq orderReq = new InvestOrderReq();
        orderReq.setOrderState(InvestStatus.SUBSCRIBED.name());

        Page pageReq = new Page(0,10000000);
        pageReq.setCondition(orderReq);

        PageInfo<InvestOrderResp> orderRespPageInfo =  orderService.getInvestOrderList(pageReq);
        List<InvestOrderResp> dataList = null;
        if (orderRespPageInfo != null){
            dataList = orderRespPageInfo.getList();
        }
        if (CollectionUtils.isEmpty(dataList)){
            log.info("预约自动审核结果:没有待审核数据");
        }

        VerifyReq verifyReq = null;
        Result result = null;
        for (InvestOrderResp orderResp : dataList){
            verifyReq = new VerifyReq();
            verifyReq.setOrderId(orderResp.getOrderId());
            verifyReq.setVerifyStatus(VerifyStatus.PASS.getCode());
            verifyReq.setVerifyDesc("预约自动审核");
            result = investVerifyHandleService.subscribeOrderVerify(verifyReq);
            log.info("预约自动审核结果:"+JSON.toJSONString(result));
        }

        log.info(">>>>>>>>>>> 处理预约自动审核业务结束...");
    }



}
