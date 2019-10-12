package com.dream.city.service.schedule.impl;

import com.dream.city.base.model.Page;
import com.dream.city.base.model.mapper.QrtzJobDetailsMapper;
import com.dream.city.base.model.req.ScheduleReq;
import com.dream.city.base.model.resp.ScheduleResp;
import com.dream.city.base.utils.DataUtils;
import com.dream.city.service.schedule.SchedulQuartzService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * @author
 */
@Slf4j
@Service
public class SchedulQuartzServiceImpl implements SchedulQuartzService {

    @Autowired
    private Scheduler scheduler;
    @Autowired
    private QrtzJobDetailsMapper jobDetailsMapper;

    @PostConstruct
    public void startScheduler() {
        try {
            scheduler.start();
        } catch (SchedulerException e) {
            log.error("startScheduler() Exception",e);
        }
    }

    /**
     * 增加一个job
     *
     * @param jobClass     任务实现类
     * @param jobName      任务名称
     * @param jobGroupName 任务组名
     * @param jobTime      时间表达式 (这是每隔多少秒为一次任务)
     * @param jobTimes     运行的次数 （<0:表示不限次数）
     */
    @Override
    public void addJob(Class<? extends QuartzJobBean> jobClass, String jobName, String jobGroupName,
                       int jobTime, int jobTimes,String data) {
        try {
            JobDetail jobDetail = JobBuilder.newJob(jobClass)
                    .withIdentity(jobName, jobGroupName)
                    .usingJobData("data",data)
                    .build();

            Trigger trigger = null;
            if (jobTimes < 0) {
                trigger = TriggerBuilder.newTrigger().withIdentity(jobName, jobGroupName)
                        .withSchedule(SimpleScheduleBuilder.repeatSecondlyForever(1).withIntervalInSeconds(jobTime))
                        .startNow().build();

            } else {
                trigger = TriggerBuilder.newTrigger()
                        .withIdentity(jobName, jobGroupName)
                        //.usingJobData(data)
                        .withSchedule(SimpleScheduleBuilder.repeatSecondlyForever(1)
                                                            .withIntervalInSeconds(jobTime)
                                                            .withRepeatCount(jobTimes)
                        )
                        .startNow().build();
            }
            //scheduler.getContext().put("data",data);
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (SchedulerException e) {
            log.error("addJob() Exception",e);
        }
    }

    @Override
    public void addJob(Class<? extends QuartzJobBean> jobClass, String jobName, String jobGroupName,
                       int jobTime, int jobTimes) {
        try {
            JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(jobName, jobGroupName).build();
            Trigger trigger = null;
            if (jobTimes < 0) {
                trigger = TriggerBuilder.newTrigger().withIdentity(jobName, jobGroupName)
                        .withSchedule(SimpleScheduleBuilder.repeatSecondlyForever(1).withIntervalInSeconds(jobTime))
                        .startNow().build();

            } else {
                trigger = TriggerBuilder.newTrigger().withIdentity(jobName, jobGroupName)
                        .withSchedule(SimpleScheduleBuilder.repeatSecondlyForever(1)
                                .withIntervalInSeconds(jobTime)
                                .withRepeatCount(jobTimes))
                        .startNow().build();
            }
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (SchedulerException e) {
            log.error("addJob() Exception",e);
        }
    }

    /**
     * 增加一个job
     *
     * @param jobClass     任务实现类
     * @param jobName      任务名称
     * @param jobGroupName 任务组名
     * @param jobTime      时间表达式 （如：0/5 * * * * ? ）
     */
    @Override
    public void addJob(Class<? extends QuartzJobBean> jobClass, String jobName, String jobGroupName, String jobTime,JobDataMap jobDataMap) {
        try {
            JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(jobName, jobGroupName).build();

            Trigger trigger = TriggerBuilder.newTrigger().withIdentity(jobName, jobGroupName)
                    .startAt(DateBuilder.futureDate(1, DateBuilder.IntervalUnit.SECOND))
                    .withSchedule(CronScheduleBuilder.cronSchedule(jobTime))
                    .startNow().build();

            scheduler.scheduleJob(jobDetail, trigger);
        } catch (SchedulerException e) {
            log.error("addJob() Exception",e);
        }
    }

    /**
     * 更新任务
     * @param jobName
     * @param jobGroupName
     * @param jobTime
     */
    @Override
    public void updateJob(String jobName, String jobGroupName, String jobTime,JobDataMap jobDataMap) {
        try {
            TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroupName);
            CronTrigger cronTrigger = (CronTrigger) scheduler.getTrigger(triggerKey);
            cronTrigger = cronTrigger.getTriggerBuilder().withIdentity(triggerKey).usingJobData(jobDataMap)
                    .withSchedule(CronScheduleBuilder.cronSchedule(jobTime)).build();
            scheduler.rescheduleJob(triggerKey, cronTrigger);

        } catch (SchedulerException e) {
            log.error("updateJob() Exception",e);
        }
    }

    /**
     * 删除任务
     * @param jobName
     * @param jobGroupName
     */
    @Override
    public void deleteJob(String jobName, String jobGroupName) {
        try {
            scheduler.deleteJob(new JobKey(jobName, jobGroupName));
        } catch (SchedulerException e) {
            log.error("deleteJob() Exception",e);
        }
    }

    /**
     * 暂停一个job
     *
     * @param jobName
     * @param jobGroupName
     */
    @Override
    public void pauseJob(String jobName, String jobGroupName) {
        try {
            JobKey jobKey = JobKey.jobKey(jobName, jobGroupName);
            scheduler.pauseJob(jobKey);
        } catch (SchedulerException e) {
            log.error("pauseJob() Exception",e);
        }
    }

    /**
     * 恢复任务
     * @param jobName
     * @param jobGroupName
     */
    @Override
    public void resumeJob(String jobName, String jobGroupName) {
        try {

            JobKey jobKey = JobKey.jobKey(jobName, jobGroupName);
            scheduler.resumeJob(jobKey);
        } catch (SchedulerException e) {
            log.error("resumeJob() Exception",e);
        }
    }

    /**
     * 执行任务
     * @param jobName
     * @param jobGroupName
     */
    @Override
    public void runJobNow(String jobName,String jobGroupName){
        try {
            JobKey jobKey = JobKey.jobKey(jobName,jobGroupName);
            scheduler.triggerJob(jobKey);
        }catch (SchedulerException e){
            log.error("runJobNow() Exception",e);
        }
    }

    /**
     * 查找所有任务
     * @return
     */
    @Override
    public List<Map<String,Object>> queryAllJobs(){
        List<Map<String,Object>> jobList = null;
        try {
            GroupMatcher<JobKey> matcher = GroupMatcher.anyGroup();
            Set<JobKey> jobKeys = scheduler.getJobKeys(matcher);
            jobList = new ArrayList<>();
            for (JobKey jobKey : jobKeys){
                List<? extends Trigger>triggers =  scheduler.getTriggersOfJob(jobKey);
                for (Trigger trigger : triggers){
                    Map<String,Object>map = new HashMap<>();
                    map.put("jobName",jobKey.getName());
                    map.put("jobGroupName",jobKey.getGroup());
                    map.put("descr","触发器："+trigger.getKey());
                    Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
                    map.put("jobStatus",triggerState.name());
                    if (trigger instanceof CronTrigger){
                        CronTrigger cronTrigger = (CronTrigger)trigger;
                        String cronExpression = cronTrigger.getCronExpression();
                        map.put("jobTime",cronExpression);
                    }
                    jobList.add(map);
                }
            }
        }catch (SchedulerException e){
            log.error("queryAllJobs() Exception",e);
        }
        return  jobList;
    }

    /**
     * 查找所有运行任务
     * @return
     */
    @Override
    public List<Map<String,Object>> queryRunJobs(){
        List<Map<String,Object>> jobList = null;
        try {
            List<JobExecutionContext>executingJobs = scheduler.getCurrentlyExecutingJobs();
            jobList = new ArrayList<Map<String, Object>>(executingJobs.size());
            for (JobExecutionContext context : executingJobs){
                Map<String,Object>map = new HashMap<String, Object>();
                JobDetail jobDetail = context.getJobDetail();
                JobKey jobKey = jobDetail.getKey();
                Trigger trigger = context.getTrigger();
                map.put("jobName",jobKey.getName());
                map.put("jobGroupName",jobKey.getGroup());
                map.put("desc","触发器："+trigger.getKey());
                Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
                map.put("jobStatus",triggerState.name());
                if (trigger instanceof  CronTrigger){
                    CronTrigger cronTrigger = (CronTrigger)trigger;
                    String cronExpression = cronTrigger.getCronExpression();
                    map.put("jobTime",cronExpression);
                }
                jobList.add(map);
            }
        }catch (SchedulerException e){
            log.error("queryRunJobs() Exception",e);
        }
        return jobList;
    }

    @Override
    public ScheduleResp getJobByJobNameJobGroup(ScheduleReq record) {
        return jobDetailsMapper.getJobByJobNameJobGroup(record);
    }

    @Override
    public PageInfo<ScheduleResp> getJobTriggerCronList(Page page) {
        ScheduleReq record = DataUtils.toJavaObject(page.getCondition(),ScheduleReq.class);
        PageHelper.startPage(page.getPageNum(),page.getPageSize());
        List<ScheduleResp> list = jobDetailsMapper.getJobTriggerCronList(record);
        return new PageInfo<>(list);
    }


}
