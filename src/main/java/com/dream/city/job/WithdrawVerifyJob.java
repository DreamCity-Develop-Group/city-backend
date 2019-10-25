package com.dream.city.job;

import com.alibaba.fastjson.JSON;
import com.dream.city.base.Result;
import com.dream.city.base.model.Page;
import com.dream.city.base.model.entity.PlayerTrade;
import com.dream.city.base.model.enu.TradeStatus;
import com.dream.city.base.model.enu.TradeType;
import com.dream.city.base.model.enu.VerifyStatus;
import com.dream.city.base.model.req.VerifyReq;
import com.dream.city.base.model.resp.PlayerTradeResp;
import com.dream.city.service.trade.PlayerTradeService;
import com.dream.city.service.verify.WithdrawVerifyHandleService;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 提现自动审核
 */
@Slf4j
@Component
public class WithdrawVerifyJob extends QuartzJobBean {

    private String jobType = "提现";

    @Autowired
    private WithdrawVerifyHandleService verifyHandleService;
    @Autowired
    private PlayerTradeService tradeService;


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

        PlayerTrade getDataReq = new PlayerTrade();
        getDataReq.setTradeType(TradeType.WITHDRAW.getCode());
        getDataReq.setTradeStatus(TradeStatus.FREEZE.getCode());

        Page pageReq = new Page(0,10000000);
        pageReq.setCondition(getDataReq);

        PageInfo<PlayerTradeResp> respPageInfo =  tradeService.getPlayerTradeList(pageReq);
        List<PlayerTradeResp> dataList = null;
        if (respPageInfo != null){
            dataList = respPageInfo.getList();
        }
        if (CollectionUtils.isEmpty(dataList)){
            log.info(jobType+"自动审核结果:没有待审核数据");
        }

        VerifyReq verifyReq = null;
        Result result = null;
        for (PlayerTradeResp resp : dataList){
            verifyReq = new VerifyReq();
            verifyReq.setTradeId(resp.getTradeId());
            verifyReq.setVerifyStatus(VerifyStatus.PASS.getCode());
            verifyReq.setVerifyDesc(jobType+"自动审核");
            result = verifyHandleService.withdrawVerify(verifyReq);
            log.info(jobType+"自动审核结果:"+JSON.toJSONString(result));
        }

        log.info(">>>>>>>>>>> 处理"+jobType+"自动审核业务结束...");
    }



}
