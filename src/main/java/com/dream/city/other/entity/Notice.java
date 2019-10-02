package com.dream.city.other.entity;

import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

/**
 * @author Wvv
 */
@Data
public class Notice  implements Serializable {
    private Integer noticeId;
    private String noticeContent;
    private Integer noticeState;
    private Date sendTime;
    private Date createTime;

}
