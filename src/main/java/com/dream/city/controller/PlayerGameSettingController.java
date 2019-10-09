package com.dream.city.controller;

import com.dream.city.base.Result;
import com.dream.city.base.model.Page;
import com.dream.city.base.model.req.PlayerGameSettingReq;
import com.dream.city.base.model.resp.PlayerGameSettingResp;
import com.dream.city.service.player.PlayerGameSettingService;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


/**
 * @author
 * 玩家游戏设置
 */
@RestController
@RequestMapping("/player/game/setting")
public class PlayerGameSettingController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private final String modelName = "玩家游戏设置";
    private final String actionPath = "player/game/setting";

    @Autowired
    private PlayerGameSettingService settingService;



    /*@RequestMapping("/add")
    public ModelAndView add(PlayerGameSetting record, Model model){
        model.addAttribute("title","添加");
        model.addAttribute("table","添加" + modelName);
        model.addAttribute("actionPath",actionPath);
        model.addAttribute("data",record);
        return new ModelAndView(actionPath + "/add");
    }
    @RequestMapping("/insert")
    public Result insert(PlayerGameSetting record){
        logger.info("新增"+ modelName +"，：{}",record);
        boolean success = Boolean.FALSE;
        Integer result = 0;
        try {
            result = settingService.insertGameSetting(record);
            if (result > 0){
                success = Boolean.TRUE;
            }
        }catch (Exception e){
            logger.error("新增"+ modelName +"异常",e);
        }
        return new Result(success,"新增" + modelName,result);
    }*/


    /*@RequestMapping("/delete/{id}")
    public Result delete(@PathVariable("id") Long id){
        logger.info("删除"+ modelName +"，：{}",id);
        boolean success = Boolean.FALSE;
        Integer result = 0;
        try {
            result = settingService.deleteGameSettingById(id);
            if (result > 0){
                success = Boolean.TRUE;
            }
        }catch (Exception e){
            logger.error("删除"+ modelName +"异常",e);
        }
        return new Result(success,"删除"+ modelName,result);
    }



    @RequestMapping("/edit/{id}")
    public ModelAndView edit(@PathVariable("id") Long id, Model model){
        model.addAttribute("data",settingService.getGameSettingById(id));
        model.addAttribute("title","编辑");
        model.addAttribute("table","编辑"+ modelName);
        model.addAttribute("edit",Boolean.TRUE);
        model.addAttribute("actionPath",actionPath);
        return new ModelAndView(actionPath + "/edit");
    }
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Result<Integer> update(PlayerGameSetting record){
        logger.info("修改"+ modelName +"，：{}",record);
        boolean success = Boolean.FALSE;
        Integer result = 0;
        try {
            result = settingService.updateGameSettingById(record);
            if (result != null && result > 0){
                success = Boolean.TRUE;
            }
        }catch (Exception e){
            logger.error("修改"+ modelName +"异常",e);
        }
        return new Result(success,"修改"+ modelName,result);
    }*/




    @RequestMapping("/get/{id}")
    public ModelAndView get(@PathVariable("id") Long id,Model model){
        logger.info("查询"+ modelName +"：{}",id);
        PlayerGameSettingResp result = null;
        try {
            result = settingService.getGameSettingById(id);
        }catch (Exception e){
            logger.error("查询"+ modelName +"异常",e);
        }
        model.addAttribute("title","详情");
        model.addAttribute("table","游戏"+ modelName);
        model.addAttribute("edit",Boolean.FALSE);
        model.addAttribute("data",result);
        return new ModelAndView(actionPath + "/edit");
    }




    @RequestMapping("/index")
    public ModelAndView index(Model model){
        model.addAttribute("title","游戏"+ modelName);
        model.addAttribute("table", modelName + "列表");
        model.addAttribute("actionPath",actionPath);
        return new ModelAndView(actionPath + "/index");
    }
    @RequestMapping("/getList")
    public Result<PageInfo> getList(Page pageReq, PlayerGameSettingReq record){
        logger.info(modelName + "列表，：{}",record);
        boolean success = Boolean.TRUE;
        PageInfo<PlayerGameSettingResp> result = null;
        try{
            pageReq.setCondition(record);
            result = settingService.getGameSettingList(pageReq);
        }catch (Exception e){
            success = Boolean.FALSE;
            logger.error("查询"+ modelName +"列表异常",e);
        }
        return new Result(success,modelName + "列表",result);
    }


}
