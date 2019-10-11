package com.dream.city.service.trade;

import com.dream.city.base.model.Page;
import com.dream.city.base.model.entity.SalesOrder;
import com.github.pagehelper.PageInfo;


/**
 * @author wvv
 */
public interface SalesOrderService {


    PageInfo<SalesOrder> getSalesOrderList(Page page) ;

    SalesOrder getSalesOrderById(Integer id);
    
}
