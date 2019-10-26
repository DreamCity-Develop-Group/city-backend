package com.dream.city.controller;


import com.dream.city.base.BaseController;
import com.dream.city.base.Result;
import com.dream.city.base.model.Page;
import com.dream.city.base.model.entity.CityInvest;
import com.dream.city.base.model.req.CityInvestReq;
import com.dream.city.base.model.req.PropertyReq;
import com.dream.city.base.model.resp.PropertyResp;
import com.dream.city.base.utils.DateUtils;
import com.dream.city.service.property.PropertyService;
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
public class PropertyController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private PropertyService propertyService;



    @RequestMapping("/add")
    public ModelAndView add(CityInvest record,Model model){
        model.addAttribute("title","添加");
        model.addAttribute("table","添加物业");
        model.addAttribute("data",record);
        return new ModelAndView("property/add");
    }
    /**
     * 新增物业
     * @param record
     * @return
     */
    @RequestMapping("/insert")
    public Result insert(CityInvest record){
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


    @RequestMapping("/delete/{id}")
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
    }

    /**
     * 修改物业
     * @param id
     * @return
     */
    @RequestMapping("/edit/{id}")
    public ModelAndView edit(@PathVariable("id") Integer id, Model model){
        PropertyResp result = propertyService.getInvestByIdOrName(id,null);
        model.addAttribute("title","编辑");
        model.addAttribute("table","编辑物业");
        model.addAttribute("edit",Boolean.TRUE);
        model.addAttribute("data",result);
        return new ModelAndView("property/edit");
    }
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Result<Integer> update(CityInvestReq record){
        logger.info("修改物业，：{}",record);
        boolean success = Boolean.FALSE;
        Integer result = 0;
        try {
            CityInvest updateInvest = new CityInvest();
            updateInvest.setInId(record.getInId());
            updateInvest.setInType(record.getInType());
            updateInvest.setInEarning(record.getInEarning());
            updateInvest.setInEnd(DateUtils.str2Date(record.getInEnd()));
            updateInvest.setInEnterpriseTax(record.getInEnterpriseTax());
            updateInvest.setInLimit(record.getInLimit());
            updateInvest.setInName(record.getInName());
            updateInvest.setInPersonalTax(record.getInPersonalTax());
            updateInvest.setInQuotaTax(record.getInQuotaTax());
            updateInvest.setInStart(DateUtils.str2Date(record.getInStart()));
            updateInvest.setIsValid(record.getIsValid());
            result = propertyService.updateInvest(updateInvest);
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
            result = propertyService.getInvestByIdOrName(id,null);
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
        return new ModelAndView("property/index");
    }
    @RequestMapping("/getList")
    public Result<PageInfo<PropertyResp>> getList(Page page, PropertyReq record){
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
