package com.dream.city.controller;

import com.dream.city.base.BaseController;
import com.dream.city.base.Result;
import com.dream.city.base.model.Page;
import com.dream.city.base.model.entity.SalesOrder;
import com.dream.city.service.trade.SalesOrderService;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;


/**
 * @author
 * 玩家购买MT
 */
@RestController
@RequestMapping("/trade/buymt")
public class SalesOrderController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private final String modelName = "玩家购买MT";
    private final String actionPath = "trade/buymt";

    @Autowired
    private SalesOrderService orderService;




    @RequestMapping("/get/{id}")
    public ModelAndView get(@PathVariable("id") Integer id,Model model){
        logger.info("查询"+ modelName +"：{}",id);
        Object result = null;
        try {
            result = orderService.getSalesOrderById(id);
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
    public ModelAndView index(SalesOrder record,Model model){
        model.addAttribute("data",record);
        model.addAttribute("title",modelName);
        model.addAttribute("table", modelName + "列表");
        model.addAttribute("actionPath",actionPath);
        return new ModelAndView(actionPath + "/index");
    }
    @RequestMapping("/getList")
    public Result getList(Page pageReq, SalesOrder record){
        logger.info(modelName + "列表，：{}",record);
        boolean success = Boolean.TRUE;
        PageInfo result = null;
        try{
            pageReq.setCondition(record);
            result = orderService.getSalesOrderList(pageReq);
        }catch (Exception e){
            success = Boolean.FALSE;
            logger.error("查询"+modelName+"列表异常",e);
        }
        return new Result(success,modelName + "列表",result);
    }




}
