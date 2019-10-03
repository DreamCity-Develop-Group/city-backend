package com.dream.city.other.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Wvv
 */
@Data
public class NoticeReq implements Serializable {

    private Integer noticeId;

    private String noticeContent;

    private Integer noticeState;

    private String sendTime;

}
