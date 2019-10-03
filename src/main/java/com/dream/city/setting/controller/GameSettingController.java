package com.dream.city.setting.controller;

import com.dream.city.base.Result;
import com.dream.city.property.dto.PropertyResp;
import com.dream.city.setting.entity.GameSetting;
import com.dream.city.setting.service.GameSettingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * @author
 * 游戏设置
 */
@RestController
@RequestMapping("/setting/game")
public class GameSettingController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private GameSettingService settingService;
    private final String actionPath = "setting/game";


    @RequestMapping("/add")
    public ModelAndView add(GameSetting record, Model model){
        model.addAttribute("title","添加");
        model.addAttribute("table","添加游戏设置");
        model.addAttribute("actionPath",actionPath);
        model.addAttribute("data",record);
        return new ModelAndView(actionPath + "/add");
    }
    @RequestMapping("/insert")
    public Result insert(GameSetting record){
        logger.info("新增游戏设置，：{}",record);
        boolean success = Boolean.FALSE;
        Integer result = 0;
        try {
            result = settingService.insertGameSetting(record);
            if (result > 0){
                success = Boolean.TRUE;
            }
        }catch (Exception e){
            logger.error("新增游戏设置异常",e);
        }
        return new Result(success,"新增游戏设置",result);
    }


    @RequestMapping("/delete/{id}")
    public Result delete(@PathVariable("id") Long id){
        logger.info("删除游戏设置，：{}",id);
        boolean success = Boolean.FALSE;
        Integer result = 0;
        try {
            result = settingService.deleteGameSettingById(id);
            if (result > 0){
                success = Boolean.TRUE;
            }
        }catch (Exception e){
            logger.error("删除游戏设置异常",e);
        }
        return new Result(success,"删除游戏设置",result);
    }



    @RequestMapping("/edit/{id}")
    public ModelAndView edit(@PathVariable("id") Long id, Model model){
        model.addAttribute("data",settingService.getGameSettingById(id));
        model.addAttribute("title","编辑");
        model.addAttribute("table","编辑游戏设置");
        model.addAttribute("edit",Boolean.TRUE);
        model.addAttribute("actionPath",actionPath);
        return new ModelAndView(actionPath + "/edit");
    }
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Result<Integer> update(@RequestBody GameSetting record){
        logger.info("修改游戏设置，：{}",record);
        boolean success = Boolean.FALSE;
        Integer result = 0;
        try {
            result = settingService.updateGameSettingById(record);
            if (result != null && result > 0){
                success = Boolean.TRUE;
            }
        }catch (Exception e){
            logger.error("修改游戏设置异常",e);
        }
        return new Result(success,"修改游戏设置",result);
    }




    @RequestMapping("/get/{id}")
    public ModelAndView get(@PathVariable("id") Long id,Model model){
        logger.info("查询游戏设置：{}",id);
        GameSetting result = null;
        try {
            result = settingService.getGameSettingById(id);
        }catch (Exception e){
            logger.error("查询游戏设置异常",e);
        }
        model.addAttribute("title","详情");
        model.addAttribute("table","游戏设置详情");
        model.addAttribute("edit",Boolean.FALSE);
        model.addAttribute("data",result);
        return new ModelAndView(actionPath + "/edit");
    }




    @RequestMapping("/index")
    public ModelAndView index(Model model){
        model.addAttribute("title","游戏设置");
        model.addAttribute("table","游戏设置列表");
        model.addAttribute("actionPath",actionPath);
        return new ModelAndView(actionPath + "/index");
    }
    @RequestMapping("/getList")
    public Result<PropertyResp> getList(GameSetting record){
        logger.info("游戏设置列表，：{}",record);
        boolean success = Boolean.TRUE;
        List<GameSetting> result = null;
        try{
            result = settingService.getGameSettingList(record);
        }catch (Exception e){
            success = Boolean.FALSE;
        }
        return new Result(success,"游戏设置列表",result);
    }


}
