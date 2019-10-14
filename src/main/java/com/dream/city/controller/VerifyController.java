package com.dream.city.controller;

import com.dream.city.base.Result;
import com.dream.city.base.model.enu.VerifyStatus;
import com.dream.city.base.model.req.VerifyReq;
import com.dream.city.base.model.resp.InvestOrderResp;
import com.dream.city.base.model.resp.PlayerTradeResp;
import com.dream.city.service.invest.OrderService;
import com.dream.city.service.trade.PlayerTradeService;
import com.dream.city.service.verify.InvestVerifyHandleService;
import com.dream.city.service.verify.WithdrawVerifyHandleService;
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
 * 审核
 */
@RestController
@RequestMapping("/verify")
public class VerifyController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    OrderService orderService;
    @Autowired
    PlayerTradeService tradeService;
    @Autowired
    WithdrawVerifyHandleService withdrawVerifyHandleService;
    @Autowired
    InvestVerifyHandleService verifyHandleService;


    /**
     * 投资预约审核
     * @param id 订单id
     * @param model
     * @return
     */
    @RequestMapping("/subscribeOrderVerifyPage/{id}")
    public ModelAndView subscribeOrderVerifyPage(@PathVariable("id") Integer id, Model model){
        InvestOrderResp result = orderService.getInvestOrderById(id);
        this.getModel(result, model, "投资预约");
        return new ModelAndView("/invest/edit");
    }
    @RequestMapping(value = "/subscribeOrderVerify", method = RequestMethod.POST)
    public Result<Integer> subscribeOrderVerify(VerifyReq record){
        logger.info("投资预约审核，{}",record);
        String msg = "投资预约审核失败";
        boolean success = Boolean.FALSE;
        try {
            if (VerifyStatus.PASS.getCode().equalsIgnoreCase(record.getVerifyStatus())
                    || VerifyStatus.NOTPASS.getCode().equalsIgnoreCase(record.getVerifyStatus())){
                Result result = verifyHandleService.subscribeOrderVerify(record);
                if (result != null){
                    success = result.getSuccess();
                    msg = result.getMsg();
                }
            }else {
                msg = "投资预约审核状态不对，当前提交状态:"+record.getVerifyStatus();
            }
        }catch (Exception e){
            logger.error("投资预约审核异常",e);
        }
        return new Result<>(success,msg);
    }



    /**
     * 提现审核
     * @param id 提现交易id
     * @param model
     * @return
     */
    @RequestMapping("/withdrawVerifyPage/{id}")
    public ModelAndView withdrawVerifyPage(@PathVariable("id") Integer id, Model model){
        PlayerTradeResp result = tradeService.getPlayerTradeById(id);
        this.getModel(result, model, "提现");
        return new ModelAndView("/trade/withdraw/edit");
    }
    @RequestMapping(value = "/withdrawVerify", method = RequestMethod.POST)
    public Result withdrawVerify(VerifyReq record){
        return withdrawAndTransferVerify(record,"提现");
    }


    /**
     * 转账审核
     * @param id 转账交易id
     * @param model
     * @return
     */
    @RequestMapping("/transferVerifyPage/{id}")
    public ModelAndView transferVerifyPage(@PathVariable("id") Integer id, Model model){
        PlayerTradeResp result = tradeService.getPlayerTradeById(id);
        this.getModel(result, model, "转账");
        return new ModelAndView("/trade/transfer/edit");
    }
    @RequestMapping(value = "/transferVerify", method = RequestMethod.POST)
    public Result transferVerify(VerifyReq record){
        return withdrawAndTransferVerify(record,"转账");
    }


    private Result withdrawAndTransferVerify(VerifyReq record,String modelName){
        logger.info(modelName + "审核，{}",record);
        String msg = modelName + "审核失败";
        boolean success = Boolean.FALSE;
        try {
            if (VerifyStatus.PASS.getCode().equalsIgnoreCase(record.getVerifyStatus())
                    || VerifyStatus.NOTPASS.getCode().equalsIgnoreCase(record.getVerifyStatus())){
                Result result = withdrawVerifyHandleService.withdrawVerify(record);
                if (result != null){
                    success = result.getSuccess();
                    msg = result.getMsg();
                }
            }else {
                msg = modelName + "审核状态不对，当前状态:"+record.getVerifyStatus();
            }
        }catch (Exception e){
            msg = modelName + "审核异常";
            logger.error(modelName + "审核异常",e);
        }
        return new Result<>(success,msg);
    }


    private void getModel(Object result,Model model,String modelName){
        model.addAttribute("data",result);
        model.addAttribute("title","审核");
        model.addAttribute("table",modelName + "审核");
        model.addAttribute("edit",Boolean.TRUE);
    }



}
