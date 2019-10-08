package com.dream.city.service.invest;

import com.dream.city.base.model.Page;
import com.dream.city.base.model.entity.InvestOrder;
import com.dream.city.base.model.resp.InvestOrderResp;
import com.github.pagehelper.PageInfo;
import org.springframework.transaction.annotation.Transactional;

/**
 * 投资订单
 */
public interface OrderService {



    /**
     * 投资物业
     * @param record
     * @return
     */
    InvestOrder insertInvestOrder(InvestOrder record);

    /**
     * 投资订单作废
     * @param record
     * @return
     */
    int investOrderInvalid(InvestOrder record);

    /**
     * 投资订单取消
     * @param record
     * @return
     */
    int investOrderCancel(InvestOrder record);

    /**
     * 查询投资订单
     * @param orderId
     * @return
     */
    InvestOrderResp getInvestOrderById(Integer orderId);

    /**
     * 投资订单列表
     * @param record
     * @return
     */
    PageInfo<InvestOrderResp> getInvestOrderList(Page record);

    /**
     * 投资数量
     * @param record orderInvestId、orderPayerId、orderState
     * @return
     */
    int countOrdersByPayerIdPropertyId(InvestOrder record);

    /**
     * 审核修改状态
     * @param record
     * @return
     */
    @Transactional
    int updateOrderStateById(InvestOrder record);
}
