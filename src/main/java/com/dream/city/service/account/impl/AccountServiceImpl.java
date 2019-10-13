package com.dream.city.service.account.impl;

import com.dream.city.base.utils.ListUtils;
import com.dream.city.service.account.AccountService;
import com.dream.city.base.model.entity.PlayerAccount;
import com.dream.city.base.model.mapper.PlayerAccountMapper;
import com.dream.city.base.model.req.PlayerAccountReq;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
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
        return accountMapper.getPlayerAccount(record.getAccPlayerId());
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
    public List<PlayerAccount> getPlatformAccounts(PlayerAccountReq record) {
        if (record == null || record.getAccId() == null) {
            record = new PlayerAccountReq();
            String[] ids = platformAccIds.split(",");
            String idList = ListUtils.listToString(Arrays.asList(ids));
            record.setPlatformAccIds(idList);
        }
        return accountMapper.getPlatformAccounts(record);
    }
}
