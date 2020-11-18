package com.seasky.sytest.dal;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.seasky.sytest.model.UserInfo;
import com.seasky.sytest.model.UserOut;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * desc
 *
 * @author Tomato
 * @date Created on 2020/11/10
 */
@Mapper
public interface UserDAL extends BaseMapper<UserInfo> {

    List<UserOut> selectUseToList(@Param("condition") UserInfo userInfo);

    // Integer insertUser(UserInfo userInfo);
    //
    // List<UserInfo> selectUseToList(UserInfo userInfo);
    //
    // UserInfo selectUserByName(String userName);
}
