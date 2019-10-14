package com.dream.city.controller;

import com.dream.city.base.BaseController;
import com.dream.city.base.Result;
import com.dream.city.base.model.Page;
import com.dream.city.base.model.req.PlayerLikesReq;
import com.dream.city.base.model.resp.PlayerLikesResp;
import com.dream.city.service.player.LikesService;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;


/**
 * 玩家获赞
 */
@RestController
@RequestMapping("/player/like")
public class LikesController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private final String modelName = "玩家获赞";
    private final String actionPath = "player/like";

    @Autowired
    LikesService likesService;


    @RequestMapping("/index")
    public ModelAndView index(Model model){
        model.addAttribute("title", modelName);
        model.addAttribute("table", modelName + "列表");
        model.addAttribute("actionPath",actionPath);
        return new ModelAndView(actionPath + "/index");
    }
    @RequestMapping("/getList")
    public Result<PageInfo> getList(Page pageReq, PlayerLikesReq record){
        logger.info(modelName + "列表，：{}",record);
        boolean success = Boolean.TRUE;
        PageInfo<PlayerLikesResp> result = null;
        try{
            pageReq.setCondition(record);
            result = likesService.playerLikesList(pageReq);
        }catch (Exception e){
            success = Boolean.FALSE;
            logger.error("查询"+ modelName +"列表异常",e);
        }
        return new Result(success,modelName + "列表",result);
    }


    @RequestMapping("/get/{id}")
    public ModelAndView get(@PathVariable("id") Integer id, Model model){
        logger.info("查询"+ modelName +"：{}",id);
        PlayerLikesResp result = null;
        try {
            PlayerLikesReq record = new PlayerLikesReq();
            record.setLikedId(id);
            Page pageReq = new Page();
            pageReq.setCondition(record);
            PageInfo<PlayerLikesResp> pageInfo = likesService.playerLikesList(pageReq);
            List<PlayerLikesResp> list = pageInfo.getList();
            if (!CollectionUtils.isEmpty(list)){
                result = list.get(0);
            }
        }catch (Exception e){
            logger.error("查询"+ modelName +"异常",e);
        }
        model.addAttribute("title","详情");
        model.addAttribute("table",modelName);
        model.addAttribute("edit",Boolean.FALSE);
        model.addAttribute("data",result);
        return new ModelAndView(actionPath + "/edit");
    }


}
