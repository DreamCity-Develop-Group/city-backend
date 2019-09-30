package com.dream.city.other.controller;

import com.dream.city.other.entity.CityFile;
import com.dream.city.other.service.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/set/file")
public class FileController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private FileService fileService;



    @RequestMapping("/deleteFileById/{id}")
    public CityFile deleteFileById(@PathVariable("id") Long id){
        logger.info("删除文件，{}", id);
        CityFile file = null;
        try {
            file = fileService.getFileById(id);
        }catch (Exception e){
            logger.error("删除文件异常",e);
        }
        return file;
    }

    @RequestMapping("/insertFile")
    public CityFile insertFile(@RequestBody CityFile record){
        logger.info("新增文件，{}", record);
        CityFile file = null;
        try {
            file = fileService.insertFile(record);
        }catch (Exception e){
            logger.error("新增文件异常",e);
        }
        return file;
    }

    @RequestMapping("/getFileById/{id}")
    public CityFile getFileById(@PathVariable("id") Long id){
        logger.info("查询文件，{}", id);
        CityFile file = null;
        try {
            file = fileService.getFileById(id);
        }catch (Exception e){
            logger.error("查询文件异常",e);
        }
        return file;
    }

    @RequestMapping("/getFileList")
    public List<CityFile> getFileList(@RequestBody CityFile record){
        logger.info("查询文件列表，{}", record);
        List<CityFile> fileList = null;
        try {
            fileList = fileService.getFileList(record);
        }catch (Exception e){
            logger.error("查询文件列表异常",e);
        }
        return fileList;
    }

    @RequestMapping("/updateFileById")
    public CityFile updateFileById(@RequestBody CityFile record){
        logger.info("修改文件，{}", record);
        CityFile cityFile = null;
        try {
            cityFile = fileService.updateFileById(record);
        }catch (Exception e){
            logger.error("修改文件失败异常",e);
        }
        return cityFile;
    }



}
