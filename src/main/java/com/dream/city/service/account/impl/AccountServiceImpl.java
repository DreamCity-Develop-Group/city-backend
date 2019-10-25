package com.dream.city.service.account.impl;

import com.dream.city.base.model.Page;
import com.dream.city.base.model.mapper.AccountMapper;
import com.dream.city.base.model.resp.PlayerAccountResp;
import com.dream.city.base.service.DictionaryService;
import com.dream.city.service.account.AccountService;
import com.dream.city.base.model.entity.PlayerAccount;
import com.dream.city.base.model.req.PlayerAccountReq;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    AccountMapper accountMapper;
    @Autowired
    DictionaryService dictionaryService;


    @Override
    @Transactional
    public int createPlayerAccount(PlayerAccount record) {
        Integer integer = accountMapper.insertSelective(record);
        return integer ==null?0:integer;
    }

    @Override
    public PlayerAccountResp getPlayerAccount(PlayerAccountReq record) {
        String ids = dictionaryService.getValByKey("platform.account.accIds");
        record.setPlatformAccIds("\'"+ids+"\'");
        return accountMapper.getPlayerAccountSelective(record);
    }

    @Override
    public PageInfo<PlayerAccountResp> getPlayerAccountList(Page pageReq, PlayerAccountReq record) {
        String ids = dictionaryService.getValByKey("platform.account.accIds");
        record.setPlatformAccIds("\'"+ids+"\'");
        PageHelper.startPage(pageReq.getPageNum(),pageReq.getPageSize());
        List<PlayerAccountResp> playerAccountList = accountMapper.getPlayerAccountList(record);
        return new PageInfo<>(playerAccountList);
    }

    @Override
    @Transactional
    public int updatePlayerAccount(PlayerAccount record) {
        Integer integer = accountMapper.updateByPlayerId(record);
        return integer ==null?0:integer;
    }


    @Override
    public List<PlayerAccount> getPlatformAccounts(PlayerAccountReq record) {
        if (record == null || record.getAccId() == null) {
            record = new PlayerAccountReq();
            String ids = dictionaryService.getValByKey("platform.account.accIds");
            record.setPlatformAccIds("\'"+ids+"\'");
        }
        return accountMapper.getPlatformAccounts(record);
    }
}
