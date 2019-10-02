package com.dream.city.other.service;

import com.dream.city.base.PageReq;
import com.dream.city.other.dto.NoticeResp;
import com.dream.city.other.entity.Notice;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 公告
 * @author Wvv
 */
@Repository
public interface NoticeService {

    List<Notice> getGameNotices(int state);

    PageInfo<NoticeResp> getNoticeList(PageReq<Notice> record);

    NoticeResp getNoticeById(Integer noticeId);

    Integer deleteNoticeById(Integer noticeId);


    Integer insertNotice(Notice record);


    Integer updateNoticeById(Notice record);

}
