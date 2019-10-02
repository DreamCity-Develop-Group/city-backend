package com.dream.city.other.service.impl;

import com.dream.city.base.PageReq;
import com.dream.city.other.dao.NoticeMapper;
import com.dream.city.other.entity.Notice;
import com.dream.city.other.service.NoticeService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public PageInfo<Notice> getNoticeList(PageReq<Notice> record) {
        PageHelper.startPage(record.getPageNum(), record.getPageSize());
        return new PageInfo<>(noticeMapper.getNoticeList(record.getCondition()));
    }

    @Override
    public Notice getNoticeById(Integer noticeId) {
        return noticeMapper.selectByPrimaryKey(noticeId);
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
