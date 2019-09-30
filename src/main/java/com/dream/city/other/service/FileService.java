package com.dream.city.other.service;


import com.dream.city.other.entity.CityFile;

import java.util.List;

public interface FileService {

    int deleteFileById(Long id);

    CityFile insertFile(CityFile record);

    CityFile getFileById(Long id);

    List<CityFile> getFileList(CityFile record);

    CityFile updateFileById(CityFile record);

}
