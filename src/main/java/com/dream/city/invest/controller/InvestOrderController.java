package com.dream.city.invest.controller;

import com.dream.city.base.PageReq;
import com.dream.city.base.Result;
import com.dream.city.invest.dto.OrderReq;
import com.dream.city.invest.service.OrderService;
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
 * 投资订单
 */
@RestController
@RequestMapping("/invest")
public class InvestOrderController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private final String modelName = "投资订单";
    private final String actionPath = "invest";

    @Autowired
    private OrderService orderService;




    @RequestMapping("/get/{id}")
    public ModelAndView get(@PathVariable("id") Integer id,Model model){
        logger.info("查询"+ modelName +"：{}",id);
        Object result = null;
        try {
            result = orderService.getInvestOrderById(id);
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
    public Result getList(PageReq pageReq, OrderReq record){
        logger.info(modelName + "列表，：{}",record);
        boolean success = Boolean.TRUE;
        PageInfo result = null;
        try{
            pageReq.setCondition(record);
            result = orderService.getInvestOrderList(pageReq);
        }catch (Exception e){
            success = Boolean.FALSE;
        }
        return new Result(success,modelName + "列表",result);
    }




}
