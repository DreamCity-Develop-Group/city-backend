package com.dream.city.other.service;

import com.dream.city.other.entity.Dictionary;

import java.util.List;

public interface DictionaryService {


    Integer deleteDictionaryById(Integer id);

    Integer insertDictionary(Dictionary record);

    Dictionary getDictionaryById(Integer id);

    List<Dictionary> getDictionaryByKey(String key);

    List<Dictionary> getDictionaryByVal(String val);

    List<Dictionary> getDictionaryByName(String name);

    List<Dictionary> getDictionaryList(Dictionary record);

    Integer updateDictionaryById(Dictionary record);

}