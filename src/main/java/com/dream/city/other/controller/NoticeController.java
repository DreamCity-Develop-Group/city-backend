package com.dream.city.other.controller;

import com.dream.city.base.Result;
import com.dream.city.other.entity.Notice;
import com.dream.city.other.service.NoticeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 公告
 * @author Wvv
 */
@RestController
@RequestMapping("/other/notice")
public class NoticeController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    NoticeService noticeService;


    @RequestMapping("/getGameNotices/{state}")
    public Result<List<Notice>> getGameNotices(@PathVariable("state") Integer state){
        logger.info("获取公告列表state:{}",state);
        boolean success = Boolean.TRUE;
        List<Notice> result = null;
        try {
            if (state == null){
                state = 0;
            }
            result = noticeService.getGameNotices(state);
        }catch (Exception e){
            success = Boolean.FALSE;
            logger.error("获取公告列表异常",e);
        }
        return new Result(success,"公告列表",result);
    }

    @RequestMapping("/get/{id}")
    public Result<Notice> getNoticeById(@PathVariable("id") Integer id){
        logger.info("获取公告id:{}",id);
        boolean success = Boolean.TRUE;
        Notice result = null;
        try {
            result = noticeService.getNoticeById(id);
        }catch (Exception e){
            success = Boolean.FALSE;
            logger.error("获取公告异常",e);
        }
        return new Result(success,"获取公告",result);
    }

    @RequestMapping("/delete/{id}")
    public Result<Integer> deleteNoticeById(@PathVariable("id") Integer id){
        logger.info("删除公告id:{}",id);
        boolean success = Boolean.TRUE;
        Integer result = 0;
        try {
            result = noticeService.deleteNoticeById(id);
            if (result < 1){
                success = Boolean.FALSE;
            }
        }catch (Exception e){
            success = Boolean.FALSE;
            logger.error("删除公告异常",e);
        }
        return new Result(success,"删除公告",result);
    }


    @RequestMapping("/insert")
    public Result<Integer> insertNotice(@RequestBody Notice record){
        logger.info("新增公告record:{}",record);
        boolean success = Boolean.TRUE;
        Integer result = 0;
        try {
            result = noticeService.insertNotice(record);
            if (result < 1){
                success = Boolean.FALSE;
            }
        }catch (Exception e){
            success = Boolean.FALSE;
            logger.error("新增公告异常",e);
        }
        return new Result(success,"新增公告",result);
    }


    @RequestMapping("/update")
    public Result<Integer> updateNoticeById(@RequestBody Notice record){
        logger.info("更新公告record:{}",record);
        boolean success = Boolean.TRUE;
        Integer result = 0;
        try {
            result = noticeService.updateNoticeById(record);
            if (result < 1){
                success = Boolean.FALSE;
            }
        }catch (Exception e){
            success = Boolean.FALSE;
            logger.error("更新公告异常",e);
        }
        return new Result(success,"更新公告",result);
    }

}
