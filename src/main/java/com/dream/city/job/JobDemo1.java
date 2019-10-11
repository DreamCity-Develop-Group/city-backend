package com.dream.city.job;

import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.SchedulerException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JobDemo1 {


    //@Scheduled(cron="0/5 * * * * ?")
    public void execute() {
        log.info(">>>>>>>>>>>任务开始执行了");
        try {
            executeTask();
        } catch (SchedulerException e) {
            log.error("JobDemo execute Exception",e);
        }
        log.info(">>>>>>>>>>>任务执行结束了");
    }

    private void executeTask() throws SchedulerException {
        // todo ...
        log.info(">>>>>>>>>>>处理业务...");

    }



}
