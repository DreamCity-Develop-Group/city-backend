package com.dream.city.service.common;

import com.dream.city.base.model.Page;
import com.dream.city.base.model.entity.Dictionary;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface DictionaryService {
    int insert(Dictionary record);

    int updateById(Dictionary record);

    int deleteById(Integer id);

    Dictionary getById(Integer id);



    String getKeyByVal(String val);

    String getValByKey(String key);

    Dictionary getOneByKey(String key);

    Dictionary getOneByVal(String val);

    List<Dictionary> getListByKey(String key);

    List<Dictionary> getListByVal(String val);

    List<Dictionary> getListByName(String name);


    PageInfo<Dictionary> getDictionaryList(Page pageReq, Dictionary record);
}
