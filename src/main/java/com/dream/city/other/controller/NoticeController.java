package com.dream.city.other.controller;

import com.dream.city.base.PageReq;
import com.dream.city.base.Result;
import com.dream.city.other.dto.NoticeReq;
import com.dream.city.other.dto.NoticeResp;
import com.dream.city.other.entity.CityHelp;
import com.dream.city.other.entity.Notice;
import com.dream.city.other.service.NoticeService;
import com.dream.city.util.DataUtils;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


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


    @RequestMapping("/index")
    public ModelAndView helpsIndex(Model model){
        model.addAttribute("title","公告");
        model.addAttribute("table","公告列表");
        return new ModelAndView("other/notice/index");
    }
    @RequestMapping("/getList")
    public Result<PageInfo> getNoticeList(PageReq page, NoticeReq record){
        logger.info("获取公告列表:{}",record);
        boolean success = Boolean.TRUE;
        PageInfo<NoticeResp> result = null;
        try {
            Notice notice = DataUtils.toJavaObject(record,Notice.class);
            page.setCondition(notice);
            result = noticeService.getNoticeList(page);
        }catch (Exception e){
            success = Boolean.FALSE;
            logger.error("获取公告列表异常",e);
        }
        return new Result(success,"公告列表",result);
    }


    @RequestMapping("/get/{id}")
    public ModelAndView getNoticeById(@PathVariable("id") Integer id,Model model){
        logger.info("获取公告详情id:{}",id);
        boolean success = Boolean.TRUE;
        NoticeResp result = null;
        try {
            result = noticeService.getNoticeById(id);
        }catch (Exception e){
            success = Boolean.FALSE;
            logger.error("获取公告详情异常",e);
        }

        model.addAttribute("title","详情");
        model.addAttribute("table","公告详情");
        model.addAttribute("edit",Boolean.FALSE);
        model.addAttribute("data",result);
        return new ModelAndView("other/notice/edit");
    }


    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public Result<Integer> deleteNoticeById(@PathVariable("id") Integer id){
        logger.info("删除公告id:{}",id);
        boolean success = Boolean.FALSE;
        Integer result = 0;
        try {
            result = noticeService.deleteNoticeById(id);
            if (result != null && result > 0){
                success = Boolean.TRUE;
            }
        }catch (Exception e){
            logger.error("删除公告异常",e);
        }
        return new Result(success,"删除公告",result);
    }


    @RequestMapping("/add")
    public ModelAndView add(CityHelp record,Model model){
        model.addAttribute("title","添加");
        model.addAttribute("table","添加公告");
        model.addAttribute("data",record);
        return new ModelAndView("other/notice/add");
    }
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public Result<Integer> insertNotice(Notice record){
        logger.info("新增公告record:{}",record);
        boolean success = Boolean.FALSE;
        Integer result = 0;
        try {
            result = noticeService.insertNotice(record);
            if (result != null && result > 0){
                success = Boolean.TRUE;
            }
        }catch (Exception e){
            logger.error("新增公告异常",e);
        }
        return new Result(success,"新增公告",result);
    }


    @RequestMapping("/edit/{id}")
    public ModelAndView edit(@PathVariable("id") Integer id,Model model){
        NoticeResp result = noticeService.getNoticeById(id);

        model.addAttribute("title","编辑");
        model.addAttribute("table","编辑公告");
        model.addAttribute("edit",Boolean.TRUE);
        model.addAttribute("data",result);
        return new ModelAndView("other/notice/edit");
    }
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Result<Integer> updateNoticeById(Notice record){
        logger.info("更新公告record:{}",record);
        boolean success = Boolean.FALSE;
        Integer result = 0;
        try {
            result = noticeService.updateNoticeById(record);
            if (result != null && result > 0){
                success = Boolean.TRUE;
            }
        }catch (Exception e){
            logger.error("更新公告异常",e);
        }
        return new Result(success,"更新公告",result);
    }

}
