package com.dream.city.controller;

import com.dream.city.base.BaseController;
import com.dream.city.base.Result;
import com.dream.city.base.model.Page;
import com.dream.city.base.model.enu.InvestStatus;
import com.dream.city.base.model.enu.OrderState;
import com.dream.city.base.model.req.InvestOrderReq;
import com.dream.city.service.invest.OrderService;
import com.dream.city.service.verify.InvestVerifyHandleService;
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
public class InvestOrderController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private final String modelName = "投资";
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
    public ModelAndView index(InvestOrderReq record,Integer verify,Model model){
        verify = verify == null?0:verify;
        model.addAttribute("data",record);
        model.addAttribute("verify",verify);
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
        String status = InvestStatus.SUBSCRIBED.name();
        if (record.getOrderState().equals(status)){
            record.setOrderState("1");
        }
        try{
            pageReq.setCondition(record);
            pageInfo = orderService.getInvestOrderList(pageReq);
        }catch (Exception e){
            success = Boolean.FALSE;
            logger.error("投查询"+ modelName +"列表异常",e);
        }
        return new Result(success,modelName + "列表",pageInfo);
    }


}
