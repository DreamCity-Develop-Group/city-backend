package com.dream.city.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by Wvv on 2017/11/02.
 */
public class DataUtils {

    private static final Logger logger = LoggerFactory.getLogger(DataUtils.class);

    public static final String DATE_FORMAT_DEFAULT = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_FORMAT_NUMBER = "yyyyMMddHHmmss";
    public static final String DATE_FORMAT_DAY_PATTERN = "yyyy-MM-dd";
    public static final String YEAR_MONTH_DAY_EN_SECOND  = "yyyy/MM/dd HH:mm:ss";
    public static final String YEAR_MONTH_DAY_CN_SECOND = "yyyy年MM月dd日 HH时mm分ss秒";
    public static final String YEAR_MONTH_DAY_CN = "yyyy年MM月dd日";

    /**
     * 转化为List<T>
     * @param data
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> List<T> getDataArray(Object data, Class<T> clazz) {
        String jsonString = JSONObject.toJSONString(data);
        return JSONObject.parseArray(jsonString, clazz);
    }

    /**
     * 转化为T
     * @param data
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T getData(Object data, Class<T> clazz) {
        String jsonString = JSONObject.toJSONString(data);
        return JSONObject.parseObject(jsonString, clazz);
    }


    public static <T> T toJavaObject(Object data, Class<T> clazz) {
        String jsonString = JSON.toJSONStringWithDateFormat(data,DATE_FORMAT_DEFAULT);
        return JSON.toJavaObject(JSON.parseObject(jsonString),clazz);
    }
}
