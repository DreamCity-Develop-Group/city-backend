package com.dream.city.other.controller;

import com.dream.city.base.PageReq;
import com.dream.city.base.Result;
import com.dream.city.other.entity.CityHelp;
import com.dream.city.other.service.CityHelpService;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


/**
 * @author
 */
@RestController
@RequestMapping("/other/help")
public class HelpController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    CityHelpService helpService;


    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public Result<Integer> deleteCityHelpById(@PathVariable("id") Integer id){
        logger.info("刪除幫助內容id:{}",id);
        boolean success = Boolean.TRUE;
        Integer result = null;
        try {
            result = helpService.deleteCityHelpById(id);
            if (result == null && (result != null && result < 1)){
                success = Boolean.FALSE;
            }
        }catch (Exception e){
            success = Boolean.FALSE;
            logger.error("刪除幫助內容异常",e);
        }
        return new Result(success,"刪除幫助內容",result);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Result<Integer> insertCityHelp(@RequestBody CityHelp record){
        logger.info("新增幫助內容:{}",record);
        boolean success = Boolean.TRUE;
        Integer result = null;
        try {
            result = helpService.insertCityHelp(record);
            if (result == null && (result != null && result < 1)){
                success = Boolean.FALSE;
            }
        }catch (Exception e){
            success = Boolean.FALSE;
            logger.error("新增幫助內容异常",e);
        }
        return new Result(success,"新增幫助內容",result);
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable("id") Integer id,Model model){
        model.addAttribute("title","编辑");
        model.addAttribute("table","编辑帮助");
        model.addAttribute("edit",Boolean.TRUE);
        CityHelp cityHelp = helpService.getCityHelpById(id);
        model.addAttribute("data",cityHelp);
        return new ModelAndView("other/help/edit");
    }
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Result<Integer> update(CityHelp record){
        logger.info("更新幫助內容:{}",record);
        boolean success = Boolean.TRUE;
        Integer result = null;
        try {
            result = helpService.updateCityHelpById(record);
            if (result == null && (result != null && result < 1)){
                success = Boolean.FALSE;
            }
        }catch (Exception e){
            success = Boolean.FALSE;
            logger.error("更新幫助內容异常",e);
        }
        return new Result(success,"更新幫助內容",result);
    }

    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    public ModelAndView getCityHelpById(@PathVariable("id") Integer id,Model model){
        logger.info("帮助详情id:{}",id);
        CityHelp result = null;
        try {
            result = helpService.getCityHelpById(id);
        }catch (Exception e){
            logger.error("帮助详情异常",e);
        }
        model.addAttribute("title","详情");
        model.addAttribute("table","帮助详情");
        model.addAttribute("edit",Boolean.FALSE);
        model.addAttribute("data",result);
        return new ModelAndView("other/help/edit");
    }


    @RequestMapping("/index")
    public ModelAndView helpsIndex(Model model){
        model.addAttribute("title","帮助");
        model.addAttribute("table","帮助列表");
        return new ModelAndView("other/help/index");
    }

    @ResponseBody
    @RequestMapping(value = "/getList", method = RequestMethod.GET)
    public Result getCityHelpList(PageReq<CityHelp> record){
        logger.info("查詢幫助列表:{}",record);
        boolean success = Boolean.TRUE;
        PageInfo<CityHelp> result = null;
        try {
            result = helpService.getCityHelpList(record);
        }catch (Exception e){
            success = Boolean.FALSE;
            logger.error("查詢幫助列表异常",e);
        }
        return new Result(success,"查詢幫助列表",result);
    }



}
