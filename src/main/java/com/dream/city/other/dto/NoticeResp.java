package com.dream.city.other.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Wvv
 */
@Data
public class NoticeResp implements Serializable {

    private Integer noticeId;

    private String noticeContent;

    private Integer noticeState;

    private String sendTime;

}
