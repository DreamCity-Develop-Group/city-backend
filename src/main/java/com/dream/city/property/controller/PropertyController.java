package com.dream.city.property.controller;


import com.dream.city.base.PageReq;
import com.dream.city.base.Result;
import com.dream.city.property.dto.PropertyReq;
import com.dream.city.property.dto.PropertyResp;
import com.dream.city.property.entity.Property;
import com.dream.city.property.service.PropertyService;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


/**
 * @author
 */
@RestController
@RequestMapping("/property")
public class PropertyController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private PropertyService propertyService;



    @RequestMapping("/add")
    public ModelAndView add(Property record,Model model){
        model.addAttribute("title","添加");
        model.addAttribute("table","添加物业");
        model.addAttribute("actionPath","property");
        model.addAttribute("data",record);
        return new ModelAndView("property/add");
    }
    /**
     * 新增物业
     * @param record
     * @return
     */
    @RequestMapping("/insert")
    public Result insert(Property record){
        logger.info("新增物业，：{}",record);
        boolean success = Boolean.FALSE;
        Integer result = 0;
        try {
            result = propertyService.insertInvest(record);
            if (result > 0){
                success = Boolean.TRUE;
            }
        }catch (Exception e){
            logger.error("新增物业异常",e);
        }
        return new Result(success,"新增物业",result);
    }


    /*@RequestMapping("/delete/{id}")
    public Result delete(@PathVariable("id") Integer id){
        logger.info("删除物业，：{}",id);
        boolean success = Boolean.FALSE;
        Integer result = 0;
        try {
            result = propertyService.deletePropertyById(id);
            if (result > 0){
                success = Boolean.TRUE;
            }
        }catch (Exception e){
            logger.error("删除物业异常",e);
        }
        return new Result(success,"删除物业",result);
    }*/

    /**
     * 修改物业
     * @param id
     * @return
     */
    @RequestMapping("/edit/{id}")
    public ModelAndView edit(@PathVariable("id") Integer id, Model model){
        Property recordReq = new Property();
        recordReq.setInId(id);
        PropertyResp result = propertyService.getInvestByIdOrName(recordReq);
        model.addAttribute("title","编辑");
        model.addAttribute("table","编辑物业");
        model.addAttribute("edit",Boolean.TRUE);
        model.addAttribute("actionPath","property");
        model.addAttribute("data",result);
        return new ModelAndView("property/edit");
    }
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Result<Integer> update(@RequestBody Property record){
        logger.info("修改物业，：{}",record);
        boolean success = Boolean.FALSE;
        Integer result = 0;
        try {
            result = propertyService.updateInvest(record);
            if (result != null && result > 0){
                success = Boolean.TRUE;
            }
        }catch (Exception e){
            logger.error("修改物业异常",e);
        }
        return new Result(success,"修改物业",result);
    }



    /**
     * 查询物业
     * @param id
     * @return
     */
    @RequestMapping("/get/{id}")
    public ModelAndView get(@PathVariable("id") Integer id,Model model){
        logger.info("查询物业：{}",id);
        PropertyResp result = null;
        try {
            Property record = new Property();
            record.setInId(id);
            result = propertyService.getInvestByIdOrName(record);
        }catch (Exception e){
            logger.error("查询物业异常",e);
        }

        model.addAttribute("title","详情");
        model.addAttribute("table","物业详情");
        model.addAttribute("edit",Boolean.FALSE);
        model.addAttribute("data",result);
        return new ModelAndView("property/edit");
    }



    /**
     * 物业列表
     * @param model
     * @return
     */
    @RequestMapping("/index")
    public ModelAndView index(Model model){
        model.addAttribute("title","物业");
        model.addAttribute("table","物业列表");
        model.addAttribute("actionPath","property");
        return new ModelAndView("property/index");
    }
    @RequestMapping("/getList")
    public Result<PropertyResp> getList(PageReq page, PropertyReq record){
        logger.info("物业列表，：{}",record);
        boolean success = Boolean.TRUE;
        PageInfo<PropertyResp> result = null;
        try{
            page.setCondition(record);
            result = propertyService.getInvestLsit(page);
        }catch (Exception e){
            success = Boolean.FALSE;
        }
        return new Result(success,"物业列表",result);
    }


}
