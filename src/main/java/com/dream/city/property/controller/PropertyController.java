package com.dream.city.property.controller;


import com.dream.city.property.entity.Property;
import com.dream.city.property.service.PropertyService;
import com.github.pagehelper.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/property")
public class PropertyController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private PropertyService propertyService;



    /**
     * 新增物业
     * @param record
     * @return
     */
    @RequestMapping("/insertInvest")
    public int insertInvest(@RequestBody Property record){
        logger.info("新增物业，：{}",record);
        return propertyService.insertInvest(record);
    }


    /**
     * 修改物业
     * @param record
     * @return
     */
    @RequestMapping("/updateInvest")
    public int updateInvest(@RequestBody Property record){
        logger.info("修改物业，：{}",record);
        return propertyService.updateInvest(record);
    }



    /**
     * 查询物业
     * @param record
     * @return
     */
    @RequestMapping("/getInvestByIdOrName")
    public Property getInvestByIdOrName(@RequestBody Property record){
        logger.info("查询物业，：{}",record);
        return propertyService.getInvestByIdOrName(record);
    }



    /**
     * 物业列表
     * @param pageReq
     * @return
     */
    @RequestMapping("/propertyList")
    public Page<Property> friendList(@RequestBody Page<Property> pageReq){
        logger.info("物业列表，：{}",pageReq);
        Page page = propertyService.getInvestLsit(pageReq);
        return page;
    }


}
