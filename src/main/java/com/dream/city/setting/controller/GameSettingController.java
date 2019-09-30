package com.dream.city.setting.controller;

import com.dream.city.setting.service.GameSettingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Wvv
 * 游戏设置
 */
@RestController
@RequestMapping("/set")
public class GameSettingController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private GameSettingService settingService;


    /**
     * 游戏音效
     * @param playerId
     * @param isOpen
     * @return
     */
    /*@RequestMapping(method = RequestMethod.POST, value = "/settingGameVoice")
    public Result settingGameVioce(@RequestParam(value = "playerId") String playerId,
                            @RequestParam(value = "isOpen") Boolean isOpen){
        logger.info("游戏音效设置,playerId:{},isOpen:{}",playerId,isOpen);
        Result result = new Result();
        boolean b = settingService.settingGameVioce(playerId, isOpen);
        result.setSuccess(b);
        return result;
    }*/


}
