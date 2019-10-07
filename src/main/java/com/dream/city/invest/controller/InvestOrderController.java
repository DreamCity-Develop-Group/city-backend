package com.dream.city.invest.controller;

import com.dream.city.base.Result;
import com.dream.city.base.model.Page;
import com.dream.city.base.model.req.InvestOrderReq;
import com.dream.city.base.model.req.VerifyReq;
import com.dream.city.invest.service.OrderService;
import com.dream.city.verify.service.InvestVerifyHandleService;
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
    @Autowired
    private InvestVerifyHandleService verifyHandleService;




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
    public Result getList(Page pageReq, InvestOrderReq record){
        logger.info(modelName + "列表，：{}",record);
        boolean success = Boolean.TRUE;
        PageInfo pageInfo = null;
        try{
            pageReq.setCondition(record);
            pageInfo = orderService.getInvestOrderList(pageReq);
        }catch (Exception e){
            success = Boolean.FALSE;
        }
        return new Result(success,modelName + "列表",pageInfo);
    }



    @RequestMapping("/verify/{id}")
    public ModelAndView verify(@PathVariable("id") Integer id, VerifyReq verifyReq, Model model){
        Object result = null;
        try {
            result = orderService.getInvestOrderById(id);
        }catch (Exception e){
            logger.error("投资预约查询"+ modelName +"异常",e);
        }
        model.addAttribute("title",modelName);
        model.addAttribute("table", modelName + "审核");
        model.addAttribute("actionPath",actionPath);
        model.addAttribute("edit",Boolean.TRUE);
        model.addAttribute("data",result);
        return new ModelAndView(actionPath + "/edit");
    }
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Result<Integer> update(VerifyReq record){
        logger.info("投资预约审核"+ modelName +"，：{}",record);
        Result result = null;
        try {
            result = verifyHandleService.subscribeOrderVerify(record);
        }catch (Exception e){
            logger.error("投资预约审核"+ modelName +"异常",e);
        }
        return new Result(result.getSuccess(),result.getMsg(),result);
    }


}
