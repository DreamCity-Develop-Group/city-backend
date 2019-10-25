package com.dream.city.service.account.impl;

import com.dream.city.base.model.entity.PlayerAccountLog;
import com.dream.city.base.model.mapper.AccountMapper;
import com.dream.city.base.model.mapper.PlayerAccountLogMapper;
import com.dream.city.base.model.resp.PlayerAccountResp;
import com.dream.city.base.service.DictionaryService;
import com.dream.city.base.utils.DataUtils;
import com.dream.city.base.utils.ListUtils;
import com.dream.city.service.account.AccountService;
import com.dream.city.base.model.entity.PlayerAccount;
import com.dream.city.base.model.req.PlayerAccountReq;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    AccountMapper accountMapper;
    @Autowired
    DictionaryService dictionaryService;

    @Autowired
    PlayerAccountLogMapper  playerAccountLogMapper;

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
    public List<PlayerAccountResp> getPlayerAccountList(PlayerAccountReq record) {
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
            String[] ids = dictionaryService.getValByKey("platform.account.accIds").split(",");
            String idList = ListUtils.listToString(Arrays.asList(ids));
            record.setPlatformAccIds(idList);
        }
        return accountMapper.getPlatformAccounts(record);
    }


    @Override
    public PlayerAccount selectByPrimaryKey(Integer accId){
        PlayerAccount  playerAccount = accountMapper.selectByPrimaryKey(accId);
        return playerAccount;
    }

    @Override
    public PlayerAccountResp getAccountByIdOrName(Integer inId, String inName) {
            if (inId == null){
                return null;
            }
        PlayerAccount property = accountMapper.selectByPrimaryPlayerId(inId);
            return DataUtils.toJavaObject(property,PlayerAccountResp.class);
    }

    public void updateByPrimaryKeySelective(PlayerAccount record){
        if (record.getAccId() != null ){
            accountMapper.updateByPrimaryKeySelective(record);
        }

    }

    @Override
    public  Integer  insertPlayAccountLog(PlayerAccountLog playerAccountLog){
       return playerAccountLogMapper.insert(playerAccountLog);
    }

}
