package com.dream.city.controller;

import com.dream.city.base.BaseController;
import com.dream.city.base.Result;
import com.dream.city.base.model.Page;
import com.dream.city.base.model.enu.AmountDynType;
import com.dream.city.base.model.enu.TradeStatus;
import com.dream.city.base.model.enu.TradeType;
import com.dream.city.base.model.enu.VerifyStatus;
import com.dream.city.base.model.req.PlayerTradeReq;
import com.dream.city.service.trade.PlayerTradeService;
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
 * 玩家转账记录
 */
@RestController
@RequestMapping("/trade/transfer")
public class PlayerTransferController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private final String modelName = "玩家转账记录";
    private final String actionPath = "trade/transfer";

    @Autowired
    private PlayerTradeService tradeService;




    @RequestMapping("/get/{id}")
    public ModelAndView get(@PathVariable("id") Integer id,Model model){
        logger.info("查询"+ modelName +"：{}",id);
        Object result = null;
        try {
            result = tradeService.getPlayerTradeById(id);
        }catch (Exception e){
            logger.error("查询"+ modelName +"异常",e);
        }
        model.addAttribute("tradeStatuss", TradeStatus.values());
        model.addAttribute("tradeTypes", TradeType.values());
        model.addAttribute("dynTypes", AmountDynType.values());
        model.addAttribute("verifyStatuss", VerifyStatus.values());
        model.addAttribute("table", modelName + "详情");
        model.addAttribute("title","详情");
        model.addAttribute("edit",Boolean.FALSE);
        model.addAttribute("data",result);
        return new ModelAndView(actionPath + "/edit");
    }




    @RequestMapping("/index")
    public ModelAndView index(PlayerTradeReq record,Model model){
        record.setTradeType(TradeType.TRANSFER.getCode());
        model.addAttribute("data",record);
        model.addAttribute("tradeStatuss", TradeStatus.values());
        model.addAttribute("tradeTypes", TradeType.values());
        model.addAttribute("dynTypes", AmountDynType.values());
        model.addAttribute("verifyStatuss", VerifyStatus.values());
        model.addAttribute("title",modelName);
        model.addAttribute("actionPath",actionPath);
        model.addAttribute("table", modelName + "列表");
        return new ModelAndView(actionPath + "/index");
    }
    @RequestMapping("/getList")
    public Result getList(Page pageReq, PlayerTradeReq record){
        record.setTradeType(TradeType.TRANSFER.getCode());
        logger.info(modelName + "列表，：{}",record);
        boolean success = Boolean.TRUE;
        PageInfo result = null;
        try{
            pageReq.setCondition(record);
            result = tradeService.getPlayerTradeList(pageReq);
        }catch (Exception e){
            success = Boolean.FALSE;
            logger.error("查询"+modelName+"列表异常",e);
        }
        return new Result(success,modelName + "列表",result);
    }




}
