package com.dream.city.service.other.impl;

import com.dream.city.base.model.entity.Dictionary;
import com.dream.city.base.model.mapper.DictionaryMapper;
import com.dream.city.service.other.DictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author
 */
@Service
public class DictionaryServiceImpl implements DictionaryService {

    @Autowired
    DictionaryMapper dictionaryMapper;

    @Override
    public Integer deleteDictionaryById(Integer id) {
        return dictionaryMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Integer insertDictionary(Dictionary record) {
        return dictionaryMapper.insertSelective(record);
    }

    @Override
    public Dictionary getDictionaryById(Integer id) {
        return dictionaryMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Dictionary> getDictionaryByKey(String key) {
        return dictionaryMapper.getDictionaryByKey(key);
    }

    @Override
    public List<Dictionary> getDictionaryByVal(String val) {
        return null;
    }

    @Override
    public List<Dictionary> getDictionaryByName(String name) {
        return dictionaryMapper.getDictionaryByName(name);
    }

    @Override
    public List<Dictionary> getDictionaryList(Dictionary record) {
        return dictionaryMapper.getDictionaryList(record);
    }

    @Override
    public Integer updateDictionaryById(Dictionary record) {
        return dictionaryMapper.updateByPrimaryKeySelective(record);
    }
}
