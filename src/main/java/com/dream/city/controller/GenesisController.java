package com.dream.city.controller;


import com.dream.city.base.BaseController;
import com.dream.city.base.Result;
import com.dream.city.base.model.Page;
import com.dream.city.base.model.entity.Genesis;
import com.dream.city.base.model.entity.Player;
import com.dream.city.base.model.req.PlayerAccountReq;
import com.dream.city.base.model.resp.PlayerAccountResp;
import com.dream.city.service.account.AccountService;
import com.dream.city.service.player.PlayerService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.sql.Date;

/**
 * @author
 */
@RestController
@RequestMapping("/player/genesis")
public class GenesisController extends BaseController {


    private final String modelName = "创世号";
    private final String actionPath = "player/genesis";

    @Autowired
    private AccountService accountService;
    @Autowired
    private PlayerService playerService;

    @RequestMapping("/addGenesis")
    public Result genesis(String playerId , String accAddr,Double accMt,String AccAddr){
        //String playerId,String accAddr,String playerName,BigDecimal accMt,String mtQuantum,String superior链接测试使用


        if(playerId == null || playerId.isEmpty() ){
            return  null;
        }
        Result result = new Result();
        Integer i = null;
        PlayerAccountReq playerAccount = new PlayerAccountReq();
        playerAccount.setAccPlayerId(playerId);
        try{
            Genesis genesis = playerService.getGenesis(playerId);
            if(genesis != null ){
                result.setMsg("已存在");
                result.setCode(500);
                result.setSuccess(Boolean.FALSE);
                result.setData(null);
                return result;
            }
            PlayerAccountResp pa = accountService.getPlayerAccount(playerAccount);
            Player player = playerService.getPlayerId(playerId);
            if(pa == null || player == null){
                result.setMsg("没有该用户");
                result.setCode(500);
                result.setSuccess(Boolean.FALSE);
                result.setData(null);
                return result;
            }
            //Mt9层下线的累加，每个下线出现都要修改
            Genesis gs = new Genesis ();
            if(accAddr == null || accAddr.isEmpty()){
                gs.setAccAddr(pa.getAccAddr());
            }else {
                gs.setAccAddr(accAddr);
            }

            gs.setPlayerId(playerId);
            gs.setAccMt(pa.getAccMt());
            gs.setMtQuantum(new Long(0));
            gs.setPlayerName(player.getPlayerName());
            Date date = new Date(System.currentTimeMillis());
            gs.setCreateTime(date);
            gs.setSuperior("system");
            i  = playerService.add(gs);

            if(i == null ||  i.intValue() <= 0){
                result.setCode(500);
                result.setMsg("创世号失败");
                result.setSuccess(Boolean.FALSE);
            }else{
                result.setCode(200);
                result.setMsg("创世号成功");
                result.setSuccess(Boolean.TRUE);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }




    @RequestMapping("/getList")
    public Result getGenesis(Page pageReq, Genesis  record){
        //logger.info(modelName + "列表，：{}",record);
        boolean success = Boolean.TRUE;
        PageInfo result = null;
        try{
            //查询出所有创世号返回页面
            pageReq.setCondition(record);
            result = playerService.getGenesis(pageReq);
            System.out.println(result);
        }catch (Exception e){
            success = Boolean.FALSE;
            e.printStackTrace();
        }
        return  new Result(success,modelName + "列表",result);
    }



    @RequestMapping("/index")
    public ModelAndView index(Model model){
        model.addAttribute("title",modelName);
        model.addAttribute("table", modelName + "列表");
        model.addAttribute("actionPath",actionPath);
        return new ModelAndView(actionPath + "/index");
    }




    @RequestMapping("/add")
    public ModelAndView add(Genesis record, Model model){
        //List<Genesis> itemTypes = playerService.getListGenesis();
        //model.addAttribute("items", itemTypes);
        model.addAttribute("title","生成创世号");
        model.addAttribute("table","生成创世号" + modelName);
        model.addAttribute("actionPath",actionPath);
        model.addAttribute("data",record);
        return new ModelAndView(actionPath + "/add");
    }


}
