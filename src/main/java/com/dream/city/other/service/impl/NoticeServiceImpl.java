package com.dream.city.other.service.impl;

import com.dream.city.other.dao.NoticeMapper;
import com.dream.city.other.entity.Notice;
import com.dream.city.other.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoticeServiceImpl implements NoticeService {

    @Autowired
    NoticeMapper noticeMapper;


    @Override
    public List<Notice> getGameNotices(int state) {
        return noticeMapper.getGameNotices(state);
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
