package com.dream.city.other.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class CityMessage implements Serializable {
    /**  */
    private Long id;

    /**  */
    private String playerId;

    /**  */
    private String friendId;

    private String content;

    private Integer haveRead;

    /**  */
    private Date createTime;

    /**  */
    private Date updateTime;


}