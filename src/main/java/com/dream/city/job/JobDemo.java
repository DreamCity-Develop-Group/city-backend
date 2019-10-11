package com.dream.city.job;

import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.SchedulerException;

@Slf4j
public class JobDemo implements Job {


    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
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
