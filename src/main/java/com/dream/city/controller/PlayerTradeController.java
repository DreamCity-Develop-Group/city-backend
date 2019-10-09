package com.dream.city.controller;

import com.dream.city.base.Result;
import com.dream.city.base.model.Page;
import com.dream.city.base.model.req.PlayerReq;
import com.dream.city.base.model.req.PlayerTradeReq;
import com.dream.city.service.player.PlayerService;
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
 * 玩家交易记录
 */
@RestController
@RequestMapping("/trade")
public class PlayerTradeController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private final String modelName = "玩家交易记录";
    private final String actionPath = "trade";

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
        model.addAttribute("title","详情");
        model.addAttribute("table", modelName + "详情");
        model.addAttribute("edit",Boolean.FALSE);
        model.addAttribute("data",result);
        return new ModelAndView(actionPath + "/edit");
    }




    @RequestMapping("/index")
    public ModelAndView index(PlayerTradeReq record,Model model){
        model.addAttribute("data",record);
        model.addAttribute("title",modelName);
        model.addAttribute("table", modelName + "列表");
        model.addAttribute("actionPath",actionPath);
        return new ModelAndView(actionPath + "/index");
    }
    @RequestMapping("/getList")
    public Result getList(Page pageReq, PlayerTradeReq record){
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




}
