package com.dream.city.other.service;

import com.dream.city.other.entity.Notice;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 公告
 * @author Wvv
 */
@Repository
public interface NoticeService {

    List<Notice> getGameNotices(int state);

    Notice getNoticeById(Integer noticeId);

    Integer deleteNoticeById(Integer noticeId);


    Integer insertNotice(Notice record);


    Integer updateNoticeById(Notice record);

}
