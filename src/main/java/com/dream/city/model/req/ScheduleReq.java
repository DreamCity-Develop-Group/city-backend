package com.dream.city.model.req;

import lombok.Data;

@Data
public class ScheduleReq {

    private String jobId;

    private String jobName;

    private String jobGroupName;

    private Class jobClass;

    private String jobTime;

    private String jobStatus;

    private String descr;

    private String editType;



}
