package com.dream.city.other.dao;


import com.dream.city.other.entity.CityFile;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 文件
 */
@Mapper
public interface CityFileMapper {


    Integer deleteByPrimaryKey(Long id);

    Integer insertSelective(CityFile record);

    CityFile getFileById(Long id);

    List<CityFile> getFileList(CityFile record);

    Integer updateByPrimaryKeySelective(CityFile record);

}