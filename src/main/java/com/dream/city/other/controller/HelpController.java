package com.dream.city.other.controller;

import com.dream.city.base.Result;
import com.dream.city.other.entity.CityHelp;
import com.dream.city.other.service.CityHelpService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/other/help")
public class HelpController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    CityHelpService helpService;


    @RequestMapping("/delete/{id}")
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

    @RequestMapping("/insert")
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

    @RequestMapping("/update")
    public Result<Integer> updateCityHelpById(@RequestBody CityHelp record){
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

    @RequestMapping("/get/{id}")
    public Result<CityHelp> getCityHelpById(@PathVariable("id") Integer id){
        logger.info("查詢幫助id:{}",id);
        boolean success = Boolean.TRUE;
        CityHelp result = null;
        try {
            result = helpService.getCityHelpById(id);
        }catch (Exception e){
            success = Boolean.FALSE;
            logger.error("查詢幫助异常",e);
        }
        return new Result(success,"查詢幫助",result);
    }


    @RequestMapping("/getList")
    public Result<List<CityHelp>> getCityHelpList(@RequestBody CityHelp record){
        logger.info("查詢幫助列表:{}",record);
        boolean success = Boolean.TRUE;
        List<CityHelp> result = null;
        try {
            result = helpService.getCityHelpList(record);
        }catch (Exception e){
            success = Boolean.FALSE;
            logger.error("查詢幫助列表异常",e);
        }
        return new Result(success,"查詢幫助列表",result);
    }



}
