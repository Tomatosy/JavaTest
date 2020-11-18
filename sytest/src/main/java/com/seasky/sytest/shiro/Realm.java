package com.seasky.sytest.shiro;

import com.seasky.sytest.bll.UserBLL;
import com.seasky.sytest.model.UserInfo;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

/**
 * desc
 *
 * @author Tomato
 * @date Created on 2020/11/17
 */
@Component
public class Realm extends AuthorizingRealm {
    @Autowired
    private UserBLL userBll;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        UserInfo user = (UserInfo) principalCollection.getPrimaryPrincipal();
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        //以下权限信息，实际场景中应从数据库获得
        Set<String> perms = new HashSet<>(0);
        perms.add("createRight");
        Set<String> roles = new HashSet<>(0);
        roles.add("queryRole");
        authorizationInfo.setStringPermissions(perms);
        authorizationInfo.setRoles(roles);
        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //1.获取到用户界面输入的用户名和密码
        UsernamePasswordToken upToken = (UsernamePasswordToken) authenticationToken;
        //2.获取用户出入的用户名和密码
        String username = (String) upToken.getPrincipal();
        String password = new String(upToken.getPassword());
        //3.根据用户名查询用户对象
        UserInfo user = userBll.getUser(username);
        if (user == null) {
            return null;
        }
        if (!user.getPassWord().equals(password)) {
            return null;
        }
        return new SimpleAuthenticationInfo(user, user.getPassWord(), this.getName());
    }
}
