package com.dream.city.service.other.impl;

import com.dream.city.base.model.Page;
import com.dream.city.base.model.entity.Notice;
import com.dream.city.base.model.mapper.NoticeMapper;
import com.dream.city.base.model.req.NoticeReq;
import com.dream.city.base.model.resp.NoticeResp;
import com.dream.city.base.utils.DataUtils;
import com.dream.city.service.other.NoticeService;
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
    public PageInfo<NoticeResp> getNoticeList(Page record) {
        NoticeReq noticeReq = DataUtils.toJavaObject(record.getCondition(), NoticeReq.class);
        PageHelper.startPage(record.getPageNum(), record.getPageSize(),record.isCount());
        List<Notice> noticeList = noticeMapper.getNoticeList(noticeReq);
        List<NoticeResp> noticeListResp = new ArrayList<>();
        if (!CollectionUtils.isEmpty(noticeList)){
            noticeListResp = new ArrayList<>();
            NoticeResp noticeResp = null;
            for (Notice notice : noticeList){
                noticeResp = DataUtils.toJavaObject(notice,NoticeResp.class);
                noticeListResp.add(noticeResp);
            }
        }
        return new PageInfo<>(noticeListResp);
    }

    @Override
    public NoticeResp getNoticeById(Integer noticeId) {
        Notice notice = noticeMapper.selectByPrimaryKey(noticeId);
        NoticeResp noticeResp = DataUtils.toJavaObject(notice,NoticeResp.class);
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
