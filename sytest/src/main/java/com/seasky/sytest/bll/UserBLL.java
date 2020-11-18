package com.seasky.sytest.bll;

import com.seasky.sytest.model.UserInfo;
import com.seasky.sytest.model.UserOut;

import java.util.List;

/**
 * desc
 *
 * @author Tomato
 * @date Created on 2020/11/10
 */
public interface UserBLL {
    UserInfo getUser(String userName);

    Boolean createUser(UserInfo userInfo);

    List<UserOut> queryUser(UserInfo userInfo);

    Boolean login(String userName, String passWord);
}
