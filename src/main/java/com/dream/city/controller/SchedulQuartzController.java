package com.dream.city.controller;

import com.dream.city.base.Result;
import com.dream.city.base.model.Page;
import com.dream.city.model.req.ScheduleReq;
import com.dream.city.service.schedule.SchedulQuartzService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;


/**
 * @author
 * 任务管理
 */
@RestController
@RequestMapping("/schedule")
public class SchedulQuartzController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private final String modelName = "计划任务";
    private final String actionPath = "schedule";

    @Autowired
    private SchedulQuartzService quartzService;



    @RequestMapping("/index")
    public ModelAndView index(ScheduleReq record,Model model){
        model.addAttribute("data",record);
        model.addAttribute("title",modelName);
        model.addAttribute("table", modelName + "列表");
        model.addAttribute("actionPath",actionPath);
        return new ModelAndView(actionPath + "/index");
    }
    @RequestMapping("/getList")
    public Result getList(Page pageReq, ScheduleReq record){
        logger.info(modelName + "列表，：{}",record);
        boolean success = Boolean.TRUE;
        //PageInfo result = null;
        List<Map<String,Object>> result = null;
        try{
            pageReq.setCondition(record);
            result = quartzService.queryAllJobs();
        }catch (Exception e){
            success = Boolean.FALSE;
        }
        return new Result(success,modelName + "列表",result);
    }


    @RequestMapping("/get/{id}")
    public ModelAndView get(@PathVariable("id") Integer id,Model model){
        logger.info("查询"+ modelName +"：{}",id);
        Object result = null;
        try {
            result = null;
        }catch (Exception e){
            logger.error("查询"+ modelName +"异常",e);
        }
        model.addAttribute("title","详情");
        model.addAttribute("table",modelName + "详情");
        model.addAttribute("edit",Boolean.FALSE);
        model.addAttribute("data",result);
        return new ModelAndView(actionPath + "/edit");
    }


    @RequestMapping("/add")
    public ModelAndView add(ScheduleReq record,Model model){
        model.addAttribute("title","添加");
        model.addAttribute("table","添加" + modelName);
        model.addAttribute("actionPath",actionPath);
        model.addAttribute("data",record);
        return new ModelAndView(actionPath + "/add");
    }
    @RequestMapping("/insert")
    public Result insert(ScheduleReq record){
        logger.info("新增"+ modelName +"，：{}",record);
        boolean success = Boolean.FALSE;
        try {
            quartzService.addJob(record.getJobClass(),record.getJobName(),record.getJobGroupName(),record.getJobTime());
            success = Boolean.TRUE;
        }catch (Exception e){
            logger.error("新增"+ modelName +"异常",e);
        }
        return new Result(success,"新增" + modelName);
    }


    @RequestMapping("/delete")
    public Result delete(ScheduleReq record){
        logger.info("删除"+ modelName +"，{}",record);
        boolean success = Boolean.FALSE;
        try {
            quartzService.deleteJob(record.getJobName(),record.getJobGroupName());
            success = Boolean.TRUE;
        }catch (Exception e){
            logger.error("删除"+ modelName +"异常",e);
        }
        return new Result(success,"删除"+ modelName);
    }


    @RequestMapping("/changeStatus")
    public Result changeStatus(ScheduleReq record){
        logger.info("修改状态"+ modelName +"，{}",record);
        boolean success = Boolean.FALSE;
        try {
            String editType = record.getEditType();
            if ("pause".equalsIgnoreCase(editType)){
                //暂停一个job
                quartzService.pauseJob(record.getJobName(),record.getJobGroupName());
            }else if ("resume".equalsIgnoreCase(editType)){
                //恢复一个已经暂停的job
                quartzService.resumeJob(record.getJobName(),record.getJobGroupName());
            }else if ("run".equalsIgnoreCase(editType)){
                //执行一个job
                quartzService.runJobNow(record.getJobName(),record.getJobGroupName());
            }
            success = Boolean.TRUE;
        }catch (Exception e){
            logger.error("修改状态"+ modelName +"异常",e);
        }
        return new Result(success,"修改状态"+ modelName);
    }



    @RequestMapping("/edit")
    public ModelAndView edit(ScheduleReq record, Model model){
        model.addAttribute("data",record);
        model.addAttribute("title","编辑");
        model.addAttribute("table","编辑"+ modelName);
        model.addAttribute("edit",Boolean.TRUE);
        model.addAttribute("actionPath",actionPath);
        return new ModelAndView(actionPath + "/edit");
    }
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Result<Integer> update(ScheduleReq record){
        logger.info("修改"+ modelName +"，：{}",record);
        boolean success = Boolean.FALSE;
        try {
            quartzService.updateJob(record.getJobName(),record.getJobGroupName(),record.getJobTime());
            success = Boolean.TRUE;
        }catch (Exception e){
            logger.error("修改"+ modelName +"异常",e);
        }
        return new Result(success,"修改"+ modelName);
    }




}
