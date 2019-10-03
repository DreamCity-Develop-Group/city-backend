package com.dream.city.setting.controller;

import com.dream.city.base.PageReq;
import com.dream.city.base.Result;
import com.dream.city.setting.dto.RuleReq;
import com.dream.city.setting.entity.InvestRule;
import com.dream.city.setting.service.InvestRuleService;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;


/**
 * @author
 * 投资规则
 */
@RestController
@RequestMapping("/setting/rule/invest")
public class InvestRuleController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private final String modelName = "投资规则";
    private final String actionPath = "setting/rule/invest";

    @Autowired
    private InvestRuleService ruleService;



    @RequestMapping("/add")
    public ModelAndView add(InvestRule record, Model model){
        model.addAttribute("title","添加");
        model.addAttribute("table","添加" + modelName);
        model.addAttribute("actionPath",actionPath);
        model.addAttribute("data",record);
        return new ModelAndView(actionPath + "/add");
    }
    @RequestMapping("/insert")
    public Result insert(InvestRule record){
        logger.info("新增"+ modelName +"，：{}",record);
        boolean success = Boolean.FALSE;
        Integer result = 0;
        try {
            result = ruleService.insertInvestRule(record);
            if (result > 0){
                success = Boolean.TRUE;
            }
        }catch (Exception e){
            logger.error("新增"+ modelName +"异常",e);
        }
        return new Result(success,"新增" + modelName,result);
    }


    @RequestMapping("/delete/{id}")
    public Result delete(@PathVariable("id") Integer id){
        logger.info("删除"+ modelName +"，：{}",id);
        boolean success = Boolean.FALSE;
        Integer result = 0;
        try {
            result = ruleService.deleteInvestRuleById(id);
            if (result > 0){
                success = Boolean.TRUE;
            }
        }catch (Exception e){
            logger.error("删除"+ modelName +"异常",e);
        }
        return new Result(success,"删除"+ modelName,result);
    }



    @RequestMapping("/edit/{id}")
    public ModelAndView edit(@PathVariable("id") Integer id, Model model){
        model.addAttribute("data",ruleService.getInvestRuleById(id));
        model.addAttribute("title","编辑");
        model.addAttribute("table","编辑"+ modelName);
        model.addAttribute("edit",Boolean.TRUE);
        model.addAttribute("actionPath",actionPath);
        return new ModelAndView(actionPath + "/edit");
    }
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Result<Integer> update(RuleReq record){
        logger.info("修改"+ modelName +"，：{}",record);
        boolean success = Boolean.FALSE;
        Integer result = 0;
        try {
            result = ruleService.updateInvestRuleById(record);
            if (result != null && result > 0){
                success = Boolean.TRUE;
            }
        }catch (Exception e){
            logger.error("修改"+ modelName +"异常",e);
        }
        return new Result(success,"修改"+ modelName,result);
    }




    @RequestMapping("/get/{id}")
    public ModelAndView get(@PathVariable("id") Integer id,Model model){
        logger.info("查询"+ modelName +"：{}",id);
        Object result = null;
        try {
            result = ruleService.getInvestRuleById(id);
        }catch (Exception e){
            logger.error("查询"+ modelName +"异常",e);
        }
        model.addAttribute("title","详情");
        model.addAttribute("table", modelName + "详情");
        model.addAttribute("edit",Boolean.FALSE);
        model.addAttribute("data",result);
        return new ModelAndView(actionPath + "/edit");
    }




    @RequestMapping("/index")
    public ModelAndView index(Model model){
        model.addAttribute("title",modelName);
        model.addAttribute("table", modelName + "列表");
        model.addAttribute("actionPath",actionPath);
        return new ModelAndView(actionPath + "/index");
    }
    @RequestMapping("/getList")
    public Result getList(PageReq pageReq, RuleReq record){
        logger.info(modelName + "列表，：{}",record);
        boolean success = Boolean.TRUE;
        PageInfo result = null;
        try{
            pageReq.setCondition(record);
            result = ruleService.getInvestRuleList(pageReq);
        }catch (Exception e){
            success = Boolean.FALSE;
        }
        return new Result(success,modelName + "列表",result);
    }


}
