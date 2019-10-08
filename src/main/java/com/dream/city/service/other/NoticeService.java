package com.dream.city.service.other;

import com.dream.city.base.model.Page;
import com.dream.city.base.model.entity.Notice;
import com.dream.city.base.model.resp.NoticeResp;
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

    PageInfo<NoticeResp> getNoticeList(Page record);

    NoticeResp getNoticeById(Integer noticeId);

    Integer deleteNoticeById(Integer noticeId);


    Integer insertNotice(Notice record);


    Integer updateNoticeById(Notice record);

}
