package com.dream.city.account.service.impl;

import com.dream.city.account.dao.PlayerAccountMapper;
import com.dream.city.account.entity.PlayerAccount;
import com.dream.city.account.service.AccountService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {

    @Value("${dreamcity.platform.account.accIds}")
    String platformAccIds;
    @Autowired
    PlayerAccountMapper accountMapper;



    @Override
    @Transactional
    public int createPlayerAccount(PlayerAccount record) {
        Integer integer = accountMapper.insertSelective(record);
        return integer ==null?0:integer;
    }

    @Override
    public PlayerAccount getPlayerAccount(PlayerAccount record) {
        if (record.getAccId() == null && StringUtils.isBlank(record.getAccPlayerId())){
            return null;
        }
        PlayerAccount accountReq = new PlayerAccount();
        accountReq.setAccId(record.getAccId());
        accountReq.setAccPlayerId(record.getAccPlayerId());
        return accountMapper.getPlayerAccount(accountReq);
    }

    @Override
    public List<PlayerAccount> getPlayerAccountList(PlayerAccount record) {
        return accountMapper.getPlayerAccountList(record);
    }

    @Override
    @Transactional
    public int updatePlayerAccount(PlayerAccount record) {
        Integer integer = accountMapper.updateByPlayerId(record);
        return integer ==null?0:integer;
    }


    @Override
    public List<PlayerAccount> getPlatformAccounts(PlayerAccount record) {
        if (record == null || (record != null && record.getAccId() != null)) {
            record.setPlatformAccIds(platformAccIds);
        }
        return accountMapper.getPlatformAccounts(record);
    }
}
