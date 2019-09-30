package com.dream.city.other.entity;

import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author Wvv
 */
@Data
public class Notice  implements Serializable {
    private Integer noticeId;
    private String noticeContent;
    private Integer noticeState;
    private Timestamp createTime;

}
