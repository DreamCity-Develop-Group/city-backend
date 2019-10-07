package com.dream.city.player.controller;


import com.dream.city.base.Result;
import com.dream.city.base.model.Page;
import com.dream.city.base.model.req.FriendsReq;
import com.dream.city.player.service.FriendsService;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;


@RestController
@RequestMapping("/player/friend")
public class FriendsController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private final String modelName = "好友";
    private final String actionPath = "player/friend";

    @Autowired
    private FriendsService friendsService;


    /**
     * 好友列表
     * @return
     */
    @RequestMapping("/index")
    public ModelAndView index(Model model){
        model.addAttribute("title",modelName);
        model.addAttribute("table", modelName + "列表");
        model.addAttribute("actionPath",actionPath);
        return new ModelAndView(actionPath + "/index");
    }
    @RequestMapping("/getList")
    public Result friendList(Page pageReq, FriendsReq record){
        logger.info(modelName + "列表，：{}", record);
        boolean success = Boolean.TRUE;
        PageInfo result = null;
        try{
            pageReq.setCondition(record);
            result = friendsService.friendList(pageReq);
        }catch (Exception e){
            success = Boolean.FALSE;
            logger.error("查询"+modelName+"列表异常",e);
        }
        return new Result(success,modelName + "列表",result);
    }


    @RequestMapping("/get/{id}")
    public ModelAndView get(@PathVariable("id") Long id, Model model){
        logger.info("查询"+ modelName +"：{}",id);
        Object result = null;
        try {
            result = friendsService.getFriendById(id);
        }catch (Exception e){
            logger.error("查询"+ modelName +"异常",e);
        }
        model.addAttribute("title","详情");
        model.addAttribute("table", modelName + "详情");
        model.addAttribute("edit",Boolean.FALSE);
        model.addAttribute("data",result);
        return new ModelAndView(actionPath + "/edit");
    }

}
