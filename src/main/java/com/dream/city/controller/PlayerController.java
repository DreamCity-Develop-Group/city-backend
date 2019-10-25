package com.dream.city.controller;

import com.dream.city.base.BaseController;
import com.dream.city.base.Result;
import com.dream.city.base.model.Page;
import com.dream.city.base.model.entity.Genesis;
import com.dream.city.base.model.entity.Player;
import com.dream.city.base.model.entity.PlayerAccount;
import com.dream.city.base.model.entity.PlayerAccountLog;
import com.dream.city.base.model.req.PlayerReq;
import com.dream.city.base.model.resp.PlayerAccountResp;
import com.dream.city.base.model.resp.PlayerBiResp;
import com.dream.city.base.model.resp.PlayerResp;
import com.dream.city.base.model.resp.PropertyResp;
import com.dream.city.service.account.AccountService;
import com.dream.city.service.player.PlayerService;
import com.dream.city.service.property.PropertyService;
import com.github.pagehelper.PageInfo;
import io.netty.util.ResourceLeakTracker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.xml.crypto.Data;
import java.awt.*;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author
 * 玩家
 */
@RestController
@RequestMapping("/player/player")
public class PlayerController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private final String modelName = "玩家";
    private final String actionPath = "player/player";

    @Autowired
    private PlayerService playerService;
    @Autowired
    private AccountService accountService;

    private PlayerAccount  systemAccount ;


    @RequestMapping("/get/{id}")
    public ModelAndView get(@PathVariable("id") Long id,Model model){
        logger.info("查询"+ modelName +"：{}",id);
        Player result = null;
        try {
            //result = playerService.getPlayerByrId(id);
            result = playerService.selectPlayerId(id);

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
    public Result getList(Page pageReq, PlayerReq record){
        logger.info(modelName + "列表，：{}",record);
        boolean success = Boolean.TRUE;
        PageInfo result = null;
        try{
            pageReq.setCondition(record);
            result = playerService.getPlayers(pageReq);
            System.out.println(result);
        }catch (Exception e){
            success = Boolean.FALSE;
            logger.error("查询"+modelName+"列表异常",e);
        }
        return new Result(success,modelName + "列表",result);
    }


    /*@RequestMapping("/add")
    public ModelAndView add(Player record, Model model){
        model.addAttribute("title","添加");
        model.addAttribute("table","添加" + modelName);
        model.addAttribute("actionPath",actionPath);
        model.addAttribute("data",record);
        return new ModelAndView(actionPath + "/add");
    }
    @RequestMapping("/insert")
    public Result insert(Player record){
        logger.info("新增"+ modelName +"，：{}",record);
        boolean success = Boolean.FALSE;
        Integer result = 0;
        try {
            result = null;
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
            result = 0;
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
        model.addAttribute("data", null);
        model.addAttribute("title","编辑");
        model.addAttribute("table","编辑"+ modelName);
        model.addAttribute("edit",Boolean.TRUE);
        model.addAttribute("actionPath",actionPath);
        return new ModelAndView(actionPath + "/edit");
    }
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Result<Integer> update(Player record){
        logger.info("修改"+ modelName +"，：{}",record);
        boolean success = Boolean.FALSE;
        Integer result = 0;
        try {
            result = 0;
            if (result != null && result > 0){
                success = Boolean.TRUE;
            }
        }catch (Exception e){
            logger.error("修改"+ modelName +"异常",e);
        }
        return new Result(success,"修改"+ modelName,result);
    }*/




    @RequestMapping("/postAccount")
    public Result postAccount(String playerId,Double money){
        Result result = new Result();
        if(playerId == null || playerId.isEmpty() || money <= 0.00 ){
            result.setSuccess(false);
            result.setCode(500);
        }

        synchronized (systemAccount){
            try {
                systemAccount = accountService.selectByPrimaryKey(888888888);;
                PlayerAccount record = new PlayerAccount();
                record.setAccPlayerId(playerId);
                PlayerAccount account = accountService.getPlayerAccount(record);
                if(account == null || account.getAccUsdt() == null){
                    //返回空
                    result.setSuccess(false);
                    result.setCode(500);
                    result.setMsg("无数据");
                    return result;
                }
                BigDecimal playerAccounts= account.getAccUsdt();
                BigDecimal  postMoney = new BigDecimal(money.toString());
                BigDecimal  endMoney = playerAccounts.add(postMoney);

                if(systemAccount.getAccUsdt().compareTo(postMoney) == 1){
                    BigDecimal systemMoney = systemAccount.getAccUsdt();
                    systemMoney =  systemMoney.subtract(postMoney);
                    playerAccounts = playerAccounts.add(postMoney);
                    PlayerAccountLog playerAccountLog = new PlayerAccountLog();
                    //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                    playerAccountLog.setAccId(888888888);
                    playerAccountLog.setPlayerId(account.getAccPlayerId());
                    playerAccountLog.setAmountUsdt(postMoney);
                    playerAccountLog.setType(2);
                    playerAccountLog.setDesc("系统拨币");
                    playerAccountLog.setCreateTime(new Date());
                    //插入日志表player_account_log
                    Integer i = accountService.insertPlayAccountLog(playerAccountLog);
                    if(i != null && i.intValue() == 1){
                        //修改玩家金额playerAccount
                        account.setAccUsdt(endMoney);
                        accountService.updatePlayerAccount(account);
                        systemAccount.setAccUsdt(systemMoney);
                        accountService.updatePlayerAccount(systemAccount);
                        result.setMsg("推币成功");
                        result.setCode(200);
                        result.setSuccess(true);
                        result.setData(new Date());
                    }
                }else{
                    result.setCode(500);
                    result.setSuccess(false);
                    result.setData(new Date());
                }

            }catch (Exception e){
                e.printStackTrace();
            }
        }

        return result;

    }




    @RequestMapping("/edit/{id}")
    public ModelAndView edit(@PathVariable("id") Long id, Model model){
        Player result = null;
        try {
            result = playerService.selectPlayerId(id);
            System.out.println("++++++++++++++++++++++++++++++++++++++"+result);
            //result  = accountService.getAccountByIdOrName(Integer.valueOf(id.toString()),null);
        }catch (Exception e){
            logger.error("查询"+ modelName +"异常",e);
        }
        model.addAttribute("title","拨币");
        model.addAttribute("table", modelName + "拨币");
        model.addAttribute("edit",Boolean.FALSE);
        model.addAttribute("data", result);
        return new ModelAndView(actionPath + "/sendMoney");
    }







}
