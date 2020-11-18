package com.seasky.sytest.bll;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.seasky.sytest.dal.UserDAL;
import com.seasky.sytest.model.UserInfo;
import com.seasky.sytest.model.UserOut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserBLLImpl implements UserBLL {
    @Autowired
    private UserDAL userDAL;


    @Override
    public UserInfo getUser(String userName) {
        return userDAL.selectOne(new QueryWrapper<>(
                UserInfo.builder().userName(userName).build()));
    }

    @Override
    public Boolean createUser(UserInfo userInfo) {
        Integer rst = userDAL.insert(userInfo);
        return rst == 1;
    }

    @Override
    public List<UserOut> queryUser(UserInfo userInfo) {
        return userDAL.selectUseToList(userInfo);
    }

    @Override
    public Boolean login(String userName, String passWord) {
        // selectOne he insert 是mybatis自带的方法，如果查不出来数据或者查出多条数据会报错
        UserInfo userInfo = userDAL.selectOne(
                new QueryWrapper<>(
                        new UserInfo(null, userName, null)
                )
        );
        if (userInfo == null) {
            return false;
        }
        return userInfo.getPassWord().equals(passWord);
    }
}