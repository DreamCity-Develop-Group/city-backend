package com.dream.city.other.controller;

import com.dream.city.base.Result;
import com.dream.city.base.model.Page;
import com.dream.city.base.model.entity.CityHelp;
import com.dream.city.base.model.req.MessageReq;
import com.dream.city.base.model.resp.MessageResp;
import com.dream.city.other.service.MessageService;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


/**
 * 消息
 * @author Wvv
 */
@RestController
@RequestMapping("/other/message")
public class MassegeController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    MessageService messageService;


    @RequestMapping("/add")
    public ModelAndView add(CityHelp record, Model model){
        model.addAttribute("title","添加");
        model.addAttribute("table","添加通知");
        model.addAttribute("data",record);
        return new ModelAndView("other/msg/add");
    }
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public Result<Integer> insert(MessageReq record){
        boolean success = Boolean.TRUE;
        int result = 0;
        try {
            result = messageService.insertMessage(record);
            if (result > 0){
                success = Boolean.TRUE;
            }
        }catch (Exception e){
            logger.error("新增通知异常",e);
        }
        return new Result(success,"新增通知",result);
    }


    @RequestMapping("/edit/{id}")
    public ModelAndView edit(@PathVariable("id") Long id,Model model){
        model.addAttribute("title","编辑");
        model.addAttribute("table","编辑通知");
        model.addAttribute("edit",Boolean.TRUE);
        model.addAttribute("data",messageService.getMessageById(id));
        return new ModelAndView("other/msg/edit");
    }
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Result<Integer> update(MessageReq record){
        boolean success = Boolean.FALSE;
        int result = 0;
        try {
            result = messageService.updateMessageById(record);
            if (result > 0) {
                success = Boolean.TRUE;
            }
        }catch (Exception e){
            logger.error("更新通知异常",e);
        }
        return new Result(success,"更新通知",result);
    }


    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public Result<Integer> delete(@PathVariable("id") Long id){
        boolean success = Boolean.FALSE;
        int result = 0;
        try {
            result = messageService.deleteMessageById(id);
            if (result > 0) {
                success = Boolean.TRUE;
            }
        }catch (Exception e){
            logger.error("删除通知异常",e);
        }
        return new Result(success,"删除通知",result);
    }


    @RequestMapping("/get/{id}")
    public ModelAndView get(@PathVariable("id") Long id,Model model){
        MessageResp result = null;
        try {
            result = messageService.getMessageById(id);
        }catch (Exception e){
            logger.error("查询通知异常",e);
        }
        model.addAttribute("title","详情");
        model.addAttribute("table","通知详情");
        model.addAttribute("edit",Boolean.FALSE);
        model.addAttribute("data",result);
        return new ModelAndView("other/msg/edit");
    }



    @RequestMapping(value = "/index")
    public ModelAndView index(Model model){
        model.addAttribute("title","通知");
        model.addAttribute("table","通知列表");
        return new ModelAndView("other/msg/index");
    }
    @RequestMapping("/getList")
    public Result<PageInfo> getList(Page page, MessageReq record){
        boolean success = Boolean.TRUE;
        PageInfo<MessageResp> result = null;
        try {
            page.setCondition(record);
            result = messageService.getCityMessageList(page);
        }catch (Exception e){
            success = Boolean.FALSE;
            logger.error("查询通知列表异常",e);
        }
        return new Result(success,"查询通知列表",result);
    }

}
