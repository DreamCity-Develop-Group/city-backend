package com.dream.city.controller;

import com.alibaba.fastjson.JSONObject;
import com.dream.city.base.BaseController;
import com.dream.city.base.Result;
import com.dream.city.base.model.Page;
import com.dream.city.base.model.entity.PlayerAccount;
import com.dream.city.base.model.entity.PlayerTrade;
import com.dream.city.base.model.entity.TradeDetail;
import com.dream.city.base.model.enu.TradeDetailType;
import com.dream.city.base.model.req.AccountUpdateReq;
import com.dream.city.base.model.req.PlayerAccountReq;
import com.dream.city.base.model.resp.PlayerAccountResp;
import com.dream.city.base.model.vo.UserVo;
import com.dream.city.base.utils.RedisKeys;
import com.dream.city.base.utils.RedisUtils;
import com.dream.city.service.account.AccountService;
import com.dream.city.service.trade.PlayerTradeService;
import com.dream.city.service.trade.TradeDetailService;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;


/**
 * @author
 * 玩家账户
 */
@RestController
@RequestMapping("/player/account")
public class PlayerAccountController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private final String modelName = "玩家账户";
    private final String actionPath = "player/account";

    @Autowired
    private AccountService accountService;
    @Autowired
    private PlayerTradeService tradeService;
    @Autowired
    private TradeDetailService detailService;
    @Autowired
    private RedisUtils redisUtils;


    @RequestMapping("/add")
    public ModelAndView add(Model model){
        model.addAttribute("title","添加");
        model.addAttribute("table","添加" + modelName);
        model.addAttribute("actionPath",actionPath);
        model.addAttribute("data",new PlayerAccountResp());
        return new ModelAndView(actionPath + "/add");
    }
    @RequestMapping("/insert")
    public Result insert(PlayerAccountResp record){
        logger.info("新增"+ modelName +"，：{}",record);
        boolean success = Boolean.FALSE;
        Integer result = 0;
        try {
            PlayerAccount accountReq = new PlayerAccount();
            accountReq.setAccAddr(record.getAccAddr());
            accountReq.setAccPlayerId(record.getPlayerId());
            accountReq.setAccMtAvailable(record.getAccMtAvailable());
            accountReq.setAccMt(record.getAccMt());
            accountReq.setAccMtFreeze(record.getAccMtFreeze());
            accountReq.setAccIncome(record.getTotalIncome());
            accountReq.setAccUsdt(record.getAccUsdt());
            accountReq.setAccUsdtFreeze(record.getAccUsdtFreeze());
            accountReq.setAccUsdtAvailable(record.getAccUsdtAvailable());
            result = accountService.createPlayerAccount(accountReq);
            if (result > 0){
                success = Boolean.TRUE;
            }
        }catch (Exception e){
            logger.error("新增"+ modelName +"异常",e);
        }
        return new Result(success,"新增" + modelName,result);
    }


    /*@RequestMapping("/delete/{id}")
    public Result delete(@PathVariable("id") Integer id){
        logger.info("删除"+ modelName +"，：{}",id);
        boolean success = Boolean.FALSE;
        Integer result = 0;
        try {
            result = accountService.deleteById(id);
            if (result > 0){
                success = Boolean.TRUE;
            }
        }catch (Exception e){
            logger.error("删除"+ modelName +"异常",e);
        }
        return new Result(success,"删除"+ modelName,result);
    }*/



    @RequestMapping("/edit/{id}")
    public ModelAndView edit(@PathVariable("id") Integer id, Model model){
        PlayerAccountReq accountReq = new PlayerAccountReq();
        accountReq.setAccId(id);
        PlayerAccountResp result = accountService.getPlayerAccount(accountReq);
        model.addAttribute("data",result);
        model.addAttribute("title","编辑");
        model.addAttribute("table","编辑"+ modelName);
        model.addAttribute("edit",Boolean.TRUE);
        model.addAttribute("actionPath",actionPath);
        return new ModelAndView(actionPath + "/edit");
    }
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Result update(AccountUpdateReq record, HttpServletRequest request){
        logger.info("修改"+ modelName +"，：{}",record);
        boolean success = Boolean.FALSE;
        Integer result = 0;
        try {
            PlayerAccountReq getAccountReq = new PlayerAccountReq();
            getAccountReq.setAccId(Integer.parseInt(record.getAccId().replaceAll(",","")));
            getAccountReq.setAccPlayerId(record.getPlayerId());
            PlayerAccountResp accountResp = accountService.getPlayerAccount(getAccountReq);

            PlayerAccount accountReq = new PlayerAccount();
            accountReq.setAccId(Integer.parseInt(record.getAccId().replaceAll(",","")));
            accountReq.setAccMtFreeze(BigDecimal.valueOf(Double.parseDouble(record.getAccMtFreeze().replaceAll(",",""))));
            accountReq.setAccMtAvailable(BigDecimal.valueOf(Double.parseDouble(record.getAccMtAvailable().replaceAll(",",""))));
            accountReq.setAccMt(BigDecimal.valueOf(Double.parseDouble(record.getAccMt().replaceAll(",",""))));
            accountReq.setAccUsdtFreeze(BigDecimal.valueOf(Double.parseDouble(record.getAccUsdtFreeze().replaceAll(",",""))));
            accountReq.setAccUsdtAvailable(BigDecimal.valueOf(Double.parseDouble(record.getAccUsdtAvailable().replaceAll(",",""))));
            accountReq.setAccUsdt(BigDecimal.valueOf(Double.parseDouble(record.getAccUsdt().replaceAll(",",""))));
            accountReq.setAccPlayerId(record.getPlayerId());

            String descrUsdt = "";
            String descrMt = "";
            BigDecimal mtChange = BigDecimal.ZERO;
            BigDecimal usdtChange = BigDecimal.ZERO;
            BigDecimal mt = accountResp.getAccMt();
            BigDecimal mtAvailable = accountResp.getAccMtAvailable().subtract(accountReq.getAccMtAvailable());
            BigDecimal usdt = accountResp.getAccUsdt();
            BigDecimal usdtAvailable = accountResp.getAccUsdtAvailable().subtract(accountReq.getAccUsdtAvailable());
            if (mtAvailable.compareTo(BigDecimal.ZERO) > 0){
                mt = accountResp.getAccMt().subtract(mtAvailable.abs());
                descrMt = "减掉["+ mtAvailable.abs() +"]MT";
                mtChange = mtAvailable.abs();
            }else if (mtAvailable.compareTo(BigDecimal.ZERO) < 0){
                mt = accountResp.getAccMt().add(mtAvailable.abs());
                descrMt = "增加["+ mtAvailable.abs() +"]MT";
                mtChange = mtAvailable.abs();
            }
            if (usdtAvailable.compareTo(BigDecimal.ZERO) > 0){
                usdt = accountResp.getAccUsdt().subtract(usdtAvailable.abs());
                descrUsdt = "减掉["+ usdtAvailable.abs() +"]USDT";
                usdtChange = usdtAvailable.abs();
            }else if (usdtAvailable.compareTo(BigDecimal.ZERO) < 0){
                usdt = accountResp.getAccUsdt().add(usdtAvailable.abs());
                descrUsdt = "增加["+ usdtAvailable.abs() +"]USDT";
                usdtChange = usdtAvailable.abs();
            }

            accountReq.setAccMt(mt);
            accountReq.setAccUsdt(usdt);
            result = accountService.updatePlayerAccount(accountReq);
            if (result > 0){
                success = Boolean.TRUE;

                UserVo userVo = null;
                String sid = request.getRequestedSessionId();
                if (redisUtils.hasKey(RedisKeys.CURRENT_USER + sid)){
                    String userVoStr = redisUtils.getStr(RedisKeys.CURRENT_USER + sid);
                    userVo = JSONObject.parseObject(userVoStr,UserVo.class);
                }
                String descr = "管理后台修改账户额度";
                if (userVo != null){
                    descr = descr +",修改人："+userVo.getLoginName();
                }
                if (StringUtils.isNotBlank(descrMt) && StringUtils.isNotBlank(descrUsdt)){
                    descrMt = descr+"," + descrMt;
                    descrUsdt = descr+"," +descrUsdt;
                }
                if (StringUtils.isNotBlank(descrMt) && StringUtils.isBlank(descrUsdt)){
                    descrMt = descr+"," + descrMt;
                }
                if (StringUtils.isBlank(descrMt) && StringUtils.isNotBlank(descrUsdt)){
                    descrUsdt = descr+"," + descrUsdt;
                }

                PlayerTrade tradeReq = new PlayerTrade();
                tradeReq.setTradeDesc(descr);
                tradeReq.setTradePlayerId(record.getPlayerId());
                tradeReq.setTradeType(TradeDetailType.PLATFORM_CHANGE_AMOUNT.getCode());
                PlayerTrade trade = null;
                if (usdtChange.compareTo(BigDecimal.ZERO) > 0){
                    tradeReq.setTradeAmount(usdtChange);
                    trade = tradeService.insertPlayerTrade(tradeReq);

                    TradeDetail detailReq = new TradeDetail();
                    detailReq.setTradeId(trade.getTradeId());
                    detailReq.setDescr(descrUsdt);
                    detailReq.setPlayerId(record.getPlayerId());
                    detailReq.setTradeDetailType(TradeDetailType.PLATFORM_CHANGE_AMOUNT.getCode());
                    detailReq.setTradeAmount(usdtChange);
                    detailReq.setMtSurplus(accountReq.getAccMt());
                    detailReq.setUsdtSurplus(accountReq.getAccUsdt());
                    detailService.insert(detailReq);
                }
                if (mtChange.compareTo(BigDecimal.ZERO) > 0){
                    tradeReq.setTradeAmount(mtChange);
                    if (trade != null){
                        tradeReq.setTradeId(trade.getTradeId()+1);
                    }
                    trade = tradeService.insertPlayerTrade(tradeReq);

                    TradeDetail detailReq = new TradeDetail();
                    detailReq.setTradeId(trade.getTradeId());
                    detailReq.setDescr(descrMt);
                    detailReq.setPlayerId(record.getPlayerId());
                    detailReq.setTradeDetailType(TradeDetailType.PLATFORM_CHANGE_AMOUNT.getCode());
                    detailReq.setTradeAmount(mtChange);
                    detailReq.setMtSurplus(accountReq.getAccMt());
                    detailReq.setUsdtSurplus(accountReq.getAccUsdt());
                    detailService.insert(detailReq);
                }

            }
        }catch (Exception e){
            logger.error("修改"+ modelName +"异常",e);
        }
        return new Result(success,"修改"+ modelName,result);
    }




    @RequestMapping("/get/{id}")
    public ModelAndView get(@PathVariable("id") Integer id,Model model){
        logger.info("查询"+ modelName +"：{}",id);
        PlayerAccountResp result = null;
        try {
            PlayerAccountReq accountReq = new PlayerAccountReq();
            accountReq.setAccId(id);
            result = accountService.getPlayerAccount(accountReq);
        }catch (Exception e){
            logger.error("查询"+ modelName +"异常",e);
        }
        model.addAttribute("data",result);
        model.addAttribute("edit",Boolean.FALSE);
        model.addAttribute("title","详情");
        model.addAttribute("table",modelName + "详情");
        return new ModelAndView(actionPath + "/edit");
    }




    @RequestMapping("/index")
    public ModelAndView index(Model model){
        model.addAttribute("title",modelName);
        model.addAttribute("table", modelName + "列表");
        model.addAttribute("actionPath",actionPath);
        model.addAttribute("data",new PlayerAccountReq());
        return new ModelAndView(actionPath + "/index");
    }
    @RequestMapping("/getList")
    public Result getList(Page pageReq, PlayerAccountReq record){
        logger.info(modelName + "列表，：{}",record);
        boolean success = Boolean.TRUE;
        PageInfo<PlayerAccountResp> result = null;
        try{
            pageReq.setCondition(record);
            result = accountService.getPlayerAccountList(pageReq, record);
        }catch (Exception e){
            success = Boolean.FALSE;
        }
        return new Result(success,modelName + "列表",result);
    }




}
