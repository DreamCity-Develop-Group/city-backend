package com.dream.city.setting.controller;

import com.dream.city.base.Result;
import com.dream.city.base.model.Page;
import com.dream.city.base.model.entity.RuleItem;
import com.dream.city.setting.service.RuleItemService;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


/**
 * @author
 * 规则项
 */
@RestController
@RequestMapping("/setting/rule/item")
public class RuleItemController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private final String modelName = "规则项";
    private final String actionPath = "setting/rule/item";

    @Autowired
    private RuleItemService itemService;



    @RequestMapping("/add")
    public ModelAndView add(RuleItem record, Model model){
        model.addAttribute("title","添加");
        model.addAttribute("table","添加" + modelName);
        model.addAttribute("actionPath",actionPath);
        model.addAttribute("data",record);
        return new ModelAndView(actionPath + "/add");
    }
    @RequestMapping("/insert")
    public Result insert(RuleItem record){
        logger.info("新增"+ modelName +"，：{}",record);
        boolean success = Boolean.FALSE;
        Integer result = 0;
        try {
            result = itemService.insertRuleItem(record);
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
            result = itemService.deleteRuleItem(id);
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
        model.addAttribute("data",itemService.getRuleItemById(id));
        model.addAttribute("title","编辑");
        model.addAttribute("table","编辑"+ modelName);
        model.addAttribute("edit",Boolean.TRUE);
        model.addAttribute("actionPath",actionPath);
        return new ModelAndView(actionPath + "/edit");
    }
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Result<Integer> update(RuleItem record){
        logger.info("修改"+ modelName +"，：{}",record);
        boolean success = Boolean.FALSE;
        Integer result = 0;
        try {
            result = itemService.updateRuleItem(record);
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
            result = itemService.getRuleItemById(id);
        }catch (Exception e){
            logger.error("查询"+ modelName +"异常",e);
        }
        model.addAttribute("title","详情");
        model.addAttribute("table",modelName + "详情");
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
    public Result getList(Page pageReq, RuleItem record){
        logger.info(modelName + "列表，：{}",record);
        boolean success = Boolean.TRUE;
        PageInfo result = null;
        try{
            pageReq.setCondition(record);
            result = itemService.getRuleItemList(pageReq);
        }catch (Exception e){
            success = Boolean.FALSE;
        }
        return new Result(success,modelName + "列表",result);
    }


}
