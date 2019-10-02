package com.dream.city.other.service.impl;

import com.alibaba.fastjson.JSON;
import com.dream.city.base.PageReq;
import com.dream.city.other.dao.NoticeMapper;
import com.dream.city.other.dto.MessageResp;
import com.dream.city.other.dto.NoticeResp;
import com.dream.city.other.entity.Notice;
import com.dream.city.other.service.NoticeService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author
 */
@Service
public class NoticeServiceImpl implements NoticeService {

    @Autowired
    NoticeMapper noticeMapper;


    @Override
    public List<Notice> getGameNotices(int state) {
        return noticeMapper.getGameNotices(state);
    }

    @Override
    public PageInfo<NoticeResp> getNoticeList(PageReq<Notice> record) {
        PageHelper.startPage(record.getPageNum(), record.getPageSize(),record.isCount());
        List<Notice> noticeList = noticeMapper.getNoticeList(record.getCondition());
        List<NoticeResp> noticeListResp = null;
        if (!CollectionUtils.isEmpty(noticeList)){
            noticeListResp = new ArrayList<>();
            NoticeResp noticeResp = null;
            for (Notice notice : noticeList){
                noticeResp = JSON.toJavaObject(JSON.parseObject(JSON.toJSONString(notice)), NoticeResp.class);
                noticeListResp.add(noticeResp);
            }
        }
        return new PageInfo<>(noticeListResp);
    }

    @Override
    public NoticeResp getNoticeById(Integer noticeId) {
        Notice notice = noticeMapper.selectByPrimaryKey(noticeId);
        NoticeResp noticeResp = JSON.toJavaObject(JSON.parseObject(JSON.toJSONString(notice)), NoticeResp.class);
        return noticeResp;
    }

    @Override
    public Integer deleteNoticeById(Integer noticeId) {
        return noticeMapper.deleteByPrimaryKey(noticeId);
    }

    @Override
    public Integer insertNotice(Notice record) {
        return noticeMapper.insertSelective(record);
    }

    @Override
    public Integer updateNoticeById(Notice record) {
        return noticeMapper.updateByPrimaryKeySelective(record);
    }
}
