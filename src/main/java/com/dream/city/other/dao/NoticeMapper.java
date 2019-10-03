package com.dream.city.other.dao;

import com.dream.city.other.entity.Notice;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 公告
 * @author Wvv
 */
@Mapper
public interface NoticeMapper {

    @ResultMap("NoticeBaseResultMap")
    @Select("select * from city_notice where 1=1 and notice_state=#{isValid}")
    List<Notice> getGameNotices(int state);

    List<Notice> getNoticeList(Notice record);

    Notice selectByPrimaryKey(Integer noticeId);

    Integer deleteByPrimaryKey(Integer noticeId);


    Integer insertSelective(Notice record);


    Integer updateByPrimaryKeySelective(Notice record);


}
